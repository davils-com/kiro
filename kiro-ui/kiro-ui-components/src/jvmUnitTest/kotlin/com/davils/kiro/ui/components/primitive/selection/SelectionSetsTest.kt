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

package com.davils.kiro.ui.components.primitive.selection

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SelectionSetsTest : FunSpec({
    context("toggledIn") {
        test("keeps one value in single mode and several in multiple mode") {
            toggledIn(selected = setOf(1), value = 2, isMultiple = false) shouldBe setOf(2)
            toggledIn(selected = setOf(1), value = 1, isMultiple = false) shouldBe emptySet()
            toggledIn(selected = setOf(1), value = 2, isMultiple = true) shouldBe setOf(1, 2)
        }
    }
})
