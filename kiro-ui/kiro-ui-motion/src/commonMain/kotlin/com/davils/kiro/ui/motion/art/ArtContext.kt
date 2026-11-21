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

package com.davils.kiro.ui.motion.art

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextMeasurer
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.motion.color.ArtPalette
import com.davils.kiro.ui.motion.quality.QualityLevel

/**
 * What an [Art] is prepared with.
 *
 * @property seed the seed of the layer the art draws in.
 * @property palette the colors of the scene.
 * @property theme the theme, whose components hold the styles of the arts.
 * @property quality the level of detail when the art is prepared.
 * @property textMeasurer measures texts for arts that draw them, or `null` where none is available.
 * @since 1.0.0
 */
@Immutable
public class ArtContext(
    public val seed: ArtSeed,
    public val palette: ArtPalette,
    public val theme: Theme,
    public val quality: QualityLevel,
    public val textMeasurer: TextMeasurer?,
) {
    /**
     * Returns a copy of this [ArtContext] with the given values replaced and every other value kept.
     *
     * @param seed the new value of [seed].
     * @param palette the new value of [palette].
     * @param theme the new value of [theme].
     * @param quality the new value of [quality].
     * @param textMeasurer the new value of [textMeasurer].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        seed: ArtSeed = this.seed,
        palette: ArtPalette = this.palette,
        theme: Theme = this.theme,
        quality: QualityLevel = this.quality,
        textMeasurer: TextMeasurer? = this.textMeasurer,
    ): ArtContext = ArtContext(
        seed = seed,
        palette = palette,
        theme = theme,
        quality = quality,
        textMeasurer = textMeasurer,
    )

    override fun equals(other: Any?): Boolean = other is ArtContext && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ArtContext(" +
        "seed=$seed, " +
        "palette=$palette, " +
        "theme=$theme, " +
        "quality=$quality, " +
        "textMeasurer=${textMeasurer ?: "null"})"

    private fun values(): List<Any?> = listOf(
        seed,
        palette,
        theme,
        quality,
        textMeasurer,
    )

    /**
     * Returns the styles registered under a key in the components of [theme], with the overrides of the theme.
     *
     * @param T the type of the styles.
     * @param key the key of the styles.
     * @return the styles.
     * @since 1.0.0
     */
    public fun <T : Any> styles(key: StyleKey<T>): T = theme.components.resolve(key, theme)
}
