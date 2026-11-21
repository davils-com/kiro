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

package com.davils.kiro.ui.window

import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.frame.FrameStrategy
import com.davils.kiro.ui.window.frame.frameStrategyOf
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ChromeWindowTest : FunSpec({
    context("frame strategy") {
        test("keeps the frame of Windows and macOS where the runtime decorates windows") {
            frameStrategyOf(DesktopPlatform.Windows, FramePreference.Native, isDecorationSupported = true) shouldBe
                FrameStrategy.Decorated
            frameStrategyOf(DesktopPlatform.MacOs, FramePreference.Native, isDecorationSupported = true) shouldBe
                FrameStrategy.Decorated
        }

        test("reaches under the title bar of macOS without the decorations of the runtime") {
            frameStrategyOf(DesktopPlatform.MacOs, FramePreference.Native, isDecorationSupported = false) shouldBe
                FrameStrategy.FullContent
        }

        test("draws the frame itself on Linux, on other systems and without decorations on Windows") {
            frameStrategyOf(DesktopPlatform.Linux, FramePreference.Native, isDecorationSupported = true) shouldBe
                FrameStrategy.Emulated
            frameStrategyOf(DesktopPlatform.Other, FramePreference.Native, isDecorationSupported = true) shouldBe
                FrameStrategy.Emulated
            frameStrategyOf(DesktopPlatform.Windows, FramePreference.Native, isDecorationSupported = false) shouldBe
                FrameStrategy.Emulated
        }

        test("draws the frame itself everywhere when the style asks for it") {
            DesktopPlatform.entries.forEach { platform ->
                frameStrategyOf(platform, FramePreference.Emulated, isDecorationSupported = true) shouldBe
                    FrameStrategy.Emulated
            }
        }
    }

    context("transparency") {
        test("is transparent only while Kiro draws a rounded frame") {
            val style = defaultChromeWindowStyle(Theme.light(), DesktopPlatform.Linux)

            isTransparent(FrameStrategy.Emulated, style) shouldBe true
            isTransparent(FrameStrategy.Emulated, style.copy(cornerRadius = 0.dp)) shouldBe false
            isTransparent(FrameStrategy.Decorated, style) shouldBe false
            isTransparent(FrameStrategy.FullContent, style) shouldBe false
        }
    }

    context("default style") {
        test("rounds the corners per system and keeps the native frame") {
            val theme = Theme.light()

            defaultChromeWindowStyle(theme, DesktopPlatform.MacOs).cornerRadius shouldBe 0.dp
            defaultChromeWindowStyle(theme, DesktopPlatform.Windows).cornerRadius shouldBe 8.dp
            defaultChromeWindowStyle(theme, DesktopPlatform.Linux).cornerRadius shouldBe 12.dp
            defaultChromeWindowStyle(theme, DesktopPlatform.Other).cornerRadius shouldBe 12.dp
            defaultChromeWindowStyle(theme, DesktopPlatform.Linux).frame shouldBe FramePreference.Native
        }
    }
})
