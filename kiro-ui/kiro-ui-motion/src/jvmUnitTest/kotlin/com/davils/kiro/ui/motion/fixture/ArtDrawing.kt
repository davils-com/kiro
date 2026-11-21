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

package com.davils.kiro.ui.motion.fixture

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.art.ArtContext
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.art.ArtSeed
import com.davils.kiro.ui.motion.time.ArtTime

internal fun Art.print(render: Render = Render()): Int = render(render = render).fingerprint()

internal fun Art.colors(render: Render = Render()): Int = render(render = render).colorCount()

internal fun Art.rendererOf(render: Render = Render()): ArtRenderer = prepare(context = contextOf(render = render))

internal fun Art.drawSequence(vararg renders: Render): List<ImageBitmap> {
    val renderer = rendererOf(render = renders.first())
    return renders.map { render -> renderer.drawInto(render = render) }
}

internal fun Art.drawEmpty() {
    rendererOf().drawEmpty()
}

internal fun ArtRenderer.drawInto(render: Render): ImageBitmap {
    val bitmap = ImageBitmap(width = render.width, height = render.height)
    val size = Size(width = render.width.toFloat(), height = render.height.toFloat())
    val frame = frameOf(render = render)
    CanvasDrawScope().draw(
        density = Density(density = render.density),
        layoutDirection = LayoutDirection.Ltr,
        canvas = Canvas(image = bitmap),
        size = size,
    ) {
        drawRect(color = render.palette.background)
        if (hasStatic) drawStatic(frame = frame)
        drawFrame(frame = frame)
    }
    return bitmap
}

internal fun ArtRenderer.drawEmpty() {
    val frame = frameOf(render = Render())
    CanvasDrawScope().draw(
        density = Density(density = 1f),
        layoutDirection = LayoutDirection.Ltr,
        canvas = Canvas(image = ImageBitmap(width = 1, height = 1)),
        size = Size.Zero,
    ) {
        drawStatic(frame = frame)
        drawFrame(frame = frame)
    }
}

internal fun contextOf(render: Render): ArtContext = ArtContext(
    seed = ArtSeed(value = render.seed),
    palette = render.palette,
    theme = render.theme,
    quality = render.quality,
    textMeasurer = render.textMeasurer,
)

internal fun frameOf(render: Render): ArtFrame = ArtFrame(
    time = ArtTime.of(render.time),
    inputs = render.inputs,
    palette = render.palette,
    quality = render.quality,
    seed = ArtSeed(value = render.seed),
)

internal fun textMeasurer(): TextMeasurer = TextMeasurer(
    defaultFontFamilyResolver = createFontFamilyResolver(),
    defaultDensity = Density(density = 1f),
    defaultLayoutDirection = LayoutDirection.Ltr,
)
