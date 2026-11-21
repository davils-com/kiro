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

package com.davils.kiro.ui.audio.timeline

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * How a [ClipTimeline] looks and scrolls: its background, the playhead across all tracks, and how far a turn of the
 * wheel scrolls or zooms.
 *
 * @property background the color behind the timeline.
 * @property border the color of the outline of the timeline.
 * @property borderWidth the width of the outline of the timeline.
 * @property radius the corner radius of the timeline.
 * @property playheadColor the color of the playhead.
 * @property playheadWidth the width of the playhead.
 * @property scrollStep how far a step of the wheel scrolls.
 * @property zoomStep the factor a step of the wheel zooms by while the control key is held.
 * @property description what the timeline announces to accessibility services.
 * @since 1.0.0
 */
@Immutable
public class ClipTimelineStyle(
    public val background: Color,
    public val border: Color,
    public val borderWidth: Dp,
    public val radius: Dp,
    public val playheadColor: Color,
    public val playheadWidth: Dp,
    public val scrollStep: Dp,
    public val zoomStep: Float,
    public val description: String,
) {
    /**
     * Returns a copy of this [ClipTimelineStyle] with the given values replaced and every other value kept.
     *
     * @param background the new value of [background].
     * @param border the new value of [border].
     * @param borderWidth the new value of [borderWidth].
     * @param radius the new value of [radius].
     * @param playheadColor the new value of [playheadColor].
     * @param playheadWidth the new value of [playheadWidth].
     * @param scrollStep the new value of [scrollStep].
     * @param zoomStep the new value of [zoomStep].
     * @param description the new value of [description].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        background: Color = this.background,
        border: Color = this.border,
        borderWidth: Dp = this.borderWidth,
        radius: Dp = this.radius,
        playheadColor: Color = this.playheadColor,
        playheadWidth: Dp = this.playheadWidth,
        scrollStep: Dp = this.scrollStep,
        zoomStep: Float = this.zoomStep,
        description: String = this.description,
    ): ClipTimelineStyle = ClipTimelineStyle(
        background = background,
        border = border,
        borderWidth = borderWidth,
        radius = radius,
        playheadColor = playheadColor,
        playheadWidth = playheadWidth,
        scrollStep = scrollStep,
        zoomStep = zoomStep,
        description = description,
    )

    override fun equals(other: Any?): Boolean = other is ClipTimelineStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ClipTimelineStyle(" +
        "background=$background, " +
        "border=$border, " +
        "borderWidth=$borderWidth, " +
        "radius=$radius, " +
        "playheadColor=$playheadColor, " +
        "playheadWidth=$playheadWidth, " +
        "scrollStep=$scrollStep, " +
        "zoomStep=$zoomStep, " +
        "description=$description)"

    private fun values(): List<Any?> = listOf(
        background,
        border,
        borderWidth,
        radius,
        playheadColor,
        playheadWidth,
        scrollStep,
        zoomStep,
        description,
    )
}
