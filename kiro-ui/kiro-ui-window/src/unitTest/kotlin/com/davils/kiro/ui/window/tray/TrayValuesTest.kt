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

package com.davils.kiro.ui.window.tray

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.window.Notification
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.components.menubar.native.NativeMenuEntry
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private val light = ColorPainter(Color.White)

private val dark = ColorPainter(Color.Black)

private val template = ColorPainter(Color.Gray)

class TrayValuesTest : FunSpec({
    context("tray icon") {
        test("shows the template on macOS") {
            val icon = TrayIcon(light = light, dark = dark, template = template)

            icon.painterFor(platform = DesktopPlatform.MacOs, isDark = false) shouldBe template
            icon.painterFor(platform = DesktopPlatform.MacOs, isDark = true) shouldBe template
        }

        test("follows the theme of the system elsewhere and on macOS without a template") {
            val icon = TrayIcon(light = light, dark = dark, template = template)
            val plain = TrayIcon(light = light, dark = dark)

            icon.painterFor(platform = DesktopPlatform.Windows, isDark = true) shouldBe dark
            icon.painterFor(platform = DesktopPlatform.Linux, isDark = false) shouldBe light
            plain.painterFor(platform = DesktopPlatform.MacOs, isDark = true) shouldBe dark
            plain.painterFor(platform = DesktopPlatform.MacOs, isDark = false) shouldBe light
        }

        test("uses the light image in both themes by default") {
            val icon = TrayIcon(light = light)

            icon.dark shouldBe light
            icon.template shouldBe null
        }

        test("compares by its values") {
            val icon = TrayIcon(light = light, dark = dark, template = template)

            icon.copy() shouldBe icon
            icon.copy().hashCode() shouldBe icon.hashCode()
            icon.copy(template = null) shouldNotBe icon
            icon.toString() shouldStartWith "TrayIcon(light="
        }
    }

    context("tray notification") {
        test("is informal by default and compares by its values") {
            val notification = TrayNotification(title = "Synced", message = "All files are up to date.")

            notification.type shouldBe TrayNotificationType.None
            notification.copy() shouldBe notification
            notification.copy().hashCode() shouldBe notification.hashCode()
            notification.copy(type = TrayNotificationType.Error) shouldNotBe notification
            notification.toString() shouldBe
                "TrayNotification(title=Synced, message=All files are up to date., type=None)"
        }

        test("becomes a notification of Compose of the same type") {
            val types = TrayNotificationType.entries.map { type ->
                TrayNotification(title = "Title", message = "Message", type = type).toCompose()
            }

            types.map(Notification::type) shouldBe listOf(
                Notification.Type.None,
                Notification.Type.Info,
                Notification.Type.Warning,
                Notification.Type.Error,
            )
            types.first().title shouldBe "Title"
            types.first().message shouldBe "Message"
        }
    }

    context("tray spec") {
        test("compares by its values") {
            val action: () -> Unit = {}
            val spec = TraySpec(
                icon = light,
                tooltip = "Kiro",
                entries = listOf(NativeMenuEntry.Separator),
                state = TrayState(),
                onAction = action,
            )

            spec.copy() shouldBe spec
            spec.copy().hashCode() shouldBe spec.hashCode()
            spec.copy(tooltip = "Other") shouldNotBe spec
            spec.toString() shouldStartWith "TraySpec(icon="
        }
    }
})
