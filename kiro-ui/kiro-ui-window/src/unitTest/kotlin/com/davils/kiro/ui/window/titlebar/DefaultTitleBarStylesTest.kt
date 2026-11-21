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

package com.davils.kiro.ui.window.titlebar

import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.state.InteractionState
import com.davils.kiro.ui.core.state.resolve
import com.davils.kiro.ui.window.controls.ControlPlacement
import com.davils.kiro.ui.window.controls.LinuxControls
import com.davils.kiro.ui.window.controls.MacOsControls
import com.davils.kiro.ui.window.controls.WindowButton
import com.davils.kiro.ui.window.controls.WindowsControls
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

private const val SMALL_COUNT: Int = 5
private const val LARGE_COUNT: Int = 150

class DefaultTitleBarStylesTest : FunSpec({
    context("platforms") {
        test("matches the height and the traffic lights of macOS") {
            val style = defaultTitleBarStyle(Theme.light(), DesktopPlatform.MacOs)

            style.height shouldBe 28.dp
            style.controls shouldBe MacOsControls(inset = 76.dp)
            style.controls.placement shouldBe ControlPlacement.Start
        }

        test("matches the caption buttons of Windows with a destructive close button") {
            val theme = Theme.light()
            val style = defaultTitleBarStyle(theme, DesktopPlatform.Windows)
            val hovered = InteractionState(isHovered = true)

            style.height shouldBe 36.dp
            val controls = style.controls.shouldBeInstanceOf<WindowsControls>()
            controls.buttons shouldBe WindowButton.entries
            controls.buttonWidth shouldBe 46.dp
            controls.placement shouldBe ControlPlacement.End
            style.closeColors.container.resolve(hovered) shouldBe theme.colors.destructive
            style.closeColors.content.resolve(hovered) shouldBe theme.colors.knob
        }

        test("matches the round buttons of Linux and every other desktop") {
            val linux = defaultTitleBarStyle(Theme.dark(), DesktopPlatform.Linux)
            val other = defaultTitleBarStyle(Theme.light(), DesktopPlatform.Other)

            linux.height shouldBe 40.dp
            val controls = linux.controls.shouldBeInstanceOf<LinuxControls>()
            controls.buttonSize shouldBe 24.dp
            controls.placement shouldBe ControlPlacement.End
            linux.closeColors shouldBe linux.controlColors
            other.controls.shouldBeInstanceOf<LinuxControls>()
            linux.controlColors.container.default shouldNotBe linux.background
        }

        test("puts the title in the middle of the window on every platform") {
            DesktopPlatform.entries.forEach { platform ->
                defaultTitleBarStyle(Theme.light(), platform).titlePlacement shouldBe TitlePlacement.WindowCenter
            }
        }
    }

    context("labels") {
        test("caps the count of a badge at its limit") {
            val style = defaultTitleBarStyle(Theme.light(), DesktopPlatform.Linux)

            style.labels.badge(SMALL_COUNT, style.badgeLimit) shouldBe "5"
            style.labels.badge(LARGE_COUNT, style.badgeLimit) shouldBe "99+"
        }
    }
})
