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

package com.davils.kiro.ui.components.questionnaire

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.core.typography.TextSize
import com.davils.kiro.ui.icons.lucide.Check
import com.davils.kiro.ui.icons.lucide.Lucide

private const val HALF: Float = 0.5f
private const val CHECKED_BORDER: Float = 0.4f
private const val DARK_CHOICE: Float = 0.2f
private const val DARK_INDICATOR: Float = 0.3f

/**
 * Resolves the [QuestionnaireStyle] of a theme.
 *
 * The styles of a theme are found under [QuestionnaireStyleKey]. Override them in [Theme.components] to change every
 * questionnaire of the theme.
 *
 * @since 1.0.0
 */
public fun interface QuestionnaireStyles {
    /**
     * Returns the style of a questionnaire for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of a questionnaire.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): QuestionnaireStyle

    /**
     * Holds the default [QuestionnaireStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default questionnaire styles, which derive the style from the tokens of the theme alone.
         *
         * @since 1.0.0
         */
        public val Default: QuestionnaireStyles = QuestionnaireStyles(::defaultQuestionnaireStyle)
    }
}

/**
 * The key of the questionnaire styles in [Theme.components]. Its default is [QuestionnaireStyles.Default].
 *
 * @since 1.0.0
 */
public val QuestionnaireStyleKey: StyleKey<QuestionnaireStyles> = StyleKey(
    name = "questionnaire",
) { _ -> QuestionnaireStyles.Default }

/**
 * Returns the style of a questionnaire in the current theme: the [QuestionnaireStyles] under [QuestionnaireStyleKey],
 * changed by the overrides of [Theme.components]. It is the default style of [Questionnaire].
 *
 * @return the style of a questionnaire in the current theme.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun questionnaireStyle(): QuestionnaireStyle = QuestionnaireStyleKey.current.resolve(theme)

internal fun defaultQuestionnaireStyle(theme: Theme): QuestionnaireStyle {
    val colors = theme.colors
    val scale = theme.typography.scale
    val sm = theme.textStyleOf(size = scale.sm)
    val progress = theme.textStyleOf(size = scale.xs, weight = FontWeight.Medium)
    return QuestionnaireStyle(
        gap = 16.dp,
        progressStyle = progress.copy(fontFeatureSettings = "tnum"),
        progressColor = colors.mutedForeground,
        titleStyle = theme.textStyleOf(
            size = scale.base,
            weight = FontWeight.Medium,
            lineHeight = theme.typography.leading.snug,
            family = theme.typography.families.heading,
        ),
        titleSpacing = 16.dp,
        descriptionStyle = sm,
        descriptionColor = colors.mutedForeground,
        choicesGap = 8.dp,
        choice = defaultChoiceStyle(theme),
        errorStyle = sm,
        errorColor = colors.destructive,
        errorSpacing = 8.dp,
        actionsGap = 8.dp,
        actionsMinHeight = 32.dp,
        labels = QuestionnaireLabels(),
    )
}

private fun defaultChoiceStyle(theme: Theme): QuestionnaireChoiceStyle {
    val colors = theme.colors
    val isDark = theme.isDark
    val shortcut = TextSize(fontSize = 10.sp, lineHeight = 1.em)
    return QuestionnaireChoiceStyle(
        minHeight = 44.dp,
        shape = RoundedCornerShape(theme.radii.lg),
        padding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
        gap = 10.dp,
        container = colors.input.copy(alpha = DARK_CHOICE).takeIf { isDark } ?: Color.Transparent,
        hover = colors.muted.copy(alpha = HALF),
        checkedContainer = colors.muted,
        border = colors.input,
        checkedBorder = colors.primary.copy(alpha = CHECKED_BORDER),
        invalidBorder = colors.destructive,
        focusBorder = colors.ring,
        ring = colors.ring.copy(alpha = HALF),
        ringWidth = 3.dp,
        disabledAlpha = HALF,
        labelStyle = theme.textStyleOf(size = theme.typography.scale.sm, lineHeight = theme.typography.leading.snug),
        labelGap = 2.dp,
        descriptionColor = colors.mutedForeground,
        indicatorSize = 16.dp,
        indicatorShape = RoundedCornerShape(4.dp),
        radioShape = CircleShape,
        indicatorOffset = 1.8.dp,
        indicatorBorder = colors.input,
        indicatorContainer = colors.input.copy(alpha = DARK_INDICATOR).takeIf { isDark } ?: Color.Transparent,
        indicatorChecked = colors.primary,
        indicatorContent = colors.primaryForeground,
        dotSize = 8.dp,
        checkIcon = Lucide.Check,
        checkSize = 14.dp,
        shortcutSize = 20.dp,
        shortcutShape = RoundedCornerShape(theme.radii.md),
        shortcutBorder = colors.input,
        shortcutContainer = colors.background,
        shortcutStyle = theme.textStyleOf(
            size = shortcut,
            weight = FontWeight.Medium,
            family = theme.typography.families.mono,
        ),
        shortcutColor = colors.mutedForeground,
    )
}
