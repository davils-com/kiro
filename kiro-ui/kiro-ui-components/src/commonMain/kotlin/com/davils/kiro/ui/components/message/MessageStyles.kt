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

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the [MessageStyle] from the tokens of a theme.
 *
 * Register an implementation for [MessageStyleKey] in the components of a theme to change the style of every message.
 *
 * @since 1.0.0
 */
public fun interface MessageStyles {
    /**
     * Returns the style of a message under a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of a message.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): MessageStyle

    /**
     * Holds the default message styles.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default message styles, derived from the colors and the typography of a theme.
         *
         * @since 1.0.0
         */
        public val Default: MessageStyles = MessageStyles(::defaultMessageStyle)
    }
}

/**
 * The key of the message styles in the components of a theme; its default is [MessageStyles.Default].
 *
 * @since 1.0.0
 */
public val MessageStyleKey: StyleKey<MessageStyles> = StyleKey(name = "message") { _ -> MessageStyles.Default }

/**
 * Returns the message style of the current theme, with the overrides of the theme for [MessageStyleKey].
 *
 * @return the style of a message.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun messageStyle(): MessageStyle = MessageStyleKey.current.resolve(theme)

internal fun defaultMessageStyle(theme: Theme): MessageStyle = MessageStyle(
    gap = 8.dp,
    contentGap = 10.dp,
    groupGap = 8.dp,
    textStyle = theme.textStyleOf(size = theme.typography.scale.sm),
    avatarMinSize = 32.dp,
    avatarShape = CircleShape,
    avatarContainer = theme.colors.muted,
    avatarLift = 32.dp,
    labelStyle = theme.textStyleOf(size = theme.typography.scale.xs, weight = FontWeight.Medium),
    labelColor = theme.colors.mutedForeground,
    labelPadding = 12.dp,
)
