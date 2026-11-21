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

package com.davils.kiro.ui.audio.ambient

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.audio.fixture.TAG
import com.davils.kiro.ui.audio.fixture.Themed
import com.davils.kiro.ui.audio.fixture.spectrumOf
import com.davils.kiro.ui.audio.signal.AudioSpectrum
import com.davils.kiro.ui.audio.signal.Samples
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec

private const val CENTER: String = "center"

@OptIn(ExperimentalTestApi::class)
class AmbientTest : FunSpec({
    val empty = AudioSpectrum(magnitudes = Samples.Empty, range = spectrumOf().range)

    test("draws the bars in every look") {
        listOf(Theme.light(), Theme.dark()).forEach { theme ->
            runComposeUiTest {
                setContent {
                    Themed(theme = theme) {
                        val bars = spectrumBarsStyle()
                        Column {
                            SpectrumBars(spectrum = spectrumOf())
                            SpectrumBars(
                                spectrum = spectrumOf(),
                                style = bars.copy(look = BarLook.Segmented, reflection = 0f),
                            )
                            SpectrumBars(
                                spectrum = spectrumOf(withHolds = false),
                                style = bars.copy(baseline = Baseline.Center),
                            )
                            SpectrumBars(
                                spectrum = spectrumOf(),
                                style = bars.copy(colors = bars.colors.take(1), capHeight = 0.dp),
                            )
                            SpectrumBars(spectrum = empty)
                        }
                    }
                }
                onRoot().captureToImage()
            }
        }
    }

    test("draws the radial spectrum around its content and the waves") {
        runComposeUiTest {
            setContent {
                Themed {
                    val radial = radialSpectrumStyle()
                    Column {
                        RadialSpectrum(spectrum = spectrumOf()) { Box(modifier = Modifier.testTag(CENTER)) }
                        RadialSpectrum(
                            spectrum = spectrumOf(withHolds = false),
                            style = radial.copy(symmetry = RadialSymmetry.Full),
                        )
                        RadialSpectrum(spectrum = empty)
                        SpectrumWave(spectrum = spectrumOf())
                        SpectrumWave(
                            spectrum = spectrumOf(),
                            style = spectrumWaveStyle().copy(baseline = Baseline.Center),
                        )
                        SpectrumWave(spectrum = empty)
                    }
                }
            }
            onNodeWithTag(CENTER).assertExists()
            onRoot().captureToImage()
        }
    }

    test("shows a changed style") {
        runComposeUiTest {
            setContent {
                Themed {
                    Column {
                        SpectrumBars(
                            spectrum = spectrumOf(),
                            modifier = Modifier.testTag(TAG),
                            style = spectrumBarsStyle().copy(height = 80.dp),
                        )
                        RadialSpectrum(
                            spectrum = spectrumOf(),
                            modifier = Modifier.testTag("radial"),
                            style = radialSpectrumStyle().copy(size = 120.dp),
                        )
                        SpectrumWave(
                            spectrum = spectrumOf(),
                            modifier = Modifier.testTag("wave"),
                            style = spectrumWaveStyle().copy(height = 60.dp),
                        )
                    }
                }
            }
            onNodeWithTag(TAG).assertHeightIsEqualTo(80.dp)
            onNodeWithTag("radial").assertWidthIsEqualTo(120.dp)
            onNodeWithTag("wave").assertHeightIsEqualTo(60.dp)
        }
    }
})
