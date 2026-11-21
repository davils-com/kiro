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

internal class SceneScopeImpl : SceneScope {
    private val layers = mutableListOf<Layer>()

    override var background: Color? = null
    override var palette: ArtPalette? = null
    override var mask: Mask = Mask.None

    override fun layer(art: Art, configure: LayerScope.() -> Unit) {
        val scope = LayerScopeImpl(art)
        scope.configure()
        layers += scope.build()
    }

    fun build(): Scene = Scene(layers = layers.toList(), background = background, palette = palette, mask = mask)
}
