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

package com.davils.kiro.ui.components.skeleton

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val WIDTH: Int = 320

@OptIn(ExperimentalTestApi::class)
class SkeletonShapesTest : FunSpec({
    val style = SkeletonStyles.Default.resolve(Theme.light())

    context("shapes") {
        test("outline rectangles, rounded boxes, pills and circles") {
            outlineOf(shape = SkeletonShape.Rectangle, style = style) shouldBe RectangleShape
            outlineOf(shape = SkeletonShape.Rounded, style = style) shouldBe RoundedCornerShape(style.radius)
            outlineOf(shape = SkeletonShape.Pill, style = style) shouldBe CircleShape
            outlineOf(shape = SkeletonShape.Circle, style = style) shouldBe CircleShape
        }
    }

    context("presets") {
        test("size avatars and buttons from the style") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Column {
                            SkeletonAvatar(modifier = Modifier.testTag(TAG))
                            SkeletonButton(modifier = Modifier.testTag("button"))
                        }
                    }
                }

                onNodeWithTag(TAG).assertWidthIsEqualTo(style.avatarSize)
                onNodeWithTag("button").assertHeightIsEqualTo(style.buttonSize.height)
            }
        }

        test("lay out text, list items, cards, images and tables with every animation") {
            SkeletonAnimation.entries.forEach { animation ->
                runComposeUiTest {
                    setContent {
                        Themed {
                            val animated = skeletonStyle().copy(animation = animation)
                            Column(modifier = Modifier.width(WIDTH.dp)) {
                                SkeletonText(style = animated)
                                SkeletonText(lines = 1, style = animated)
                                SkeletonListItem(style = animated)
                                SkeletonCard(modifier = Modifier.testTag(TAG), style = animated)
                                SkeletonTable(rows = 2, columns = 2, style = animated)
                            }
                        }
                    }

                    onNodeWithTag(TAG).assertWidthIsEqualTo(WIDTH.dp)
                }
            }
        }
    }
})
