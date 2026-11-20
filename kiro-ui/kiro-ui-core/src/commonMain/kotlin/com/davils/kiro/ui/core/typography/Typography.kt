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

package com.davils.kiro.ui.core.typography

import androidx.compose.runtime.Immutable

/**
 * The typography of a theme: font families, type scale, letter spacing and line heights.
 *
 * @property families the font families; [FontFamilies.Bundled] stands for the fonts that ship with Kiro.
 * @property scale the type scale.
 * @property tracking the letter spacings.
 * @property leading the line heights.
 * @since 1.0.0
 */
@Immutable
public class Typography(
    public val families: FontFamilies = FontFamilies.Bundled,
    public val scale: TextScale = TextScale(),
    public val tracking: Tracking = Tracking(),
    public val leading: Leading = Leading(),
) {
    /**
     * Returns a copy of this [Typography] with the given values replaced and every other value kept.
     *
     * @param families the new value of [families].
     * @param scale the new value of [scale].
     * @param tracking the new value of [tracking].
     * @param leading the new value of [leading].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        families: FontFamilies = this.families,
        scale: TextScale = this.scale,
        tracking: Tracking = this.tracking,
        leading: Leading = this.leading,
    ): Typography = Typography(
        families = families,
        scale = scale,
        tracking = tracking,
        leading = leading,
    )

    override fun equals(other: Any?): Boolean = other is Typography && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Typography(" +
        "families=$families, " +
        "scale=$scale, " +
        "tracking=$tracking, " +
        "leading=$leading)"

    private fun values(): List<Any?> = listOf(
        families,
        scale,
        tracking,
        leading,
    )
}
