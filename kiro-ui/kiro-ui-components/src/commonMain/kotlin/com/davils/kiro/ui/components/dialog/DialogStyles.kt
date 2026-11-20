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

package com.davils.kiro.ui.components.dialog

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.primitive.overlay.PanelStyle
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.elevation.BoxShadow
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.X

private const val RING_ALPHA: Float = 0.1f
private const val FOOTER_ALPHA: Float = 0.5f

public fun interface DialogStyles {
    public fun resolve(theme: Theme): DialogStyle

    public companion object {
        public val Default: DialogStyles = DialogStyles(::defaultDialogStyle)
    }
}

@Composable
@ReadOnlyComposable
public fun dialogStyle(): DialogStyle = DialogStyleKey.current.resolve(theme)

internal fun defaultDialogStyle(theme: Theme): DialogStyle {
    val colors = theme.colors
    val scale = theme.typography.scale
    return DialogStyle(
        panel = PanelStyle(
            container = colors.popover,
            content = colors.popoverForeground,
            shape = RoundedCornerShape(theme.radii.xl),
            ring = Border(width = 1.dp, color = colors.foreground.copy(alpha = RING_ALPHA)),
            shadow = BoxShadow.None,
            padding = 16.dp,
        ),
        radius = theme.radii.xl,
        maxWidth = 384.dp,
        margin = 16.dp,
        gap = 16.dp,
        textStyle = theme.textStyleOf(size = scale.sm),
        headerGap = 8.dp,
        titleStyle = theme.textStyleOf(
            size = scale.base,
            weight = FontWeight.Medium,
            lineHeight = theme.typography.leading.none,
            family = theme.typography.families.heading,
        ),
        descriptionStyle = theme.textStyleOf(size = scale.sm),
        descriptionColor = colors.mutedForeground,
        footerContainer = colors.muted.copy(alpha = FOOTER_ALPHA),
        footerBorder = Border(width = 1.dp, color = colors.border),
        footerGap = 8.dp,
        closeIcon = Lucide.X,
        closeOffset = 8.dp,
        closeLabel = Messages.Dialog.close,
        scrim = Color.Black.copy(alpha = RING_ALPHA),
    )
}

public val DialogStyleKey: StyleKey<DialogStyles> = StyleKey(name = "dialog") { _ -> DialogStyles.Default }
