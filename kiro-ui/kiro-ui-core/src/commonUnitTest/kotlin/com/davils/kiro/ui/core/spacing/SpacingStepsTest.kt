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

package com.davils.kiro.ui.core.spacing

import androidx.compose.ui.unit.dp
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SpacingStepsTest : FunSpec({
    context("invoke") {
        test("measures steps in quarters of a rem like tailwind") {
            val steps = 2.5f

            Spacing()(steps) shouldBe 10.dp
        }

        test("follows a changed unit") {
            val steps = 3f

            Spacing(unit = 5.dp)(steps) shouldBe 15.dp
        }
    }
})
