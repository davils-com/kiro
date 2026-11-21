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

package com.davils.kiro.ui.motion.quality

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import kotlin.time.Duration.Companion.milliseconds

private const val PATIENCE: Int = 30

class QualityPolicyTest : FunSpec({
    test("a fixed policy is a value") {
        val fixed = QualityPolicy.Fixed(level = QualityLevel.Low)

        fixed.copy() shouldBe fixed
        fixed.copy().hashCode() shouldBe fixed.hashCode()
        fixed.copy(level = QualityLevel.High) shouldNotBe fixed
        fixed.toString() shouldContain "Low"
        fixed.equals(QualityLevel.Low) shouldBe false
    }

    test("an adaptive policy is a value") {
        val adaptive = QualityPolicy.Adaptive(
            budget = 16.milliseconds,
            initial = QualityLevel.High,
            minimum = QualityLevel.Low,
            maximum = QualityLevel.High,
            headroom = 0.5f,
            smoothing = 0.1f,
            patience = PATIENCE,
        )

        adaptive.copy() shouldBe adaptive
        adaptive.copy().hashCode() shouldBe adaptive.hashCode()
        adaptive.copy(patience = 1) shouldNotBe adaptive
        adaptive.toString() shouldContain "patience=$PATIENCE"
        adaptive.equals(QualityLevel.Low) shouldBe false
    }

    test("every level draws a larger share of the detail") {
        val densities = QualityLevel.entries.map { level -> level.density }

        densities shouldBe densities.sorted()
        QualityLevel.High.density shouldBe 1f
    }
})
