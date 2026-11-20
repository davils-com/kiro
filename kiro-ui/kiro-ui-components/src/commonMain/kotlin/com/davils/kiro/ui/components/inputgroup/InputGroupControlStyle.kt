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

package com.davils.kiro.ui.components.inputgroup

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.input.InputTextStyle

/**
 * The look of the input or textarea of an [InputGroup].
 *
 * @property text the text style, placeholder color, cursor color and selection colors of the control.
 * @property horizontalPadding the padding at the start and the end of the control on a side without an addon.
 * @property verticalPadding the padding above and below a single-line input.
 * @property inlinePadding the padding of a single-line input on the side of an addon at the start or the end.
 * @property blockPadding the padding of a single-line input on its side away from an addon above or below it.
 * @property textareaVerticalPadding the padding above and below a textarea.
 * @property textareaMinHeight the minimum height of a textarea.
 * @since 1.0.0
 */
@Immutable
public class InputGroupControlStyle(
    public val text: InputTextStyle,
    public val horizontalPadding: Dp,
    public val verticalPadding: Dp,
    public val inlinePadding: Dp,
    public val blockPadding: Dp,
    public val textareaVerticalPadding: Dp,
    public val textareaMinHeight: Dp,
) {
    /**
     * Returns a copy of this [InputGroupControlStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        text: InputTextStyle = this.text,
        horizontalPadding: Dp = this.horizontalPadding,
        verticalPadding: Dp = this.verticalPadding,
        inlinePadding: Dp = this.inlinePadding,
        blockPadding: Dp = this.blockPadding,
        textareaVerticalPadding: Dp = this.textareaVerticalPadding,
        textareaMinHeight: Dp = this.textareaMinHeight,
    ): InputGroupControlStyle = InputGroupControlStyle(
        text = text,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding,
        inlinePadding = inlinePadding,
        blockPadding = blockPadding,
        textareaVerticalPadding = textareaVerticalPadding,
        textareaMinHeight = textareaMinHeight,
    )

    override fun equals(other: Any?): Boolean = other is InputGroupControlStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "InputGroupControlStyle(" +
        "text=$text, " +
        "horizontalPadding=$horizontalPadding, " +
        "verticalPadding=$verticalPadding, " +
        "inlinePadding=$inlinePadding, " +
        "blockPadding=$blockPadding, " +
        "textareaVerticalPadding=$textareaVerticalPadding, " +
        "textareaMinHeight=$textareaMinHeight)"

    private fun values(): List<Any?> = listOf(
        text,
        horizontalPadding,
        verticalPadding,
        inlinePadding,
        blockPadding,
        textareaVerticalPadding,
        textareaMinHeight,
    )
}
