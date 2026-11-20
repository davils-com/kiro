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

package com.davils.kiro.ui.components.avatar

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithContentDescription
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
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.lucide.Check
import com.davils.kiro.ui.icons.lucide.Lucide
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val SECOND: String = "second"

@OptIn(ExperimentalTestApi::class)
class AvatarTest : FunSpec({
    val light = Theme.light()

    context("styles") {
        test("are size-8, size-6 and size-10 with matching badges") {
            val default = AvatarStyles.Default.resolve(light, AvatarSize.Default)
            val sm = AvatarStyles.Default.resolve(light, AvatarSize.Sm)
            val lg = AvatarStyles.Default.resolve(light, AvatarSize.Lg)

            default.size shouldBe 32.dp
            sm.size shouldBe 24.dp
            lg.size shouldBe 40.dp
            default.badgeSize shouldBe 10.dp
            sm.badgeIconSize shouldBe null
            sm.fallbackTextStyle.fontSize shouldBe 12.sp
            lg.countIconSize shouldBe 20.dp
        }

        test("darken the outline in light mode and lighten it in dark mode") {
            AvatarStyles.Default.resolve(light, AvatarSize.Default).outlineBlendMode shouldBe BlendMode.Darken
            AvatarStyles.Default.resolve(Theme.dark(), AvatarSize.Default).outlineBlendMode shouldBe
                BlendMode.Lighten
        }

        test("need a style of their own for custom sizes") {
            shouldThrow<IllegalArgumentException> { AvatarStyles.Default.resolve(light, Unknown) }
        }
    }

    context("avatar") {
        test("shows the image when there is one and the fallback otherwise") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Avatar(
                            modifier = Modifier.testTag(TAG),
                            image = ColorPainter(Color.Red),
                            contentDescription = "Nils",
                            badge = { Icon(icon = Lucide.Check, contentDescription = null) },
                        )
                        Avatar(size = AvatarSize.Sm, badge = { Text("hidden") }) { Text("NJ") }
                    }
                }

                onNodeWithTag(TAG).assertWidthIsEqualTo(32.dp)
                onNodeWithContentDescription("Nils").assertExists()
                onNodeWithText("NJ").assertExists()
                onNodeWithText("hidden").assertDoesNotExist()
            }
        }

        test("overlaps the avatars of a group by two units and counts the rest") {
            runComposeUiTest {
                setContent {
                    Themed {
                        AvatarGroup(size = AvatarSize.Lg) {
                            Avatar(modifier = Modifier.testTag(TAG), size = AvatarSize.Lg) { Text("A") }
                            Avatar(modifier = Modifier.testTag(SECOND), size = AvatarSize.Lg) { Text("B") }
                            AvatarGroupCount { Text("+3") }
                        }
                    }
                }

                val first = onNodeWithTag(TAG).getUnclippedBoundsInRoot()
                val second = onNodeWithTag(SECOND).getUnclippedBoundsInRoot()
                first.right - second.left shouldBe 8.dp
                onNodeWithText("+3").assertExists()
            }
        }
    }
})
