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

package com.davils.kiro.ui.audio.controls

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.audio.AudioAction
import com.davils.kiro.ui.audio.AudioControlsStyles
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.audio.RepeatMode
import com.davils.kiro.ui.audio.fixture.FIRST
import com.davils.kiro.ui.audio.fixture.Themed
import com.davils.kiro.ui.audio.fixture.coverOf
import com.davils.kiro.ui.audio.fixture.tracks
import com.davils.kiro.ui.audio.palette.coverPaletteOf
import com.davils.kiro.ui.audio.palette.fallbackPaletteOf
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.seconds

private const val HALF: Float = 0.5f
private const val DRAG: Float = 60f
private const val QUIET: Float = 0.2f
private const val MEDIUM: Float = 0.5f
private val orchid = Color(0xFFC05AD0)
private val lemon = Color(0xFFFDE047)
private val style = AudioControlsStyles.Default.resolve(Theme.light())

@OptIn(ExperimentalTestApi::class)
class ControlsTest : FunSpec({
    context("transport") {
        test("reports every button as an action") {
            val actions = mutableListOf<AudioAction>()
            val state = AudioPlayerState(queue = tracks)

            runComposeUiTest {
                setContent {
                    Themed {
                        TransportControls(
                            state = state,
                            onAction = { action ->
                                actions += action
                                state.apply(action)
                            },
                        )
                    }
                }
                listOf("Shuffle", "Previous", "Play", "Pause", "Next", "Repeat").forEach { label ->
                    onNodeWithContentDescription(label).performClick()
                    waitForIdle()
                }
            }

            actions shouldBe listOf(
                AudioAction.Shuffle,
                AudioAction.Previous,
                AudioAction.Play,
                AudioAction.Pause,
                AudioAction.Next,
                AudioAction.Repeat,
            )
            state.repeat shouldBe RepeatMode.All
        }

        test("shows the labels of a changed style and leaves shuffle and repeat out when compact") {
            val state = AudioPlayerState(queue = tracks)
            val changed = style.copy(labels = style.labels.copy(play = "Start"))

            runComposeUiTest {
                setContent {
                    Themed { TransportControls(state = state, onAction = {}, isCompact = true, style = changed) }
                }
                onNodeWithContentDescription("Start").assertExists()
                onNodeWithContentDescription("Shuffle").assertDoesNotExist()
            }
        }
    }

    context("scrubber") {
        test("seeks by drag, tap, keys and accessibility actions and previews the time") {
            val actions = mutableListOf<AudioAction>()
            val state = AudioPlayerState(queue = tracks)
            state.apply(AudioAction.Seek(20.seconds))

            runComposeUiTest {
                setContent {
                    Themed {
                        Column(modifier = Modifier.width(300.dp)) {
                            AudioScrubber(
                                state = state,
                                onAction = { action -> actions += action },
                                modifier = Modifier.width(300.dp),
                            )
                        }
                    }
                }
                val scrubber = onNodeWithContentDescription("Seek")
                scrubber.performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = DRAG, y = 0f))
                    release()
                }
                scrubber.performMouseInput {
                    moveTo(center)
                    press()
                    release()
                }
                scrubber.requestFocus()
                scrubber.performKeyInput { pressKey(Key.DirectionRight) }
                scrubber.performSemanticsAction(SemanticsActions.SetProgress) { set -> set(HALF) }
                scrubber.performMouseInput { moveTo(centerLeft) }
                scrubber.performMouseInput { exit() }
                waitForIdle()
            }

            actions shouldContain AudioAction.Seek(25.seconds)
            actions shouldContain AudioAction.Seek(90.seconds)
        }
    }

    context("volume") {
        test("mutes from its icon, follows the slider and picks an icon per level") {
            val actions = mutableListOf<AudioAction>()
            val state = AudioPlayerState(queue = tracks)

            runComposeUiTest {
                setContent { Themed { VolumeControl(state = state, onAction = { action -> actions += action }) } }
                onNodeWithContentDescription("Mute").performClick()
                waitForIdle()
            }

            actions shouldBe listOf(AudioAction.Mute)
            iconOf(level = 0f, style = style) shouldBe style.icons.muted
            iconOf(level = QUIET, style = style) shouldBe style.icons.quiet
            iconOf(level = MEDIUM, style = style) shouldBe style.icons.medium
            iconOf(level = 1f, style = style) shouldBe style.icons.loud
        }
    }

    context("backdrop") {
        test("puts light content on a bright cover that the backdrop darkens") {
            val palette = coverPaletteOf(cover = coverOf(orchid), style = style)

            palette.onDominant shouldBe style.darkContent
            contentOf(palette = palette, hasCover = true, style = style) shouldBe style.lightContent
        }

        test("keeps dark content on a cover that stays light behind the scrim") {
            val palette = coverPaletteOf(cover = coverOf(lemon), style = style)

            contentOf(palette = palette, hasCover = true, style = style) shouldBe style.darkContent
        }

        test("uses the theme content without a cover") {
            val palette = fallbackPaletteOf(Theme.dark().colors)

            contentOf(palette = palette, hasCover = false, style = style) shouldBe palette.onDominant
        }
    }

    context("cover") {
        test("shows the cover, a placeholder and animates in motion") {
            val state = AudioPlayerState(queue = tracks)

            runComposeUiTest {
                setContent {
                    Themed(theme = Theme.dark()) {
                        Column {
                            AudioCover(track = state.track, isPlaying = true, modifier = Modifier.size(40.dp))
                            AudioCover(track = tracks[1], isPlaying = false, modifier = Modifier.size(40.dp))
                            AudioVisualizer(isPlaying = true)
                            PlayPauseButton(isPlaying = false, onToggle = {})
                            CoverBackdrop(track = state.track, modifier = Modifier.size(80.dp)) {}
                            CoverBackdrop(track = null, modifier = Modifier.size(80.dp), isBreathing = false) {}
                            TrackInfo(state = state, isCentered = true)
                        }
                    }
                }
                onNodeWithText(FIRST).assertExists()
                state.apply(AudioAction.Next)
                waitForIdle()
            }
        }
    }
})
