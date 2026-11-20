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

/**
 * The line heights of a theme, relative to the font size.
 *
 * @property none a line height equal to the font size.
 * @property tight a tight line height.
 * @property snug a snug line height.
 * @property normal the normal line height.
 * @property relaxed a relaxed line height.
 * @property loose a loose line height.
 * @since 1.0.0
 */
@Immutable
public class Leading(
    public val none: TextUnit = 1.em,
    public val tight: TextUnit = 1.25.em,
    public val snug: TextUnit = 1.375.em,
    public val normal: TextUnit = 1.5.em,
    public val relaxed: TextUnit = 1.625.em,
    public val loose: TextUnit = 2.em,
) {
    /**
     * Returns a copy of this [Leading] with the given values replaced and every other value kept.
     *
     * @param none the new value of [none].
     * @param tight the new value of [tight].
     * @param snug the new value of [snug].
     * @param normal the new value of [normal].
     * @param relaxed the new value of [relaxed].
     * @param loose the new value of [loose].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        none: TextUnit = this.none,
        tight: TextUnit = this.tight,
        snug: TextUnit = this.snug,
        normal: TextUnit = this.normal,
        relaxed: TextUnit = this.relaxed,
        loose: TextUnit = this.loose,
    ): Leading = Leading(
        none = none,
        tight = tight,
        snug = snug,
        normal = normal,
        relaxed = relaxed,
        loose = loose,
    )

    override fun equals(other: Any?): Boolean = other is Leading && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Leading(" +
        "none=$none, " +
        "tight=$tight, " +
        "snug=$snug, " +
        "normal=$normal, " +
        "relaxed=$relaxed, " +
        "loose=$loose)"

    private fun values(): List<Any?> = listOf(
        none,
        tight,
        snug,
        normal,
        relaxed,
        loose,
    )
}
