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

package com.davils.kiro.ui.components.primitive.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.direction.Direction
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val PARENT: Int = 100

private const val ICON: Int = 20

private const val INSET: Int = 4

@OptIn(ExperimentalTestApi::class)
private fun boundsOf(placement: IconPlacement, direction: LayoutDirection): DpRect {
    var bounds = DpRect(origin = DpOffset.Zero, size = DpSize.Zero)
    runComposeUiTest {
        setContent {
            Themed {
                Direction(direction = direction) {
                    Box(modifier = Modifier.size(PARENT.dp)) {
                        Box(modifier = Modifier.edgeInset(placement, INSET.dp).testTag(TAG).size(ICON.dp))
                    }
                }
            }
        }
        bounds = onNodeWithTag(TAG).getUnclippedBoundsInRoot()
    }
    return bounds
}

class EdgeInsetTest : FunSpec({
    context("edgeInset") {
        test("moves a leading icon towards the edge the language starts from") {
            boundsOf(IconPlacement.Leading, LayoutDirection.Ltr).left shouldBe -INSET.dp
            boundsOf(IconPlacement.Leading, LayoutDirection.Rtl).right shouldBe (PARENT + INSET).dp
        }

        test("lets a trailing icon reach beyond the edge the language ends at") {
            boundsOf(IconPlacement.Trailing, LayoutDirection.Ltr).right shouldBe ICON.dp
            boundsOf(IconPlacement.Trailing, LayoutDirection.Rtl).left shouldBe (PARENT - ICON).dp
        }

        test("leaves an inline icon where it is") {
            boundsOf(IconPlacement.Inline, LayoutDirection.Ltr).left shouldBe 0.dp
            boundsOf(IconPlacement.Inline, LayoutDirection.Rtl).right shouldBe PARENT.dp
        }
    }
})
