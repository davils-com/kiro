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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.color.ArtPalettes
import com.davils.kiro.ui.motion.color.artPaletteOf
import io.kotest.matchers.ints.shouldBeGreaterThan
import kotlin.time.Duration

private const val FEW: Int = 8
private const val SHEET_WIDTH: Int = 480
private const val SHEET_HEIGHT: Int = 300

internal fun Art.saveSheet(name: String, times: List<Duration>) {
    val light = Theme.light()
    val dark = Theme.dark()
    val rows = listOf(
        Triple("light", light, artPaletteOf(light)),
        Triple("dark", dark, artPaletteOf(dark)),
        Triple("mono", light, ArtPalettes.Monochrome.resolve(light)),
    )
    val sheet = ImageBitmap(width = SHEET_WIDTH * times.size, height = SHEET_HEIGHT * rows.size)
    val canvas = Canvas(sheet)
    rows.forEachIndexed { row, (label, theme, palette) ->
        times.forEachIndexed { column, time ->
            val image = render(
                Render(time = time, theme = theme, width = SHEET_WIDTH, height = SHEET_HEIGHT, palette = palette),
            )
            image.save(name = "$name-$label-${time.inWholeSeconds}")
            image.colorCount() shouldBeGreaterThan FEW
            val offset = Offset(x = column * SHEET_WIDTH.toFloat(), y = row * SHEET_HEIGHT.toFloat())
            canvas.drawImage(image = image, topLeftOffset = offset, paint = Paint())
        }
    }
    sheet.save(name = "sheet-$name")
}
