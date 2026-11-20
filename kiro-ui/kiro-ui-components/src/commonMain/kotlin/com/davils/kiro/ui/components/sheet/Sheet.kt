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

/**
 * A modal panel that slides in from an edge of the window over a scrim.
 *
 * The panel takes a fraction of the width or the height of the window, but at least the minimum size of the style.
 * Clicking the scrim, pressing Escape or clicking the close button asks to close it.
 *
 * @param open whether the sheet is open.
 * @param onOpenChange called with `false` when the sheet asks to close.
 * @param modifier the modifier of the panel.
 * @param side the edge the sheet slides in from; [Side.Start] and [Side.End] follow the layout direction.
 * @param isCloseButtonShown whether a close button is shown in the top end corner of the panel.
 * @param style the look of the sheet and its parts.
 * @param content the content of the panel, laid out in a column.
 * @since 1.0.0
 */
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
                modifier = modifier.then(
                    Modifier
                        .edgeSized(physical = physical, fraction = style.fraction, minSize = style.minSize)
                        .boxShadow(shape = RectangleShape, shadow = style.shadow)
                        .background(style.container)
                        .innerEdge(physical = physical, border = style.border)
                        .initialFocus(),
                ),
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

/**
 * The top section of a [Sheet], such as for a [SheetTitle] and a [SheetDescription], laid out in a column.
 *
 * @param modifier the modifier of the header.
 * @param style the style that gives the padding and the gap of the header.
 * @param content the content of the header.
 * @since 1.0.0
 */
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

/**
 * The bottom section of a [Sheet], such as for its buttons, pushed to the bottom of the panel and laid out in a column.
 *
 * @param modifier the modifier of the footer.
 * @param style the style that gives the padding and the gap of the footer.
 * @param content the content of the footer.
 * @since 1.0.0
 */
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

/**
 * Shows the title of a [Sheet] in the title style.
 *
 * @param text the title.
 * @param modifier the modifier of the title.
 * @param style the style that gives the text style of the title.
 * @since 1.0.0
 */
@Composable
public fun SheetTitle(text: String, modifier: Modifier = Modifier, style: SheetStyle = sheetStyle()) {
    Text(text = text, modifier = modifier, style = style.titleStyle)
}

/**
 * Shows the description of a [Sheet] in the description style and color.
 *
 * @param text the description.
 * @param modifier the modifier of the description.
 * @param style the style that gives the text style and the color of the description.
 * @since 1.0.0
 */
@Composable
public fun SheetDescription(text: String, modifier: Modifier = Modifier, style: SheetStyle = sheetStyle()) {
    Text(text = text, modifier = modifier, style = style.descriptionStyle, color = style.descriptionColor)
}
