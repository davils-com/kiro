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

package com.davils.kiro.ui.components.brand

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.DrawResult
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme

private const val NAME: String = "kide"
private const val VIEWPORT: Float = 320f
private const val HALF: Float = 0.5f
private val gradientStart = Offset(x = 160.99467f, y = 17.720467f)
private val gradientEnd = Offset(x = 160.99467f, y = 295.54984f)

private val mark: Path by lazy {
    val parser = PathParser().parsePathString(brandMarkPath)
    parser.toPath()
}

/**
 * Draws the brand mark of Kide as an image named `kide`, filled with a vertical gradient from the start to the end
 * color of its style and centered in a square of its size.
 *
 * @param modifier the modifier of the mark.
 * @param style the style of the mark.
 * @since 1.0.0
 */
@Composable
public fun BrandMark(modifier: Modifier = Modifier, style: BrandMarkStyle = brandMarkStyle()) {
    Spacer(
        modifier = modifier.then(Modifier.size(style.size).described().drawWithCache { drawMark(style) }),
    )
}

private fun Modifier.described(): Modifier = semantics {
    contentDescription = NAME
    role = Role.Image
}

private fun CacheDrawScope.drawMark(style: BrandMarkStyle): DrawResult {
    val factor = minOf(a = size.width, b = size.height) / VIEWPORT
    val left = (size.width - VIEWPORT * factor) * HALF
    val top = (size.height - VIEWPORT * factor) * HALF
    val brush = Brush.linearGradient(
        colors = listOf(style.startColor, style.endColor),
        start = gradientStart,
        end = gradientEnd,
    )
    return onDrawBehind {
        translate(left = left, top = top) {
            scale(scale = factor, pivot = Offset.Zero) { drawPath(path = mark, brush = brush) }
        }
    }
}

internal fun defaultBrandMarkStyle(theme: Theme): BrandMarkStyle = BrandMarkStyle(
    size = 28.dp,
    startColor = theme.colors.brand,
    endColor = theme.colors.foreground,
)
