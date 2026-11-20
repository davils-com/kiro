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

package com.davils.kiro.ui.components.buttongroup

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.width
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.direction.Direction
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.math.roundToInt

private const val SECOND: String = "second"

@OptIn(ExperimentalTestApi::class)
class ButtonGroupTest : FunSpec({
    val square = CornerSize(0.dp)
    val round = CornerSize(10.dp)

    context("shapes") {
        test("round the outer corners of the first and last item of a row") {
            val first = GroupItem(
                orientation = Orientation.Horizontal,
                isFirst = true,
                isLast = false,
                radius = 10.dp,
            )
            val last = first.copy(isFirst = false, isLast = true)

            first.shapeOr(
                RoundedCornerShape(1.dp),
            ) shouldBe RoundedCornerShape(topStart = round, topEnd = square, bottomEnd = square, bottomStart = round)
            last.shapeOr(
                RoundedCornerShape(1.dp),
            ) shouldBe RoundedCornerShape(topStart = square, topEnd = round, bottomEnd = round, bottomStart = square)
        }

        test("round the top of the first and the bottom of the last item of a column") {
            val middle = GroupItem(
                orientation = Orientation.Vertical,
                isFirst = false,
                isLast = false,
                radius = 10.dp,
            )
            val single = middle.copy(isFirst = true, isLast = true)

            middle.shapeOr(RoundedCornerShape(1.dp)) shouldBe RoundedCornerShape(0.dp)
            single.shapeOr(RoundedCornerShape(1.dp)) shouldBe RoundedCornerShape(10.dp)
        }

        test("keep the shape of an item outside a group") {
            val item: GroupItem? = null

            item.shapeOr(RoundedCornerShape(1.dp)) shouldBe RoundedCornerShape(1.dp)
        }
    }

    context("group") {
        test("joins its buttons by removing the border between them") {
            runComposeUiTest {
                setContent {
                    Themed {
                        ButtonGroup {
                            item {
                                Button(
                                    onClick = {},
                                    modifier = Modifier.testTag(TAG),
                                    variant = ButtonVariant.Outline,
                                ) {
                                    Text("One")
                                }
                            }
                            item { ButtonGroupSeparator() }
                            item {
                                Button(
                                    onClick = {},
                                    modifier = Modifier.testTag(SECOND),
                                    variant = ButtonVariant.Outline,
                                ) {
                                    Text("Two")
                                }
                            }
                            item { ButtonGroupText { Text("Text") } }
                        }
                    }
                }

                val first = onNodeWithTag(TAG).getUnclippedBoundsInRoot()
                val second = onNodeWithTag(SECOND).getUnclippedBoundsInRoot()
                second.left - first.right shouldBe 0.dp
                onNodeWithText("Text").assertExists()
            }
        }

        test("joins its buttons from right to left in a language that is written that way") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Direction(direction = LayoutDirection.Rtl) {
                            ButtonGroup {
                                item {
                                    Button(
                                        onClick = {},
                                        modifier = Modifier.testTag(TAG),
                                        variant = ButtonVariant.Outline,
                                    ) {
                                        Text("One")
                                    }
                                }
                                item {
                                    Button(
                                        onClick = {},
                                        modifier = Modifier.testTag(SECOND),
                                        variant = ButtonVariant.Outline,
                                    ) {
                                        Text("Two")
                                    }
                                }
                            }
                        }
                    }
                }

                val first = onNodeWithTag(TAG).getUnclippedBoundsInRoot()
                val second = onNodeWithTag(SECOND).getUnclippedBoundsInRoot()
                (second.right - first.left).value.roundToInt() shouldBe 1
            }
        }

        test("stacks its items in a column") {
            runComposeUiTest {
                setContent {
                    Themed {
                        ButtonGroup(orientation = Orientation.Vertical) {
                            item { Button(onClick = {}, modifier = Modifier.testTag(TAG)) { Text("Wide button") } }
                            item { ButtonGroupSeparator() }
                            item { Button(onClick = {}, modifier = Modifier.testTag(SECOND)) { Text("B") } }
                        }
                    }
                }

                val first = onNodeWithTag(TAG).getUnclippedBoundsInRoot()
                val second = onNodeWithTag(SECOND).getUnclippedBoundsInRoot()
                second.width shouldBe first.width
            }
        }

        test("draws a lone separator horizontally") {
            runComposeUiTest {
                setContent { Themed { ButtonGroupSeparator(modifier = Modifier.testTag(TAG)) } }

                onNodeWithTag(TAG).assertExists()
            }
        }
    }
})
