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

package com.davils.kiro.ui.motion.art.geometry

/**
 * The regular solid of a [WireframeArt].
 *
 * @since 1.0.0
 */
public enum class WireframeSolid {
    /**
     * The cube, with 8 corners and 12 edges.
     *
     * @since 1.0.0
     */
    Cube,

    /**
     * The octahedron, with 6 corners and 12 edges.
     *
     * @since 1.0.0
     */
    Octahedron,

    /**
     * The icosahedron, with 12 corners and 30 edges.
     *
     * @since 1.0.0
     */
    Icosahedron,

    /**
     * The dodecahedron, with 20 corners and 30 edges.
     *
     * @since 1.0.0
     */
    Dodecahedron,
}
