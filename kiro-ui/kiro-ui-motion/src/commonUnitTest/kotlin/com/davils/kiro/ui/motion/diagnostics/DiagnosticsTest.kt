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

package com.davils.kiro.ui.motion.diagnostics

import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.nanoseconds

private const val MILLISECOND: Long = 1_000_000L
private const val SHORT: Long = 10L
private const val LONG: Long = 30L
private const val RATE: Float = 50f
private const val TOLERANCE: Float = 0.01f
private const val LAYERS: Int = 3
private const val ROUNDED_RATE: Float = 59.96f
private const val FRAME_NANOS: Long = 16_680_000L

private val diagnostics: ArtDiagnostics = ArtDiagnostics(
    framesPerSecond = ROUNDED_RATE,
    frameTime = FRAME_NANOS.nanoseconds,
    layers = LAYERS,
    quality = QualityLevel.High,
)

class DiagnosticsTest : FunSpec({
    test("reports after every interval of frames") {
        val meter = FrameMeter(interval = 2)

        meter.record(frameNanos = SHORT * MILLISECOND, layers = LAYERS, quality = QualityLevel.High).shouldBeNull()
        val report = meter.record(frameNanos = LONG * MILLISECOND, layers = LAYERS, quality = QualityLevel.High)

        report.shouldNotBeNull()
        report.framesPerSecond shouldBe (RATE plusOrMinus TOLERANCE)
        report.frameTime shouldBe 20.milliseconds
        report.layers shouldBe LAYERS
        meter.record(frameNanos = SHORT * MILLISECOND, layers = LAYERS, quality = QualityLevel.High).shouldBeNull()
    }

    test("skips frames without time and treats an interval below one as one") {
        val meter = FrameMeter(interval = 0)

        meter.record(frameNanos = 0L, layers = 1, quality = QualityLevel.Low).shouldBeNull()
        meter.record(frameNanos = SHORT * MILLISECOND, layers = 1, quality = QualityLevel.Low).shouldNotBeNull()
    }

    test("describes diagnostics in one line") {
        diagnosticsText(diagnostics) shouldBe "60.0 fps · 16.7 ms · 3 layers · High"
    }

    test("is a value") {
        diagnostics.copy() shouldBe diagnostics
        diagnostics.copy().hashCode() shouldBe diagnostics.hashCode()
        diagnostics.copy(layers = 1) shouldNotBe diagnostics
        diagnostics.toString() shouldContain "layers=$LAYERS"
        diagnostics.equals(QualityLevel.High) shouldBe false
    }
})
