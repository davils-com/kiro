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

package com.davils.kiro.ui.window.application

import java.util.concurrent.atomic.AtomicBoolean

/**
 * A request of the operating system to quit the application, such as Quit in the application menu of macOS, that the
 * application answers once: it confirms to let the application quit or cancels to keep it running, for example while
 * a document has unsaved changes. Only the first answer counts; every later answer has no effect.
 *
 * The operating system waits for the answer, so an application must answer every request, if need be after asking the
 * user.
 *
 * @param onConfirm called by the first answer when it is [confirm].
 * @param onCancel called by the first answer when it is [cancel].
 * @since 1.0.0
 */
public class QuitRequest(private val onConfirm: () -> Unit, private val onCancel: () -> Unit) {
    private val answered = AtomicBoolean(false)

    /**
     * Whether the request has been confirmed or cancelled.
     *
     * @since 1.0.0
     */
    public val isAnswered: Boolean
        get() = answered.get()

    /**
     * Lets the application quit, unless the request has already been answered.
     *
     * @since 1.0.0
     */
    public fun confirm() {
        if (!answer()) return
        onConfirm()
    }

    /**
     * Keeps the application running, unless the request has already been answered.
     *
     * @since 1.0.0
     */
    public fun cancel() {
        if (!answer()) return
        onCancel()
    }

    override fun toString(): String = "QuitRequest(isAnswered=$isAnswered)"

    private fun answer(): Boolean = answered.compareAndSet(false, true)
}
