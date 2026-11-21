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

@file:OptIn(ExperimentalTestApi::class)

package com.davils.kiro.ui.motion.diagnostics

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.fixture.TAG
import com.davils.kiro.ui.motion.fixture.Themed
import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import kotlin.time.Duration.Companion.milliseconds

private const val RATE: Float = 60f

private val diagnostics: ArtDiagnostics = ArtDiagnostics(
    framesPerSecond = RATE,
    frameTime = 16.milliseconds,
    layers = 2,
    quality = QualityLevel.High,
)

private fun overlayHeight(style: (DiagnosticsOverlayStyle) -> DiagnosticsOverlayStyle): Dp {
    var height = 0.dp
    runComposeUiTest {
        setContent {
            Themed {
                DiagnosticsOverlay(
                    diagnostics = diagnostics,
                    modifier = Modifier.testTag(TAG),
                    style = style(diagnosticsOverlayStyle()),
                )
            }
        }
        val bounds = onNodeWithTag(TAG).getUnclippedBoundsInRoot()
        height = bounds.bottom - bounds.top
    }
    return height
}

class DiagnosticsOverlayTest : FunSpec({
    test("shows the diagnostics in one line") {
        runComposeUiTest {
            setContent { Themed { DiagnosticsOverlay(diagnostics = diagnostics) } }
            onNodeWithText("60.0 fps · 16.0 ms · 2 layers · High").assertExists()
        }
    }

    test("takes its text from a parameter") {
        runComposeUiTest {
            setContent {
                Themed { DiagnosticsOverlay(diagnostics = diagnostics, text = { shown -> "${shown.layers} Ebenen" }) }
            }
            onNodeWithText("2 Ebenen").assertExists()
        }
    }

    test("shows new diagnostics and keeps unchanged ones") {
        var shown by mutableStateOf(diagnostics)
        var unrelated by mutableIntStateOf(0)

        runComposeUiTest {
            setContent {
                Themed {
                    BasicText(text = "$unrelated")
                    DiagnosticsOverlay(diagnostics = shown)
                }
            }
            unrelated = 1
            waitForIdle()
            shown = diagnostics.copy(layers = 1)
            waitForIdle()
            onNodeWithText("60.0 fps · 16.0 ms · 1 layers · High").assertExists()
        }
    }

    test("shows a changed style") {
        val plain = overlayHeight { style -> style }
        val padded = overlayHeight { style -> style.copy(verticalPadding = 20.dp) }

        padded shouldBeGreaterThan plain
    }

    test("derives its style from the theme") {
        val light = defaultDiagnosticsOverlayStyle(Theme.light())

        light shouldNotBe defaultDiagnosticsOverlayStyle(Theme.dark())
        light.copy() shouldBe light
        light.copy().hashCode() shouldBe light.hashCode()
        light.equals(diagnostics) shouldBe false
        light.toString() shouldStartWith "DiagnosticsOverlayStyle("
    }
})
