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

package com.davils.kiro.ui.motion.art

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

private const val SALTS: Int = 64

class ArtSeedTest : FunSpec({
    test("derives the same seed from the same salt") {
        ArtSeed(value = 7).derive(salt = 3) shouldBe ArtSeed(value = 7).derive(salt = 3)
    }

    test("derives different seeds for different salts and seeds") {
        val derived = (0 until SALTS).map { salt -> ArtSeed(value = 7).derive(salt = salt) }.toSet()

        derived shouldHaveSize SALTS
        (ArtSeed(value = 8).derive(salt = 0) == ArtSeed(value = 7).derive(salt = 0)) shouldBe false
    }
})
