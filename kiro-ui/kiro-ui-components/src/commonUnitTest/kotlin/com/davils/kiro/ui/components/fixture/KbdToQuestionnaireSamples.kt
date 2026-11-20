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

package com.davils.kiro.ui.components.fixture

import androidx.compose.animation.EnterTransition
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.kbd.defaultKbdStyle
import com.davils.kiro.ui.components.label.defaultLabelStyle
import com.davils.kiro.ui.components.marker.defaultMarkerStyle
import com.davils.kiro.ui.components.menu.defaultMenuStyle
import com.davils.kiro.ui.components.menubar.defaultMenubarStyle
import com.davils.kiro.ui.components.message.defaultMessageStyle
import com.davils.kiro.ui.components.messagescroller.defaultMessageScrollerStyle
import com.davils.kiro.ui.components.nativeselect.NativeSelectSize
import com.davils.kiro.ui.components.nativeselect.defaultNativeSelectStyle
import com.davils.kiro.ui.components.navigationmenu.defaultNavigationMenuStyle
import com.davils.kiro.ui.components.pagination.PaginationItem
import com.davils.kiro.ui.components.pagination.defaultPaginationStyle
import com.davils.kiro.ui.components.popover.defaultPopoverStyle
import com.davils.kiro.ui.components.primitive.interaction.ResolvedColors
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.primitive.overlay.dialogTransitions
import com.davils.kiro.ui.components.progress.defaultProgressStyle
import com.davils.kiro.ui.components.questionnaire.Answer
import com.davils.kiro.ui.components.questionnaire.Question
import com.davils.kiro.ui.components.questionnaire.QuestionKey
import com.davils.kiro.ui.components.questionnaire.QuestionKind
import com.davils.kiro.ui.components.questionnaire.QuestionnaireLabels
import com.davils.kiro.ui.components.questionnaire.QuestionnaireStep
import com.davils.kiro.ui.components.questionnaire.defaultQuestionnaireStyle
import com.davils.kiro.ui.core.Theme

private val theme: Theme = Theme.light()

internal fun kbdToQuestionnaireSamples(): List<ValueSample> = kbdToMenuSamples() +
    messageToNativeSelectSamples() +
    navigationToPopoverSamples() +
    overlaySamples() +
    answerSamples() +
    questionnaireSamples()

private fun kbdToMenuSamples(): List<ValueSample> {
    val kbd = defaultKbdStyle(theme)
    val label = defaultLabelStyle(theme)
    val marker = defaultMarkerStyle(theme)
    val menubar = defaultMenubarStyle(theme)
    val menu = defaultMenuStyle(theme)
    return listOf(
        ValueSample(
            name = "KbdStyle",
            value = kbd,
            copy = kbd.copy(),
            changed = kbd.copy(container = Color.Red),
        ),
        ValueSample(
            name = "LabelStyle",
            value = label,
            copy = label.copy(),
            changed = label.copy(disabledAlpha = 1f),
        ),
        ValueSample(
            name = "MarkerStyle",
            value = marker,
            copy = marker.copy(),
            changed = marker.copy(color = Color.Red),
        ),
        ValueSample(
            name = "MenubarStyle",
            value = menubar,
            copy = menubar.copy(),
            changed = menubar.copy(triggerActive = Color.Red),
        ),
        ValueSample(
            name = "MenuStyle",
            value = menu,
            copy = menu.copy(),
            changed = menu.copy(highlight = Color.Red),
        ),
    )
}

private fun messageToNativeSelectSamples(): List<ValueSample> {
    val message = defaultMessageStyle(theme)
    val scroller = defaultMessageScrollerStyle(theme)
    val nativeSelect = defaultNativeSelectStyle(theme = theme, size = NativeSelectSize.Default)
    val icon = nativeSelect.icon
    val menu = nativeSelect.menu
    return listOf(
        ValueSample(
            name = "MessageStyle",
            value = message,
            copy = message.copy(),
            changed = message.copy(avatarContainer = Color.Red),
        ),
        ValueSample(
            name = "MessageScrollerStyle",
            value = scroller,
            copy = scroller.copy(),
            changed = scroller.copy(label = "Changed"),
        ),
        ValueSample(
            name = "NativeSelectIcon",
            value = icon,
            copy = icon.copy(),
            changed = icon.copy(color = Color.Red),
        ),
        ValueSample(
            name = "NativeSelectMenuStyle",
            value = menu,
            copy = menu.copy(),
            changed = menu.copy(highlight = Color.Red),
        ),
        ValueSample(
            name = "NativeSelectStyle",
            value = nativeSelect,
            copy = nativeSelect.copy(),
            changed = nativeSelect.copy(height = 99.dp),
        ),
    )
}

