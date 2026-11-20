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

package com.davils.kiro.ui.components.contextmenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.menu.MenuController
import com.davils.kiro.ui.components.menu.MenuPanel
import com.davils.kiro.ui.components.menu.MenuScopeImpl
import com.davils.kiro.ui.components.menu.MenuScope
import com.davils.kiro.ui.components.menu.MenuStyle
import com.davils.kiro.ui.components.menu.menuStyle
import com.davils.kiro.ui.components.menu.rememberMenuCascade
import com.davils.kiro.ui.components.primitive.overlay.Align
import com.davils.kiro.ui.components.primitive.overlay.OverlayPopup
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.Side
import kotlin.math.roundToInt

private val MIN_WIDTH = 144.dp

@Composable
public fun ContextMenu(
    menu: MenuScope.() -> Unit,
    modifier: Modifier = Modifier,
    style: MenuStyle = menuStyle(),
    content: @Composable () -> Unit,
) {
    val entries = MenuScopeImpl().apply(menu).entries
    var isOpen by remember { mutableStateOf(false) }
    var position by remember { mutableStateOf(Offset.Zero) }
    val cascade = rememberMenuCascade { isOpen = false }
    val press = Modifier.pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                val isSecondary = event.type == PointerEventType.Press && event.buttons.isSecondaryPressed
                if (isSecondary) {
                    position = event.changes.first().position
                    isOpen = true
                }
            }
        }
    }
    Box(modifier = modifier.then(press)) {
        content()
        val anchor = Modifier.offset { IntOffset(x = position.x.roundToInt(), y = position.y.roundToInt()) }
        Box(modifier = anchor.size(0.dp)) {
            OverlayPopup(
                isOpen = isOpen,
                placement = Placement(side = Side.End, align = Align.Start, sideOffset = 0.dp),
                isFocusable = true,
                onDismiss = cascade::dismiss,
            ) {
                MenuPanel(
                    entries = entries,
                    controller = remember { MenuController() },
                    minWidth = maxOf(style.minWidth, MIN_WIDTH),
                    panelStyle = style.panel,
                    style = style,
                    onCloseAll = { isOpen = false },
                    isFocusRequested = true,
                    cascade = cascade,
                )
            }
        }
    }
}
