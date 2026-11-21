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

package com.davils.kiro.ui.motion.diagnostics

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val CONTAINER_ALPHA: Float = 0.72f

/**
 * Derives the style of a [DiagnosticsOverlay] from the tokens of a theme.
 *
 * Register an implementation under [DiagnosticsOverlayStyleKey] in the components of a theme to change how every
 * diagnostics overlay looks.
 *
 * @since 1.0.0
 */
public fun interface DiagnosticsOverlayStyles {
    /**
     * Returns the style of a diagnostics overlay for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the diagnostics overlay.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): DiagnosticsOverlayStyle

    /**
     * Holds the default [DiagnosticsOverlayStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a diagnostics overlay.
         *
         * @since 1.0.0
         */
        public val Default: DiagnosticsOverlayStyles = DiagnosticsOverlayStyles(::defaultDiagnosticsOverlayStyle)
    }
}

/**
 * The key of the styles of a [DiagnosticsOverlay] in the components of a theme, with
 * [DiagnosticsOverlayStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val DiagnosticsOverlayStyleKey: StyleKey<DiagnosticsOverlayStyles> =
    StyleKey(name = "diagnostics-overlay") { _ -> DiagnosticsOverlayStyles.Default }

/**
 * Returns the style of a [DiagnosticsOverlay] in the current theme, with the overrides of its components.
 *
 * @return the style of the diagnostics overlay.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun diagnosticsOverlayStyle(): DiagnosticsOverlayStyle =
    DiagnosticsOverlayStyleKey.current.resolve(Theme.current)

internal fun defaultDiagnosticsOverlayStyle(theme: Theme): DiagnosticsOverlayStyle {
    val colors = theme.colors
    val typography = theme.typography
    val size = typography.scale.xs
    return DiagnosticsOverlayStyle(
        container = colors.foreground.copy(alpha = CONTAINER_ALPHA),
        content = colors.background,
        textStyle = TextStyle(
            fontFamily = typography.families.mono,
            fontSize = size.fontSize,
            lineHeight = size.lineHeight,
        ),
        shape = RoundedCornerShape(size = theme.radii.sm),
        horizontalPadding = theme.spacing(steps = 2f),
        verticalPadding = theme.spacing(steps = 1f),
    )
}