private fun navigationToPopoverSamples(): List<ValueSample> {
    val navigation = defaultNavigationMenuStyle(theme)
    val page = PaginationItem.Page(number = 1)
    val ellipsis = PaginationItem.Ellipsis(position = 1)
    val pagination = defaultPaginationStyle(theme)
    val popover = defaultPopoverStyle(theme)
    return listOf(
        ValueSample(
            name = "NavigationMenuStyle",
            value = navigation,
            copy = navigation.copy(),
            changed = navigation.copy(hover = Color.Red),
        ),
        ValueSample(
            name = "Page",
            value = page,
            copy = page.copy(),
            changed = page.copy(number = 2),
        ),
        ValueSample(
            name = "Ellipsis",
            value = ellipsis,
            copy = ellipsis.copy(),
            changed = ellipsis.copy(position = 2),
        ),
        ValueSample(
            name = "PaginationStyle",
            value = pagination,
            copy = pagination.copy(),
            changed = pagination.copy(iconSize = 99.dp),
        ),
        ValueSample(
            name = "PopoverStyle",
            value = popover,
            copy = popover.copy(),
            changed = popover.copy(descriptionColor = Color.Red),
        ),
    )
}

private fun overlaySamples(): List<ValueSample> {
    val colors = theme.colors
    val resolved = ResolvedColors(
        container = colors.background,
        content = colors.foreground,
        border = colors.border,
        ring = colors.ring,
    )
    val transitions = dialogTransitions(motion = theme.motion)
    val panel = defaultPopoverStyle(theme).panel
    val placement = Placement()
    val progress = defaultProgressStyle(theme)
    return listOf(
        ValueSample(
            name = "ResolvedColors",
            value = resolved,
            copy = resolved.copy(),
            changed = resolved.copy(ring = Color.Red),
        ),
        ValueSample(
            name = "ModalTransitions",
            value = transitions,
            copy = transitions.copy(),
            changed = transitions.copy(scrimEnter = EnterTransition.None),
        ),
        ValueSample(
            name = "PanelStyle",
            value = panel,
            copy = panel.copy(),
            changed = panel.copy(container = Color.Red),
        ),
        ValueSample(
            name = "Placement",
            value = placement,
            copy = placement.copy(),
            changed = placement.copy(side = Side.Top),
        ),
        ValueSample(
            name = "ProgressStyle",
            value = progress,
            copy = progress.copy(),
            changed = progress.copy(indicatorColor = Color.Red),
        ),
    )
}

private fun answerSamples(): List<ValueSample> {
    val single = Answer.Single(value = "a")
    val multiple = Answer.Multiple(values = setOf("a"))
    val text = Answer.Text(text = "a")
    return listOf(
        ValueSample(
            name = "Single",
            value = single,
            copy = single.copy(),
            changed = single.copy(value = "b"),
        ),
        ValueSample(
            name = "Multiple",
            value = multiple,
            copy = multiple.copy(),
            changed = multiple.copy(values = setOf("b")),
        ),
        ValueSample(
            name = "Text",
            value = text,
            copy = text.copy(),
            changed = text.copy(text = "b"),
        ),
    )
}

private fun questionnaireSamples(): List<ValueSample> {
    val key = QuestionKey(value = "a")
    val question = Question(key = key, kind = QuestionKind.Single)
    val labels = QuestionnaireLabels()
    val moved = QuestionnaireStep.Moved(index = 0)
    val invalid = QuestionnaireStep.Invalid(question = question)
    val submitted = QuestionnaireStep.Submitted(answers = mapOf(key to Answer.Single(value = "a")))
    val style = defaultQuestionnaireStyle(theme)
    val choice = style.choice
    return listOf(
        ValueSample(
            name = "Question",
            value = question,
            copy = question.copy(),
            changed = question.copy(isRequired = true),
        ),
        ValueSample(
            name = "QuestionnaireLabels",
            value = labels,
            copy = labels.copy(),
            changed = labels.copy(next = "Changed"),
        ),
        ValueSample(
            name = "Moved",
            value = moved,
            copy = moved.copy(),
            changed = moved.copy(index = 1),
        ),
        ValueSample(
            name = "Invalid",
            value = invalid,
            copy = invalid.copy(),
            changed = invalid.copy(question = question.copy(isRequired = true)),
        ),
        ValueSample(
            name = "Submitted",
            value = submitted,
            copy = submitted.copy(),
            changed = submitted.copy(answers = emptyMap()),
        ),
        ValueSample(
            name = "QuestionnaireChoiceStyle",
            value = choice,
            copy = choice.copy(),
            changed = choice.copy(container = Color.Red),
        ),
        ValueSample(
            name = "QuestionnaireStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(progressColor = Color.Red),
        ),
    )
}
