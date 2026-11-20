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

package com.davils.kiro.ui.components.sidebar.tree

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.primitive.layout.mirroredInRtl
import com.davils.kiro.ui.components.primitive.motion.fadeTransition
import com.davils.kiro.ui.components.primitive.motion.sizeTransition
import com.davils.kiro.ui.components.primitive.motion.turnBy
import com.davils.kiro.ui.components.sidebar.LocalIconCollapsed
import com.davils.kiro.ui.components.sidebar.SidebarButtonRow
import com.davils.kiro.ui.components.sidebar.SidebarStyle
import com.davils.kiro.ui.components.sidebar.sidebarStyle
import com.davils.kiro.ui.components.sidebar.startLine
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.icons.Icon
import kotlinx.coroutines.delay

private const val QUARTER_TURN: Float = 90f
private val ROW_HEIGHT = 32.dp

/**
 * A tree of navigation items in a [com.davils.kiro.ui.components.sidebar.Sidebar], with branches that expand and
 * collapse.
 *
 * Clicking an item selects it, clicking a branch expands or collapses it. The arrow keys move the focus between the
 * visible rows and expand, enter, collapse or leave branches. While the sidebar is collapsed to its icons, the tree
 * shows one icon button per top-level node, and a branch opens its children in a menu at its end.
 *
 * @param T the type of the values that identify the nodes.
 * @param state the selected node and the expanded branches.
 * @param modifier the modifier of the tree.
 * @param style the style of the sidebar, by default the style of the theme.
 * @param content declares the items and the branches of the tree.
 * @since 1.0.0
 */
@Composable
public fun <T> SidebarTree(
    state: SidebarTreeState<T>,
    modifier: Modifier = Modifier,
    style: SidebarStyle = sidebarStyle(),
    content: SidebarTreeScope<T>.() -> Unit,
) {
    val nodes = TreeScope<T>().apply(content).nodes
    SideEffect {
        state.parents = parentsOf(nodes)
        state.branches = branchesOf(nodes)
    }
    LaunchedEffect(state) {
        state.parents = parentsOf(nodes)
        state.selected?.let(state::reveal)
    }
    Crossfade(targetState = LocalIconCollapsed.current, animationSpec = theme.fadeTransition()) { isCollapsed ->
        TreeBody(isCollapsed = isCollapsed, nodes = nodes, state = state, modifier = modifier, style = style)
    }
}

@Composable
private fun <T> TreeBody(
    isCollapsed: Boolean,
    nodes: List<TreeNode<T>>,
    state: SidebarTreeState<T>,
    modifier: Modifier,
    style: SidebarStyle,
) {
    if (isCollapsed) {
        IconTree(nodes = nodes, state = state, modifier = modifier, style = style)
        return
    }
    val focusers = remember { mutableMapOf<T, FocusRequester>() }
    var focused by remember { mutableStateOf<T?>(null) }
    val isReversed = LocalLayoutDirection.current == LayoutDirection.Rtl
    val keys = TreeKeys(nodes = nodes, state = state, focused = focused, isReversed = isReversed) { value ->
        focusers[value]?.requestFocus()
    }
    val context = TreeContext(state = state, style = style, focusers = focusers) { value -> focused = value }
    Column(
        modifier = modifier.fillMaxWidth().onPreviewKeyEvent(keys::handle),
        verticalArrangement = Arrangement.spacedBy(style.subGap),
    ) {
        nodes.forEach { node -> key(node.value) { TreeNodeView(node = node, depth = 0, context = context) } }
    }
}

@Composable
private fun <T> TreeNodeView(node: TreeNode<T>, depth: Int, context: TreeContext<T>) {
    val focus = remember { FocusRequester() }
    DisposableEffect(node.value) {
        context.focusers[node.value] = focus
        onDispose { context.focusers.remove(node.value) }
    }
    when (node) {
        is TreeNode.Leaf<T> -> TreeRow(
            node = node,
            depth = depth,
            isExpanded = null,
            focus = focus,
            context = context,
        ) {
            context.state.select(node.value)
            node.onClick()
        }

        is TreeNode.Branch<T> -> TreeBranch(branch = node, depth = depth, focus = focus, context = context)
    }
}

@Composable
private fun <T> TreeBranch(branch: TreeNode.Branch<T>, depth: Int, focus: FocusRequester, context: TreeContext<T>) {
    val style = context.style
    val isExpanded = context.state.isExpanded(branch.value)
    val requester = remember { BringIntoViewRequester() }
    var hasBeenExpanded by remember { mutableStateOf(isExpanded) }
    val settle = theme.motion.durations.medium
    LaunchedEffect(isExpanded) {
        if (isExpanded && !hasBeenExpanded) {
            delay(settle)
            requester.bringIntoView()
        }
        hasBeenExpanded = isExpanded
    }
    Column(modifier = Modifier.bringIntoViewRequester(requester)) {
        TreeRow(node = branch, depth = depth, isExpanded = isExpanded, focus = focus, context = context) {
            context.state.toggle(branch.value)
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(
                animationSpec = theme.sizeTransition(),
            ) +
                fadeIn(animationSpec = theme.fadeTransition()),
            exit = shrinkVertically(
                animationSpec = theme.sizeTransition(),
            ) +
                fadeOut(animationSpec = theme.fadeTransition()),
        ) {
            Column(
                modifier = Modifier
                    .padding(start = style.subIndent, top = style.subGap)
                    .then(startLine(style.border))
                    .padding(start = style.subPadding),
                verticalArrangement = Arrangement.spacedBy(style.subGap),
            ) {
                branch.children.forEach { child ->
                    key(child.value) { TreeNodeView(node = child, depth = depth + 1, context = context) }
                }
            }
        }
    }
}

@Composable
private fun <T> TreeRow(
    node: TreeNode<T>,
    depth: Int,
    isExpanded: Boolean?,
    focus: FocusRequester,
    context: TreeContext<T>,
    onClick: () -> Unit,
) {
    val style = context.style
    SidebarButtonRow(
        onClick = onClick,
        modifier = Modifier
            .focusRequester(focus)
            .onFocusChanged { state -> if (state.isFocused) context.onFocused(node.value) },
        isActive = context.state.selected == node.value,
        isOutlined = false,
        height = style.subHeight.takeIf { depth > 0 } ?: ROW_HEIGHT,
        textStyle = style.textStyle,
        style = style,
    ) {
        node.icon?.let { icon -> Icon(icon = icon, placement = IconPlacement.Inline) }
        Text(text = node.label, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
        node.badge?.let { badge -> Text(text = badge, style = style.badgeStyle) }
        isExpanded?.let { expanded -> Chevron(isExpanded = expanded, style = style) }
    }
}

@Composable
private fun Chevron(isExpanded: Boolean, style: SidebarStyle) {
    val turn = animateFloatAsState(
        targetValue = QUARTER_TURN.takeIf { isExpanded } ?: 0f,
        animationSpec = theme.sizeTransition(),
    )
    Icon(icon = style.chevron, contentDescription = null, modifier = Modifier.mirroredInRtl().turnBy(turn))
}
