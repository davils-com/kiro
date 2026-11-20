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

package com.davils.kiro.ui.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.content.LocalContentColor

/**
 * Draws an icon.
 *
 * The values of [style] that are unspecified come from the icon tokens of the theme and the content color. An icon
 * with a [contentDescription] is an image for assistive technology; without one it is decoration and stays out of
 * the semantics.
 *
 * @param icon the icon to draw, such as `Lucide.ArrowUpRight`.
 * @param contentDescription what the icon means, or null if it only decorates text that says so.
 * @param modifier the modifier of the icon.
 * @param style how the icon is drawn.
 * @since 1.0.0
 */
@Composable
public fun Icon(
    icon: IconGraphic,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    style: IconStyle = LocalIconStyle.current,
) {
    val resolved = style.resolve(tokens = Theme.current.icons, contentColor = LocalContentColor.current)
    val geometry = remember(icon) { icon.toGeometry() }
    Canvas(modifier = modifier.size(resolved.size).described(contentDescription)) {
        drawIcon(geometry = geometry, style = resolved)
    }
}

private fun Modifier.described(contentDescription: String?): Modifier {
    if (contentDescription == null) return this
    return semantics {
        this.contentDescription = contentDescription
        role = Role.Image
    }
}
