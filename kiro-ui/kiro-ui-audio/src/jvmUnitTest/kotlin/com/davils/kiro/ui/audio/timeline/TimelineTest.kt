/*
 * Copyright 2026 Davils
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.davils.kiro.ui.audio.timeline

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.ScrollWheel
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.click
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.audio.fixture.TAG
import com.davils.kiro.ui.audio.fixture.Themed
import com.davils.kiro.ui.audio.fixture.clipOf
import com.davils.kiro.ui.audio.fixture.lanesOf
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.time.Duration

private const val ZOOM: Float = 100f
private const val DRAG: Float = 120f

@OptIn(ExperimentalTestApi::class)
private fun dragOn(clip: Clip, from: (header: Float) -> Offset, by: Offset): List<TimelineAction> {
    val actions = mutableListOf<TimelineAction>()
    runComposeUiTest {
        setContent {
            Themed {
                Box(modifier = Modifier.height(120.dp)) {
                    AudioClip(
                        clip = clip,
                        zoom = ZOOM,
                        onAction = { action -> actions += action },
                        modifier = Modifier.testTag(TAG),
                    )
                }
            }
        }
        val header = with(density) { audioClipStyleOf().headerHeight.toPx() }
        onNodeWithTag(TAG).performTouchInput {
            val start = from(header)
            down(start)
            moveBy(by / 2f)
            moveBy(by / 2f)
            up()
        }
    }
    return actions
}

private fun audioClipStyleOf(): AudioClipStyle = AudioClipStyles.Default.resolve(Theme.light())

@OptIn(ExperimentalTestApi::class)
class TimelineTest : FunSpec({
    val clip = clipOf()
    val right = Offset(x = DRAG, y = 0f)

    test("moves, trims and fades a clip by a drag") {
        val moved = dragOn(clip = clip, from = { Offset(x = 200f, y = 110f) }, by = right).last()
        (moved.shouldBeInstanceOf<TimelineAction.Move>().start > clip.start) shouldBe true
        val started = dragOn(clip = clip, from = { Offset(x = 2f, y = 100f) }, by = right).last()
        started.shouldBeInstanceOf<TimelineAction.Trim>().edge shouldBe ClipEdge.Start
        val ended = dragOn(clip = clip, from = { Offset(x = 398f, y = 100f) }, by = right).last()
        (ended.shouldBeInstanceOf<TimelineAction.Trim>().at > clip.end) shouldBe true
        val faded = dragOn(clip = clip, from = { header -> Offset(x = 100f, y = header) }, by = right).last()
        (faded.shouldBeInstanceOf<TimelineAction.Fade>().length > clip.fadeIn) shouldBe true
    }

    test("changes the gain of a clip by a drag on its line") {
        val down = Offset(x = 0f, y = 60f)
        val actions = dragOn(
            clip = clip,
            from = { header -> Offset(x = 200f, y = header + (120f - header) / 4f) },
            by = down,
        )
        val gain = actions.last().shouldBeInstanceOf<TimelineAction.Gain>()
        (gain.gain.value < 0f) shouldBe true
    }

    test("only selects a locked clip") {
        dragOn(clip = clipOf(isLocked = true), from = { Offset(x = 200f, y = 110f) }, by = right).shouldContainOnly(
            TimelineAction.Select(ClipId("clip")),
        )
    }

    test("selects a clip by a tap and shows its hover, badges and style") {
        val actions = mutableListOf<TimelineAction>()
        val muted = clipOf(id = "muted").copy(isMuted = true, isLocked = true, gain = Decibels(-6f))
        val style = audioClipStyleOf().copy(headerHeight = 32.dp)
        runComposeUiTest {
            setContent {
                Themed {
                    Box(modifier = Modifier.height(120.dp)) {
                        AudioClip(
                            clip = muted,
                            zoom = ZOOM,
                            onAction = { action -> actions += action },
                            modifier = Modifier.testTag(TAG),
                            isSelected = true,
                            style = style,
                        )
                    }
                }
            }
            onNodeWithContentDescription("Muted").assertExists()
            onNodeWithContentDescription("Locked").assertExists()
            onNodeWithTag(TAG).performTouchInput { click(Offset(x = 200f, y = 100f)) }
            listOf(Offset(x = 2f, y = 80f), Offset(x = 100f, y = 32f), Offset(x = 200f, y = 100f)).forEach { point ->
                onNodeWithTag(TAG).performMouseInput { moveTo(point) }
                onRoot().captureToImage()
            }
            onNodeWithTag(TAG).performMouseInput { exit() }
            val name = onNodeWithText("muted").fetchSemanticsNode().boundsInRoot
            name.center.y shouldBe (with(density) { 16.dp.toPx() } plusOrMinus 2f)
        }
        actions shouldContain TimelineAction.Select(muted.id)
    }

    test("replaces the badges of a clip through their slot") {
        runComposeUiTest {
            setContent {
                Themed {
                    Box(modifier = Modifier.height(80.dp)) {
                        AudioClip(
                            clip = clip,
                            zoom = ZOOM,
                            onAction = {},
                            badges = { Box(modifier = Modifier.testTag("badge")) },
                        )
                    }
                }
            }
            onNodeWithTag("badge").assertExists()
        }
    }

    test("switches a track and clears the selection on its empty lane") {
        val actions = mutableListOf<TimelineAction>()
        val state = TimelineState(lanes = lanesOf(), zoom = ZOOM)
        runComposeUiTest {
            setContent {
                Themed {
                    ClipTrack(
                        lane = state.lanes.first(),
                        state = state,
                        onAction = { action -> actions += action },
                        modifier = Modifier.testTag(TAG).width(1_400.dp),
                        style = clipTrackStyle().copy(height = 90.dp),
                    )
                }
            }
            onNodeWithContentDescription("Mute track").performClick()
            onNodeWithContentDescription("Solo track").performClick()
            onNodeWithContentDescription("Arm track for recording").performClick()
            onNodeWithTag(TAG).performTouchInput { click(Offset(x = 250.dp.toPx(), y = centerY)) }
            onNodeWithTag(TAG).assertHeightIsEqualTo(90.dp)
        }
        val lane = state.lanes.first().id
        actions shouldContain TimelineAction.Mute(lane)
        actions shouldContain TimelineAction.Solo(lane)
        actions shouldContain TimelineAction.Arm(lane)
        actions shouldContain TimelineAction.Select(null)
    }

    test("replaces the switches of a track through their slot") {
        val state = TimelineState(lanes = lanesOf())
        runComposeUiTest {
            setContent {
                Themed {
                    ClipTrack(
                        lane = state.lanes.first(),
                        state = state,
                        onAction = {},
                        controls = { Box(Modifier.testTag("switch")) },
                    )
                }
            }
            onNodeWithTag("switch").assertExists()
        }
    }

    test("seeks on the ruler, scrolls by the wheel and zooms with the control key") {
        val state = TimelineState(lanes = lanesOf(), zoom = ZOOM)
        listOf(Theme.light(), Theme.dark()).forEach { theme ->
            runComposeUiTest {
                setContent {
                    Themed(theme = theme) {
                        ClipTimeline(
                            state = state,
                            onAction = state::apply,
                            modifier = Modifier.testTag(TAG).width(900.dp),
                        )
                    }
                }
                val expected = timeAt(state = state, x = 300f, density = density.density)
                onNodeWithContentDescription("Time ruler").performTouchInput { click(Offset(x = 300f, y = centerY)) }
                state.playhead shouldBe expected
                onNodeWithTag(TAG).performMouseInput {
                    moveTo(center)
                    scroll(delta = 2f, scrollWheel = ScrollWheel.Vertical)
                }
                onNodeWithTag(TAG).performKeyInput { keyDown(Key.CtrlLeft) }
                onNodeWithTag(TAG).performMouseInput { scroll(delta = -1f) }
                onNodeWithTag(TAG).performKeyInput { keyUp(Key.CtrlLeft) }
                onRoot().captureToImage()
            }
        }
        (state.scroll > Duration.ZERO) shouldBe true
        (state.zoom > ZOOM) shouldBe true
    }

    test("shows a changed style of the ruler") {
        val state = TimelineState(lanes = lanesOf())
        runComposeUiTest {
            setContent {
                Themed {
                    TimeRuler(
                        state = state,
                        onAction = {},
                        modifier = Modifier.testTag(TAG),
                        style = timeRulerStyle().copy(height = 44.dp, intervals = emptyList()),
                    )
                }
            }
            onNodeWithTag(TAG).assertHeightIsEqualTo(44.dp)
        }
    }
})
