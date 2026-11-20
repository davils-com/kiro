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

package com.davils.kiro.ui.components.nativeselect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

/**
 * A field that picks one value from a list of options, drawn by Kiro in the manner of the select element of a web
 * page.
 *
 * The field is as wide as its longest option. Clicking it opens a menu below it, at least as wide as the field, in
 * which the arrow keys move the highlight and Enter or Space pick the highlighted option. The arrow keys on the
 * focused field step to the previous or next enabled option without opening the menu.
 *
 * @param T the type of the values.
 * @param selected the value of the picked option.
 * @param onSelectedChange called with the value of the option the user picks.
 * @param modifier the modifier of the field.
 * @param size the size of the field.
 * @param enabled whether the field can be opened and changed; a disabled field is drawn with the disabled alpha of
 * its style.
 * @param isInvalid whether the value is invalid, which the field shows with the invalid colors of its style.
 * @param style the style of the field, by default the style of the theme for [size].
 * @param content declares the options and the groups of the field.
 * @since 1.0.0
 */
@Composable
public fun <T> NativeSelect(
    selected: T,
    onSelectedChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    size: NativeSelectSize = NativeSelectSize.Default,
    enabled: Boolean = true,
    isInvalid: Boolean = false,
    style: NativeSelectStyle = nativeSelectStyle(size),
    content: NativeSelectScope<T>.() -> Unit,
) {
    val entries = NativeSelectScopeImpl<T>().apply(content).entries
    val options = entries.filterIsInstance<NativeSelectEntry.Option<T>>()
    var isOpen by remember { mutableStateOf(false) }
    var anchorWidth by remember { mutableIntStateOf(0) }
    val source = remember { MutableInteractionSource() }
    val state = source.collectInteractionState(isDisabled = !enabled, isExpanded = isOpen, isInvalid = isInvalid)
    val colors = style.colors.animate(state = state, animation = style.colorAnimation)
    val surface = Modifier.surface(
        shape = style.shape,
        color = colors.container,
        border = Border(width = style.borderWidth, color = colors.border),
        ring = Border(width = style.ringWidth, color = colors.ring),
    )
    val keys = Modifier.onKeyEvent { event ->
        val step = arrowSteps[event.key]?.takeIf { event.type == KeyEventType.KeyDown }
        val next = step?.let { delta -> stepFrom(options = options, index = indexOf(options, selected), delta = delta) }
        next?.let { index -> onSelectedChange(options[index].value) }
        step != null
    }
    val open = Modifier.pressableCursor(enabled = enabled).clickable(
        interactionSource = source,
        indication = null,
        enabled = enabled,
        role = Role.DropdownList,
        onClick = { isOpen = true },
    )
    Box(
        modifier = modifier.then(
            Modifier
                .alpha(disabledAlphaOf(isDisabled = !enabled, alpha = style.disabledAlpha))
                .height(style.height)
                .onSizeChanged { measured -> anchorWidth = measured.width }
                .then(surface)
                .hoverable(interactionSource = source, enabled = enabled)
                .then(open.then(keys)),
        ),
        contentAlignment = Alignment.CenterStart,
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.content, LocalTextStyle provides style.textStyle) {
            Box(modifier = Modifier.padding(style.padding)) {
                options.forEach { option -> SizingLabel(label = option.label, isShown = option.value == selected) }
            }
        }
        Chevron(icon = style.icon, modifier = Modifier.align(Alignment.CenterEnd))
        if (isOpen) {
            NativeSelectMenu(
                entries = entries,
                selected = selected,
                minWidth = with(LocalDensity.current) { anchorWidth.toDp() },
                style = style.menu,
                onSelect = { value ->
                    isOpen = false
                    onSelectedChange(value)
                },
                onDismiss = { isOpen = false },
            )
        }
    }
}

@Composable
private fun Chevron(icon: NativeSelectIcon, modifier: Modifier) {
    CompositionLocalProvider(LocalIconStyle provides IconStyle(size = icon.size, color = icon.color)) {
        Icon(icon = icon.icon, contentDescription = null, modifier = modifier.padding(end = icon.endPadding))
    }
}

@Composable
private fun SizingLabel(label: String, isShown: Boolean) {
    Text(text = label, modifier = labelModifierOf(isShown), maxLines = 1)
}

private fun labelModifierOf(isShown: Boolean): Modifier {
    if (isShown) return Modifier
    return Modifier.alpha(0f).clearAndSetSemantics {}
}
