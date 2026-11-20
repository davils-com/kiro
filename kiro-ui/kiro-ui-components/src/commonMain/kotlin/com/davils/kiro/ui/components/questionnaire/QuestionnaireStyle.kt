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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * The style of a [Questionnaire] and of its parts.
 *
 * @property gap the space between the parts of the questionnaire and of a [QuestionnaireItem].
 * @property progressStyle the text style of a [QuestionnaireProgress].
 * @property progressColor the color of a [QuestionnaireProgress].
 * @property titleStyle the text style of a [QuestionnaireTitle].
 * @property titleSpacing the space below a [QuestionnaireTitle] that has no [QuestionnaireDescription].
 * @property descriptionStyle the text style of a [QuestionnaireDescription].
 * @property descriptionColor the color of a [QuestionnaireDescription].
 * @property choicesGap the space between the choices of [QuestionnaireChoices].
 * @property choice the style of a [QuestionnaireChoice].
 * @property errorStyle the text style of a [QuestionnaireError].
 * @property errorColor the color of a [QuestionnaireError].
 * @property errorSpacing the space above a [QuestionnaireError].
 * @property actionsGap the space between the buttons of [QuestionnaireActions].
 * @property actionsMinHeight the smallest height of [QuestionnaireActions].
 * @property labels the texts of the questionnaire.
 * @since 1.0.0
 */
@Immutable
public class QuestionnaireStyle(
    public val gap: Dp,
    public val progressStyle: TextStyle,
    public val progressColor: Color,
    public val titleStyle: TextStyle,
    public val titleSpacing: Dp,
    public val descriptionStyle: TextStyle,
    public val descriptionColor: Color,
    public val choicesGap: Dp,
    public val choice: QuestionnaireChoiceStyle,
    public val errorStyle: TextStyle,
    public val errorColor: Color,
    public val errorSpacing: Dp,
    public val actionsGap: Dp,
    public val actionsMinHeight: Dp,
    public val labels: QuestionnaireLabels,
) {
    /**
     * Returns a copy of this [QuestionnaireStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        gap: Dp = this.gap,
        progressStyle: TextStyle = this.progressStyle,
        progressColor: Color = this.progressColor,
        titleStyle: TextStyle = this.titleStyle,
        titleSpacing: Dp = this.titleSpacing,
        descriptionStyle: TextStyle = this.descriptionStyle,
        descriptionColor: Color = this.descriptionColor,
        choicesGap: Dp = this.choicesGap,
        choice: QuestionnaireChoiceStyle = this.choice,
        errorStyle: TextStyle = this.errorStyle,
        errorColor: Color = this.errorColor,
        errorSpacing: Dp = this.errorSpacing,
        actionsGap: Dp = this.actionsGap,
        actionsMinHeight: Dp = this.actionsMinHeight,
        labels: QuestionnaireLabels = this.labels,
    ): QuestionnaireStyle = QuestionnaireStyle(
        gap = gap,
        progressStyle = progressStyle,
        progressColor = progressColor,
        titleStyle = titleStyle,
        titleSpacing = titleSpacing,
        descriptionStyle = descriptionStyle,
        descriptionColor = descriptionColor,
        choicesGap = choicesGap,
        choice = choice,
        errorStyle = errorStyle,
        errorColor = errorColor,
        errorSpacing = errorSpacing,
        actionsGap = actionsGap,
        actionsMinHeight = actionsMinHeight,
        labels = labels,
    )

    override fun equals(other: Any?): Boolean = other is QuestionnaireStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "QuestionnaireStyle(" +
        "gap=$gap, " +
        "progressStyle=$progressStyle, " +
        "progressColor=$progressColor, " +
        "titleStyle=$titleStyle, " +
        "titleSpacing=$titleSpacing, " +
        "descriptionStyle=$descriptionStyle, " +
        "descriptionColor=$descriptionColor, " +
        "choicesGap=$choicesGap, " +
        "choice=$choice, " +
        "errorStyle=$errorStyle, " +
        "errorColor=$errorColor, " +
        "errorSpacing=$errorSpacing, " +
        "actionsGap=$actionsGap, " +
        "actionsMinHeight=$actionsMinHeight, " +
        "labels=$labels)"

    private fun values(): List<Any?> = listOf(
        gap,
        progressStyle,
        progressColor,
        titleStyle,
        titleSpacing,
        descriptionStyle,
        descriptionColor,
        choicesGap,
        choice,
        errorStyle,
        errorColor,
        errorSpacing,
        actionsGap,
        actionsMinHeight,
        labels,
    )
}
