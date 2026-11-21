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

package com.davils.kiro.ui.motion.typewriter

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val CARET_HEIGHT: Float = 0.82f
private const val CARET_PERIOD: Double = 3.0
private const val TYPE_DELAY: Double = 0.45
private const val DELETE_DELAY: Double = 0.22
private const val PUNCTUATION_DELAY: Double = 0.6
private const val PUNCTUATION: String = ".,;:!?…"
private const val JITTER: Float = 0.35f
private const val HOLD: Double = 6.0

/**
 * Derives the style of a [TypewriterText] from the tokens of a theme.
 *
 * Register an implementation under [TypewriterStyleKey] in the components of a theme to change how every typewriter
 * looks.
 *
 * @since 1.0.0
 */
public fun interface TypewriterStyles {
    /**
     * Returns the style of a typewriter for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the typewriter.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): TypewriterStyle

    /**
     * Holds the default [TypewriterStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a typewriter: a semibold heading in the color of the foreground with a caret in the
         * primary color.
         *
         * @since 1.0.0
         */
        public val Default: TypewriterStyles = TypewriterStyles(::defaultTypewriterStyle)
    }
}

/**
 * The key of the styles of a [TypewriterText] in the components of a theme, with [TypewriterStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val TypewriterStyleKey: StyleKey<TypewriterStyles> =
    StyleKey(name = "typewriter") { _ -> TypewriterStyles.Default }

/**
 * Returns the style of a [TypewriterText] in the current theme, with the overrides of its components.
 *
 * @return the style of the typewriter.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun typewriterStyle(): TypewriterStyle = TypewriterStyleKey.current.resolve(Theme.current)

internal fun defaultTypewriterStyle(theme: Theme): TypewriterStyle {
    val typography = theme.typography
    val size = typography.scale.xl3
    val motion = theme.motion
    val durations = motion.durations
    return TypewriterStyle(
        textStyle = TextStyle(
            fontFamily = typography.families.heading,
            fontSize = size.fontSize,
            lineHeight = size.lineHeight,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = typography.tracking.tight,
            localeList = theme.localeList,
        ),
        color = theme.colors.foreground,
        caretColor = theme.colors.primary,
        caretWidth = theme.spacing(steps = 0.5f),
        caretHeight = CARET_HEIGHT,
        caretGap = theme.spacing(steps = 0.5f),
        caretShape = RoundedCornerShape(size = theme.radii.sm),
        caretPeriod = durations.long * CARET_PERIOD,
        caretFade = durations.short,
        caretEasing = motion.easings.standard,
        hasCaret = true,
        typeDelay = durations.short * TYPE_DELAY,
        deleteDelay = durations.short * DELETE_DELAY,
        punctuationDelay = durations.long * PUNCTUATION_DELAY,
        punctuation = PUNCTUATION,
        jitter = JITTER,
        hold = durations.long * HOLD,
        speed = motion.ambient.speed,
        isSpaceReserved = true,
        isAnimated = !motion.isReduced,
    )
}
