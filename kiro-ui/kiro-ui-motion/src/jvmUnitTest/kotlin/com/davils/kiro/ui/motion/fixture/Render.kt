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

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.text.TextMeasurer
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.color.ArtPalette
import com.davils.kiro.ui.motion.color.artPaletteOf
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.quality.QualityLevel
import java.io.File
import javax.imageio.ImageIO
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO

internal const val RENDER_DIRECTORY: String = "KIRO_RENDER_DIR"

internal class Render(
    val time: Duration = ZERO,
    val theme: Theme = Theme.light(),
    val seed: Int = 7,
    val width: Int = 320,
    val height: Int = 200,
    val density: Float = 1f,
    val quality: QualityLevel = QualityLevel.High,
    val inputs: ArtInputs = ArtInputs.None,
    val palette: ArtPalette = artPaletteOf(theme),
    val textMeasurer: TextMeasurer? = null,
)

internal fun Art.render(render: Render = Render()): ImageBitmap = rendererOf(render = render).drawInto(render = render)

internal fun ImageBitmap.pixels(): IntArray {
    val buffer = IntArray(width * height)
    readPixels(buffer)
    return buffer
}

internal fun ImageBitmap.colorCount(): Int = pixels().toSet().size

internal fun ImageBitmap.fingerprint(): Int = pixels().contentHashCode()

internal fun ImageBitmap.save(name: String) {
    val directory = System.getenv(RENDER_DIRECTORY) ?: return
    val target = File(directory, "$name.png")
    target.parentFile.mkdirs()
    ImageIO.write(toAwtImage(), "png", target)
}
