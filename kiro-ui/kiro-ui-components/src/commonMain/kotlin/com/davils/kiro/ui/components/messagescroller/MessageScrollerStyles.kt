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

public fun interface MessageScrollerStyles {
    public fun resolve(theme: Theme): MessageScrollerStyle

    public companion object {
        public val Default: MessageScrollerStyles = MessageScrollerStyles(::defaultMessageScrollerStyle)
    }
}

public val MessageScrollerStyleKey: StyleKey<MessageScrollerStyles> = StyleKey(
    name = "message-scroller",
) { _ -> MessageScrollerStyles.Default }

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
    label = Messages.MessageScroller.scrollToEnd,
    hiddenScale = HIDDEN_SCALE,
    showAnimation = theme.tweenOf(duration = theme.motion.durations.medium, easing = theme.motion.easings.emphasized),
    hideAnimation = theme.tweenOf(duration = theme.motion.durations.medium, easing = theme.motion.easings.emphasized),
)
