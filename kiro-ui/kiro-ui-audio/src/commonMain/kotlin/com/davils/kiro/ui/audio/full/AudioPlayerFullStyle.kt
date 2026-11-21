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

package com.davils.kiro.ui.audio.full

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

/**
 * How an [AudioPlayerFull] looks: a now-playing view on the backdrop of the cover with a large cover, the controls
 * below it and the queue beside it.
 *
 * @property padding the padding of the view.
 * @property gap the space between the cover and the controls.
 * @property controlsGap the space between the controls.
 * @property coverSize the largest size of the cover and the width of the controls.
 * @property queueWidth the width of the queue.
 * @property queueGap the space between the rows of the queue.
 * @property queueRowPadding the padding of a row of the queue and of its title.
 * @property queueRowGap the space between the parts of a row of the queue.
 * @property queueRowShape the shape of a row of the queue and of its cover.
 * @property queueCoverSize the size of the cover in a row of the queue.
 * @property queueCoverElevation the elevation of the cover in a row of the queue.
 * @property activeAlpha the alpha of the content color behind the row of the current track.
 * @since 1.0.0
 */
@Immutable
public class AudioPlayerFullStyle(
    public val padding: Dp,
    public val gap: Dp,
    public val controlsGap: Dp,
    public val coverSize: Dp,
    public val queueWidth: Dp,
    public val queueGap: Dp,
    public val queueRowPadding: Dp,
    public val queueRowGap: Dp,
    public val queueRowShape: Shape,
    public val queueCoverSize: Dp,
    public val queueCoverElevation: Dp,
    public val activeAlpha: Float,
) {
    /**
     * Returns a copy of this [AudioPlayerFullStyle] with the given values replaced and every other value kept.
     *
     * @param padding the new value of [padding].
     * @param gap the new value of [gap].
     * @param controlsGap the new value of [controlsGap].
     * @param coverSize the new value of [coverSize].
     * @param queueWidth the new value of [queueWidth].
     * @param queueGap the new value of [queueGap].
     * @param queueRowPadding the new value of [queueRowPadding].
     * @param queueRowGap the new value of [queueRowGap].
     * @param queueRowShape the new value of [queueRowShape].
     * @param queueCoverSize the new value of [queueCoverSize].
     * @param queueCoverElevation the new value of [queueCoverElevation].
     * @param activeAlpha the new value of [activeAlpha].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        padding: Dp = this.padding,
        gap: Dp = this.gap,
        controlsGap: Dp = this.controlsGap,
        coverSize: Dp = this.coverSize,
        queueWidth: Dp = this.queueWidth,
        queueGap: Dp = this.queueGap,
        queueRowPadding: Dp = this.queueRowPadding,
        queueRowGap: Dp = this.queueRowGap,
        queueRowShape: Shape = this.queueRowShape,
        queueCoverSize: Dp = this.queueCoverSize,
        queueCoverElevation: Dp = this.queueCoverElevation,
        activeAlpha: Float = this.activeAlpha,
    ): AudioPlayerFullStyle = AudioPlayerFullStyle(
        padding = padding,
        gap = gap,
        controlsGap = controlsGap,
        coverSize = coverSize,
        queueWidth = queueWidth,
        queueGap = queueGap,
        queueRowPadding = queueRowPadding,
        queueRowGap = queueRowGap,
        queueRowShape = queueRowShape,
        queueCoverSize = queueCoverSize,
        queueCoverElevation = queueCoverElevation,
        activeAlpha = activeAlpha,
    )

    override fun equals(other: Any?): Boolean = other is AudioPlayerFullStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AudioPlayerFullStyle(" +
        "padding=$padding, " +
        "gap=$gap, " +
        "controlsGap=$controlsGap, " +
        "coverSize=$coverSize, " +
        "queueWidth=$queueWidth, " +
        "queueGap=$queueGap, " +
        "queueRowPadding=$queueRowPadding, " +
        "queueRowGap=$queueRowGap, " +
        "queueRowShape=$queueRowShape, " +
        "queueCoverSize=$queueCoverSize, " +
        "queueCoverElevation=$queueCoverElevation, " +
        "activeAlpha=$activeAlpha)"

    private fun values(): List<Any?> = listOf(
        padding,
        gap,
        controlsGap,
        coverSize,
        queueWidth,
        queueGap,
        queueRowPadding,
        queueRowGap,
        queueRowShape,
        queueCoverSize,
        queueCoverElevation,
        activeAlpha,
    )
}
