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

package com.davils.kiro.ui.components.trace

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.collapsible.defaultCollapsibleStyle
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.ChevronRight
import com.davils.kiro.ui.icons.lucide.Lucide

private const val PENDING_ALPHA: Float = 0.6f
private const val DESCRIPTION_ALPHA: Float = 0.7f
private const val RING_ALPHA: Float = 0.5f
private const val SHIMMER_MILLIS: Int = 2000

public fun interface TraceStyles {
    public fun resolve(theme: Theme): TraceStyle

    public companion object {
        public val Default: TraceStyles = TraceStyles(::defaultTraceStyle)
    }
}

public val TraceStyleKey: StyleKey<TraceStyles> = StyleKey(name = "trace") { _ -> TraceStyles.Default }

@Composable
@ReadOnlyComposable
public fun traceStyle(): TraceStyle = TraceStyleKey.current.resolve(theme)

internal fun defaultTraceStyle(theme: Theme): TraceStyle {
    val colors = theme.colors
    val sm = theme.typography.scale.sm
    return TraceStyle(
        gap = 8.dp,
        lineGap = 8.dp,
        textStyle = theme.textStyleOf(size = sm),
        color = colors.mutedForeground,
        hoverColor = colors.foreground,
        errorColor = colors.destructive,
        pendingAlpha = PENDING_ALPHA,
        descriptionColor = colors.mutedForeground.copy(alpha = colors.mutedForeground.alpha * DESCRIPTION_ALPHA),
        iconSize = 16.dp,
        shape = RoundedCornerShape(theme.radii.md),
        ring = Border(width = theme.focusRing.width, color = colors.ring.copy(alpha = RING_ALPHA)),
        chevron = Lucide.ChevronRight,
        contentStyle = theme.textStyleOf(size = sm, lineHeight = theme.typography.leading.relaxed),
        contentBorder = colors.border,
        contentIndent = 7.dp,
        contentPadding = 16.dp,
        shimmerColor = colors.foreground,
        shimmerMillis = SHIMMER_MILLIS,
        motion = defaultCollapsibleStyle(theme),
    )
}
