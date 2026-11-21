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
 * The round window buttons of GNOME and KDE, at the side of the bar the desktop puts them.
 *
 * @property buttons the buttons in the order they show.
 * @property placement the physical side of the bar the buttons sit on.
 * @property buttonSize the diameter of a button.
 * @property spacing the space between two buttons.
 * @property padding the space between the buttons and the edge of the bar.
 * @property glyphSize the size of the glyph of a button.
 * @property strokeWidth the width of the lines of a glyph.
 * @property restoreShift how far the back square of the restore glyph is offset, as a fraction of the glyph.
 * @since 1.0.0
 */
@Immutable
public class LinuxControls(
    public val buttons: List<WindowButton>,
    override val placement: ControlPlacement,
    public val buttonSize: Dp,
    public val spacing: Dp,
    public val padding: Dp,
    public val glyphSize: Dp,
    public val strokeWidth: Dp,
    public val restoreShift: Float,
) : WindowControls {
    /**
     * Returns a copy of this [LinuxControls] with the given values replaced and every other value kept.
     *
     * @param buttons the new value of [buttons].
     * @param placement the new value of [placement].
     * @param buttonSize the new value of [buttonSize].
     * @param spacing the new value of [spacing].
     * @param padding the new value of [padding].
     * @param glyphSize the new value of [glyphSize].
     * @param strokeWidth the new value of [strokeWidth].
     * @param restoreShift the new value of [restoreShift].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        buttons: List<WindowButton> = this.buttons,
        placement: ControlPlacement = this.placement,
        buttonSize: Dp = this.buttonSize,
        spacing: Dp = this.spacing,
        padding: Dp = this.padding,
        glyphSize: Dp = this.glyphSize,
        strokeWidth: Dp = this.strokeWidth,
        restoreShift: Float = this.restoreShift,
    ): LinuxControls = LinuxControls(
        buttons = buttons,
        placement = placement,
        buttonSize = buttonSize,
        spacing = spacing,
        padding = padding,
        glyphSize = glyphSize,
        strokeWidth = strokeWidth,
        restoreShift = restoreShift,
    )

    override fun equals(other: Any?): Boolean = other is LinuxControls && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "LinuxControls(" +
        "buttons=$buttons, " +
        "placement=$placement, " +
        "buttonSize=$buttonSize, " +
        "spacing=$spacing, " +
        "padding=$padding, " +
        "glyphSize=$glyphSize, " +
        "strokeWidth=$strokeWidth, " +
        "restoreShift=$restoreShift)"

    private fun values(): List<Any?> = listOf(
        buttons,
        placement,
        buttonSize,
        spacing,
        padding,
        glyphSize,
        strokeWidth,
        restoreShift,
    )

    @Composable
    override fun Content(actions: WindowActions, style: TitleBarStyle) {
        RoundButtons(controls = this, actions = actions, style = style)
    }
}
