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

package com.davils.kiro.ui.window.action

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.icons.lucide.Bell
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.window.titlebar.TitleBarStyle
import com.davils.kiro.ui.window.titlebar.titleBarStyle

/**
 * A bell for the end slot of a title bar that shows how many notifications are unread.
 *
 * @param count the number of unread notifications; the bell shows no badge while it is `0`.
 * @param onClick called when the bell is clicked, such as to open the notifications.
 * @param modifier the modifier of the bell.
 * @param style the style of the title bar.
 * @param contentDescription the spoken label of the bell.
 * @param icon the icon of the bell.
 * @since 1.0.0
 */
@Composable
public fun NotificationBell(
    count: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TitleBarStyle = titleBarStyle(),
    contentDescription: String = style.labels.notifications,
    icon: IconGraphic = Lucide.Bell,
) {
    TitleBarButton(
        icon = icon,
        contentDescription = contentDescription,
        onClick = onClick,
        modifier = modifier,
        badge = badgeOf(count),
        style = style,
    )
}

private fun badgeOf(count: Int): TitleBarBadge? {
    if (count <= 0) return null
    return TitleBarBadge.Count(count)
}
