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

package com.davils.kiro.ui.audio.card

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

/**
 * How an [AudioPlayerCard] looks: a card on the backdrop of the cover with the cover, the track, the timeline and
 * the playback stacked.
 *
 * @property width the width of the card.
 * @property padding the padding of the card.
 * @property gap the space between the parts of the card.
 * @property shape the shape of the card.
 * @since 1.0.0
 */
@Immutable
public class AudioPlayerCardStyle(
    public val width: Dp,
    public val padding: Dp,
    public val gap: Dp,
    public val shape: Shape,
) {
    /**
     * Returns a copy of this [AudioPlayerCardStyle] with the given values replaced and every other value kept.
     *
     * @param width the new value of [width].
     * @param padding the new value of [padding].
     * @param gap the new value of [gap].
     * @param shape the new value of [shape].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        width: Dp = this.width,
        padding: Dp = this.padding,
        gap: Dp = this.gap,
        shape: Shape = this.shape,
    ): AudioPlayerCardStyle = AudioPlayerCardStyle(
        width = width,
        padding = padding,
        gap = gap,
        shape = shape,
    )

    override fun equals(other: Any?): Boolean = other is AudioPlayerCardStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AudioPlayerCardStyle(" +
        "width=$width, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "shape=$shape)"

    private fun values(): List<Any?> = listOf(
        width,
        padding,
        gap,
        shape,
    )
}
