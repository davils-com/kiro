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

package com.davils.kiro.ui.components.item

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalTestApi::class)
class ItemTest : FunSpec({
    val light = Theme.light()

    fun style(variant: ItemVariant = ItemVariant.Default, size: ItemSize = ItemSize.Default) =
        ItemStyles.Default.resolve(theme = light, variant = variant, size = size)

    context("styles") {
        test("are rounded-lg with px-3 py-2.5 gap-2.5 by default") {
            val default = style()

            default.shape shouldBe RoundedCornerShape(10.dp)
            default.horizontalPadding shouldBe 12.dp
            default.verticalPadding shouldBe 10.dp
            default.gap shouldBe 10.dp
            default.imageSize shouldBe 40.dp
        }

        test("shrink for the small sizes") {
            style(size = ItemSize.Sm).imageSize shouldBe 32.dp
            style(size = ItemSize.Xs).gap shouldBe 8.dp
            style(size = ItemSize.Xs).descriptionStyle.fontSize shouldBe 12.sp
            style(size = ItemSize.Xs).contentGap shouldBe 0.dp
        }

        test("draw outline and muted variants") {
            style(variant = ItemVariant.Outline).colors.border.default shouldBe light.colors.border
            style(
                variant = ItemVariant.Muted,
            ).colors.container.default shouldBe light.colors.muted.copy(alpha = 0.5f)
            style().colors.container.default shouldBe Color.Transparent
            style().colors.container.hovered shouldBe light.colors.muted
        }

        test("need a style of their own for custom variants and sizes") {
            shouldThrow<IllegalArgumentException> { style(variant = Unknown) }
            shouldThrow<IllegalArgumentException> { style(size = Unknown) }
        }
    }

    context("item") {
        test("lays out media, content and actions between header and footer") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Item(
                            variant = ItemVariant.Outline,
                            header = { Text("Header") },
                            footer = { Text("Footer") },
                        ) {
                            ItemMedia(
                                variant = ItemMediaVariant.Icon,
                            ) { Icon(icon = Lucide.User, contentDescription = null) }
                            ItemMedia(variant = ItemMediaVariant.Image) { Text("image") }
                            ItemMedia { Text("plain") }
                            ItemContent {
                                ItemTitle(text = "Nils")
                                ItemDescription(text = "Maintainer")
                            }
                            ItemActions { Text("Edit") }
                        }
                    }
                }

                onNodeWithText("Header").assertExists()
                onNodeWithText("Footer").assertExists()
                onNodeWithText("Maintainer").assertExists()
            }
        }

        test("acts like a link with onClick and reacts to hover") {
            var clicks = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        Item(modifier = Modifier.testTag(TAG), onClick = { clicks++ }) {
                            ItemContent { ItemTitle(text = "Open") }
                        }
                    }
                }

                val node = onNodeWithTag(TAG)
                node.performMouseInput { moveTo(center) }
                node.performClick()
            }

            clicks shouldBe 1
        }
    }
})
