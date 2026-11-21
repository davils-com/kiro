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

package com.davils.kiro.ui.audio.palette

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * The colors taken from the cover of a track, which tint the audio players.
 *
 * @property dominant the color that covers most of the cover.
 * @property vibrant the most saturated color of the cover, the accent of the players.
 * @property muted a darker shade of [dominant] for backgrounds.
 * @property onDominant the content color that reads best on [dominant].
 * @since 1.0.0
 */
@Immutable
public class CoverPalette(
    public val dominant: Color,
    public val vibrant: Color,
    public val muted: Color,
    public val onDominant: Color,
) {
    /**
     * Returns a copy of this [CoverPalette] with the given values replaced and every other value kept.
     *
     * @param dominant the new value of [dominant].
     * @param vibrant the new value of [vibrant].
     * @param muted the new value of [muted].
     * @param onDominant the new value of [onDominant].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        dominant: Color = this.dominant,
        vibrant: Color = this.vibrant,
        muted: Color = this.muted,
        onDominant: Color = this.onDominant,
    ): CoverPalette = CoverPalette(
        dominant = dominant,
        vibrant = vibrant,
        muted = muted,
        onDominant = onDominant,
    )

    override fun equals(other: Any?): Boolean = other is CoverPalette && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CoverPalette(" +
        "dominant=$dominant, " +
        "vibrant=$vibrant, " +
        "muted=$muted, " +
        "onDominant=$onDominant)"

    private fun values(): List<Any?> = listOf(
        dominant,
        vibrant,
        muted,
        onDominant,
    )
}
