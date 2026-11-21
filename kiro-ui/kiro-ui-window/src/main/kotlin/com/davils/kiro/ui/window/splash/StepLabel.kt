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

package com.davils.kiro.ui.window.splash

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.motion.Motion
import com.davils.kiro.ui.window.fading
import com.davils.kiro.ui.window.sliding
import com.davils.kiro.ui.window.theme

@Composable
internal fun StepLabel(
    text: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.CenterStart,
) {
    val motion = theme.motion
    AnimatedContent(
        targetState = text,
        modifier = modifier,
        transitionSpec = { motion.changing() },
        contentAlignment = alignment,
        label = "step",
    ) { label ->
        Text(
            text = label,
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            style = style,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

internal fun Splash.stepText(labels: SplashLabels): String = current?.label ?: labels.starting

private fun Motion.changing(): ContentTransform {
    val offset = sliding<IntOffset>()
    val fade = fading<Float>()
    val entering = slideInVertically(offset) { height -> height } + fadeIn(fade)
    val leaving = slideOutVertically(offset) { height -> -height } + fadeOut(fade)
    return entering togetherWith leaving
}
