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

package com.davils.kiro.ui.components.message

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Immutable
public class MessageStyle(
    public val gap: Dp,
    public val contentGap: Dp,
    public val groupGap: Dp,
    public val textStyle: TextStyle,
    public val avatarMinSize: Dp,
    public val avatarShape: Shape,
    public val avatarContainer: Color,
    public val avatarLift: Dp,
    public val labelStyle: TextStyle,
    public val labelColor: Color,
    public val labelPadding: Dp,
) {
    /**
     * Returns a copy of this [MessageStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        gap: Dp = this.gap,
        contentGap: Dp = this.contentGap,
        groupGap: Dp = this.groupGap,
        textStyle: TextStyle = this.textStyle,
        avatarMinSize: Dp = this.avatarMinSize,
        avatarShape: Shape = this.avatarShape,
        avatarContainer: Color = this.avatarContainer,
        avatarLift: Dp = this.avatarLift,
        labelStyle: TextStyle = this.labelStyle,
        labelColor: Color = this.labelColor,
        labelPadding: Dp = this.labelPadding,
    ): MessageStyle = MessageStyle(
        gap = gap,
        contentGap = contentGap,
        groupGap = groupGap,
        textStyle = textStyle,
        avatarMinSize = avatarMinSize,
        avatarShape = avatarShape,
        avatarContainer = avatarContainer,
        avatarLift = avatarLift,
        labelStyle = labelStyle,
        labelColor = labelColor,
        labelPadding = labelPadding,
    )

    override fun equals(other: Any?): Boolean = other is MessageStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MessageStyle(" +
        "gap=$gap, " +
        "contentGap=$contentGap, " +
        "groupGap=$groupGap, " +
        "textStyle=$textStyle, " +
        "avatarMinSize=$avatarMinSize, " +
        "avatarShape=$avatarShape, " +
        "avatarContainer=$avatarContainer, " +
        "avatarLift=$avatarLift, " +
        "labelStyle=$labelStyle, " +
        "labelColor=$labelColor, " +
        "labelPadding=$labelPadding)"

    private fun values(): List<Any?> = listOf(
        gap,
        contentGap,
        groupGap,
        textStyle,
        avatarMinSize,
        avatarShape,
        avatarContainer,
        avatarLift,
        labelStyle,
        labelColor,
        labelPadding,
    )
}
