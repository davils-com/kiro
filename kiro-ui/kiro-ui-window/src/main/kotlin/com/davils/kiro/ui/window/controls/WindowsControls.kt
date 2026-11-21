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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.window.titlebar.TitleBarStyle
import com.davils.kiro.ui.window.titlebar.WindowActions

/**
 * The square caption buttons of Windows at the full height of the bar, always at its physical right.
 *
 * @property buttons the buttons in the order they show.
 * @property buttonWidth the width of a button.
 * @property glyphSize the size of the glyph of a button.
 * @property strokeWidth the width of the lines of a glyph.
 * @property restoreShift how far the back square of the restore glyph is offset, as a fraction of the glyph.
 * @since 1.0.0
 */
@Immutable
public class WindowsControls(
    public val buttons: List<WindowButton>,
    public val buttonWidth: Dp,
    public val glyphSize: Dp,
    public val strokeWidth: Dp,
    public val restoreShift: Float,
) : WindowControls {
    override val placement: ControlPlacement
        get() = ControlPlacement.End

    /**
     * Returns a copy of this [WindowsControls] with the given values replaced and every other value kept.
     *
     * @param buttons the new value of [buttons].
     * @param buttonWidth the new value of [buttonWidth].
     * @param glyphSize the new value of [glyphSize].
     * @param strokeWidth the new value of [strokeWidth].
     * @param restoreShift the new value of [restoreShift].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        buttons: List<WindowButton> = this.buttons,
        buttonWidth: Dp = this.buttonWidth,
        glyphSize: Dp = this.glyphSize,
        strokeWidth: Dp = this.strokeWidth,
        restoreShift: Float = this.restoreShift,
    ): WindowsControls = WindowsControls(
        buttons = buttons,
        buttonWidth = buttonWidth,
        glyphSize = glyphSize,
        strokeWidth = strokeWidth,
        restoreShift = restoreShift,
    )

    override fun equals(other: Any?): Boolean = other is WindowsControls && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "WindowsControls(" +
        "buttons=$buttons, " +
        "buttonWidth=$buttonWidth, " +
        "glyphSize=$glyphSize, " +
        "strokeWidth=$strokeWidth, " +
        "restoreShift=$restoreShift)"

    private fun values(): List<Any?> = listOf(
        buttons,
        buttonWidth,
        glyphSize,
        strokeWidth,
        restoreShift,
    )

    @Composable
    override fun Content(actions: WindowActions, style: TitleBarStyle) {
        CaptionButtons(controls = this, actions = actions, style = style)
    }
}
