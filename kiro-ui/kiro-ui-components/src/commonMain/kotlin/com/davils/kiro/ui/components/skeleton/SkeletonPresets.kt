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

package com.davils.kiro.ui.components.skeleton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

private const val WIDE: Float = 16f / 9f
private const val TITLE_WIDTH: Float = 0.7f
private const val NAME_WIDTH: Float = 0.5f
private const val DETAIL_WIDTH: Float = 0.8f
private const val ICON_ALPHA: Float = 0.4f
private const val TEXT_LINES: Int = 3
private const val TABLE_ROWS: Int = 5
private const val TABLE_COLUMNS: Int = 4

/**
 * A skeleton for a paragraph: lines of text in the line height and gap of the style, the last one shorter when there
 * is more than one.
 *
 * @param modifier the modifier of the paragraph.
 * @param lines the number of lines.
 * @param lastLineFraction the width of the last line, as a fraction of the width of the paragraph.
 * @param style the style of the skeleton.
 * @since 1.0.0
 */
@Composable
public fun SkeletonText(
    modifier: Modifier = Modifier,
    lines: Int = TEXT_LINES,
    lastLineFraction: Float = 0.6f,
    style: SkeletonStyle = skeletonStyle(),
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(style.lineGap)) {
        repeat(lines) { line ->
            val width = lastLineFraction.takeIf { line == lines - 1 && lines > 1 } ?: 1f
            Skeleton(
                modifier = Modifier.fillMaxWidth(width).height(style.lineHeight),
                shape = SkeletonShape.Pill,
                style = style,
            )
        }
    }
}

/**
 * A circular skeleton for an avatar.
 *
 * @param modifier the modifier of the avatar.
 * @param size the width and height of the avatar, or `null` for the avatar size of the style.
 * @param style the style of the skeleton.
 * @since 1.0.0
 */
@Composable
public fun SkeletonAvatar(modifier: Modifier = Modifier, size: Dp? = null, style: SkeletonStyle = skeletonStyle()) {
    Skeleton(modifier = modifier.size(size ?: style.avatarSize), shape = SkeletonShape.Circle, style = style)
}

/**
 * A rounded skeleton in the button size of the style.
 *
 * @param modifier the modifier of the button.
 * @param style the style of the skeleton.
 * @since 1.0.0
 */
@Composable
public fun SkeletonButton(modifier: Modifier = Modifier, style: SkeletonStyle = skeletonStyle()) {
    Skeleton(modifier = modifier.size(style.buttonSize), shape = SkeletonShape.Rounded, style = style)
}

/**
 * A skeleton for an image: a rounded block with an aspect ratio and the image icon of the style in its middle.
 *
 * @param modifier the modifier of the image, which gives it its width.
 * @param aspectRatio the ratio of the width to the height of the image, 16 to 9 by default.
 * @param style the style of the skeleton.
 * @since 1.0.0
 */
@Composable
public fun SkeletonImage(
    modifier: Modifier = Modifier,
    aspectRatio: Float = WIDE,
    style: SkeletonStyle = skeletonStyle(),
) {
    Box(modifier = modifier.aspectRatio(aspectRatio), contentAlignment = Alignment.Center) {
        Skeleton(modifier = Modifier.matchParentSize(), style = style)
        CompositionLocalProvider(
            LocalIconStyle provides IconStyle(size = style.imageIconSize, color = style.color),
        ) {
            Icon(icon = style.imageIcon, contentDescription = null, modifier = Modifier.alpha(ICON_ALPHA))
        }
    }
}

/**
 * A skeleton for an item of a list: an avatar next to a line for a name and a longer line for its details.
 *
 * @param modifier the modifier of the item.
 * @param style the style of the skeleton.
 * @since 1.0.0
 */
@Composable
public fun SkeletonListItem(modifier: Modifier = Modifier, style: SkeletonStyle = skeletonStyle()) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(style.gap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SkeletonAvatar(style = style)
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(style.lineGap)) {
            Line(fraction = NAME_WIDTH, style = style)
            Line(fraction = DETAIL_WIDTH, style = style)
        }
    }
}

/**
 * A skeleton for a card: an image, a title, a paragraph and a button, one below the other.
 *
 * @param modifier the modifier of the card.
 * @param style the style of the skeleton.
 * @since 1.0.0
 */
@Composable
public fun SkeletonCard(modifier: Modifier = Modifier, style: SkeletonStyle = skeletonStyle()) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(style.gap)) {
        SkeletonImage(modifier = Modifier.fillMaxWidth(), style = style)
        Line(fraction = TITLE_WIDTH, style = style)
        SkeletonText(style = style)
        SkeletonButton(style = style)
    }
}

/**
 * A skeleton for a table: a header row and the given number of rows, each with a line in every column.
 *
 * @param modifier the modifier of the table.
 * @param rows the number of rows below the header row.
 * @param columns the number of columns.
 * @param style the style of the skeleton.
 * @since 1.0.0
 */
@Composable
public fun SkeletonTable(
    modifier: Modifier = Modifier,
    rows: Int = TABLE_ROWS,
    columns: Int = TABLE_COLUMNS,
    style: SkeletonStyle = skeletonStyle(),
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(style.gap)) {
        repeat(rows + 1) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(style.gap)) {
                repeat(columns) { Box(modifier = Modifier.weight(1f)) { Line(fraction = 1f, style = style) } }
            }
        }
    }
}

@Composable
private fun Line(fraction: Float, style: SkeletonStyle) {
    Skeleton(
        modifier = Modifier.fillMaxWidth(fraction).height(style.lineHeight),
        shape = SkeletonShape.Pill,
        style = style,
    )
}
