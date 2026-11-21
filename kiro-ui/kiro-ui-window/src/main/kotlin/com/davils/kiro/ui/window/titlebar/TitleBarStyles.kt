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

package com.davils.kiro.ui.window.titlebar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.window.Messages
import com.davils.kiro.ui.window.WindowLabels
import com.davils.kiro.ui.window.colorTransition
import com.davils.kiro.ui.window.controls.ControlPlacement
import com.davils.kiro.ui.window.controls.LinuxControls
import com.davils.kiro.ui.window.controls.MacOsControls
import com.davils.kiro.ui.window.controls.WindowButton
import com.davils.kiro.ui.window.controls.WindowControls
import com.davils.kiro.ui.window.controls.WindowsControls
import com.davils.kiro.ui.window.darkAware
import com.davils.kiro.ui.window.textStyleOf

private const val HOVER_LIGHT: Float = 0.06f
private const val HOVER_DARK: Float = 0.08f
private const val PRESSED_LIGHT: Float = 0.1f
private const val PRESSED_DARK: Float = 0.12f
private const val ROUND_LIGHT: Float = 0.08f
private const val ROUND_DARK: Float = 0.1f
private const val ROUND_HOVER_LIGHT: Float = 0.12f
private const val ROUND_HOVER_DARK: Float = 0.16f
private const val CLOSE_PRESSED_ALPHA: Float = 0.9f
private const val RESTORE_SHIFT: Float = 0.2f
private const val BADGE_LIMIT: Int = 99
private const val MAC_OS_HEIGHT_STEPS: Float = 7f
private const val WINDOWS_HEIGHT_STEPS: Float = 9f
private const val LINUX_HEIGHT_STEPS: Float = 10f

/**
 * Derives the style of a title bar from the tokens of a theme for an operating system.
 *
 * Register an implementation under [TitleBarStyleKey] in the components of a theme to change how every title bar
 * looks.
 *
 * @since 1.0.0
 */
public fun interface TitleBarStyles {
    /**
     * Returns the style of a title bar for a theme and an operating system.
     *
     * @param theme the theme whose tokens the style follows.
     * @param platform the operating system whose title bars the style resembles.
     * @return the style of the title bar.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, platform: DesktopPlatform): TitleBarStyle

    /**
     * Holds the default [TitleBarStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a title bar: the height and the window buttons of each operating system, with the
         * title in the middle of the window everywhere.
         *
         * @since 1.0.0
         */
        public val Default: TitleBarStyles = TitleBarStyles(::defaultTitleBarStyle)
    }
}

