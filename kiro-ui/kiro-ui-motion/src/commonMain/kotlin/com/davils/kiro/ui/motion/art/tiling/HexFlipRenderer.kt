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

package com.davils.kiro.ui.motion.art.tiling

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.ColorGradient
import com.davils.kiro.ui.motion.color.colorsOr

internal class HexFlipRenderer(private val style: HexFlipArtStyle) : ArtRenderer {
    private val card = Path()
    private val inner = Path()
    private val gradient = ColorGradient()
    private var layout = HexLayout.Empty
    private var outline = Stroke()
    private var builtSize = Size.Unspecified
    private var builtDensity = 0f

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        prepare()
        if (style.outlineAlpha <= 0f) return
        val ink = style.line ?: frame.palette.ink
        for (index in 0 until layout.count) {
            translate(left = layout.xs[index], top = layout.ys[index]) {
                drawPath(path = card, color = ink, alpha = style.outlineAlpha, style = outline)
            }
        }
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        prepare()
        val seed = frame.seed.derive(style.salt).value
        val phase = frame.time.phase(style.period)
        val shades = gradient.of(colors = frame.palette.colorsOr(style.colors), steps = style.shades)
        val paint = CardPaint(
            ink = style.line ?: frame.palette.ink,
            shade = style.shade ?: frame.palette.shade,
            isDetailed = style.detail > 0f && frame.quality.density >= style.detailQuality.density,
        )
        for (index in 0 until layout.count) {
            val local = phase - layout.distances[index] * style.spread
            val wave = wholeTurns(local)
            val column = layout.columns[index]
            val row = layout.rows[index]
            val isFromBack = hexFace(column = column, row = row, wave = wave - 1, share = style.share, seed = seed)
            val isToBack = hexFace(column = column, row = row, wave = wave, share = style.share, seed = seed)
            val progress = flipProgress(
                from = isFromBack,
                to = isToBack,
                local = local,
                turn = style.turn,
                easing = style.easing,
            )
            val tint = shades[shadeIndex(distance = layout.distances[index], shades = shades.size)]
            val isBack = flipFace(from = isFromBack, to = isToBack, progress = progress)
            drawCard(index = index, progress = progress, isBack = isBack, tint = tint, paint = paint)
        }
    }

    private fun DrawScope.prepare() {
        if (size == builtSize && density == builtDensity) return
        builtSize = size
        builtDensity = density
        val radius = style.radius.toPx()
        layout = HexLayout.of(
            width = size.width,
            height = size.height,
            radius = radius,
            originX = size.width * style.centerX,
            originY = size.height * style.centerY,
        )
        val cardRadius = cardRadius(radius = radius, gap = style.gap.toPx())
        card.hexagon(radius = cardRadius)
        inner.hexagon(radius = cardRadius * style.detail)
        outline = Stroke(width = style.outlineWidth.toPx())
    }

    private fun DrawScope.drawCard(index: Int, progress: Float, isBack: Boolean, tint: Color, paint: CardPaint) {
        val degrees = layout.angles[index]
        withTransform(
            transformBlock = {
                translate(left = layout.xs[index], top = layout.ys[index])
                rotate(degrees = degrees, pivot = Offset.Zero)
                scale(scaleX = flipSquash(progress), scaleY = 1f, pivot = Offset.Zero)
                rotate(degrees = -degrees, pivot = Offset.Zero)
            },
        ) {
            drawFace(isBack = isBack, tint = tint, paint = paint)
            drawShade(paint = paint, lean = flipLean(progress))
        }
    }

    private fun DrawScope.drawFace(isBack: Boolean, tint: Color, paint: CardPaint) {
        if (!isBack) {
            drawPath(path = card, color = paint.ink, alpha = style.frontAlpha)
            return
        }
        drawPath(path = card, color = tint, alpha = style.backAlpha)
        drawPath(path = card, color = tint, alpha = style.edgeAlpha, style = outline)
        if (paint.isDetailed) drawPath(path = inner, color = tint, alpha = style.detailAlpha, style = outline)
    }

    private fun DrawScope.drawShade(paint: CardPaint, lean: Float) {
        if (lean <= 0f || style.shading <= 0f) return
        drawPath(path = card, color = paint.shade, alpha = style.shading * lean)
    }

    private class CardPaint(val ink: Color, val shade: Color, val isDetailed: Boolean)
}
