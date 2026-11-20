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

package com.davils.kiro.ui.components.field

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val DISABLED_ALPHA: Float = 0.5f

/**
 * Derives the [FieldStyle] of a field from the tokens of a theme.
 *
 * The components of a theme hold one under each key of this type; override a key in the components of a theme to change
 * the style of every field that reads it.
 *
 * @since 1.0.0
 */
public fun interface FieldStyles {
    /**
     * Returns the style of a field for the tokens of a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): FieldStyle

    /**
     * Holds the [FieldStyles] that Kiro ships.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a field, derived only from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: FieldStyles = FieldStyles(::defaultFieldStyle)
    }
}

/**
 * Returns the style of a field in the theme of the composition, resolved through the styles that the components of the
 * theme hold under [FieldStyleKey].
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun fieldStyle(): FieldStyle = FieldStyleKey.current.resolve(theme)

internal fun defaultFieldStyle(theme: Theme): FieldStyle {
    val scale = theme.typography.scale
    val leading = theme.typography.leading
    return FieldStyle(
        gap = 8.dp,
        setGap = 16.dp,
        groupGap = 20.dp,
        contentGap = 2.dp,
        legendSpacing = 6.dp,
        legendStyle = theme.textStyleOf(size = scale.base, weight = FontWeight.Medium),
        legendLabelStyle = theme.textStyleOf(size = scale.sm, weight = FontWeight.Medium),
        labelStyle = theme.textStyleOf(size = scale.sm, weight = FontWeight.Medium, lineHeight = leading.snug),
        titleStyle = theme.textStyleOf(size = scale.sm, weight = FontWeight.Medium, lineHeight = leading.snug),
        descriptionStyle = theme.textStyleOf(size = scale.sm, lineHeight = leading.normal),
        descriptionColor = theme.colors.mutedForeground,
        errorStyle = theme.textStyleOf(size = scale.sm),
        errorIndent = 16.dp,
        errorGap = 4.dp,
        invalidColor = theme.colors.destructive,
        disabledAlpha = DISABLED_ALPHA,
        controlNudge = 1.dp,
        responsiveBreakpoint = 448.dp,
        separatorHeight = 20.dp,
        separatorOverlap = 8.dp,
        separatorColor = theme.colors.border,
        separatorPadding = 8.dp,
        separatorBackground = theme.colors.background,
    )
}

/**
 * The key of the styles of a field in the components of a theme, named `field`; its default is [FieldStyles.Default].
 *
 * @since 1.0.0
 */
public val FieldStyleKey: StyleKey<FieldStyles> = StyleKey(name = "field") { _ -> FieldStyles.Default }
