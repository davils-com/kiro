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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.window.titlebar.TitleBarStyle
import com.davils.kiro.ui.window.titlebar.titleBarStyle

/**
 * A button with an icon for the slots of a title bar, with an optional badge at its top end corner.
 *
 * @param icon the icon of the button.
 * @param contentDescription the spoken label of the button.
 * @param onClick called when the button is clicked.
 * @param modifier the modifier of the button.
 * @param badge the badge of the button, or `null` for none.
 * @param enabled whether the button can be clicked.
 * @param style the style of the title bar, which decides the variant and size of the button and the badge.
 * @param mark the badge, a dot or a count in the badge colors of [style] by default.
 * @since 1.0.0
 */
@Composable
public fun TitleBarButton(
    icon: IconGraphic,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badge: TitleBarBadge? = null,
    enabled: Boolean = true,
    style: TitleBarStyle = titleBarStyle(),
    mark: @Composable (TitleBarBadge, Modifier) -> Unit = { shown, markModifier ->
        BadgeMark(badge = shown, style = style, modifier = markModifier)
    },
) {
    Box(modifier = modifier) {
        Button(onClick = onClick, variant = style.buttonVariant, size = style.buttonSize, enabled = enabled) {
            Icon(icon = icon, contentDescription = contentDescription)
        }
        badge?.let { shown ->
            mark(shown, Modifier.align(Alignment.TopEnd).offset(x = style.badgeOffset.x, y = style.badgeOffset.y))
        }
    }
}
