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

package com.davils.kiro.ui.motion.typewriter

import kotlin.time.Duration

/**
 * Writes a [TypewriterScript] step by step, inside [typewriter].
 *
 * @since 1.0.0
 */
public class TypewriterScriptBuilder internal constructor() {
    private val steps: MutableList<TypewriterStep> = mutableListOf()
    private var isRepeating: Boolean = false

    /**
     * Types a text after the text so far.
     *
     * @param text the text to type.
     * @since 1.0.0
     */
    public fun type(text: String) {
        steps += TypeStep(text = text)
    }

    /**
     * Waits with the text as it is.
     *
     * @param duration how long to wait, or `null` for the [TypewriterStyle.hold] of the style.
     * @since 1.0.0
     */
    public fun pause(duration: Duration? = null) {
        steps += PauseStep(duration = duration)
    }

    /**
     * Deletes characters from the end of the text.
     *
     * @param count how many characters to delete, or `null` for the whole text.
     * @since 1.0.0
     */
    public fun delete(count: Int? = null) {
        steps += DeleteStep(count = count)
    }

    /**
     * Lets the script start over after its last step, forever.
     *
     * @since 1.0.0
     */
    public fun repeat() {
        isRepeating = true
    }

    internal fun build(): TypewriterScript = TypewriterScript(steps = steps.toList(), isRepeating = isRepeating)
}
