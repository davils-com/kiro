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

/**
 * A piece of generative art that a [com.davils.kiro.ui.motion.scene.Layer] draws, such as a Truchet field, orbits or
 * confetti.
 *
 * An art is an immutable description. [prepare] turns it into a renderer once for a context, which may build caches and
 * draw its seeds; the renderer then draws every frame. Two equal arts draw the same picture.
 *
 * @since 1.0.0
 */
public interface Art {
    /**
     * Whether the art looks the same at every point in time, which lets the host stop its clock while every layer of a
     * scene is static.
     *
     * @since 1.0.0
     */
    public val isStatic: Boolean
        get() = false

    /**
     * Prepares the art for drawing.
     *
     * @param context the seed, the palette, the theme and the quality the art draws with.
     * @return the renderer that draws the art.
     * @since 1.0.0
     */
    public fun prepare(context: ArtContext): ArtRenderer
}
