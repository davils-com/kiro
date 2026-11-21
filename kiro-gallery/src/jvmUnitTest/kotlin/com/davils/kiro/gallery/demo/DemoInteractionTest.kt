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

package com.davils.kiro.gallery.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performSemanticsAction
import com.davils.kiro.gallery.fixture.click
import com.davils.kiro.gallery.fixture.escape
import com.davils.kiro.gallery.fixture.role
import com.davils.kiro.gallery.fixture.shown
import com.davils.kiro.gallery.fixture.type
import com.davils.kiro.gallery.page.file
import io.kotest.core.spec.style.FunSpec

private const val SLIDER_TARGET: Float = 0.8f
private const val UPLOAD_MILLIS: Long = 4_000L

private val clicks: List<Pair<String, List<String>>> = listOf(
    "Accordion" to listOf("What is your return policy?"),
    "AlertDialog" to listOf("Show dialog", "Continue"),
    "Collapsible" to listOf("Show repositories"),
    "Command" to listOf("Open command dialog"),
    "Combobox" to listOf("Select framework", "Ktor"),
    "Dialog" to listOf("Edit profile", "Save changes"),
    "Drawer" to listOf("Open drawer", "Submit"),
    "DropdownMenu" to listOf("Open", "Profile"),
    "Pagination" to listOf("2", "Next", "Previous"),
    "Resizable" to listOf("Toggle Project", "Toggle Project", "Toggle Terminal"),
    "Select" to listOf("Select a fruit", "Banana"),
    "Sidebar" to listOf("Inventory", "Frankfurt", "Davils", "Campus Berlin"),
    "Skeleton" to listOf("Shimmer", "None"),
    "Sheet" to listOf("Open end", "Save changes"),
    "Tabs" to listOf("Password"),
    "Toast" to listOf("Info", "With action", "Undo", "Dismiss all"),
    "Toggle" to listOf("Bold", "Italic"),
    "ToggleGroup" to listOf("Center"),
    "Trace" to listOf("Thought for 3 seconds"),
    "NativeSelect" to listOf("Europe", "Asia Pacific"),
)

private val texts: List<String> = listOf(
    "Field",
    "Input",
    "InputGroup",
    "InputOTP",
    "Textarea",
)

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.described(label: String): SemanticsNodeInteraction =
    onAllNodesWithContentDescription(label).onFirst()

private fun demoNamed(file: String): @Composable () -> Unit = demos
    .flatMap { demo -> demo.sections }
    .first { section -> section.file == "${file}Demo.kt" }
    .content

@OptIn(ExperimentalTestApi::class)
class DemoInteractionTest : FunSpec({
    context("demos") {
        test("react to clicks") {
            clicks.forEach { (name, labels) ->
                shown(
                    content = demoNamed(name),
                ) { labels.forEach { label -> click(label) } }
            }
        }

        test("take typed text") {
            texts.forEach { name -> shown(content = demoNamed(name)) { type("123") } }
            shown(content = { FieldDemo() }) { type(text = "lace", index = 1) }
            shown(content = { InputDemo() }) { type(text = "secret", index = 1) }
        }

        test("toggle their controls") {
            shown(content = { CheckboxDemo() }) { click(role(Role.Checkbox)) }
            shown(content = { LabelDemo() }) { click(role(Role.Checkbox)) }
            shown(content = { SwitchDemo() }) { click(role(Role.Switch)) }
            shown(content = { RadioGroupDemo() }) { click(role(Role.RadioButton, index = 2)) }
            shown(content = { SliderDemo() }) {
                val slider = onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsActions.SetProgress)).onFirst()
                slider.performSemanticsAction(SemanticsActions.SetProgress) { set -> set(SLIDER_TARGET) }
            }
        }

        test("run the richer demos of the second round") {
            shown(content = { SidebarDemo() }) {
                click(role(Role.Switch))
                click("Nils")
                click("Log out")
            }
            shown(content = { ToastDemo() }) {
                click(role(Role.Switch))
                click(role(Role.Switch, index = 1))
                click("Upload")
                mainClock.advanceTimeBy(UPLOAD_MILLIS)
                waitForIdle()
            }
            shown(content = { SheetDemo() }) {
                click("Open top")
                escape("Edit profile")
            }
            shown(content = { DrawerDemo() }) {
                click("Open start")
                escape("Nightly maintenance")
            }
        }

        test("play, pause and open the queue of the audio players") {
            shown(content = demoNamed("AudioPlayerBar")) {
                click(described("Play"))
                click(described("Queue"))
            }
            shown(content = demoNamed("AudioPlayerCard")) { click(described("Next")) }
            shown(content = demoNamed("AudioPlayerFull")) {
                click(described("Queue"))
                click("Packet Loss")
            }
            shown(content = demoNamed("AudioPlayerMini")) { click(described("Play")) }
        }

        test("open and close their floating panels") {
            shown(content = { PopoverDemo() }) {
                click("Open popover")
                escape("Dimensions")
            }
            shown(content = { CalendarDemo() }) { click("15") }
            shown(content = { DatePickerDemo() }) { click(role(Role.Button)) }
        }

        test("walk through the questionnaire") {
            shown(content = { QuestionnaireDemo() }) {
                listOf(
                    "Next",
                    "Administrator",
                    "Next",
                    "SSH",
                    "Previous",
                    "Next",
                    "Skip",
                ).forEach { label -> click(label) }
                type("Thanks")
                click("Submit")
            }
        }
    }
})
