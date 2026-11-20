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

package com.davils.kiro.ui.components.inputgroup

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.TextFieldCore
import com.davils.kiro.ui.components.primitive.textfield.collectFieldState
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

@Composable
public fun InputGroup(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isInvalid: Boolean = false,
    style: InputGroupStyle = inputGroupStyle(),
    content: InputGroupScope.() -> Unit,
) {
    InputGroupLayout(
        modifier = modifier,
        enabled = enabled,
        isInvalid = isInvalid,
        style = style,
        focus = remember { FocusRequester() },
        content = content,
    )
}

@Composable
internal fun InputGroupLayout(
    modifier: Modifier,
    enabled: Boolean,
    isInvalid: Boolean,
    style: InputGroupStyle,
    focus: FocusRequester,
    content: InputGroupScope.() -> Unit,
) {
    val scope = InputGroupScopeImpl().apply(content)
    val addons = scope.addons
    val control = scope.control
    val source = remember { MutableInteractionSource() }
    val state = source.collectFieldState(isDisabled = !enabled, isInvalid = isInvalid)
    val colors = style.colors.animate(state = state, animation = style.colorAnimation)
    val alpha = disabledAlphaOf(isDisabled = !enabled, alpha = style.disabledAlpha)
    val surface = Modifier.surface(
        shape = style.shape,
        color = colors.container,
        border = Border(width = style.borderWidth, color = colors.border),
        ring = Border(width = style.ringWidth, color = colors.ring),
    )
    Column(
        modifier = modifier
            .graphicsLayer { this.alpha = alpha }
            .fillMaxWidth()
            .groupHeight(isFixed = isFixedHeight(control = control, addons = addons), height = style.minHeight)
            .then(surface)
            .padding(style.borderWidth),
        verticalArrangement = Arrangement.Center,
    ) {
        Addons(addons = addons, align = InputGroupAlign.BlockStart, style = style.addon, focus = focus)
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Addons(addons = addons, align = InputGroupAlign.InlineStart, style = style.addon, focus = focus)
            CompositionLocalProvider(LocalContentColor provides colors.content) {
                TextFieldCore(
                    text = control.text,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focus)
                        .heightIn(min = minHeightOf(control = control, style = style.control)),
                    enabled = enabled,
                    options = control.options,
                    isSingleLine = control.isSingleLine,
                    padding = paddingOf(control = control, addons = addons, style = style.control),
                    style = style.control.text,
                    interactionSource = source,
                )
            }
            Addons(addons = addons, align = InputGroupAlign.InlineEnd, style = style.addon, focus = focus)
        }
        Addons(addons = addons, align = InputGroupAlign.BlockEnd, style = style.addon, focus = focus)
    }
}

@Composable
private fun Addons(
    addons: List<GroupAddon>,
    align: InputGroupAlign,
    style: InputGroupAddonStyle,
    focus: FocusRequester,
) {
    val aligned = addons.filter { addon -> addon.align == align }
    aligned.forEach { addon -> Addon(addon = addon, style = style, focus = focus) }
}

@Composable
private fun Addon(addon: GroupAddon, style: InputGroupAddonStyle, focus: FocusRequester) {
    val tap = Modifier.pointerInput(focus) { detectTapGestures { focus.requestFocus() } }
    Layout(
        content = {
            CompositionLocalProvider(
                LocalContentColor provides style.color,
                LocalTextStyle provides style.textStyle,
                LocalIconStyle provides IconStyle(size = style.iconSize),
                content = addon.content,
            )
        },
        modifier = addonModifierOf(
            align = addon.align,
            style = style,
        ).then(Modifier.pointerHoverIcon(PointerIcon.Text).then(tap)),
        measurePolicy = AddonMeasurePolicy(gap = style.gap, buttonOffset = style.buttonOffset, align = addon.align),
    )
}

private fun addonModifierOf(align: InputGroupAlign, style: InputGroupAddonStyle): Modifier = when (align) {
    InputGroupAlign.InlineStart -> Modifier.wrapContentHeight(unbounded = true).padding(
        start = style.inlinePadding,
        top = style.verticalPadding,
        bottom = style.verticalPadding,
    )

    InputGroupAlign.InlineEnd -> Modifier.wrapContentHeight(unbounded = true).padding(
        end = style.inlinePadding,
        top = style.verticalPadding,
        bottom = style.verticalPadding,
    )

    InputGroupAlign.BlockStart -> Modifier.fillMaxWidth().padding(
        start = style.blockHorizontalPadding,
        end = style.blockHorizontalPadding,
        top = style.blockPadding,
        bottom = style.verticalPadding,
    )

    InputGroupAlign.BlockEnd -> Modifier.fillMaxWidth().padding(
        start = style.blockHorizontalPadding,
        end = style.blockHorizontalPadding,
        top = style.verticalPadding,
        bottom = style.blockPadding,
    )
}

private fun isFixedHeight(control: GroupControl, addons: List<GroupAddon>): Boolean {
    val hasBlock = addons.any { addon -> addon.align in blockAligns }
    return control.isSingleLine && !hasBlock
}

private val blockAligns: Set<InputGroupAlign> = setOf(InputGroupAlign.BlockStart, InputGroupAlign.BlockEnd)

private fun Modifier.groupHeight(isFixed: Boolean, height: Dp): Modifier {
    if (isFixed) return height(height)
    return heightIn(min = height)
}

private fun minHeightOf(control: GroupControl, style: InputGroupControlStyle): Dp {
    if (control.isSingleLine) return 0.dp
    return style.textareaMinHeight
}

private fun paddingOf(control: GroupControl, addons: List<GroupAddon>, style: InputGroupControlStyle): PaddingValues {
    if (!control.isSingleLine) {
        return PaddingValues(horizontal = style.horizontalPadding, vertical = style.textareaVerticalPadding)
    }
    val aligns = addons.map { addon -> addon.align }
    return PaddingValues(
        start = edgeOf(
            isAdjacent = InputGroupAlign.InlineStart in aligns,
            style.inlinePadding,
            style.horizontalPadding,
        ),
        top = edgeOf(isAdjacent = InputGroupAlign.BlockEnd in aligns, style.blockPadding, style.verticalPadding),
        end = edgeOf(
            isAdjacent = InputGroupAlign.InlineEnd in aligns,
            style.inlinePadding,
            style.horizontalPadding,
        ),
        bottom = edgeOf(
            isAdjacent = InputGroupAlign.BlockStart in aligns,
            style.blockPadding,
            style.verticalPadding,
        ),
    )
}

private fun edgeOf(isAdjacent: Boolean, adjacent: Dp, otherwise: Dp): Dp {
    if (isAdjacent) return adjacent
    return otherwise
}
