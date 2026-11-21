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

package com.davils.kiro.ui.window.application

import com.davils.kiro.ui.components.menubar.DesktopPlatform
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

private const val NAME: String = "apple.awt.application.name"

private const val SCREEN_MENU_BAR: String = "apple.laf.useScreenMenuBar"

private const val APPEARANCE: String = "apple.awt.application.appearance"

private const val TEMPLATE_IMAGES: String = "apple.awt.enableTemplateImages"

private val keys: List<String> = listOf(NAME, SCREEN_MENU_BAR, APPEARANCE, TEMPLATE_IMAGES)

class DesktopEnvironmentTest : FunSpec({
    context("prepare") {
        test("names the application and enables the native menu bar, appearance and template images on macOS") {
            val written = mutableListOf<Pair<String, String>>()

            DesktopEnvironment.prepare(name = "Kiro", platform = DesktopPlatform.MacOs) { key, value ->
                written += key to value
            }

            written shouldBe listOf(
                NAME to "Kiro",
                SCREEN_MENU_BAR to "true",
                APPEARANCE to "system",
                TEMPLATE_IMAGES to "true",
            )
        }

        test("changes nothing on other platforms") {
            val written = mutableListOf<Pair<String, String>>()

            listOf(DesktopPlatform.Windows, DesktopPlatform.Linux, DesktopPlatform.Other).forEach { platform ->
                DesktopEnvironment.prepare(name = "Kiro", platform = platform) { key, value -> written += key to value }
            }

            written.shouldBeEmpty()
        }

        test("rejects a blank name") {
            shouldThrow<IllegalArgumentException> {
                DesktopEnvironment.prepare(name = " ", platform = DesktopPlatform.Linux)
            }
        }

        test("writes the system properties of the Java runtime") {
            val before = keys.associateWith(System::getProperty)
            try {
                DesktopEnvironment.prepare(name = "Kiro")
                DesktopEnvironment.prepare(name = "Kiro", platform = DesktopPlatform.MacOs)

                System.getProperty(NAME) shouldBe "Kiro"
                System.getProperty(SCREEN_MENU_BAR) shouldBe "true"
            } finally {
                before.forEach { (key, value) -> restore(key = key, value = value) }
            }
        }
    }
})

private fun restore(key: String, value: String?) {
    if (value == null) {
        System.clearProperty(key)
        return
    }
    System.setProperty(key, value)
}
