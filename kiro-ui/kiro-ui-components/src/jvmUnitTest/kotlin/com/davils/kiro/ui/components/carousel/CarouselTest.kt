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

package com.davils.kiro.ui.components.carousel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.direction.Direction
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val SLIDES: Int = 5
private const val NEXT: String = "Next slide"
private const val PREVIOUS: String = "Previous slide"

@OptIn(ExperimentalTestApi::class)
class CarouselTest : FunSpec({
    context("styles") {
        test("space items by 4 and place round buttons 12 outside") {
            val style = CarouselStyles.Default.resolve(Theme.light())

            style.spacing shouldBe 16.dp
            style.buttonOffset shouldBe 48.dp
            style.nextLabel shouldBe NEXT
        }
    }

    context("carousel") {
        test("moves between slides with its buttons") {
            var page = -1

            runComposeUiTest {
                setContent {
                    Themed {
                        val state = rememberPagerState(pageCount = { SLIDES })
                        Carousel(
                            count = SLIDES,
                            modifier = Modifier.padding(64.dp).size(200.dp),
                            state = state,
                        ) { index ->
                            Text("Slide $index")
                        }
                        page = state.currentPage
                    }
                }

                onNodeWithContentDescription(PREVIOUS).assertIsNotEnabled()
                onNodeWithContentDescription(NEXT).performClick()
                waitForIdle()
                onNodeWithText("Slide 1").assertExists()
                onNodeWithContentDescription(PREVIOUS).performClick()
                waitForIdle()
            }

            page shouldBe 0
        }

        test("steps with the arrow keys and stacks vertically") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Column {
                            Direction(direction = LayoutDirection.Rtl) {
                                Carousel(
                                    count = SLIDES,
                                    modifier = Modifier.padding(64.dp).size(200.dp).testTag(TAG),
                                    itemsPerView = 2,
                                    isNavigationShown = false,
                                ) { index -> Text("Item $index") }
                            }
                            Carousel(
                                count = SLIDES,
                                modifier = Modifier.padding(64.dp).size(100.dp),
                                orientation = Orientation.Vertical,
                            ) { index -> Text("Row $index") }
                        }
                    }
                }

                onNodeWithTag(TAG).requestFocus()
                onNodeWithTag(TAG).performKeyInput { pressKey(Key.DirectionLeft) }
                onNodeWithTag(TAG).performKeyInput { pressKey(Key.A) }
                waitForIdle()
                onNodeWithText("Row 0").assertExists()
            }
        }

        test("shows at least one item at a time") {
            shouldThrow<IllegalArgumentException> {
                runComposeUiTest {
                    setContent { Themed { Carousel(count = 1, itemsPerView = 0) { Text("none") } } }
                }
            }
        }
    }
})
