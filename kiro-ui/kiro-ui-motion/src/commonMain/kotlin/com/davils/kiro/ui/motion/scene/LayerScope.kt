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

import androidx.compose.ui.graphics.BlendMode
import com.davils.kiro.ui.motion.art.ArtSeed
import com.davils.kiro.ui.motion.mask.Mask
import com.davils.kiro.ui.motion.time.TimeRemap

/**
 * Sets up a [Layer] in [SceneScope.layer]. Every value starts at the default of [Layer].
 *
 * @since 1.0.0
 */
public interface LayerScope {
    /**
     * The opacity of the layer, between `0` and `1`.
     *
     * @since 1.0.0
     */
    public var opacity: Float

    /**
     * How the layer mixes with the layers below it.
     *
     * @since 1.0.0
     */
    public var blend: BlendMode

    /**
     * How the layer is moved, scaled and turned.
     *
     * @since 1.0.0
     */
    public var transform: LayerTransform

    /**
     * How the layer fades out.
     *
     * @since 1.0.0
     */
    public var mask: Mask

    /**
     * How the layer sees the time of the scene.
     *
     * @since 1.0.0
     */
    public var time: TimeRemap

    /**
     * The seed of the layer, or `null` to derive it from the scene.
     *
     * @since 1.0.0
     */
    public var seed: ArtSeed?

    /**
     * Whether the layer draws.
     *
     * @since 1.0.0
     */
    public var isVisible: Boolean

    /**
     * Whether the layer sees the pointer, the progress and the signals.
     *
     * @since 1.0.0
     */
    public var isInteractive: Boolean
}
