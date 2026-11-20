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

package com.davils.kiro.ui.components.datepicker

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.calendar.LocalClock
import com.davils.kiro.ui.components.calendar.LocalTimeZone
import com.davils.kiro.ui.components.calendar.fixture.FixedClock
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeLessThan
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlin.math.abs

@OptIn(ExperimentalTestApi::class)
class DatePickerTest : FunSpec({
    context("styles") {
        test("use a w-60 outline trigger with a muted placeholder") {
            val light = Theme.light()
            val style = DatePickerStyles.Default.resolve(light)

            style.triggerWidth shouldBe 240.dp
            style.placeholderColor shouldBe light.colors.mutedForeground
            style.popover.panel.padding shouldBe 0.dp
        }
    }

    context("date picker") {
        test("centers its calendar below the trigger") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        CompositionLocalProvider(LocalClock provides FixedClock, LocalTimeZone provides TimeZone.UTC) {
                            DatePicker(date = null, onDateChange = {}, modifier = Modifier.testTag(TAG))
                        }
                    }
                }

                onNodeWithTag(TAG).performClick()
                waitForIdle()
                val trigger = onNodeWithTag(TAG).getBoundsInRoot()
                val caption = onNodeWithText("November 2026").getBoundsInRoot()
                val triggerCenter = (trigger.left + trigger.right) / 2
                val captionCenter = (caption.left + caption.right) / 2

                abs(triggerCenter.value - captionCenter.value) shouldBeLessThan 1f
            }
        }

        test("picks a date from its calendar and shows it") {
            var date by mutableStateOf<LocalDate?>(null)

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        CompositionLocalProvider(LocalClock provides FixedClock, LocalTimeZone provides TimeZone.UTC) {
                            DatePicker(date = date, onDateChange = { date = it })
                        }
                    }
                }

                onNodeWithText("Pick a date").performClick()
                waitForIdle()
                onNodeWithContentDescription("Friday, November 20, 2026").performClick()
                waitForIdle()
                onNodeWithText("November 20, 2026").assertExists()
            }

            date shouldBe LocalDate(year = 2026, month = 11, day = 20)
        }
    }
})
