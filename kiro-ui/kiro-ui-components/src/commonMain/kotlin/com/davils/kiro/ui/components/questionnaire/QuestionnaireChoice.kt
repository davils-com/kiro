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

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

/**
 * Places the [QuestionnaireChoice]s of a question in a column, with the choices gap of the style between them.
 *
 * @param modifier the modifier of the column, which fills the available width.
 * @param content the choices.
 * @throws IllegalArgumentException if it is not placed inside a [Questionnaire].
 * @since 1.0.0
 */
@Composable
public fun QuestionnaireChoices(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    val style = questionnaire().style
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(style.choicesGap),
        content = content,
    )
}

/**
 * Shows a choice of the current question of a [Questionnaire], with a radio indicator for a question of the kind
 * [QuestionKind.Single] and a checkbox for [QuestionKind.Multiple].
 *
 * Clicking the choice selects its value, or toggles it for a question with multiple answers. With a shortcut, typing
 * that character inside the questionnaire does the same, whatever its case.
 *
 * @param value the value the choice stands for in the [Answer].
 * @param modifier the modifier of the choice, which fills the available width.
 * @param enabled whether the choice can be selected; a disabled choice also gives up its shortcut.
 * @param shortcut the character that selects the choice, shown at its end, or `null` for none.
 * @param content the label of the choice, such as a [com.davils.kiro.ui.components.text.Text] and a
 * [QuestionnaireChoiceDescription].
 * @throws IllegalArgumentException if it is not placed inside a [Questionnaire].
 * @since 1.0.0
 */
@Composable
public fun QuestionnaireChoice(
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shortcut: Char? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val context = questionnaire()
    val state = context.state
    val style = context.style.choice
    ShortcutRegistration(context = context, shortcut = shortcut, value = value, enabled = enabled)
    val isMultiple = state.current.kind == QuestionKind.Multiple
    val isChecked = state.isSelected(value)
    val isInvalid = state.invalid == state.current.key
    val source = remember { MutableInteractionSource() }
    val interaction = source.collectInteractionState(
        isDisabled = !enabled,
        isSelected = isChecked,
        isInvalid = isInvalid,
    )
    val transition = theme.colorTransition()
    val container by animateColor(
        containerOf(style = style, isChecked = isChecked, isHovered = interaction.isHovered),
        transition,
    )
    val border = borderOf(
        style = style,
        isChecked = isChecked,
        isInvalid = isInvalid,
        isFocusVisible = interaction.isFocusVisible,
    )
    val ring = style.ring.takeIf { interaction.isFocusVisible } ?: Color.Transparent
    val selection = selectionOf(isMultiple = isMultiple, isChecked = isChecked, enabled = enabled, source = source) {
        state.select(value)
    }
    val face = Modifier.surface(
        shape = style.shape,
        color = container,
        border = Border(width = 1.dp, color = border),
        ring = Border(style.ringWidth, ring),
    )
    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .alpha(style.disabledAlpha.takeIf { !enabled } ?: 1f)
                .heightIn(min = style.minHeight)
                .then(face)
                .hoverable(interactionSource = source, enabled = enabled)
                .then(selection)
                .padding(style.padding),
        ),
        horizontalArrangement = Arrangement.spacedBy(style.gap),
    ) {
        ChoiceIndicator(style = style, isMultiple = isMultiple, isChecked = isChecked)
        CompositionLocalProvider(LocalTextStyle provides style.labelStyle) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(style.labelGap),
                content = content,
            )
        }
        shortcut?.let { key -> ChoiceShortcut(key = key, style = style) }
    }
}

/**
 * Shows a description below the label of a [QuestionnaireChoice], in the description color of the choice style.
 *
 * @param text the description.
 * @param modifier the modifier of the description.
 * @throws IllegalArgumentException if it is not placed inside a [Questionnaire].
 * @since 1.0.0
 */
@Composable
public fun QuestionnaireChoiceDescription(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, color = questionnaire().style.choice.descriptionColor)
}

@Composable
private fun ShortcutRegistration(context: QuestionnaireContext, shortcut: Char?, value: String, enabled: Boolean) {
    DisposableEffect(context, shortcut, value, enabled) {
        val key = shortcut?.lowercaseChar().takeIf { enabled }
        key?.let { registered -> context.shortcuts[registered] = value }
        onDispose { key?.let { registered -> context.shortcuts.remove(registered) } }
    }
}

@Composable
private fun ChoiceIndicator(style: QuestionnaireChoiceStyle, isMultiple: Boolean, isChecked: Boolean) {
    val shape = style.indicatorShape.takeIf { isMultiple } ?: style.radioShape
    val fill = style.indicatorChecked.takeIf { isChecked } ?: style.indicatorContainer
    val edge = style.indicatorChecked.takeIf { isChecked } ?: style.indicatorBorder
    Box(
        modifier = Modifier
            .offset(y = style.indicatorOffset)
            .size(style.indicatorSize)
            .surface(shape = shape, color = fill, border = Border(width = 1.dp, color = edge)),
        contentAlignment = Alignment.Center,
    ) {
        if (isChecked) IndicatorMark(style = style, isMultiple = isMultiple)
    }
}

@Composable
private fun IndicatorMark(style: QuestionnaireChoiceStyle, isMultiple: Boolean) {
    if (!isMultiple) {
        Box(modifier = Modifier.size(style.dotSize).surface(shape = style.radioShape, color = style.indicatorContent))
        return
    }
    CompositionLocalProvider(
        LocalIconStyle provides IconStyle(size = style.checkSize, color = style.indicatorContent),
    ) {
        Icon(icon = style.checkIcon, contentDescription = null)
    }
}

@Composable
private fun ChoiceShortcut(key: Char, style: QuestionnaireChoiceStyle) {
    Box(
        modifier = Modifier
            .offset(y = style.indicatorOffset)
            .size(style.shortcutSize)
            .surface(
                shape = style.shortcutShape,
                color = style.shortcutContainer,
                border = Border(width = 1.dp, color = style.shortcutBorder),
            ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides style.shortcutColor) {
            Text(text = key.uppercase(), style = style.shortcutStyle)
        }
    }
}

private fun selectionOf(
    isMultiple: Boolean,
    isChecked: Boolean,
    enabled: Boolean,
    source: MutableInteractionSource,
    onSelect: () -> Unit,
): Modifier {
    if (isMultiple) {
        return Modifier.pressableCursor(enabled = enabled).toggleable(
            value = isChecked,
            interactionSource = source,
            indication = null,
            enabled = enabled,
            role = Role.Checkbox,
        ) {
            onSelect()
        }
    }
    return Modifier.pressableCursor(enabled = enabled).selectable(
        selected = isChecked,
        interactionSource = source,
        indication = null,
        enabled = enabled,
        role = Role.RadioButton,
    ) {
        onSelect()
    }
}

private fun containerOf(style: QuestionnaireChoiceStyle, isChecked: Boolean, isHovered: Boolean): Color =
    style.checkedContainer.takeIf {
        isChecked
    } ?: style.hover.takeIf { isHovered } ?: style.container

private fun borderOf(
    style: QuestionnaireChoiceStyle,
    isChecked: Boolean,
    isInvalid: Boolean,
    isFocusVisible: Boolean,
): Color {
    val focused = style.focusBorder.takeIf { isFocusVisible }
    return focused ?: style.invalidBorder.takeIf { isInvalid } ?: style.checkedBorder.takeIf { isChecked }
        ?: style.border
}
