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

package com.davils.kiro.ui.audio.controls

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.resizing
import com.davils.kiro.ui.audio.springing
import com.davils.kiro.ui.components.primitive.surface.surface

private val playLeft = listOf(
    Offset(x = 0.36f, y = 0.26f),
    Offset(x = 0.57f, y = 0.38f),
    Offset(x = 0.57f, y = 0.62f),
    Offset(x = 0.36f, y = 0.74f),
)

private val playRight = listOf(
    Offset(x = 0.57f, y = 0.38f),
    Offset(x = 0.78f, y = 0.5f),
    Offset(x = 0.78f, y = 0.5f),
    Offset(x = 0.57f, y = 0.62f),
)

private val pauseLeft = listOf(
    Offset(x = 0.32f, y = 0.27f),
    Offset(x = 0.45f, y = 0.27f),
    Offset(x = 0.45f, y = 0.73f),
    Offset(x = 0.32f, y = 0.73f),
)

private val pauseRight = listOf(
    Offset(x = 0.55f, y = 0.27f),
    Offset(x = 0.68f, y = 0.27f),
    Offset(x = 0.68f, y = 0.73f),
    Offset(x = 0.55f, y = 0.73f),
)

/**
 * A round button whose glyph morphs between play and pause and that shrinks a little while it is pressed.
 *
 * @param isPlaying whether the track plays, which shows the pause glyph.
 * @param onToggle called when the button is clicked.
 * @param modifier the modifier of the button.
 * @param style the style of the audio controls.
 * @param container the color of the button.
 * @param content the color of the glyph.
 * @param size the size of the button.
 * @since 1.0.0
 */
@Composable
public fun PlayPauseButton(
    isPlaying: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    style: AudioControlsStyle = audioControlsStyle(),
    container: Color = style.playContainer,
    content: Color = style.playContent,
    size: Dp = style.playSize,
) {
    val label = style.labels.pause.takeIf { isPlaying } ?: style.labels.play
    val source = remember { MutableInteractionSource() }
    val isPressed by source.collectIsPressedAsState()
    val morph = animateFloatAsState(
        targetValue = 1f.takeIf { isPlaying } ?: 0f,
        animationSpec = style.motion.resizing(),
    )
    val press = animateFloatAsState(
        targetValue = style.pressedScale.takeIf { isPressed } ?: 1f,
        animationSpec = style.motion.springing(),
    )
    val pressing = Modifier.graphicsLayer {
        scaleX = press.value
        scaleY = press.value
    }
    Box(
        modifier = modifier.then(
            Modifier
                .size(size)
                .then(pressing)
                .surface(shape = CircleShape, color = container)
                .pointerHoverIcon(PointerIcon.Hand)
                .clickable(interactionSource = source, indication = null, role = Role.Button, onClick = onToggle)
                .semantics { contentDescription = label }
                .drawBehind { drawGlyph(morph = morph, color = content) },
        ),
    )
}

private fun DrawScope.drawGlyph(morph: State<Float>, color: Color) {
    val progress = morph.value
    drawPath(path = quadOf(from = playLeft, to = pauseLeft, progress = progress), color = color)
    drawPath(path = quadOf(from = playRight, to = pauseRight, progress = progress), color = color)
}

private fun DrawScope.quadOf(from: List<Offset>, to: List<Offset>, progress: Float): Path {
    val points = from.zip(to) { start, end -> mix(start, end, progress) }
    val corners = points.map { point -> Offset(x = point.x * size.width, y = point.y * size.height) }
    val path = Path()
    val start = corners.first()
    path.moveTo(x = start.x, y = start.y)
    corners.drop(1).forEach { corner -> path.lineTo(x = corner.x, y = corner.y) }
    path.close()
    return path
}

private fun mix(start: Offset, end: Offset, fraction: Float): Offset =
    Offset(x = start.x + (end.x - start.x) * fraction, y = start.y + (end.y - start.y) * fraction)
