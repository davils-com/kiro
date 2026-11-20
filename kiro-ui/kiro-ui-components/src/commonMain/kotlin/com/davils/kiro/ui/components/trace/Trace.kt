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

package com.davils.kiro.ui.components.trace

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import com.davils.kiro.ui.components.collapsible.Collapsible
import com.davils.kiro.ui.components.collapsible.CollapsibleContent
import com.davils.kiro.ui.components.collapsible.CollapsibleScope
import com.davils.kiro.ui.components.collapsible.CollapsibleTrigger
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.motion.sizeTransition
import com.davils.kiro.ui.components.primitive.motion.turnBy
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.text.ShimmerText
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.focus.collectIsFocusVisibleAsState
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

private const val QUARTER_TURN: Float = 90f

internal val LocalTraceStatus = staticCompositionLocalOf { TraceStatus.Done }

@Composable
public fun Trace(
    modifier: Modifier = Modifier,
    style: TraceStyle = traceStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalTextStyle provides style.textStyle,
        LocalContentColor provides style.color,
        LocalIconStyle provides IconStyle(size = style.iconSize),
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(style.gap),
            content = content,
        )
    }
}

@Composable
public fun TraceLine(
    modifier: Modifier = Modifier,
    status: TraceStatus = TraceStatus.Done,
    onClick: (() -> Unit)? = null,
    style: TraceStyle = traceStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    val isFocusVisible by source.collectIsFocusVisibleAsState()
    val isLit = onClick != null && isHovered
    val color by animateColor(colorOf(status = status, isLit = isLit, style = style), theme.colorTransition())
    val click = onClick?.let { action ->
        Modifier.pressableCursor().clickable(
            interactionSource = source,
            indication = null,
            role = Role.Button,
            onClick = action,
        )
    }
    val ring = Border(
        width = style.ring.width,
        color = style.ring.color.takeIf { isFocusVisible } ?: Color.Transparent,
    )
    CompositionLocalProvider(LocalContentColor provides color, LocalTraceStatus provides status) {
        Row(
            modifier = modifier
                .alpha(style.pendingAlpha.takeIf { status == TraceStatus.Pending } ?: 1f)
                .surface(shape = style.shape, color = Color.Transparent, ring = ring)
                .hoverable(interactionSource = source)
                .then(click ?: Modifier),
            horizontalArrangement = Arrangement.spacedBy(style.lineGap),
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

@Composable
public fun TraceIcon(
    modifier: Modifier = Modifier,
    style: TraceStyle = traceStyle(),
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier.size(style.iconSize), contentAlignment = Alignment.Center) { content() }
}

@Composable
public fun TraceTitle(text: String, modifier: Modifier = Modifier, style: TraceStyle = traceStyle()) {
    val isShimmering = LocalTraceStatus.current == TraceStatus.Running && !theme.motion.isReduced
    if (!isShimmering) {
        Text(text = text, modifier = modifier, maxLines = 1, overflow = TextOverflow.Ellipsis)
        return
    }
    ShimmerText(text = text, highlight = style.shimmerColor, modifier = modifier, durationMillis = style.shimmerMillis)
}

@Composable
public fun TraceDescription(text: String, modifier: Modifier = Modifier, style: TraceStyle = traceStyle()) {
    Text(text = text, modifier = modifier, color = style.descriptionColor, maxLines = 1)
}

@Composable
public fun TraceCollapsible(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    style: TraceStyle = traceStyle(),
    content: @Composable CollapsibleScope.() -> Unit,
) {
    Collapsible(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier.fillMaxWidth(),
        style = style.motion,
        content = content,
    )
}

@Composable
public fun CollapsibleScope.TraceCollapsibleTrigger(
    modifier: Modifier = Modifier,
    style: TraceStyle = traceStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    val turn = animateFloatAsState(QUARTER_TURN.takeIf { isExpanded } ?: 0f, animationSpec = theme.sizeTransition())
    CollapsibleTrigger(modifier = modifier) {
        content()
        TraceIcon(
            style = style,
        ) { Icon(icon = style.chevron, contentDescription = null, modifier = Modifier.turnBy(turn)) }
    }
}

@Composable
public fun CollapsibleScope.TraceCollapsibleContent(
    modifier: Modifier = Modifier,
    style: TraceStyle = traceStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    CollapsibleContent(modifier = modifier) {
        CompositionLocalProvider(LocalTextStyle provides style.contentStyle, LocalContentColor provides style.color) {
            Column(
                modifier = Modifier
                    .padding(start = style.contentIndent, top = style.gap, bottom = style.gap / 2)
                    .then(startLine(style.contentBorder))
                    .padding(start = style.contentPadding, end = style.gap / 2),
                content = content,
            )
        }
    }
}

private fun startLine(color: Color): Modifier = Modifier.drawBehind {
    val stroke = 1f * density
    drawLine(color = color, start = Offset(stroke / 2, 0f), end = Offset(stroke / 2, size.height), strokeWidth = stroke)
}

private fun colorOf(status: TraceStatus, isLit: Boolean, style: TraceStyle): Color {
    if (status == TraceStatus.Error) return style.errorColor
    return style.hoverColor.takeIf { isLit } ?: style.color
}
