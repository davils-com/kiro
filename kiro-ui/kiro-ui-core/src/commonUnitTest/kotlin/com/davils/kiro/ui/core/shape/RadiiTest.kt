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

package com.davils.kiro.ui.core.shape

import androidx.compose.ui.unit.dp
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class RadiiTest : FunSpec({
    context("from") {
        test("derives the radius scale of the website from 0.625rem") {
            Radii.from() shouldBe Radii(
                sm = 6.dp,
                md = 8.dp,
                lg = 10.dp,
                xl = 14.dp,
                xl2 = 18.dp,
                xl3 = 22.dp,
                xl4 = 26.dp,
            )
        }

        test("scales every step with another base radius") {
            val radii = Radii.from(base = 20.dp)

            radii.sm shouldBe 12.dp
            radii.xl4 shouldBe 52.dp
        }
    }
})
