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

package com.davils.kiro.gallery.page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.davils.kiro.gallery.demo.demos
import com.davils.kiro.gallery.fixture.click
import com.davils.kiro.gallery.fixture.shown
import com.davils.kiro.ui.core.Theme
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldStartWith
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.MissingResourceException

private const val SETTLE: Long = 1_000L
private const val SHOW_CODE: String = "Show code"

@OptIn(ExperimentalTestApi::class)
class DemoPageTest : FunSpec({
    context("source") {
        test("shows the demo file from its imports on") {
            demos.flatMap { demo -> demo.sections }.forEach { section ->
                val source = section.source()

                source shouldStartWith "import "
                source shouldContain "internal fun ${section.file.removeSuffix(".kt")}()"
            }
        }

        test("names the file after the component without spaces") {
            val name = "Alert Dialog"

            DemoSection(name = name) {}.file shouldBe "AlertDialogDemo.kt"
            GalleryDemo(name = name, category = GalleryCategory.Overlays) {}.sections.single().name shouldBe name
        }

        test("fails for a component without a demo file") {
            shouldThrow<MissingResourceException> {
                DemoSection(name = "Missing") {}.source()
            }
        }
    }

    context("page") {
        test("shows every component with its code in the light and the dark theme") {
            listOf(Theme.light(), Theme.dark()).forEach { theme ->
                var current by mutableStateOf(demos.first())

                shown(theme = theme, content = { DemoPage(current) }) {
                    demos.forEach { demo ->
                        current = demo
                        waitForIdle()
                        demo.sections.forEach { section -> onNodeWithText(section.file).assertExists() }
                    }
                }
            }
        }

        test("gives every section of a demo its own box and code block") {
            val audio = demos.first { demo -> demo.category == GalleryCategory.Audio }

            shown(content = { DemoPage(audio) }) {
                audio.sections.forEach { section ->
                    onNodeWithText(section.name).assertExists()
                    onNodeWithText(section.file).assertExists()
                }
                onAllNodesWithText(SHOW_CODE).assertCountEquals(audio.sections.size)
            }
        }

        test("hides the code until it is opened and hides it again for the next demo") {
            var current by mutableStateOf(demos.first())

            shown(content = { DemoPage(current) }) {
                val source = runBlocking { current.sections.single().source() }
                val line = source.lines().first()
                onNodeWithText(line, substring = true).assertDoesNotExist()
                click(SHOW_CODE)
                mainClock.advanceTimeBy(SETTLE)
                waitForIdle()
                onNodeWithText("Hide code").assertExists()
                current = demos.last()
                mainClock.advanceTimeBy(SETTLE)
                waitForIdle()
                onNodeWithText(SHOW_CODE).assertExists()
            }
        }
    }
})
