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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal val LocalCodeBlock = staticCompositionLocalOf<CodeBlockSlots?> { null }

/**
 * The row above the code of a [CodeBlock], such as for a [CodeBlockTitle] and [CodeBlockActions], in the muted color
 * and the title style of the code block.
 *
 * @param modifier the modifier of the header.
 * @param content the content of the header.
 * @since 1.0.0
 */
@Composable
public fun CodeBlockScope.CodeBlockHeader(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    val slots = this as CodeBlockSlots
    DisposableEffect(slots) {
        slots.hasHeader = true
        onDispose { slots.hasHeader = false }
    }
    Edge(
        slots = slots,
        modifier = modifier.padding(start = slots.style.padding, top = slots.style.padding, end = slots.style.padding),
        content = content,
    )
}

/**
 * The row below the code of a [CodeBlock], in the muted color and the title style of the code block.
 *
 * @param modifier the modifier of the footer.
 * @param content the content of the footer.
 * @since 1.0.0
 */
@Composable
public fun CodeBlockScope.CodeBlockFooter(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    val slots = this as CodeBlockSlots
    DisposableEffect(slots) {
        slots.hasFooter = true
        onDispose { slots.hasFooter = false }
    }
    Edge(
        slots = slots,
        modifier = modifier.padding(
            start = slots.style.padding,
            end = slots.style.padding,
            bottom = slots.style.padding,
        ),
        content = content,
    )
}

@Composable
private fun Edge(slots: CodeBlockSlots, modifier: Modifier, content: @Composable RowScope.() -> Unit) {
    CompositionLocalProvider(
        LocalContentColor provides slots.style.muted,
        LocalTextStyle provides slots.style.titleStyle,
        LocalIconStyle provides IconStyle(size = slots.style.iconSize),
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(slots.style.gap),
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

/**
 * Shows the title of a code block, such as a file name, on one line with an optional icon before it.
 *
 * In a [CodeBlock] the title takes the content color of the code block, elsewhere the current content color.
 *
 * @param text the title.
 * @param modifier the modifier of the title.
 * @param icon the icon before the title, or `null` for none.
 * @since 1.0.0
 */
@Composable
public fun CodeBlockTitle(text: String, modifier: Modifier = Modifier, icon: IconGraphic? = null) {
    val slots = LocalCodeBlock.current
    val color = slots?.run { style.content } ?: LocalContentColor.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TitleGap),
    ) {
        icon?.let { glyph -> Icon(icon = glyph, contentDescription = null) }
        Text(text = text, color = color, maxLines = 1)
    }
}

/**
 * Holds actions at the end of a [CodeBlockHeader] or a [CodeBlockFooter] and takes the width the row leaves.
 *
 * @param modifier the modifier of the actions.
 * @param content the actions.
 * @since 1.0.0
 */
@Composable
public fun RowScope.CodeBlockActions(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = modifier.weight(1f),
        horizontalArrangement = Arrangement.spacedBy(Gap, Alignment.End),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

/**
 * An icon button that copies a text to the clipboard and shows the copied icon and label for a while afterwards.
 *
 * @param value the text to copy.
 * @param modifier the modifier of the button.
 * @param style the style that gives the icons, the labels and how long the copied state lasts; by default the style
 * of the enclosing [CodeBlock], or the default code block style outside one.
 * @since 1.0.0
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
public fun CodeBlockCopyButton(
    value: String,
    modifier: Modifier = Modifier,
    style: CodeBlockStyle = defaultCopyStyle(),
) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    var isCopied by remember { mutableStateOf(false) }
    LaunchedEffect(isCopied) {
        if (isCopied) {
            delay(style.copiedDuration)
            isCopied = false
        }
    }
    val shown = style.copiedLabel.takeIf { isCopied } ?: style.copyLabel
    val label = shown
    Button(
        onClick = {
            scope.launch {
                clipboard.setClipEntry(clipEntryOf(value))
                isCopied = true
            }
        },
        modifier = modifier.semantics { contentDescription = label },
        variant = ButtonVariant.Ghost,
        size = ButtonSize.IconXs,
    ) {
        Icon(icon = style.copiedIcon.takeIf { isCopied } ?: style.copyIcon, placement = IconPlacement.Inline)
    }
}

@Composable
private fun defaultCopyStyle(): CodeBlockStyle =
    LocalCodeBlock.current?.style ?: codeBlockStyle(CodeBlockVariant.Default, CodeBlockSize.Default)

private val Gap: Dp = 4.dp
private val TitleGap: Dp = 6.dp
