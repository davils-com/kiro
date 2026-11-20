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

public fun interface MessageStyles {
    public fun resolve(theme: Theme): MessageStyle

    public companion object {
        public val Default: MessageStyles = MessageStyles(::defaultMessageStyle)
    }
}

public val MessageStyleKey: StyleKey<MessageStyles> = StyleKey(name = "message") { _ -> MessageStyles.Default }

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
