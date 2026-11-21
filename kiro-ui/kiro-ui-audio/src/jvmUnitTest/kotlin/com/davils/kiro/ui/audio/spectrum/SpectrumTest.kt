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

package com.davils.kiro.ui.audio.spectrum

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.audio.fixture.TAG
import com.davils.kiro.ui.audio.fixture.Themed
import com.davils.kiro.ui.audio.fixture.spectrumOf
import com.davils.kiro.ui.audio.signal.AudioSpectrum
import com.davils.kiro.ui.audio.signal.Samples
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec

@OptIn(ExperimentalTestApi::class)
class SpectrumTest : FunSpec({
    test("draws every curve of the analyzer and a crosshair under the pointer") {
        runComposeUiTest {
            setContent {
                Themed(theme = Theme.dark()) {
                    Column {
                        SpectrumAnalyzer(spectrum = spectrumOf(), modifier = Modifier.testTag(TAG))
                        SpectrumAnalyzer(
                            spectrum = spectrumOf(withHolds = false),
                            style = spectrumAnalyzerStyle().copy(curve = SpectrumCurve.Linear),
                        )
                        SpectrumAnalyzer(
                            spectrum = spectrumOf(),
                            style = spectrumAnalyzerStyle().copy(curve = SpectrumCurve.Steps),
                        )
                        SpectrumAnalyzer(
                            spectrum = AudioSpectrum(magnitudes = Samples.Empty, range = spectrumOf().range),
                        )
                    }
                }
            }
            onNodeWithTag(TAG).performMouseInput { moveTo(center) }
            onRoot().captureToImage()
            onNodeWithTag(TAG).performMouseInput { moveTo(Offset(x = width - 2f, y = 4f)) }
            onRoot().captureToImage()
            onNodeWithTag(TAG).performMouseInput { exit() }
            onRoot().captureToImage()
        }
    }

    test("scrolls the history of the spectrogram") {
        val state = SpectrogramState(capacity = 4)
        runComposeUiTest {
            setContent { Themed { Spectrogram(state = state) } }
            onRoot().captureToImage()
            repeat(times = 3) { state.push(spectrumOf()) }
            onRoot().captureToImage()
            repeat(times = 6) { state.push(spectrumOf(bands = 12)) }
            onRoot().captureToImage()
            state.clear()
            state.push(spectrumOf())
            onRoot().captureToImage()
        }
    }

    test("shows a changed style") {
        runComposeUiTest {
            setContent {
                Themed {
                    Column {
                        SpectrumAnalyzer(
                            spectrum = spectrumOf(),
                            modifier = Modifier.testTag(TAG),
                            style = spectrumAnalyzerStyle().copy(height = 140.dp),
                        )
                        Spectrogram(
                            state = rememberSpectrogramState(),
                            modifier = Modifier.testTag("history"),
                            style = spectrogramStyle().copy(height = 70.dp),
                        )
                    }
                }
            }
            onNodeWithTag(TAG).assertHeightIsEqualTo(140.dp)
            onNodeWithTag("history").assertHeightIsEqualTo(70.dp)
        }
    }
})
