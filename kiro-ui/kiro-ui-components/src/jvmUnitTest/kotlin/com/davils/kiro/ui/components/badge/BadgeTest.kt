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

package com.davils.kiro.ui.components.badge

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.lucide.Check
import com.davils.kiro.ui.icons.lucide.Lucide
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val LABEL: String = "New"

@OptIn(ExperimentalTestApi::class)
class BadgeTest : FunSpec({
    val light = Theme.light()
    val dark = Theme.dark()

    fun style(variant: BadgeVariant, theme: Theme = light): BadgeStyle =
        BadgeStyles.Default.resolve(theme = theme, variant = variant)

    context("styles") {
        test("are h-5 px-2 rounded-4xl text-xs medium with size-3 icons") {
            val badge = style(BadgeVariant.Default)

            badge.height shouldBe 20.dp
            badge.horizontalPadding shouldBe 8.dp
            badge.iconEdgePadding shouldBe 6.dp
            badge.shape shouldBe RoundedCornerShape(light.radii.xl4)
            badge.textStyle.fontSize shouldBe 12.sp
            badge.iconSize shouldBe 12.dp
        }

        test("follow the colors of each variant") {
            style(BadgeVariant.Default).colors.container.default shouldBe light.colors.primary
            style(BadgeVariant.Secondary).colors.container.default shouldBe light.colors.secondary
            style(BadgeVariant.Destructive).colors.container.default shouldBe
                light.colors.destructive.copy(alpha = 0.1f)
            style(BadgeVariant.Destructive, dark).colors.container.default shouldBe
                dark.colors.destructive.copy(alpha = 0.2f)
            style(BadgeVariant.Outline).colors.border.default shouldBe light.colors.border
            style(BadgeVariant.Ghost).colors.container.default shouldBe Color.Transparent
            style(BadgeVariant.Ghost, dark).colors.container.hovered shouldBe dark.colors.muted.copy(alpha = 0.5f)
            style(BadgeVariant.Link).isUnderlinedOnHover shouldBe true
        }

        test("need a style of their own for custom variants") {
            shouldThrow<IllegalArgumentException> { style(Unknown) }
        }
    }

    context("badge") {
        test("is 20 dp high and no button without onClick") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Badge(modifier = Modifier.testTag(TAG)) {
                            Icon(icon = Lucide.Check, placement = IconPlacement.Leading)
                            Text(LABEL)
                        }
                    }
                }

                val node = onNodeWithTag(TAG).assertHeightIsEqualTo(20.dp)
                node.fetchSemanticsNode().config.contains(SemanticsProperties.Role) shouldBe false
            }
        }

        test("acts like a link with onClick and reacts to hover") {
            var clicks = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        Badge(
                            modifier = Modifier.testTag(TAG),
                            variant = BadgeVariant.Link,
                            onClick = { clicks++ },
                        ) {
                            Text(LABEL)
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
