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

package com.davils.kiro.ui.components.menu.intent

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val panel = Rect(left = 100f, top = 0f, right = 200f, bottom = 100f)
private val left = Offset(x = 50f, y = 50f)
private val right = Offset(x = 250f, y = 50f)

class SafeAreaTest : FunSpec({
    context("towards a panel on the right") {
        test("contains points on the way to the panel") {
            isInSafeArea(apex = left, target = panel, point = Offset(x = 80f, y = 40f)) shouldBe true
            isInSafeArea(apex = left, target = panel, point = Offset(x = 99f, y = 90f)) shouldBe true
        }

        test("leaves out points that move away from the panel") {
            isInSafeArea(apex = left, target = panel, point = Offset(x = 80f, y = 0f)) shouldBe false
            isInSafeArea(apex = left, target = panel, point = Offset(x = 40f, y = 50f)) shouldBe false
        }
    }

    context("towards a panel on the left") {
        test("measures from the right edge of the panel") {
            isInSafeArea(apex = right, target = panel, point = Offset(x = 220f, y = 50f)) shouldBe true
            isInSafeArea(apex = right, target = panel, point = Offset(x = 220f, y = 0f)) shouldBe false
        }
    }
})
