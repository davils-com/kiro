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

package com.davils.kiro.ui.components.table

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.scrollarea.ScrollArea
import com.davils.kiro.ui.components.scrollarea.ScrollDirection
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle

@Composable
public fun Table(modifier: Modifier = Modifier, style: TableStyle = tableStyle(), content: TableScopeImpl.() -> Unit) {
    val scope = TableScope().apply(content)
    val rows = scope.rows
    val headHeight = with(LocalDensity.current) { style.headHeight.roundToPx() }
    val minimums = rows.map { row -> headHeight.takeIf { row.kind == TableRowKind.Head } ?: 0 }
    Column(modifier = modifier.fillMaxWidth()) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val viewport = maxWidth.takeIf { constraints.hasBoundedWidth } ?: 0.dp
            CompositionLocalProvider(LocalTextStyle provides style.textStyle) {
                ScrollArea(modifier = Modifier.fillMaxWidth(), direction = ScrollDirection.Horizontal) {
                    Layout(
                        content = {
                            rows.forEachIndexed { index, row ->
                                TableRowContent(
                                    index = index,
                                    row = row,
                                    style = style,
                                )
                            }
                        },
                        modifier = Modifier.widthIn(min = viewport),
                        measurePolicy = TableMeasurePolicy(minRowHeights = minimums),
                    )
                }
            }
        }
        scope.caption?.let { caption ->
            Text(
                text = caption,
                modifier = Modifier.fillMaxWidth().padding(top = style.captionSpacing),
                style = style.captionStyle.copy(textAlign = TextAlign.Center),
                color = style.captionColor,
            )
        }
    }
}

@Composable
private fun TableRowContent(index: Int, row: TableRowSpec, style: TableStyle) {
    TableRowBackground(index = index, row = row, style = style)
    val starts = row.cells.runningFold(0) { column, cell -> column + cell.span }
    row.cells.forEachIndexed { position, cell ->
        Box(
            modifier = Modifier
                .layoutId(TableSlot.Cell(row = index, column = starts[position], span = cell.span))
                .padding(
                    horizontal = horizontalPaddingOf(row = row, style = style),
                    vertical = verticalPaddingOf(row, style),
                ),
            contentAlignment = alignmentOf(cell.alignment),
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides textStyleOf(row = row, style = style),
                LocalContentColor provides contentColorOf(
                    row = row,
                    style = style,
                    current = LocalContentColor.current,
                ),
                content = cell.content,
            )
        }
    }
}

private fun horizontalPaddingOf(row: TableRowSpec, style: TableStyle): Dp {
    if (row.kind == TableRowKind.Head) return style.headPadding
    return style.cellPadding
}

private fun verticalPaddingOf(row: TableRowSpec, style: TableStyle): Dp {
    if (row.kind == TableRowKind.Head) return 0.dp
    return style.cellPadding
}

private fun textStyleOf(row: TableRowSpec, style: TableStyle): TextStyle = when (row.kind) {
    TableRowKind.Head -> style.headTextStyle
    TableRowKind.Body -> style.textStyle
    TableRowKind.Foot -> style.footerTextStyle
}

private fun contentColorOf(row: TableRowSpec, style: TableStyle, current: Color): Color =
    style.headColor.takeIf { row.kind == TableRowKind.Head } ?: current

private val alignments: Map<Alignment.Horizontal, Alignment> = mapOf(
    Alignment.End to Alignment.CenterEnd,
    Alignment.CenterHorizontally to Alignment.Center,
)

private fun alignmentOf(horizontal: Alignment.Horizontal): Alignment = alignments[horizontal] ?: Alignment.CenterStart
