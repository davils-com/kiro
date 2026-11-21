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

/**
 * The bold shape a tile of a [BauhausArt] carries.
 *
 * @since 1.0.0
 */
public enum class BauhausShape {
    /**
     * A quarter of a disc whose center sits in a corner and whose radius is the side of the tile.
     *
     * @since 1.0.0
     */
    QuarterCircle,

    /**
     * Half a disc whose diameter is one side of the tile.
     *
     * @since 1.0.0
     */
    HalfCircle,

    /**
     * A disc in the middle of the tile.
     *
     * @since 1.0.0
     */
    Circle,

    /**
     * A ring in the middle of the tile.
     *
     * @since 1.0.0
     */
    Ring,

    /**
     * A triangle that fills half the tile, cut along its diagonal.
     *
     * @since 1.0.0
     */
    Triangle,

    /**
     * Parallel bars across the tile.
     *
     * @since 1.0.0
     */
    Stripes,

    /**
     * Two quarter discs in opposite corners, each with half the side of the tile as its radius.
     *
     * @since 1.0.0
     */
    DoubleQuarter,

    /**
     * A small dot off the middle of the tile, as a quiet accent.
     *
     * @since 1.0.0
     */
    Dot,
}
