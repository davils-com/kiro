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

package com.davils.kiro.ui.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.menu.intent.PanelCoordinates
import com.davils.kiro.ui.components.menu.intent.pointerTracking
import com.davils.kiro.ui.components.primitive.overlay.Align
import com.davils.kiro.ui.components.primitive.overlay.OverlayPopup
import com.davils.kiro.ui.components.primitive.overlay.PanelStyle
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.primitive.overlay.hoverTracking
import com.davils.kiro.ui.components.primitive.overlay.panel
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor
import kotlinx.coroutines.delay

@Composable
internal fun MenuPanel(
    entries: List<MenuEntry>,
    controller: MenuController,
    minWidth: Dp,
    panelStyle: PanelStyle,
    style: MenuStyle,
    onCloseAll: () -> Unit,
    isFocusRequested: Boolean,
    nesting: MenuNesting = MenuNesting(),
    cascade: MenuCascade? = null,
) {
    val shared = cascade ?: rememberMenuCascade(onCloseAll)
    val closeAll: () -> Unit = shared::dismiss.takeIf { nesting.chain == null } ?: onCloseAll
    val focus = remember { FocusRequester() }
    val isOpenSub = controller.openSub != null
    LaunchedEffect(isOpenSub, isFocusRequested) { if (isFocusRequested && !isOpenSub) focus.requestFocus() }
    SubMenuTimers(controller = controller, entries = entries, style = style)
    val coordinates = remember { PanelCoordinates() }
    val keys = MenuKeys(
        entries = entries,
        controller = controller,
        isReversed = LocalLayoutDirection.current == LayoutDirection.Rtl,
        onCloseAll = closeAll,
        onCloseSelf = nesting.onCloseSelf,
    )
    val chain = nesting.chain ?: shared.chain
    ChainRegistration(chain = chain, depth = nesting.depth, controller = controller)
    val hovering = Modifier.hoverTracking(
        onHover = { isOver -> hoverOf(chain = chain, nesting = nesting, isOver = isOver) },
    )
    val inner = innerOf(nesting = nesting, chain = chain, panelStyle = panelStyle, onCloseAll = closeAll)
    val positioned = Modifier.onGloballyPositioned { layout ->
        coordinates.layout = layout
        nesting.onBounds(layout.boundsInWindow())
    }
    val tracking = Modifier.pointerTracking(coordinates) { point -> controller.track(entries = entries, point = point) }
    CompositionLocalProvider(LocalContentColor provides panelStyle.content) {
        Column(
            modifier = Modifier
                .widthIn(min = minWidth)
                .width(IntrinsicSize.Max)
                .then(positioned)
                .then(tracking)
                .then(hovering)
                .panel(panelStyle)
                .focusRequester(focus)
                .onKeyEvent(keys::handle)
                .focusable(),
        ) {
            entries.forEachIndexed { index, entry ->
                MenuEntryView(
                    index = index,
                    entry = entry,
                    entries = entries,
                    controller = controller,
                    style = style,
                    onCloseAll = closeAll,
                    nesting = inner,
                )
            }
        }
    }
}

@Composable
private fun SubMenuTimers(controller: MenuController, entries: List<MenuEntry>, style: MenuStyle) {
    val wanted = controller.wantedSub
    LaunchedEffect(wanted) {
        delay(style.subOpenDelay.takeIf { wanted != null } ?: style.subCloseDelay)
        controller.settle()
    }
    val deferred = controller.deferred
    LaunchedEffect(deferred) {
        delay(style.subCloseDelay)
        controller.release(entries)
    }
}

@Composable
private fun ChainRegistration(chain: MenuChain, depth: Int, controller: MenuController) {
    DisposableEffect(chain, controller, depth) {
        chain.register(depth = depth, controller = controller)
        onDispose { chain.unregister(depth = depth, controller = controller) }
    }
}

