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

package com.davils.kiro.ui.components.tabs

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.zIndex
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.layout.InlineScopeImpl
import com.davils.kiro.ui.components.primitive.surface.boxShadow
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.elevation.BoxShadow
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

@Composable
internal fun <T> TabTrigger(
    entry: TabEntry<T>,
    state: TabState,
    orientation: Orientation,
    modifier: Modifier,
    onSelect: () -> Unit,
    style: TabsStyle,
) {
    val source = remember { MutableInteractionSource() }
    val requester = remember { FocusRequester() }
    LaunchedEffect(state.isFocusRequested) { if (state.isFocusRequested) requester.requestFocus() }
    val interaction = source.collectInteractionState(isDisabled = !entry.isEnabled, isSelected = state.isSelected)
    val colors = style.colors.animate(state = interaction, animation = style.colorAnimation)
    val indicator = animateColor(
        targetValue = style.indicator?.run { color.takeIf { state.isSelected } } ?: Color.Transparent,
        animationSpec = style.colorAnimation,
    )
    val shadow = style.activeShadow.takeIf { state.isSelected } ?: BoxShadow.None
    val face = Modifier
        .boxShadow(shape = style.triggerShape, shadow = shadow)
        .surface(
            shape = style.triggerShape,
            color = colors.container,
            border = Border(width = style.borderWidth, color = colors.border),
            ring = Border(width = style.ringWidth, color = colors.ring),
        )
    val select = Modifier.pressableCursor(enabled = entry.isEnabled).selectable(
        selected = state.isSelected,
        interactionSource = source,
        indication = null,
        enabled = entry.isEnabled,
        role = Role.Tab,
        onClick = onSelect,
    )
    Row(
        modifier = modifier.then(
            Modifier
                .zIndex(layerOf(interaction.isFocusVisible))
                .alpha(disabledAlphaOf(isDisabled = !entry.isEnabled, alpha = style.disabledAlpha))
                .heightIn(min = style.triggerHeight)
                .drawBehind { drawIndicator(color = indicator.value, orientation = orientation, style = style) }
                .then(face)
                .focusRequester(requester)
                .focusProperties { canFocus = state.isTabbable }
                .hoverable(interactionSource = source, enabled = entry.isEnabled)
                .then(select)
                .padding(horizontal = style.triggerHorizontalPadding),
        ),
        horizontalArrangement = arrangementOf(orientation = orientation, gap = style),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides colors.content,
            LocalTextStyle provides style.textStyle,
            LocalIconStyle provides IconStyle(size = style.iconSize),
        ) {
            val inline = InlineScopeImpl(
                rowScope = this,
                edgeInset = style.triggerHorizontalPadding - style.triggerIconEdgePadding,
            )
            entry.label(inline)
        }
    }
}

private fun arrangementOf(orientation: Orientation, gap: TabsStyle): Arrangement.Horizontal = when (orientation) {
    Orientation.Horizontal -> Arrangement.spacedBy(gap.triggerGap, Alignment.CenterHorizontally)
    Orientation.Vertical -> Arrangement.spacedBy(gap.triggerGap, Alignment.Start)
}

private fun DrawScope.drawIndicator(color: Color, orientation: Orientation, style: TabsStyle) {
    val indicator = style.indicator ?: return
    val thickness = indicator.thickness.toPx()
    when (orientation) {
        Orientation.Horizontal -> drawRect(
            color = color,
            topLeft = Offset(x = 0f, y = size.height + indicator.offset.toPx() - thickness),
            size = Size(width = size.width, height = thickness),
        )

        Orientation.Vertical -> drawRect(
            color = color,
            topLeft = Offset(x = size.width + indicator.verticalOffset.toPx() - thickness, y = 0f),
            size = Size(width = thickness, height = size.height),
        )
    }
}

private fun layerOf(isFocusVisible: Boolean): Float {
    if (isFocusVisible) return 1f
    return 0f
}