/**
 * The key of the styles of a title bar in the components of a theme, with [TitleBarStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val TitleBarStyleKey: StyleKey<TitleBarStyles> = StyleKey(name = "title-bar") { _ -> TitleBarStyles.Default }

/**
 * Returns the style of a title bar in the current theme for an operating system, with the overrides of its components.
 *
 * @param platform the operating system whose title bars the style resembles.
 * @return the style of the title bar.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun titleBarStyle(platform: DesktopPlatform = DesktopPlatform.current()): TitleBarStyle =
    TitleBarStyleKey.current.resolve(Theme.current, platform)

internal fun defaultTitleBarStyle(theme: Theme, platform: DesktopPlatform): TitleBarStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    val scale = theme.typography.scale
    val isRound = platform != DesktopPlatform.Windows
    return TitleBarStyle(
        height = spacing(steps = heightSteps(platform)),
        background = colors.background,
        inactiveBackground = colors.background,
        content = colors.foreground,
        inactiveContent = colors.mutedForeground,
        divider = Border(width = theme.borders.thin, color = colors.border),
        titleStyle = theme.textStyleOf(size = scale.sm, weight = FontWeight.Medium),
        horizontalPadding = spacing(steps = 2f),
        gap = spacing(steps = 1f),
        titlePlacement = TitlePlacement.WindowCenter,
        controls = theme.controlsOf(platform),
        controlColors = theme.controlColorsOf(isRound),
        closeColors = theme.closeColorsOf(isRound),
        buttonVariant = ButtonVariant.Ghost,
        buttonSize = ButtonSize.IconSm,
        badgeColor = colors.destructive,
        badgeContent = colors.knob,
        badgeTextStyle = theme.textStyleOf(size = scale.xs, weight = FontWeight.SemiBold),
        badgeDotSize = spacing(steps = 2f),
        badgeMinSize = spacing(steps = 4f),
        badgePadding = spacing(steps = 1f),
        badgeOffset = DpOffset(x = spacing(steps = 1f), y = spacing(steps = -0.5f)),
        badgeLimit = BADGE_LIMIT,
        colorAnimation = theme.colorTransition(),
        labels = WindowLabels(
            minimize = Messages.MINIMIZE,
            maximize = Messages.MAXIMIZE,
            restore = Messages.RESTORE,
            close = Messages.CLOSE,
            notifications = Messages.NOTIFICATIONS,
            badge = Messages::badge,
        ),
    )
}

private fun heightSteps(platform: DesktopPlatform): Float = when (platform) {
    DesktopPlatform.MacOs -> MAC_OS_HEIGHT_STEPS
    DesktopPlatform.Windows -> WINDOWS_HEIGHT_STEPS
    DesktopPlatform.Linux -> LINUX_HEIGHT_STEPS
    DesktopPlatform.Other -> LINUX_HEIGHT_STEPS
}

private fun Theme.controlsOf(platform: DesktopPlatform): WindowControls = when (platform) {
    DesktopPlatform.MacOs -> MacOsControls(inset = spacing(steps = 19f))

    DesktopPlatform.Windows -> WindowsControls(
        buttons = WindowButton.entries,
        buttonWidth = spacing(steps = 11.5f),
        glyphSize = spacing(steps = 2.5f),
        strokeWidth = borders.thin,
        restoreShift = RESTORE_SHIFT,
    )

    DesktopPlatform.Linux -> linuxControls()

    DesktopPlatform.Other -> linuxControls()
}

private fun Theme.linuxControls(): LinuxControls = LinuxControls(
    buttons = WindowButton.entries,
    placement = ControlPlacement.End,
    buttonSize = spacing(steps = 6f),
    spacing = spacing(steps = 2.5f),
    padding = spacing(steps = 2f),
    glyphSize = spacing(steps = 2f),
    strokeWidth = (borders.thin + borders.thick) / 2,
    restoreShift = RESTORE_SHIFT,
)

private fun Theme.controlColorsOf(isRound: Boolean): StateColors {
    val foreground = colors.foreground
    val idle = foreground.copy(alpha = roundAlpha(isRound))
    val hovered = foreground.copy(alpha = hoverAlpha(isRound))
    val pressed = foreground.copy(alpha = darkAware(light = PRESSED_LIGHT, dark = PRESSED_DARK) + roundAlpha(isRound))
    return StateColors(
        container = StateValue(default = idle, hovered = hovered, pressed = pressed),
        content = StateValue(default = foreground, disabled = colors.mutedForeground),
        border = StateValue(default = Color.Transparent),
        ring = StateValue(default = Color.Transparent, focusVisible = colors.ring),
    )
}

private fun Theme.closeColorsOf(isRound: Boolean): StateColors {
    val base = controlColorsOf(isRound)
    if (isRound) return base
    val close = colors.destructive
    return base.copy(
        container = StateValue(
            default = Color.Transparent,
            hovered = close,
            pressed = close.copy(alpha = CLOSE_PRESSED_ALPHA),
        ),
        content = StateValue(default = colors.foreground, hovered = colors.knob, pressed = colors.knob),
    )
}

private fun Theme.roundAlpha(isRound: Boolean): Float {
    if (!isRound) return 0f
    return darkAware(light = ROUND_LIGHT, dark = ROUND_DARK)
}

private fun Theme.hoverAlpha(isRound: Boolean): Float {
    if (!isRound) return darkAware(light = HOVER_LIGHT, dark = HOVER_DARK)
    return darkAware(light = ROUND_HOVER_LIGHT, dark = ROUND_HOVER_DARK)
}
