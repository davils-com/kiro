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

@file:OptIn(DavilsInternal::class)

package com.davils.kiro.ui.window

import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.action.TitleBarBadge
import com.davils.kiro.ui.window.controls.LinuxControls
import com.davils.kiro.ui.window.controls.MacOsControls
import com.davils.kiro.ui.window.controls.WindowsControls
import com.davils.kiro.ui.window.fixture.ValueSample
import com.davils.kiro.ui.window.frame.CaptionInsets
import com.davils.kiro.ui.window.host.WindowSpec
import com.davils.kiro.ui.window.screen.Screen
import com.davils.kiro.ui.window.screen.ScreenId
import com.davils.kiro.ui.window.screen.ScreenInsets
import com.davils.kiro.ui.window.titlebar.TitleBarStyles
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.types.shouldBeInstanceOf

private fun samples(): List<ValueSample> = styleSamples() + screenSamples() + windowSamples()

private fun styleSamples(): List<ValueSample> {
    val theme = Theme.light()
    val chrome = ChromeWindowStyles.Default.resolve(theme, DesktopPlatform.Linux)
    val bar = TitleBarStyles.Default.resolve(theme, DesktopPlatform.Linux)
    val windowsBar = TitleBarStyles.Default.resolve(theme, DesktopPlatform.Windows)
    val windows = windowsBar.controls.shouldBeInstanceOf<WindowsControls>()
    val linux = bar.controls.shouldBeInstanceOf<LinuxControls>()
    val mac = MacOsControls(inset = 76.dp)
    return listOf(
        ValueSample(
            name = "ChromeWindowStyle",
            value = chrome,
            copy = chrome.copy(),
            changed = chrome.copy(cornerRadius = 1.dp),
        ),
        ValueSample(name = "TitleBarStyle", value = bar, copy = bar.copy(), changed = bar.copy(height = 1.dp)),
        ValueSample(
            name = "WindowLabels",
            value = bar.labels,
            copy = bar.labels.copy(),
            changed = bar.labels.copy(close = "Exit"),
        ),
        ValueSample(
            name = "WindowsControls",
            value = windows,
            copy = windows.copy(),
            changed = windows.copy(buttonWidth = 1.dp),
        ),
        ValueSample(name = "LinuxControls", value = linux, copy = linux.copy(), changed = linux.copy(spacing = 1.dp)),
        ValueSample(name = "MacOsControls", value = mac, copy = mac.copy(), changed = mac.copy(inset = 1.dp)),
    )
}

private fun screenSamples(): List<ValueSample> {
    val insets = ScreenInsets(left = 0.dp, top = 1.dp, right = 2.dp, bottom = 3.dp)
    val caption = CaptionInsets(left = 0.dp, right = 10.dp)
    val screen = Screen(
        id = ScreenId("a"),
        bounds = DpRect(left = 0.dp, top = 0.dp, right = 1.dp, bottom = 1.dp),
        workArea = DpRect(left = 0.dp, top = 0.dp, right = 1.dp, bottom = 1.dp),
        isPrimary = true,
    )
    return listOf(
        ValueSample(name = "ScreenInsets", value = insets, copy = insets.copy(), changed = insets.copy(top = 9.dp)),
        ValueSample(
            name = "CaptionInsets",
            value = caption,
            copy = caption.copy(),
            changed = caption.copy(left = 9.dp),
        ),
        ValueSample(name = "Screen", value = screen, copy = screen.copy(), changed = screen.copy(isPrimary = false)),
    )
}

private fun windowSamples(): List<ValueSample> {
    val options = WindowOptions()
    val spec = WindowSpec(
        onCloseRequest = {},
        state = WindowState(),
        title = "a",
        options = options,
        isUndecorated = true,
        isTransparent = true,
        isResizable = true,
    )
    val count = TitleBarBadge.Count(3)
    return listOf(
        ValueSample(
            name = "WindowOptions",
            value = options,
            copy = options.copy(),
            changed = options.copy(isVisible = false),
        ),
        ValueSample(name = "WindowSpec", value = spec, copy = spec.copy(), changed = spec.copy(title = "b")),
        ValueSample(name = "Count", value = count, copy = count.copy(), changed = count.copy(value = 4)),
    )
}

class ValueTypesTest : FunSpec({
    context("every value type") {
        samples().forEach { sample ->
            test("${sample.name} equals its copy and differs from a copy with a changed value") {
                sample.copy shouldBe sample.value
                sample.copy.hashCode() shouldBe sample.value.hashCode()
                sample.changed shouldNotBe sample.value
                sample.value shouldNotBe Any()
            }

            test("${sample.name} describes itself by its name and values") {
                sample.value.toString() shouldStartWith sample.name
            }
        }
    }

    context("caption insets") {
        test("reserve nothing for a title bar Kiro draws") {
            CaptionInsets.None shouldBe CaptionInsets(left = 0.dp, right = 0.dp)
        }
    }
})
