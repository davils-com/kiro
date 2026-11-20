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

package com.davils.kiro.ui.components.codeblock

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.focus.collectIsFocusVisibleAsState

private const val ADDED: String = "+"
private const val REMOVED_POINT: Int = 0x2212
private val MARKER_INSET: Dp = 4.dp

/**
 * Shows the code of a [CodeBlock] in a monospaced font, one line of [code] per row.
 *
 * Long lines scroll horizontally unless [isWrapped] is set. The content can be focused, which shows the focus ring of
 * the code block, and its text can be selected; line numbers and the markers of added and removed lines cannot.
 *
 * @param code the code, with lines separated by `\n`; a trailing line break is ignored.
 * @param modifier the modifier of the content.
 * @param isWrapped whether long lines wrap instead of scrolling horizontally.
 * @param isLineNumbered whether a gutter shows the number of each line.
 * @param startLine the number of the first line.
 * @param lineVariants the variants of lines by their number, counted from [startLine]; other lines are
 * [CodeLineVariant.Default].
 * @param highlighter colors each line of the code.
 * @since 1.0.0
 */
@Composable
public fun CodeBlockScope.CodeBlockContent(
    code: String,
    modifier: Modifier = Modifier,
    isWrapped: Boolean = false,
    isLineNumbered: Boolean = false,
    startLine: Int = 1,
    lineVariants: Map<Int, CodeLineVariant> = emptyMap(),
    highlighter: CodeHighlighter = CodeHighlighter.Plain,
) {
    val slots = this as CodeBlockSlots
    val style = slots.style
    val source = remember { MutableInteractionSource() }
    val isFocusVisible by source.collectIsFocusVisibleAsState()
    LaunchedEffect(isFocusVisible) { slots.isFocusVisible = isFocusVisible }
    val lines = code.removeSuffix("\n").split("\n")
    val top = style.sectionGap.takeIf { slots.hasHeader } ?: style.padding
    val bottom = style.sectionGap.takeIf { slots.hasFooter } ?: style.padding
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val viewport = maxWidth.takeIf { constraints.hasBoundedWidth } ?: 0.dp
        val scroll = rememberScrollState()
        val sizing = Modifier
            .horizontalScroll(scroll)
            .widthIn(min = viewport)
            .width(IntrinsicSize.Max)
            .takeUnless { isWrapped }
            ?: Modifier.fillMaxWidth()
        SelectionContainer {
            Column(
                modifier = Modifier
                    .then(sizing)
                    .focusable(interactionSource = source)
                    .padding(top = top, bottom = bottom),
            ) {
                lines.forEachIndexed { index, line ->
                    val number = startLine + index
                    CodeLine(
                        number = number.takeIf { isLineNumbered },
                        text = highlighter.highlight(line),
                        variant = lineVariants[number] ?: CodeLineVariant.Default,
                        isWrapped = isWrapped,
                        style = style,
                    )
                }
            }
        }
    }
}

@Composable
private fun CodeLine(
    number: Int?,
    text: AnnotatedString,
    variant: CodeLineVariant,
    isWrapped: Boolean,
    style: CodeBlockStyle,
) {
    val code = style.codeStyle.copy(color = style.content)
    Box(modifier = Modifier.fillMaxWidth().background(backgroundOf(variant = variant, style = style))) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = style.padding),
            horizontalArrangement = Arrangement.spacedBy(style.gutterGap),
        ) {
            number?.let { line ->
                DisableSelection {
                    BasicText(
                        text = line.toString(),
                        modifier = Modifier.widthIn(min = style.gutterMinWidth),
                        style = code.copy(color = style.muted, textAlign = TextAlign.End),
                    )
                }
            }
            BasicText(text = text, style = code, softWrap = isWrapped)
        }
        markerOf(variant)?.let { marker ->
            DisableSelection {
                BasicText(
                    text = marker,
                    modifier = Modifier.padding(start = MARKER_INSET),
                    style = code.copy(color = markerColorOf(variant = variant, style = style)),
                )
            }
        }
    }
}

private fun backgroundOf(variant: CodeLineVariant, style: CodeBlockStyle): Color = when (variant) {
    CodeLineVariant.Default -> Color.Transparent
    CodeLineVariant.Added -> style.added
    CodeLineVariant.Removed -> style.removed
    CodeLineVariant.Highlighted -> style.highlighted
}

private fun markerOf(variant: CodeLineVariant): String? = when (variant) {
    CodeLineVariant.Added -> ADDED
    CodeLineVariant.Removed -> Char(REMOVED_POINT).toString()
    CodeLineVariant.Default -> null
    CodeLineVariant.Highlighted -> null
}

private fun markerColorOf(variant: CodeLineVariant, style: CodeBlockStyle): Color {
    if (variant == CodeLineVariant.Removed) return style.removedMarker
    return style.muted
}
