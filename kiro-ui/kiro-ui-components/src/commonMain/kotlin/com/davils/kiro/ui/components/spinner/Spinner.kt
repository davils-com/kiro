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

package com.davils.kiro.ui.components.spinner

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.lucide.LoaderCircle
import com.davils.kiro.ui.icons.lucide.Lucide
import kotlin.time.Duration.Companion.seconds

private const val FULL_TURN: Float = 360f

/**
 * Shows that something is loading with the icon of its style turning endlessly, or standing still when the style is
 * not animated.
 *
 * The spinner is a polite live region, so that accessibility services announce it when it appears.
 *
 * @param modifier the modifier of the spinner.
 * @param contentDescription the text that accessibility services read for the spinner.
 * @param style the style of the spinner.
 * @since 1.0.0
 */
@Composable
public fun Spinner(
    modifier: Modifier = Modifier,
    contentDescription: String = Messages.Spinner.LOADING,
    style: SpinnerStyle = spinnerStyle(),
) {
    val rotation by rotationOf(style)
    Icon(
        icon = style.icon,
        contentDescription = contentDescription,
        modifier = modifier
            .semantics { liveRegion = LiveRegionMode.Polite }
            .graphicsLayer { rotationZ = rotation },
        style = IconStyle(size = style.size),
    )
}

@Composable
private fun rotationOf(style: SpinnerStyle): State<Float> {
    if (!style.isAnimated) return remember { mutableFloatStateOf(0f) }
    val transition = rememberInfiniteTransition(label = "spinner")
    val animation = tween<Float>(durationMillis = style.turnDuration.inWholeMilliseconds.toInt(), easing = LinearEasing)
    return transition.animateFloat(
        initialValue = 0f,
        targetValue = FULL_TURN,
        animationSpec = infiniteRepeatable(animation = animation, repeatMode = RepeatMode.Restart),
        label = "rotation",
    )
}

internal fun defaultSpinnerStyle(theme: Theme): SpinnerStyle = SpinnerStyle(
    icon = Lucide.LoaderCircle,
    size = 16.dp,
    turnDuration = 1.seconds,
    isAnimated = !theme.motion.isReduced,
)
