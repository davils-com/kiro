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

package com.davils.kiro.ui.motion.input

import androidx.compose.ui.geometry.Offset
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain

private const val DEFAULT: Float = 0.25f
private const val FED: Float = 0.75f

private val level: SignalKey = SignalKey(name = "level", default = DEFAULT)

class ArtInputsTest : FunSpec({
    test("reads a signal or its default") {
        val fed = ArtInputs.None.copy(signals = mapOf(level to FED))

        ArtInputs.None[level] shouldBe DEFAULT
        fed[level] shouldBe FED
    }

    test("is a value") {
        val inputs = ArtInputs.None.copy(pointer = Offset(x = 1f, y = 2f), progress = FED)

        inputs.copy() shouldBe inputs
        inputs.copy().hashCode() shouldBe inputs.hashCode()
        inputs shouldNotBe ArtInputs.None
        inputs.toString() shouldContain "progress=$FED"
        ArtInputs.None.toString() shouldContain "pointer=null"
        level.toString() shouldBe "SignalKey(name=level)"
        inputs.equals(level) shouldBe false
    }
})
