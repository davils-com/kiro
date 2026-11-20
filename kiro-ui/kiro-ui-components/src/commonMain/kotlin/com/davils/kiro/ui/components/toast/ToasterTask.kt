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

import kotlinx.coroutines.CancellationException

/**
 * Runs [block] while a loading toast shows its progress, then turns the toast into a success or an error toast.
 *
 * The toast starts with an indeterminate progress bar; each report of [block] makes it determinate. When [block]
 * returns, the toast shows [success] with the default duration; when it throws an exception, the toast shows [error]
 * and the exception is thrown again. When the coroutine is cancelled, the toast is dismissed.
 *
 * @param T the type of the result of [block].
 * @param loading the title of the toast while [block] runs.
 * @param success returns the title of the toast from the result of [block].
 * @param error returns the title of the toast from the exception of [block].
 * @param block the work, which may report its progress as a fraction from `0` to `1`; values outside are clamped.
 * @return the result of [block].
 * @throws Exception the exception that [block] threw.
 * @since 1.0.0
 */
public suspend fun <T> ToasterState.task(
    loading: String,
    success: (T) -> String,
    error: (Throwable) -> String,
    block: suspend (report: (Float) -> Unit) -> T,
): T {
    val id = show(title = loading, type = ToastType.Loading, progress = ToastProgress.Indeterminate)
    val report = { value: Float ->
        update(
            id,
        ) { copy(progress = ToastProgress.Determinate(value.coerceIn(minimumValue = 0f, maximumValue = 1f))) }
    }
    var isSettled = false
    try {
        val result = block(report)
        finish(id = id, title = success(result), type = ToastType.Success)
        isSettled = true
        return result
    } catch (cancelled: CancellationException) {
        throw cancelled
    } catch (expected: Exception) {
        finish(id = id, title = error(expected), type = ToastType.Error)
        isSettled = true
        throw expected
    } finally {
        if (!isSettled) dismiss(id)
    }
}

private fun ToasterState.finish(id: ToastId, title: String, type: ToastType) {
    update(id) {
        copy(title = title, type = type, progress = null, duration = ToasterState.DefaultDuration, isDismissible = true)
    }
}
