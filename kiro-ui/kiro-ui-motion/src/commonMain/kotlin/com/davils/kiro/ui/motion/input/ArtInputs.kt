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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset

/**
 * What a scene knows about the world outside it in one frame: the pointer, the progress of a task, the scroll and the
 * signals an application feeds in.
 *
 * @property pointer the smoothed position of the pointer over the scene, or `null` while it is outside.
 * @property velocity the velocity of the smoothed pointer, in pixels per second.
 * @property isPressed whether a pointer button is down over the scene.
 * @property progress the progress of a task between `0` and `1`, such as the start of an application, or `null`.
 * @property scroll the scroll position that layers follow with their parallax, in pixels.
 * @property signals the signals an application feeds in.
 * @since 1.0.0
 */
@Immutable
public class ArtInputs(
    public val pointer: Offset?,
    public val velocity: Offset,
    public val isPressed: Boolean,
    public val progress: Float?,
    public val scroll: Float,
    public val signals: Map<SignalKey, Float>,
) {
    /**
     * Returns the value of a signal, or its default while the application has not fed one.
     *
     * @param key the key of the signal.
     * @return the value of the signal.
     * @since 1.0.0
     */
    public operator fun get(key: SignalKey): Float = signals[key] ?: key.default

    /**
     * Returns a copy of these inputs with the given values replaced and every other value kept.
     *
     * @param pointer the new value of [pointer].
     * @param velocity the new value of [velocity].
     * @param isPressed the new value of [isPressed].
     * @param progress the new value of [progress].
     * @param scroll the new value of [scroll].
     * @param signals the new value of [signals].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        pointer: Offset? = this.pointer,
        velocity: Offset = this.velocity,
        isPressed: Boolean = this.isPressed,
        progress: Float? = this.progress,
        scroll: Float = this.scroll,
        signals: Map<SignalKey, Float> = this.signals,
    ): ArtInputs = ArtInputs(
        pointer = pointer,
        velocity = velocity,
        isPressed = isPressed,
        progress = progress,
        scroll = scroll,
        signals = signals,
    )

    override fun equals(other: Any?): Boolean = other is ArtInputs && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ArtInputs(pointer=${pointer ?: "null"}, velocity=$velocity, " +
        "isPressed=$isPressed, progress=${progress ?: "null"}, scroll=$scroll, signals=$signals)"

    private fun values(): List<Any?> = listOf(pointer, velocity, isPressed, progress, scroll, signals)

    /**
     * Holds the inputs of a scene that nothing reaches.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * No pointer, no progress, no scroll and no signals.
         *
         * @since 1.0.0
         */
        public val None: ArtInputs = ArtInputs(
            pointer = null,
            velocity = Offset.Zero,
            isPressed = false,
            progress = null,
            scroll = 0f,
            signals = emptyMap(),
        )
    }
}
