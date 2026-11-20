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

package com.davils.kiro.ui.components.toast

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.elevation.BoxShadow
import com.davils.kiro.ui.core.elevation.ShadowLayer
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.core.typography.TextSize
import com.davils.kiro.ui.icons.lucide.CircleCheck
import com.davils.kiro.ui.icons.lucide.Info
import com.davils.kiro.ui.icons.lucide.LoaderCircle
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.OctagonX
import com.davils.kiro.ui.icons.lucide.TriangleExclamationPoint
import com.davils.kiro.ui.icons.lucide.X
import kotlin.time.Duration.Companion.milliseconds

private const val SHADOW_ALPHA: Float = 0.1f
private const val TITLE_LEADING: Float = 1.5f
private const val DESCRIPTION_LEADING: Float = 1.4f
private const val SCALE_STEP: Float = 0.05f
private const val VISIBLE: Int = 3
private const val TICK_MILLIS: Int = 100
private const val TIMER_ALPHA: Float = 0.2f
private const val SWIPE_THRESHOLD: Float = 0.45f

public fun interface ToastStyles {
    public fun resolve(theme: Theme): ToastStyle

    public companion object {
        public val Default: ToastStyles = ToastStyles(::defaultToastStyle)
    }
}

public val ToastStyleKey: StyleKey<ToastStyles> = StyleKey(name = "toast") { _ -> ToastStyles.Default }

@Composable
@ReadOnlyComposable
public fun toastStyle(): ToastStyle = ToastStyleKey.current.resolve(theme)

internal fun defaultToastStyle(theme: Theme): ToastStyle {
    val colors = theme.colors
    val body = TextSize(fontSize = 13.sp, lineHeight = TITLE_LEADING.em)
    return ToastStyle(
        width = 356.dp,
        padding = 16.dp,
        gap = 6.dp,
        container = colors.popover,
        content = colors.popoverForeground,
        border = Border(width = 1.dp, color = colors.border),
        shape = RoundedCornerShape(theme.radii.lg),
        shadow = BoxShadow(
            listOf(ShadowLayer(offsetY = 4.dp, blur = 12.dp, color = Color.Black.copy(alpha = SHADOW_ALPHA))),
        ),
        titleStyle = theme.textStyleOf(size = body, weight = FontWeight.Medium),
        descriptionStyle = theme.textStyleOf(size = body, lineHeight = DESCRIPTION_LEADING.em),
        iconSize = 16.dp,
        icons = mapOf(
            ToastType.Success to Lucide.CircleCheck,
            ToastType.Info to Lucide.Info,
            ToastType.Warning to Lucide.TriangleExclamationPoint,
            ToastType.Error to Lucide.OctagonX,
            ToastType.Loading to Lucide.LoaderCircle,
        ),
        action = ToastActionStyle(
            height = 24.dp,
            horizontalPadding = 8.dp,
            shape = RoundedCornerShape(4.dp),
            textStyle = theme.textStyleOf(
                size = TextSize(fontSize = 12.sp, lineHeight = 16.sp),
                weight = FontWeight.Medium,
            ),
            container = colors.popoverForeground,
            content = colors.popover,
        ),
        edgeOffset = 24.dp,
        expandedGap = 14.dp,
        stackGap = 14.dp,
        scaleStep = SCALE_STEP,
        visibleCount = VISIBLE,
        tick = TICK_MILLIS.milliseconds,
        statuses = mapOf(
            ToastType.Success to colors.status.success,
            ToastType.Info to colors.status.info,
            ToastType.Warning to colors.status.warning,
            ToastType.Error to colors.status.error,
        ),
        closeIcon = Lucide.X,
        closeLabel = Messages.Toast.close,
        closeSize = 20.dp,
        progressHeight = 4.dp,
        progressTrack = colors.muted,
        progressColor = colors.primary,
        timerHeight = 2.dp,
        timerColor = colors.foreground.copy(alpha = TIMER_ALPHA),
        swipeThreshold = SWIPE_THRESHOLD,
    )
}
