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

package com.davils.kiro.ui.audio.waveform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.click
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.swipeRight
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.audio.fixture.TAG
import com.davils.kiro.ui.audio.fixture.Themed
import com.davils.kiro.ui.audio.fixture.peaks
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val QUARTER: Float = 0.25f

@OptIn(ExperimentalTestApi::class)
class WaveformTest : FunSpec({
    test("seeks by a tap, a drag, the arrow keys and accessibility") {
        val seeks = mutableListOf<Duration>()
        runComposeUiTest {
            setContent {
                Themed {
                    Waveform(
                        peaks = peaks,
                        modifier = Modifier.testTag(TAG).width(400.dp),
                        progress = 0.5f,
                        onSeek = { at -> seeks += at },
                    )
                }
            }
            onNodeWithTag(TAG).performTouchInput { click(Offset(x = 100f, y = centerY)) }
            seeks.last() shouldBe 4.seconds
            onNodeWithTag(TAG).performTouchInput { swipeRight() }
            onNodeWithTag(TAG).requestFocus()
            onNodeWithTag(TAG).performKeyInput { pressKey(Key.DirectionRight) }
            seeks.last() shouldBe 13.seconds
            onNodeWithTag(TAG).performKeyInput { pressKey(Key.A) }
            onNodeWithTag(TAG).performSemanticsAction(SemanticsActions.SetProgress) { action -> action(QUARTER) }
            seeks.last() shouldBe 4.seconds
        }
        seeks.shouldNotBeEmpty()
    }

    test("draws every layout and look with the time under the pointer") {
        listOf(Theme.light(), Theme.dark()).forEach { theme ->
            runComposeUiTest {
                setContent {
                    Themed(theme = theme) {
                        val style = waveformStyle()
                        Column {
                            Waveform(peaks = peaks, modifier = Modifier.testTag(TAG), progress = 0.3f)
                            Waveform(
                                peaks = peaks,
                                style = style.copy(layout = WaveformLayout.Stacked, look = WaveformLook.Bars),
                            )
                            Waveform(peaks = peaks, style = style.copy(centerWidth = 0.dp, playheadWidth = 0.dp))
                        }
                    }
                }
                onNodeWithTag(TAG).performMouseInput { moveTo(center) }
                onRoot().captureToImage()
                onNodeWithTag(TAG).performMouseInput { exit() }
                onRoot().captureToImage()
            }
        }
    }

    test("shows a changed style") {
        runComposeUiTest {
            setContent {
                Themed {
                    Waveform(
                        peaks = peaks,
                        modifier = Modifier.testTag(TAG),
                        style = waveformStyle().copy(height = 77.dp),
                    )
                }
            }
            onNodeWithTag(TAG).assertHeightIsEqualTo(77.dp)
        }
    }
})
