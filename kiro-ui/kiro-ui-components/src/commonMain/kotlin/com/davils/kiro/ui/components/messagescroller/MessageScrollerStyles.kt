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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.primitive.motion.tweenOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.ArrowDown
import com.davils.kiro.ui.icons.lucide.Lucide

private const val HIDDEN_SCALE: Float = 0.95f
private const val GAP_STEPS: Int = 6
private const val FADE_STEPS: Int = 8
private const val INSET_STEPS: Int = 4

/**
 * Resolves the style of a [MessageScroller] for a theme.
 *
 * Replace it in the components of a theme under [MessageScrollerStyleKey] to change the style of every message
 * scroller.
 *
 * @since 1.0.0
 */
public fun interface MessageScrollerStyles {
    /**
     * Returns the style of a [MessageScroller] in a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the message scroller.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): MessageScrollerStyle

    /**
     * Holds the default [MessageScrollerStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [MessageScroller], derived from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: MessageScrollerStyles = MessageScrollerStyles(::defaultMessageScrollerStyle)
    }
}

/**
 * The key of the [MessageScrollerStyles] of a [MessageScroller] in the components of a theme; its default is
 * [MessageScrollerStyles.Default].
 *
 * @since 1.0.0
 */
public val MessageScrollerStyleKey: StyleKey<MessageScrollerStyles> = StyleKey(
    name = "message-scroller",
) { _ -> MessageScrollerStyles.Default }

/**
 * Returns the style of a [MessageScroller] in the theme of the composition, resolved by the [MessageScrollerStyles]
 * under [MessageScrollerStyleKey].
 *
 * @return the style of the message scroller.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun messageScrollerStyle(): MessageScrollerStyle = MessageScrollerStyleKey.current.resolve(theme)

internal fun defaultMessageScrollerStyle(theme: Theme): MessageScrollerStyle = MessageScrollerStyle(
    gap = theme.spacing.unit * GAP_STEPS,
    fadeLength = theme.spacing.unit * FADE_STEPS,
    buttonInset = theme.spacing.unit * INSET_STEPS,
    buttonVariant = ButtonVariant.Outline,
    buttonSize = ButtonSize.IconSm,
    icon = Lucide.ArrowDown,
    label = Messages.MessageScroller.SCROLL_TO_END,
    hiddenScale = HIDDEN_SCALE,
    showAnimation = theme.tweenOf(duration = theme.motion.durations.medium, easing = theme.motion.easings.emphasized),
    hideAnimation = theme.tweenOf(duration = theme.motion.durations.medium, easing = theme.motion.easings.emphasized),
)