@Composable
private fun MenuEntryView(
    index: Int,
    entry: MenuEntry,
    entries: List<MenuEntry>,
    controller: MenuController,
    style: MenuStyle,
    onCloseAll: () -> Unit,
    nesting: MenuNesting,
) {
    when (entry) {
        is MenuEntry.Label -> Text(
            text = entry.text,
            modifier = Modifier.padding(
                start = style.insetPadding.takeIf { entry.isInset } ?: style.itemHorizontalPadding,
                end = style.itemHorizontalPadding,
                top = style.itemVerticalPadding,
                bottom = style.itemVerticalPadding,
            ),
            style = style.labelStyle,
            color = style.labelColor,
        )

        MenuEntry.Separator -> Box(
            modifier = Modifier
                .bleed(style.panel.padding)
                .padding(vertical = style.separatorSpacing)
                .height(1.dp)
                .background(style.separatorColor),
        )

        is MenuEntry.Interactive -> Box {
            MenuRow(
                entry = entry,
                isHighlighted = controller.highlighted == index,
                style = style,
                onHover = { controller.hover(entries = entries, index = index) },
                onActivate = { controller.activate(entries = entries, index = index, onCloseAll = onCloseAll) },
            )
            if (entry is MenuEntry.Sub) {
                SubPopup(
                    sub = entry,
                    isOpen = controller.openSub == index,
                    controller = controller,
                    style = style,
                    onCloseAll = onCloseAll,
                    nesting = nesting,
                )
            }
        }
    }
}

@Composable
private fun SubPopup(
    sub: MenuEntry.Sub,
    isOpen: Boolean,
    controller: MenuController,
    style: MenuStyle,
    onCloseAll: () -> Unit,
    nesting: MenuNesting,
) {
    OverlayPopup(
        isOpen = isOpen,
        placement = Placement(
            side = Side.End,
            align = Align.Start,
            sideOffset = nesting.edge,
            alignOffset = -style.subPanel.padding,
        ),
        isFocusable = nesting.isSubFocusable,
        onDismiss = { dismissSub(chain = nesting.chain, controller = controller, onOutside = nesting.onOutside) },
        onEscape = controller::close,
    ) {
        MenuPanel(
            entries = sub.entries,
            controller = remember { MenuController() },
            minWidth = style.subMinWidth,
            panelStyle = style.subPanel,
            style = style,
            onCloseAll = onCloseAll,
            isFocusRequested = nesting.isSubFocusable,
            nesting = nesting.copy(
                onCloseSelf = controller::close,
                onBounds = { bounds -> controller.subBounds = bounds },
                depth = nesting.depth + 1,
            ),
        )
    }
}

private fun innerOf(
    nesting: MenuNesting,
    chain: MenuChain,
    panelStyle: PanelStyle,
    onCloseAll: () -> Unit,
): MenuNesting = nesting.copy(
    chain = chain,
    onOutside = nesting.onOutside ?: onCloseAll,
    edge = panelStyle.padding + panelStyle.ring.width,
)

private fun hoverOf(chain: MenuChain, nesting: MenuNesting, isOver: Boolean) {
    chain.hover(isOver)
    nesting.onHover?.invoke(isOver)
}

private fun dismissSub(chain: MenuChain?, controller: MenuController, onOutside: (() -> Unit)?) {
    val isInside = chain?.isPointerInside != false
    if (isInside || onOutside == null) {
        controller.close()
        return
    }
    onOutside()
}

private fun Modifier.bleed(amount: Dp): Modifier = layout { measurable, constraints ->
    val extra = amount.roundToPx() * 2
    val placeable = measurable.measure(widened(constraints = constraints, extra = extra))
    layout(
        widthOf(constraints = constraints, measured = placeable.width),
        placeable.height,
    ) { placeable.place(-extra / 2, 0) }
}.fillMaxWidth()

private fun widened(constraints: Constraints, extra: Int): Constraints {
    if (!constraints.hasBoundedWidth) return constraints
    return constraints.copy(minWidth = constraints.minWidth + extra, maxWidth = constraints.maxWidth + extra)
}

private fun widthOf(constraints: Constraints, measured: Int): Int {
    if (!constraints.hasBoundedWidth) return measured
    return constraints.maxWidth
}
