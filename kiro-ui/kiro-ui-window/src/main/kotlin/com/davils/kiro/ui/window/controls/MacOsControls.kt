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

package com.davils.kiro.ui.window.controls

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.window.titlebar.TitleBarStyle
import com.davils.kiro.ui.window.titlebar.WindowActions

/**
 * The room for the traffic lights that macOS draws itself at the physical left of the bar.
 *
 * @property inset the width the traffic lights need.
 * @since 1.0.0
 */
@Immutable
public class MacOsControls(
    public val inset: Dp,
) : WindowControls {
    override val placement: ControlPlacement
        get() = ControlPlacement.Start

    /**
     * Returns a copy of this [MacOsControls] with the given values replaced and every other value kept.
     *
     * @param inset the new value of [inset].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(inset: Dp = this.inset): MacOsControls = MacOsControls(
        inset = inset,
    )

    override fun equals(other: Any?): Boolean = other is MacOsControls && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MacOsControls(" +
        "inset=$inset)"

    private fun values(): List<Any?> = listOf(
        inset,
    )

    @Composable
    override fun Content(actions: WindowActions, style: TitleBarStyle) {
        Spacer(modifier = Modifier.width(inset))
    }
}
