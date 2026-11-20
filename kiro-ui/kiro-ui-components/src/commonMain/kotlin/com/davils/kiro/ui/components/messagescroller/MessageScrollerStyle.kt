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

package com.davils.kiro.ui.components.messagescroller

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.icons.IconGraphic

/**
 * The look of a [MessageScroller]: the space between its messages, the fade at its bottom edge and the button that
 * scrolls to the end.
 *
 * @property gap the space between two messages.
 * @property fadeLength the height of the fade at the bottom edge while the end is not reached.
 * @property buttonInset the distance between the button and the bottom edge.
 * @property buttonVariant the variant of the button.
 * @property buttonSize the size of the button.
 * @property icon the icon of the button.
 * @property label the content description of the button, announced by screen readers.
 * @property hiddenScale the scale of the button when it is hidden, which it grows from as it appears.
 * @property showAnimation the animation of the button as it appears.
 * @property hideAnimation the animation of the button as it disappears.
 * @since 1.0.0
 */
@Immutable
public class MessageScrollerStyle(
    public val gap: Dp,
    public val fadeLength: Dp,
    public val buttonInset: Dp,
    public val buttonVariant: ButtonVariant,
    public val buttonSize: ButtonSize,
    public val icon: IconGraphic,
    public val label: String,
    public val hiddenScale: Float,
    public val showAnimation: AnimationSpec<Float>,
    public val hideAnimation: AnimationSpec<Float>,
) {
    /**
     * Returns a copy of this [MessageScrollerStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        gap: Dp = this.gap,
        fadeLength: Dp = this.fadeLength,
        buttonInset: Dp = this.buttonInset,
        buttonVariant: ButtonVariant = this.buttonVariant,
        buttonSize: ButtonSize = this.buttonSize,
        icon: IconGraphic = this.icon,
        label: String = this.label,
        hiddenScale: Float = this.hiddenScale,
        showAnimation: AnimationSpec<Float> = this.showAnimation,
        hideAnimation: AnimationSpec<Float> = this.hideAnimation,
    ): MessageScrollerStyle = MessageScrollerStyle(
        gap = gap,
        fadeLength = fadeLength,
        buttonInset = buttonInset,
        buttonVariant = buttonVariant,
        buttonSize = buttonSize,
        icon = icon,
        label = label,
        hiddenScale = hiddenScale,
        showAnimation = showAnimation,
        hideAnimation = hideAnimation,
    )

    override fun equals(other: Any?): Boolean = other is MessageScrollerStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MessageScrollerStyle(" +
        "gap=$gap, " +
        "fadeLength=$fadeLength, " +
        "buttonInset=$buttonInset, " +
        "buttonVariant=$buttonVariant, " +
        "buttonSize=$buttonSize, " +
        "icon=$icon, " +
        "label=$label, " +
        "hiddenScale=$hiddenScale, " +
        "showAnimation=$showAnimation, " +
        "hideAnimation=$hideAnimation)"

    private fun values(): List<Any?> = listOf(
        gap,
        fadeLength,
        buttonInset,
        buttonVariant,
        buttonSize,
        icon,
        label,
        hiddenScale,
        showAnimation,
        hideAnimation,
    )
}
