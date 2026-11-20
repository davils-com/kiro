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

package com.davils.kiro.ui.components.primitive.surface

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.core.style.Border
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val OUTER_DP: Int = 32
private const val PADDING_DP: Int = 6

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.surfacePixels(shape: Shape, color: Color, border: Border?, ring: Border?): PixelMap {
    setContent {
        Box(modifier = Modifier.size(OUTER_DP.dp).testTag(TAG)) {
            Box(
                modifier = Modifier
                    .padding(PADDING_DP.dp)
                    .size((OUTER_DP - PADDING_DP * 2).dp)
                    .surface(shape = shape, color = color, border = border, ring = ring),
            )
        }
    }
    val image = onNodeWithTag(TAG).captureToImage()
    return image.toPixelMap()
}

@OptIn(ExperimentalTestApi::class)
class SurfaceTest : FunSpec({
    context("surface") {
        test("draws the ring outside the shape and nothing beneath a transparent fill") {
            runComposeUiTest {
                val ring = Border(width = 3.dp, color = Color.Blue)
                val pixels = surfacePixels(
                    shape = RoundedCornerShape(4.dp),
                    color = Color.Transparent,
                    border = null,
                    ring = ring,
                )
                val unit = pixels.width / OUTER_DP

                pixels[pixels.width / 2, (PADDING_DP - 1) * unit] shouldBe Color.Blue
                pixels[pixels.width / 2, pixels.height / 2] shouldBe Color.Transparent
            }
        }

        test("draws the fill inside the border like bg-clip-padding") {
            runComposeUiTest {
                val border = Border(width = 2.dp, color = Color.Green)
                val pixels = surfacePixels(
                    shape = RoundedCornerShape(2.dp),
                    color = Color.Red,
                    border = border,
                    ring = null,
                )
                val unit = pixels.width / OUTER_DP

                pixels[pixels.width / 2, PADDING_DP * unit] shouldBe Color.Green
                pixels[pixels.width / 2, pixels.height / 2] shouldBe Color.Red
            }
        }

        test("draws the ring around shapes that are no rounded rectangles") {
            runComposeUiTest {
                val ring = Border(width = 3.dp, color = Color.Blue)
                val pixels = surfacePixels(shape = RectangleShape, color = Color.Red, border = null, ring = ring)
                val unit = pixels.width / OUTER_DP

                pixels[pixels.width / 2, (PADDING_DP - 1) * unit] shouldBe Color.Blue
            }
        }

        test("keeps the ring of shapes other than rounded rectangles") {
            runComposeUiTest {
                val ring = Border(width = 3.dp, color = Color.Blue)
                val pixels = surfacePixels(shape = CircleShape, color = Color.Red, border = null, ring = ring)
                val unit = pixels.width / OUTER_DP

                pixels[pixels.width / 2, (PADDING_DP - 1) * unit] shouldBe Color.Blue
            }
        }
    }
})
