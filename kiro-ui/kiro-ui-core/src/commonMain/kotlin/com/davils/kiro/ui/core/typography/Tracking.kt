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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em

private const val TIGHTER: Double = 0.05

private const val TIGHT: Double = 0.025

/**
 * The letter spacings of a theme, relative to the font size.
 *
 * @property tighter the tightest spacing.
 * @property tight a tight spacing.
 * @property normal the normal spacing.
 * @property wide a wide spacing.
 * @property wider a wider spacing.
 * @property widest the widest spacing.
 * @since 0.1.0
 */
@Immutable
public class Tracking(
    public val tighter: TextUnit = -TIGHTER.em,
    public val tight: TextUnit = -TIGHT.em,
    public val normal: TextUnit = 0.em,
    public val wide: TextUnit = 0.025.em,
    public val wider: TextUnit = 0.05.em,
    public val widest: TextUnit = 0.1.em,
) {
    /**
     * Returns a copy of this [Tracking] with the given values replaced and every other value kept.
     *
     * @param tighter the new value of [tighter].
     * @param tight the new value of [tight].
     * @param normal the new value of [normal].
     * @param wide the new value of [wide].
     * @param wider the new value of [wider].
     * @param widest the new value of [widest].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(
        tighter: TextUnit = this.tighter,
        tight: TextUnit = this.tight,
        normal: TextUnit = this.normal,
        wide: TextUnit = this.wide,
        wider: TextUnit = this.wider,
        widest: TextUnit = this.widest,
    ): Tracking = Tracking(
        tighter = tighter,
        tight = tight,
        normal = normal,
        wide = wide,
        wider = wider,
        widest = widest,
    )

    override fun equals(other: Any?): Boolean = other is Tracking && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Tracking(" +
        "tighter=$tighter, " +
        "tight=$tight, " +
        "normal=$normal, " +
        "wide=$wide, " +
        "wider=$wider, " +
        "widest=$widest)"

    private fun values(): List<Any?> = listOf(
        tighter,
        tight,
        normal,
        wide,
        wider,
        widest,
    )
}
