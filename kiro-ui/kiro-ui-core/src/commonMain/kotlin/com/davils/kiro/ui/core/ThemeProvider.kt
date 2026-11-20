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

package com.davils.kiro.ui.core

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.focus.InputModality
import com.davils.kiro.ui.core.focus.LocalInputModality
import com.davils.kiro.ui.core.focus.trackInputModality
import com.davils.kiro.ui.core.typography.bundledFontFamilies

internal val LocalTheme: ProvidableCompositionLocal<Theme> = staticCompositionLocalOf { Theme.light() }

/**
 * Provides a theme to its content, together with the content color, the body text style and the tracking of the
 * input modality that decides when controls show their focus ring.
 *
 * Place it once at the root of a window or of a screen. A nested provider replaces the theme for its own content
 * and reuses the input modality of the outer provider. If the typography of [theme] uses
 * [com.davils.kiro.ui.core.typography.FontFamilies.Bundled], the provider loads the bundled fonts and hands its
 * content a theme with the loaded families.
 *
 * @param theme the theme of the content.
 * @param content the content.
 * @since 1.0.0
 */
@Composable
public fun ThemeProvider(theme: Theme, content: @Composable () -> Unit) {
    ProvideTheme(theme = theme) { InputModalityScope(content = content) }
}

/**
 * Provides a theme like [ThemeProvider], but without tracking the input modality, for content without a layout of
 * its own, such as the windows and menus of an application, which each bring a [ThemeProvider] of their own.
 *
 * @param theme the theme of the content.
 * @param content the content.
 * @since 1.0.0
 */
@Composable
public fun ApplicationThemeProvider(theme: Theme, content: @Composable () -> Unit) {
    ProvideTheme(theme = theme, content = content)
}

@Composable
private fun ProvideTheme(theme: Theme, content: @Composable () -> Unit) {
    val resolved = withLoadedFonts(theme)
    val body = resolved.typography.scale.base
    val textStyle = TextStyle(
        fontFamily = resolved.typography.families.sans,
        fontSize = body.fontSize,
        lineHeight = body.lineHeight,
        localeList = resolved.localeList,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None,
        ),
    )
    CompositionLocalProvider(
        LocalTheme provides resolved,
        LocalContentColor provides resolved.colors.foreground,
        LocalTextStyle provides textStyle,
        content = content,
    )
}

@Composable
private fun withLoadedFonts(theme: Theme): Theme {
    val typography = theme.typography
    if (!typography.families.isBundled) return theme
    val families = bundledFontFamilies()
    return remember(theme, families) { theme.copy(typography = typography.copy(families = families)) }
}

@Composable
private fun InputModalityScope(content: @Composable () -> Unit) {
    if (LocalInputModality.current != null) return content()
    val modality = remember { InputModality() }
    CompositionLocalProvider(LocalInputModality provides modality) {
        Box(modifier = Modifier.trackInputModality(modality), propagateMinConstraints = true) { content() }
    }
}
