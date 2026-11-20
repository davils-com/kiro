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

package com.davils.kiro.ui.components.alert

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.lucide.CircleExclamationPoint
import com.davils.kiro.ui.icons.lucide.Lucide
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalTestApi::class)
class AlertTest : FunSpec({
    val light = Theme.light()

    context("styles") {
        test("are rounded-lg px-2.5 py-2 with a border on the card color") {
            val style = AlertStyles.Default.resolve(light, AlertVariant.Default)

            style.shape shouldBe RoundedCornerShape(10.dp)
            style.horizontalPadding shouldBe 10.dp
            style.verticalPadding shouldBe 8.dp
            style.container shouldBe light.colors.card
            style.border.color shouldBe light.colors.border
            style.descriptionColor shouldBe light.colors.mutedForeground
        }

        test("color destructive alerts and their description in the destructive color") {
            val style = AlertStyles.Default.resolve(light, AlertVariant.Destructive)

            style.content shouldBe light.colors.destructive
            style.descriptionColor shouldBe light.colors.destructive.copy(alpha = 0.9f)
        }

        test("need a style of their own for custom variants") {
            shouldThrow<IllegalArgumentException> { AlertStyles.Default.resolve(light, Unknown) }
        }
    }

    context("alert") {
        test("announces itself and shows icon, title, description and action") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Alert(
                            modifier = Modifier.testTag(TAG),
                            variant = AlertVariant.Destructive,
                            icon = Lucide.CircleExclamationPoint,
                            action = { Text("Undo") },
                        ) {
                            AlertTitle(text = "Failed")
                            AlertDescription(text = "The server did not answer.")
                        }
                    }
                }

                val node = onNodeWithTag(TAG)
                node.assert(SemanticsMatcher.expectValue(SemanticsProperties.LiveRegion, LiveRegionMode.Assertive))
                onNodeWithText("Undo").assertExists()
            }
        }

        test("renders without icon and action") {
            runComposeUiTest {
                setContent { Themed { Alert { AlertTitle(text = "Saved") } } }

                onNodeWithText("Saved").assertExists()
            }
        }
    }
})
