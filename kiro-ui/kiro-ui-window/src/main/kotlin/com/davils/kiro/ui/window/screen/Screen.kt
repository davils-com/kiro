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

package com.davils.kiro.ui.window.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.DpRect

/**
 * A screen attached to the computer, in the coordinates of the desktop that spans all screens.
 *
 * @property id the identifier of the screen, stable while the screen stays attached.
 * @property bounds the whole area of the screen.
 * @property workArea the area of the screen that windows may use, without the bars of the operating system.
 * @property isPrimary whether the screen is the primary screen of the desktop.
 * @since 1.0.0
 */
@Immutable
public class Screen(
    public val id: ScreenId,
    public val bounds: DpRect,
    public val workArea: DpRect,
    public val isPrimary: Boolean,
) {
    /**
     * Returns a copy of this [Screen] with the given values replaced and every other value kept.
     *
     * @param id the new value of [id].
     * @param bounds the new value of [bounds].
     * @param workArea the new value of [workArea].
     * @param isPrimary the new value of [isPrimary].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        id: ScreenId = this.id,
        bounds: DpRect = this.bounds,
        workArea: DpRect = this.workArea,
        isPrimary: Boolean = this.isPrimary,
    ): Screen = Screen(
        id = id,
        bounds = bounds,
        workArea = workArea,
        isPrimary = isPrimary,
    )

    override fun equals(other: Any?): Boolean = other is Screen && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Screen(" +
        "id=$id, " +
        "bounds=$bounds, " +
        "workArea=$workArea, " +
        "isPrimary=$isPrimary)"

    private fun values(): List<Any?> = listOf(
        id,
        bounds,
        workArea,
        isPrimary,
    )
}
