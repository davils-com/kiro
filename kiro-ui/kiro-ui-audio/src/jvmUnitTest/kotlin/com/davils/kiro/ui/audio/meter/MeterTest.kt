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

package com.davils.kiro.ui.audio.meter

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.audio.fixture.TAG
import com.davils.kiro.ui.audio.fixture.Themed
import com.davils.kiro.ui.audio.fixture.reducedMotion
import com.davils.kiro.ui.audio.fixture.stereo
import com.davils.kiro.ui.audio.fixture.surround
import com.davils.kiro.ui.audio.signal.AudioLevels
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val METER: String = "Level meter"
private const val METERS: Int = 4

@OptIn(ExperimentalTestApi::class)
class MeterTest : FunSpec({
    test("draws levels upright, lying and segmented in both themes") {
        listOf(reducedMotion, Theme.dark()).forEach { theme ->
            runComposeUiTest {
                setContent {
                    Themed(theme = theme) {
                        val segmented = levelMeterStyle().copy(mode = MeterMode.Segmented)
                        Column {
                            LevelMeter(levels = stereo)
                            LevelMeter(levels = surround, orientation = Orientation.Horizontal, style = segmented)
                            LevelMeter(levels = stereo, style = segmented)
                            LevelMeter(
                                levels = AudioLevels(channels = emptyList()),
                                orientation = Orientation.Horizontal,
                            )
                            CorrelationMeter(correlation = -0.6f)
                            CorrelationMeter(correlation = Float.NaN)
                        }
                    }
                }
                onAllNodesWithContentDescription(METER).assertCountEquals(expectedSize = METERS)
                onRoot().captureToImage()
            }
        }
    }

    test("announces the peak of each channel and the correlation") {
        runComposeUiTest {
            setContent {
                Themed {
                    Column {
                        LevelMeter(levels = stereo)
                        CorrelationMeter(correlation = 0.72f)
                    }
                }
            }
            onNodeWithContentDescription(METER)
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "L -3.0, R -9.0"))
            onNodeWithContentDescription("Phase correlation")
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "+0.7"))
        }
    }

    test("asks to reset the clip indicators by a tap and by accessibility") {
        var resets = 0
        runComposeUiTest {
            setContent { Themed { LevelMeter(levels = stereo, onResetClip = { resets += 1 }) } }
            onNodeWithContentDescription(METER).performClick()
            onNodeWithContentDescription(METER).performSemanticsAction(SemanticsActions.OnClick)
        }
        resets shouldBe 2
    }

    test("follows new levels with motion") {
        runComposeUiTest {
            var levels by mutableStateOf(stereo)
            setContent {
                Themed(theme = Theme.light()) {
                    LevelMeter(levels = levels)
                    CorrelationMeter(correlation = levels.channels.size / 3f)
                }
            }
            levels = surround
            mainClock.advanceTimeBy(milliseconds = 50L)
            onRoot().captureToImage()
        }
    }

    test("shows a changed style") {
        runComposeUiTest {
            setContent {
                Themed {
                    Column {
                        LevelMeter(
                            levels = stereo,
                            modifier = Modifier.testTag(TAG),
                            style = levelMeterStyle().copy(length = 300.dp),
                        )
                        LevelMeter(
                            levels = stereo,
                            modifier = Modifier.testTag("lying"),
                            orientation = Orientation.Horizontal,
                            style = levelMeterStyle().copy(length = 280.dp),
                        )
                        CorrelationMeter(
                            correlation = 0f,
                            modifier = Modifier.testTag("correlation"),
                            style = correlationMeterStyle().copy(length = 260.dp),
                        )
                    }
                }
            }
            onNodeWithTag(TAG).assertHeightIsEqualTo(300.dp)
            onNodeWithTag("lying").assertWidthIsEqualTo(280.dp)
            onNodeWithTag("correlation").assertWidthIsEqualTo(260.dp)
        }
    }
})
