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

package com.davils.kiro.ui.window.splash

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

private const val HALF: Float = 0.5f
private const val QUARTER: Float = 0.25f

private val done: SplashStep = SplashStep(id = "a", label = "a", status = SplashStepStatus.Done)
private val running: SplashStep = SplashStep(id = "b", label = "b", status = SplashStepStatus.Running)
private val failedStep: SplashStep = SplashStep(id = "c", label = "c", status = SplashStepStatus.Failed)
private val pending: SplashStep = SplashStep(id = "d", label = "d", status = SplashStepStatus.Pending)
private val skipped: SplashStep = SplashStep(id = "e", label = "e", status = SplashStepStatus.Skipped)

class SplashTest : FunSpec({
    context("current step") {
        test("prefers a failed step, then the running one, then the last finished one") {
            val later = done.copy(id = "f", label = "f")

            Splash(name = NAME, steps = listOf(done, running, failedStep)).current shouldBe failedStep
            Splash(name = NAME, steps = listOf(done, running, pending)).current shouldBe running
            Splash(name = NAME, steps = listOf(done, later, pending)).current shouldBe later
            Splash(name = NAME, steps = listOf(pending, skipped)).current shouldBe null
            Splash(name = NAME).current shouldBe null
        }
    }

    context("failure") {
        test("knows whether the start failed") {
            Splash(name = NAME).isFailed shouldBe false
            Splash(name = NAME, failure = FAILURE).isFailed shouldBe true
        }
    }

    context("fraction") {
        test("counts the steps that are done or skipped") {
            Splash(name = NAME, steps = listOf(done, skipped, running, pending)).fraction shouldBe HALF
            Splash(name = NAME, steps = listOf(failedStep, pending)).fraction shouldBe 0f
            Splash(name = NAME, steps = listOf(done, skipped)).fraction shouldBe 1f
        }

        test("prefers an explicit progress and keeps it between 0 and 1") {
            Splash(name = NAME, steps = listOf(pending), progress = QUARTER).fraction shouldBe QUARTER
            Splash(name = NAME, progress = 2f).fraction shouldBe 1f
            Splash(name = NAME, progress = -1f).fraction shouldBe 0f
        }

        test("is unknown without steps and without a progress") {
            Splash(name = NAME).fraction shouldBe null
        }
    }

    context("labels") {
        test("names the start, the buttons, the version and every status in English") {
            val labels = SplashMessages.labels

            labels.starting shouldBe "Starting…"
            labels.close shouldBe CLOSE
            labels.retry shouldBe RETRY
            labels.version("1.2.3") shouldBe "Version 1.2.3"
            SplashStepStatus.entries.map(labels.status) shouldBe
                listOf("Pending", "Running", "Done", "Failed", "Skipped")
        }

        test("describes every value, also the missing ones") {
            Splash(name = NAME).toString() shouldContain "version=null"
            failed.copy(progress = 1f).toString() shouldContain "failure=$FAILURE"
            sample.toString() shouldContain "vendor=$VENDOR"
        }

        test("shows the label of the current step or that the start begins") {
            sample.stepText(SplashMessages.labels) shouldBe CONFIGURING
            Splash(name = NAME).stepText(SplashMessages.labels) shouldBe "Starting…"
        }
    }
})
