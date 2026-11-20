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

package com.davils.kiro.ui.core

import com.davils.kiro.ui.core.fixture.tokenSamples
import com.davils.kiro.ui.core.typography.FontFamilies
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

class TokenValuesTest : FunSpec({
    context("every token") {
        tokenSamples().forEach { sample ->
            test("${sample.name} equals its copy and differs from a copy with a changed value") {
                sample.copy shouldBe sample.value
                sample.copy.hashCode() shouldBe sample.value.hashCode()
                sample.changed shouldNotBe sample.value
                sample.value shouldNotBe Any()
            }

            test("${sample.name} describes itself by its name and values") {
                sample.value.toString() shouldStartWith sample.name
            }
        }
    }

    context("font families") {
        test("tell the bundled fonts apart from equal families") {
            FontFamilies.Bundled.toString() shouldBe "FontFamilies.Bundled"
            FontFamilies.Bundled.copy() shouldBe FontFamilies.System
        }
    }
})
