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

package com.davils.kiro.ui.motion.export

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.toSize
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.color.artPaletteOf
import com.davils.kiro.ui.motion.host.motionStyleOf
import com.davils.kiro.ui.motion.render.PreparedScene
import com.davils.kiro.ui.motion.render.SceneSetup
import com.davils.kiro.ui.motion.render.StaticDrawer
import com.davils.kiro.ui.motion.render.drawScene
import com.davils.kiro.ui.motion.render.prepareScene
import com.davils.kiro.ui.motion.scene.Scene
import com.davils.kiro.ui.motion.time.ArtTime
import kotlin.time.Duration

/**
 * Draws one frame of a [Scene] into an image, exactly as a [com.davils.kiro.ui.motion.host.MotionCanvas] of that size
 * draws it at that time, such as for a poster, a thumbnail or a test.
 *
 * ```kotlin
 * val poster = renderFrame(scene = sky, size = IntSize(1200, 630), time = 4.seconds, theme = Theme.dark())
 * ```
 *
 * @param scene the scene to draw.
 * @param size the size of the image, in pixels.
 * @param time the time of the scene.
 * @param theme the theme, whose components hold the styles of the arts and whose palette the scene draws with unless
 * it has its own.
 * @param options the density, the seed, the quality, the inputs and the text measurer to draw with.
 * @return the image.
 * @throws IllegalArgumentException if [size] is empty.
 * @since 1.0.0
 */
public fun renderFrame(
    scene: Scene,
    size: IntSize,
    time: Duration,
    theme: Theme,
    options: ExportOptions = ExportOptions(),
): ImageBitmap {
    requireSize(size)
    val prepared = prepareScene(scene = scene, setup = setupOf(scene = scene, theme = theme, options = options))
    return prepared.render(size = size, time = time, options = options)
}

/**
 * Draws the frames of a [Scene] between two points in time at an even frame rate, such as for a video or an animated
 * image. The layers are prepared once and every frame is drawn when the sequence reaches it.
 *
 * @param scene the scene to draw.
 * @param size the size of every image, in pixels.
 * @param from the time of the first frame.
 * @param to the time the frames end at; the last frame is the one before it.
 * @param framesPerSecond the number of frames in a second.
 * @param theme the theme, whose components hold the styles of the arts and whose palette the scene draws with unless
 * it has its own.
 * @param options the density, the seed, the quality, the inputs and the text measurer to draw with.
 * @return the images, one for every frame.
 * @throws IllegalArgumentException if [size] is empty, [framesPerSecond] is not positive or [to] is before [from].
 * @since 1.0.0
 */
public fun renderFrames(
    scene: Scene,
    size: IntSize,
    from: Duration,
    to: Duration,
    framesPerSecond: Int,
    theme: Theme,
    options: ExportOptions = ExportOptions(),
): Sequence<ImageBitmap> {
    requireSize(size)
    val times = frameTimes(from = from, to = to, framesPerSecond = framesPerSecond)
    val prepared = prepareScene(scene = scene, setup = setupOf(scene = scene, theme = theme, options = options))
    return times.map { time -> prepared.render(size = size, time = time, options = options) }
}

private fun setupOf(scene: Scene, theme: Theme, options: ExportOptions): SceneSetup = SceneSetup(
    theme = theme,
    palette = scene.palette ?: artPaletteOf(theme),
    quality = options.quality,
    seed = options.seed ?: motionStyleOf(theme).seed,
    textMeasurer = options.textMeasurer,
)

private fun PreparedScene.render(size: IntSize, time: Duration, options: ExportOptions): ImageBitmap {
    val bitmap = ImageBitmap(width = size.width, height = size.height)
    val scope = CanvasDrawScope()
    scope.draw(
        density = options.density,
        layoutDirection = LayoutDirection.Ltr,
        canvas = Canvas(bitmap),
        size = size.toSize(),
    ) {
        drawScene(scene = this@render, time = ArtTime.of(time), inputs = options.inputs, statics = StaticDrawer.Direct)
    }
    return bitmap
}

private fun requireSize(size: IntSize) {
    require(size.width > 0 && size.height > 0) { "A frame needs a size, but got $size." }
}
