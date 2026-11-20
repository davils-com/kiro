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

package com.davils.kiro.ui.components.toast

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest

private const val HALF: Float = 0.5f
private const val UPLOADING: String = "Uploading"
private const val DONE: String = "Done"
private const val RESULT: Int = 42

class ToasterTaskTest : FunSpec({
    context("task") {
        test("reports progress and turns into a success") {
            val toaster = ToasterState()

            runTest {
                val result = toaster.task(
                    loading = UPLOADING,
                    success = { value: Int -> "Uploaded $value" },
                    error = { "Failed" },
                ) { report ->
                    report(HALF)
                    toaster.toasts.single().progress shouldBe ToastProgress.Determinate(HALF)
                    RESULT
                }

                result shouldBe RESULT
            }

            val toast = toaster.toasts.single()
            toast.title shouldBe "Uploaded $RESULT"
            toast.type shouldBe ToastType.Success
            toast.progress shouldBe null
            toast.isDismissible shouldBe true
        }

        test("turns into an error and rethrows the failure") {
            val toaster = ToasterState()

            runTest {
                shouldThrow<IllegalStateException> {
                    toaster.task<Int>(
                        loading = UPLOADING,
                        success = { DONE },
                        error = { failure -> failure.message.orEmpty() },
                    ) {
                        error("Disk full")
                    }
                }
            }

            toaster.toasts.single().title shouldBe "Disk full"
            toaster.toasts.single().type shouldBe ToastType.Error
        }

        test("leaves quietly when the task is cancelled") {
            val toaster = ToasterState()

            runTest {
                shouldThrow<CancellationException> {
                    toaster.task<Int>(loading = UPLOADING, success = { DONE }, error = { "Failed" }) {
                        throw CancellationException("stopped")
                    }
                }
            }

            toaster.isLeaving(toaster.toasts.single().id) shouldBe true
        }
    }
})
