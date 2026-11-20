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

package com.davils.kiro.ui.components.inputotp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.input.fieldColorsOf
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.darkAware
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Minus
import kotlin.time.Duration.Companion.seconds

private const val INVALID_RING_LIGHT: Float = 0.2f
private const val INVALID_RING_DARK: Float = 0.4f
private const val DISABLED_ALPHA: Float = 0.5f
private const val DARK_BORDER: Float = 0.3f

/**
 * Derives the style of an [InputOtp] from the tokens of a theme.
 *
 * Register an implementation under [InputOtpStyleKey] in the components of a theme to change how every one-time
 * password field looks.
 *
 * @since 1.0.0
 */
public fun interface InputOtpStyles {
    /**
     * Returns the style of an [InputOtp] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the field.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): InputOtpStyle

    /**
     * Holds the default [InputOtpStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of an [InputOtp], with the colors of an input field and a caret that blinks once a
         * second unless the theme reduces motion.
         *
         * @since 1.0.0
         */
        public val Default: InputOtpStyles = InputOtpStyles(::defaultInputOtpStyle)
    }
}

/**
 * The key of the styles of an [InputOtp] in the components of a theme, with [InputOtpStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val InputOtpStyleKey: StyleKey<InputOtpStyles> = StyleKey(name = "input-otp") { _ -> InputOtpStyles.Default }

/**
 * Returns the style of an [InputOtp] in the current theme, with the overrides of its components.
 *
 * @return the style of the field.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun inputOtpStyle(): InputOtpStyle = InputOtpStyleKey.current.resolve(theme)

internal fun defaultInputOtpStyle(theme: Theme): InputOtpStyle = InputOtpStyle(
    slotSize = 32.dp,
    radius = theme.radii.lg,
    textStyle = theme.textStyleOf(size = theme.typography.scale.sm),
    colors = slotColorsOf(theme),
    borderWidth = 1.dp,
    ringWidth = theme.focusRing.width,
    invalidRing = theme.colors.destructive.copy(
        alpha = theme.darkAware(light = INVALID_RING_LIGHT, dark = INVALID_RING_DARK),
    ),
    gap = 8.dp,
    separatorIcon = Lucide.Minus,
    separatorSize = 16.dp,
    caret = InputOtpCaret(
        width = 1.dp,
        height = 16.dp,
        color = theme.colors.foreground,
        period = 1.seconds.takeUnless { theme.motion.isReduced },
    ),
    disabledAlpha = DISABLED_ALPHA,
    colorAnimation = theme.colorTransition(),
)

private fun slotColorsOf(theme: Theme): StateColors {
    val colors = fieldColorsOf(theme)
    if (!theme.isDark) return colors
    val input = theme.colors.input
    return colors.copy(
        container = colors.container.copy(default = input),
        border = colors.border.copy(default = input.copy(alpha = DARK_BORDER)),
    )
}
