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

import com.davils.kiro.ui.motion.art.Art

internal class LayerScopeImpl(private val art: Art, base: Layer = Layer(art = art)) : LayerScope {
    override var opacity = base.opacity
    override var blend = base.blend
    override var transform = base.transform
    override var mask = base.mask
    override var time = base.time
    override var seed = base.seed
    override var isVisible = base.isVisible
    override var isInteractive = base.isInteractive

    fun build(): Layer = Layer(
        art = art,
        opacity = opacity,
        blend = blend,
        transform = transform,
        mask = mask,
        time = time,
        seed = seed,
        isVisible = isVisible,
        isInteractive = isInteractive,
    )
}
