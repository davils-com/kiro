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

package com.davils.kiro.ui.audio.spectrum

import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.audio.fixture.range
import com.davils.kiro.ui.audio.fixture.spectrumOf
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val PUSHED: Long = 3L
private const val NEWEST: Int = 6

class SpectrogramStateTest : FunSpec({
    test("keeps the newest spectra up to its capacity") {
        val state = SpectrogramState(capacity = 2)
        state.range shouldBe null
        state.push(spectrumOf(bands = 4))
        state.push(spectrumOf(bands = 5))
        state.push(spectrumOf(bands = 6))
        state.count shouldBe PUSHED
        state.range shouldBe range
        state.columnAt(index = PUSHED - 1L)?.size shouldBe NEWEST
        state.columnAt(index = 1L)?.size shouldBe NEWEST - 1
        state.clear()
        state.count shouldBe 0L
        state.range shouldBe null
        state.columnAt(0L) shouldBe null
    }

    test("refuses an empty history") {
        shouldThrow<IllegalArgumentException> { SpectrogramState(capacity = 0) }
    }

    test("blends the colors of its ramp") {
        val ramp = listOf(Color.Black, Color.White)
        rampAt(fraction = 0f, ramp = ramp) shouldBe Color.Black
        rampAt(fraction = 2f, ramp = ramp) shouldBe Color.White
        rampAt(fraction = 0.5f, ramp = listOf(Color.Red)) shouldBe Color.Red
    }
})
