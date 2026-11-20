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

package com.davils.kiro.ui.components.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.components.dialog.CloseButton
import com.davils.kiro.ui.components.dialog.initialFocus
import com.davils.kiro.ui.components.primitive.overlay.ModalLayer
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.primitive.overlay.latched
import com.davils.kiro.ui.components.primitive.overlay.physicalSideOf
import com.davils.kiro.ui.components.primitive.overlay.sheetTransitions
import com.davils.kiro.ui.components.primitive.surface.boxShadow
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle

@Composable
public fun Sheet(
    open: Boolean,
    onOpenChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    side: Side = Side.End,
    isCloseButtonShown: Boolean = true,
    style: SheetStyle = sheetStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val shown = latched(value = side, isOpen = open)
    val physical = physicalSideOf(side = shown, isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl)
    ModalLayer(
        isOpen = open,
        onDismiss = { onOpenChange(false) },
        isScrimDismissing = true,
        scrim = style.scrim,
        transitions = sheetTransitions(motion = theme.motion, physical = physical),
        alignment = alignmentOf(physical),
    ) {
        CompositionLocalProvider(LocalContentColor provides style.content, LocalTextStyle provides style.textStyle) {
            Box(
                modifier = modifier
                    .edgeSized(physical = physical, fraction = style.fraction, minSize = style.minSize)
                    .boxShadow(shape = RectangleShape, shadow = style.shadow)
                    .background(style.container)
                    .innerEdge(physical = physical, border = style.border)
                    .initialFocus(),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(style.gap), content = content)
                if (isCloseButtonShown) {
                    CloseButton(
                        icon = style.closeIcon,
                        label = style.closeLabel,
                        offset = style.closeOffset,
                    ) { onOpenChange(false) }
                }
            }
        }
    }
}

@Composable
public fun SheetHeader(
    modifier: Modifier = Modifier,
    style: SheetStyle = sheetStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.padding(style.sectionPadding),
        verticalArrangement = Arrangement.spacedBy(style.headerGap),
        content = content,
    )
}

@Composable
public fun ColumnScope.SheetFooter(
    modifier: Modifier = Modifier,
    style: SheetStyle = sheetStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Spacer(modifier = Modifier.weight(1f))
    Column(
        modifier = modifier.padding(style.sectionPadding),
        verticalArrangement = Arrangement.spacedBy(style.footerGap),
        content = content,
    )
}

@Composable
public fun SheetTitle(text: String, modifier: Modifier = Modifier, style: SheetStyle = sheetStyle()) {
    Text(text = text, modifier = modifier, style = style.titleStyle)
}

@Composable
public fun SheetDescription(text: String, modifier: Modifier = Modifier, style: SheetStyle = sheetStyle()) {
    Text(text = text, modifier = modifier, style = style.descriptionStyle, color = style.descriptionColor)
}
