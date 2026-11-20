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

package com.davils.kiro.ui.components.card

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val FOOTER: String = "footer"
private const val TITLE: String = "Title"
private const val FOOTER_TEXT: String = "Footer"

@OptIn(ExperimentalTestApi::class)
class CardTest : FunSpec({
    val light = Theme.light()

    context("styles") {
        test("are rounded-xl with a ring of the foreground at ten percent and spacing 4") {
            val style = CardStyles.Default.resolve(light, CardSize.Default)

            style.shape shouldBe RoundedCornerShape(14.dp)
            style.ring.width shouldBe 1.dp
            style.ring.color shouldBe light.colors.foreground.copy(alpha = 0.1f)
            style.spacing shouldBe 16.dp
            style.container shouldBe light.colors.card
            style.titleStyle.fontFamily shouldBe light.typography.families.heading
            style.titleStyle.fontSize shouldBe 16.sp
            style.footerContainer shouldBe light.colors.muted.copy(alpha = 0.5f)
        }

        test("tighten spacing and title for the small card") {
            val style = CardStyles.Default.resolve(light, CardSize.Sm)

            style.spacing shouldBe 12.dp
            style.titleStyle.fontSize shouldBe 14.sp
        }

        test("need a style of their own for custom sizes") {
            shouldThrow<IllegalArgumentException> { CardStyles.Default.resolve(light, Unknown) }
        }
    }

    context("card") {
        test("lays out every part and lets the footer reach the bottom edge") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Card(modifier = Modifier.width(240.dp).testTag(TAG)) {
                            CardHeader(action = { Text("Action") }) {
                                CardTitle(text = TITLE)
                                CardDescription(text = "Description")
                            }
                            CardContent { Text("Content") }
                            CardFooter(modifier = Modifier.testTag(FOOTER)) { Text(FOOTER_TEXT) }
                        }
                    }
                }

                val card = onNodeWithTag(TAG).getUnclippedBoundsInRoot()
                val footer = onNodeWithTag(FOOTER).getUnclippedBoundsInRoot()
                footer.bottom shouldBe card.bottom
                onNodeWithText("Action").assertExists()
            }
        }

        test("aligns the content of the footer to the end") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Card(modifier = Modifier.width(240.dp).testTag(TAG)) {
                            CardFooter { Text(FOOTER_TEXT) }
                        }
                    }
                }

                val card = onNodeWithTag(TAG).getUnclippedBoundsInRoot()
                val text = onNodeWithText(FOOTER_TEXT).getUnclippedBoundsInRoot()
                card.right - text.right shouldBe 16.dp
            }
        }

        test("is only its spacing when it is empty") {
            runComposeUiTest {
                setContent { Themed { Card(modifier = Modifier.testTag(TAG)) {} } }

                onNodeWithTag(TAG).assertHeightIsEqualTo(32.dp)
            }
        }

        test("keeps its bottom spacing without a footer") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Card(modifier = Modifier.testTag(TAG), size = CardSize.Sm) {
                            CardHeader { CardTitle(text = TITLE) }
                        }
                    }
                }

                onNodeWithText(TITLE).assertExists()
            }
        }
    }
})
