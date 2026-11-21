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

package com.davils.kiro.ui.motion.scene

import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.color.ArtPalette
import com.davils.kiro.ui.motion.mask.Mask

/**
 * Builds a [Scene] in [scene].
 *
 * @since 1.0.0
 */
public interface SceneScope {
    /**
     * The color behind every layer, or `null` for the background of the palette.
     *
     * @since 1.0.0
     */
    public var background: Color?

    /**
     * The colors of the scene, or `null` for the palette of the theme.
     *
     * @since 1.0.0
     */
    public var palette: ArtPalette?

    /**
     * How the whole scene fades out.
     *
     * @since 1.0.0
     */
    public var mask: Mask

    /**
     * Adds a layer on top of the layers added before.
     *
     * @param art the art of the layer.
     * @param configure sets the opacity, the blend mode, the transform, the mask, the time and the seed of the layer.
     * @since 1.0.0
     */
    public fun layer(art: Art, configure: LayerScope.() -> Unit = {})
}
