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
 * The pattern of the lattice of a [WeaveArt].
 *
 * @since 1.0.0
 */
public enum class WeavePattern {
    /**
     * The hemp leaf: a grid of triangles whose corners are joined to their middles, so that six-pointed stars appear.
     *
     * @since 1.0.0
     */
    Asanoha,

    /**
     * The basket weave: hexagons and triangles, where three families of lines cross.
     *
     * @since 1.0.0
     */
    Kagome,

    /**
     * The seven treasures: overlapping circles on a square grid, which form petals and four-pointed stars.
     *
     * @since 1.0.0
     */
    Shippo,
}
