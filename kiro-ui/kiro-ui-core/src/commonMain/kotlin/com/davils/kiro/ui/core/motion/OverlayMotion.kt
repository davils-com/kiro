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

package com.davils.kiro.ui.core.motion

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * How overlays such as popovers, menus and tooltips appear and disappear.
 *
 * @property enterDuration the duration of appearing.
 * @property exitDuration the duration of disappearing.
 * @property initialAlpha the alpha an overlay starts from and fades back to.
 * @property initialScale the scale an overlay starts from and shrinks back to.
 * @property slideDistance the distance an overlay slides in from its anchor.
 * @since 1.0.0
 */
@Immutable
public class OverlayMotion(
    public val enterDuration: Duration = 180.milliseconds,
    public val exitDuration: Duration = 40.milliseconds,
    public val initialAlpha: Float = 0f,
    public val initialScale: Float = 0.95f,
    public val slideDistance: Dp = 8.dp,
) {
    /**
     * Returns a copy of this [OverlayMotion] with the given values replaced and every other value kept.
     *
     * @param enterDuration the new value of [enterDuration].
     * @param exitDuration the new value of [exitDuration].
     * @param initialAlpha the new value of [initialAlpha].
     * @param initialScale the new value of [initialScale].
     * @param slideDistance the new value of [slideDistance].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        enterDuration: Duration = this.enterDuration,
        exitDuration: Duration = this.exitDuration,
        initialAlpha: Float = this.initialAlpha,
        initialScale: Float = this.initialScale,
        slideDistance: Dp = this.slideDistance,
    ): OverlayMotion = OverlayMotion(
        enterDuration = enterDuration,
        exitDuration = exitDuration,
        initialAlpha = initialAlpha,
        initialScale = initialScale,
        slideDistance = slideDistance,
    )

    override fun equals(other: Any?): Boolean = other is OverlayMotion && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "OverlayMotion(" +
        "enterDuration=$enterDuration, " +
        "exitDuration=$exitDuration, " +
        "initialAlpha=$initialAlpha, " +
        "initialScale=$initialScale, " +
        "slideDistance=$slideDistance)"

    private fun values(): List<Any?> = listOf(
        enterDuration,
        exitDuration,
        initialAlpha,
        initialScale,
        slideDistance,
    )
}
