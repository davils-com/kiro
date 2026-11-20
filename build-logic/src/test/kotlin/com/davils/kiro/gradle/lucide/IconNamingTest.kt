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

package com.davils.kiro.gradle.lucide

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class IconNamingTest : FunSpec({
    context("propertyNameOf") {
        test("turns a kebab case icon name into pascal case") {
            propertyNameOf("arrow-up-right") shouldBe "ArrowUpRight"
        }

        test("keeps digits inside a name") {
            propertyNameOf("rotate-3d") shouldBe "Rotate3d"
        }

        test("refuses a name that gives no identifier") {
            shouldThrow<IllegalArgumentException> { propertyNameOf("3d-box") }
        }
    }
})
