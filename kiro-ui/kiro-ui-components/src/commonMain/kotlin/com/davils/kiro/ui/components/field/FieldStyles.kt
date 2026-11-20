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

public fun interface FieldStyles {
    public fun resolve(theme: Theme): FieldStyle

    public companion object {
        public val Default: FieldStyles = FieldStyles(::defaultFieldStyle)
    }
}

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

public val FieldStyleKey: StyleKey<FieldStyles> = StyleKey(name = "field") { _ -> FieldStyles.Default }
