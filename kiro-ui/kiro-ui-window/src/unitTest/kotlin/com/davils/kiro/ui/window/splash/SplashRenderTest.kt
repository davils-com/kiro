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

package com.davils.kiro.ui.window.splash

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runSkikoComposeUiTest
import androidx.compose.ui.unit.Density
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.fixture.Themed
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.io.File
import javax.imageio.ImageIO

private const val RENDER_DIRECTORY: String = "KIRO_RENDER_DIR"
private const val SCALE: Float = 2f
private const val MOMENT: Long = 2_600
private const val SPLASH: String = "splash"
private const val SHOWN_PROGRESS: Float = 0.45f

private val showcase: Splash = Splash(
    name = "Kiro Studio",
    version = "1.0.0",
    steps = listOf(
        SplashStep(id = "configuration", label = "Reading the configuration", status = SplashStepStatus.Done),
        SplashStep(id = "plugins", label = "Loading the plugins", status = SplashStepStatus.Done),
        SplashStep(id = "index", label = "Indexing the workspace", status = SplashStepStatus.Running),
        SplashStep(id = "windows", label = "Opening the windows", status = SplashStepStatus.Pending),
        SplashStep(id = "session", label = "Restoring the session", status = SplashStepStatus.Pending),
    ),
    subtitle = "The workbench of Davils",
    edition = "Enterprise",
    vendor = "Davils",
    copyright = "© 2026 Davils",
    progress = SHOWN_PROGRESS,
)

private val broken: Splash = showcase.copy(
    steps = showcase.steps.map { step -> step.copy(status = failedIfRunning(step.status)) },
    failure = "The index of the workspace is damaged.",
)

private fun failedIfRunning(status: SplashStepStatus): SplashStepStatus {
    if (status == SplashStepStatus.Running) return SplashStepStatus.Failed
    return status
}

@OptIn(ExperimentalTestApi::class)
private fun render(variant: RenderedVariant, theme: Theme, splash: Splash, file: String) {
    val size = variant.size(theme)
    val pixels = Size(width = size.width.value * SCALE, height = size.height.value * SCALE)
    runSkikoComposeUiTest(size = pixels, density = Density(density = SCALE)) {
        mainClock.autoAdvance = false
        setContent {
            Themed(theme = theme) {
                variant.content(splash, Modifier.size(width = size.width, height = size.height).testTag(SPLASH))
            }
        }
        mainClock.advanceTimeBy(MOMENT)
        val image = onNodeWithTag(SPLASH).captureToImage()
        (image.width > 0) shouldBe true
        val directory = System.getenv(RENDER_DIRECTORY) ?: return@runSkikoComposeUiTest
        val target = File(directory, "$file.png")
        target.parentFile.mkdirs()
        ImageIO.write(image.toAwtImage(), "png", target)
    }
}

class SplashRenderTest : FunSpec({
    context("renders") {
        RenderedVariant.all.forEach { variant ->
            test("renders the ${variant.name} splash in both themes while it runs and once it failed") {
                listOf("light" to Theme.light(), "dark" to Theme.dark()).forEach { (mode, theme) ->
                    render(variant = variant, theme = theme, splash = showcase, file = "${variant.name}-$mode")
                    render(variant = variant, theme = theme, splash = broken, file = "${variant.name}-$mode-failed")
                }
            }
        }
    }
})
