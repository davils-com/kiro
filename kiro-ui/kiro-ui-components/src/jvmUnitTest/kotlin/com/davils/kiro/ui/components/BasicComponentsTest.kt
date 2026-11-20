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

package com.davils.kiro.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.kbd.Kbd
import com.davils.kiro.ui.components.kbd.KbdGroup
import com.davils.kiro.ui.components.kbd.KbdStyles
import com.davils.kiro.ui.components.label.Label
import com.davils.kiro.ui.components.label.LabelStyles
import com.davils.kiro.ui.components.label.labelStyle
import com.davils.kiro.ui.components.separator.Separator
import com.davils.kiro.ui.components.separator.SeparatorStyles
import com.davils.kiro.ui.components.skeleton.Skeleton
import com.davils.kiro.ui.components.skeleton.SkeletonStyles
import com.davils.kiro.ui.components.spinner.Spinner
import com.davils.kiro.ui.components.spinner.SpinnerStyles
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val HALF: Float = 0.5f

@OptIn(ExperimentalTestApi::class)
class BasicComponentsTest : FunSpec({
    val light = Theme.light()

    context("label") {
        test("is text-sm medium with a line height of one and fades when disabled") {
            val style = LabelStyles.Default.resolve(light)

            style.textStyle.fontSize shouldBe 14.sp
            style.textStyle.lineHeight shouldBe light.typography.leading.none
            style.disabledAlpha shouldBe HALF
        }

        test("renders enabled and disabled") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Label(text = "Email")
                        Label(text = "Password", modifier = Modifier.testTag(TAG), isDisabled = true)
                    }
                }

                onNodeWithTag(TAG).assertHeightIsEqualTo(14.dp)
            }
        }
    }

    context("line box") {
        test("takes a line height in sp as it is") {
            runComposeUiTest {
                setContent {
                    Themed {
                        val base = labelStyle()
                        val style = base.copy(textStyle = base.textStyle.copy(lineHeight = 10.sp))
                        Label(text = "Short", modifier = Modifier.testTag(TAG), style = style)
                    }
                }

                onNodeWithTag(TAG).assertHeightIsEqualTo(10.dp)
            }
        }
    }

    context("kbd") {
        test("is h-5 min-w-5 rounded-sm in the muted colors") {
            val style = KbdStyles.Default.resolve(light)

            style.height shouldBe 20.dp
            style.minWidth shouldBe 20.dp
            style.container shouldBe light.colors.muted
            style.content shouldBe light.colors.mutedForeground
        }

        test("renders keys in a group") {
            runComposeUiTest {
                setContent {
                    Themed {
                        KbdGroup {
                            Kbd(modifier = Modifier.testTag(TAG)) { Text("K") }
                            Kbd { Text("Ctrl") }
                        }
                    }
                }

                val node = onNodeWithTag(TAG).assertHeightIsEqualTo(20.dp)
                node.assertWidthIsEqualTo(20.dp)
            }
        }
    }

    context("separator") {
        test("is one dp in the border color") {
            val style = SeparatorStyles.Default.resolve(light)

            style.thickness shouldBe 1.dp
            style.color shouldBe light.colors.border
        }

        test("spans the width or the height of its parent") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Box(modifier = Modifier.width(40.dp)) { Separator(modifier = Modifier.testTag(TAG)) }
                        Box(modifier = Modifier.height(30.dp)) {
                            Separator(modifier = Modifier.testTag("vertical"), orientation = Orientation.Vertical)
                        }
                    }
                }

                val horizontal = onNodeWithTag(TAG).assertWidthIsEqualTo(40.dp)
                horizontal.assertHeightIsEqualTo(1.dp)
                onNodeWithTag("vertical").assertHeightIsEqualTo(30.dp)
            }
        }
    }

    context("spinner") {
        test("spins a size-4 loader once a second unless motion is reduced") {
            val style = SpinnerStyles.Default.resolve(light)

            style.size shouldBe 16.dp
            style.isAnimated shouldBe true
            SpinnerStyles.Default.resolve(reducedMotion).isAnimated shouldBe false
        }

        test("announces that something is loading") {
            runComposeUiTest {
                setContent {
                    Themed { Spinner() }
                    Themed(theme = reducedMotion) { Spinner(contentDescription = "Still") }
                }

                onNodeWithContentDescription("Loading").assertWidthIsEqualTo(16.dp)
                onNodeWithContentDescription("Still").assertWidthIsEqualTo(16.dp)
            }
        }
    }

    context("skeleton") {
        test("pulses in the muted color with the medium radius") {
            val style = SkeletonStyles.Default.resolve(light)

            style.color shouldBe light.colors.muted
            style.dimmedAlpha shouldBe HALF
            SkeletonStyles.Default.resolve(reducedMotion).isAnimated shouldBe false
        }

        test("takes the size it is given") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Skeleton(modifier = Modifier.size(24.dp).testTag(TAG))
                        Themed(theme = reducedMotion) { Skeleton(modifier = Modifier.size(8.dp)) }
                    }
                }

                onNodeWithTag(TAG).assertWidthIsEqualTo(24.dp)
            }
        }
    }

    context("text") {
        test("prefers its own color, then the color of its style, then the content color") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Text("own", color = Color.Red)
                        Text("styled", style = TextStyle(color = Color.Blue))
                        Text("inherited")
                    }
                }
            }
        }
    }
})
