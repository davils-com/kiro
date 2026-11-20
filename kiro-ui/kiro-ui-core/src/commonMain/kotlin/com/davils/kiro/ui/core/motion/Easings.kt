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

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable

/**
 * The easing curves of the transitions of a theme.
 *
 * @property standard the curve of a change in place.
 * @property enter the curve of something that appears.
 * @property exit the curve of something that disappears.
 * @property ease the curve of the CSS keyword `ease`.
 * @property outExpo a curve that starts fast and settles slowly.
 * @property emphasized the curve of an emphasized change.
 * @since 1.0.0
 */
@Immutable
public class Easings(
    public val standard: Easing = CubicBezierEasing(a = 0.4f, b = 0f, c = 0.2f, d = 1f),
    public val enter: Easing = CubicBezierEasing(a = 0f, b = 0f, c = 0.2f, d = 1f),
    public val exit: Easing = CubicBezierEasing(a = 0.4f, b = 0f, c = 1f, d = 1f),
    public val ease: Easing = CubicBezierEasing(a = 0.25f, b = 0.1f, c = 0.25f, d = 1f),
    public val outExpo: Easing = CubicBezierEasing(a = 0.16f, b = 1f, c = 0.3f, d = 1f),
    public val emphasized: Easing = CubicBezierEasing(a = 0.2f, b = 0f, c = 0f, d = 1f),
) {
    /**
     * Returns a copy of this [Easings] with the given values replaced and every other value kept.
     *
     * @param standard the new value of [standard].
     * @param enter the new value of [enter].
     * @param exit the new value of [exit].
     * @param ease the new value of [ease].
     * @param outExpo the new value of [outExpo].
     * @param emphasized the new value of [emphasized].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        standard: Easing = this.standard,
        enter: Easing = this.enter,
        exit: Easing = this.exit,
        ease: Easing = this.ease,
        outExpo: Easing = this.outExpo,
        emphasized: Easing = this.emphasized,
    ): Easings = Easings(
        standard = standard,
        enter = enter,
        exit = exit,
        ease = ease,
        outExpo = outExpo,
        emphasized = emphasized,
    )

    override fun equals(other: Any?): Boolean = other is Easings && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Easings(" +
        "standard=$standard, " +
        "enter=$enter, " +
        "exit=$exit, " +
        "ease=$ease, " +
        "outExpo=$outExpo, " +
        "emphasized=$emphasized)"

    private fun values(): List<Any?> = listOf(
        standard,
        enter,
        exit,
        ease,
        outExpo,
        emphasized,
    )
}
