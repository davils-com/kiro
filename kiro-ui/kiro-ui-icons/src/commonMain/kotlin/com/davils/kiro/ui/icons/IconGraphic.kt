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

package com.davils.kiro.ui.icons

import androidx.compose.runtime.Immutable

private const val LUCIDE_VIEWPORT: Float = 24f

/**
 * The drawing of an icon: paths on a square canvas, stroked with round caps and joins like the icons of Lucide.
 *
 * The icons of [com.davils.kiro.ui.icons.lucide.Lucide] are generated from the SVGs of Lucide. An application may
 * create its own graphics; their path data is parsed when an [Icon] first draws them.
 *
 * @param name the name of the icon, such as `arrow-up-right`.
 * @param paths the paths of the icon; the list is copied.
 * @param viewportSize the width and height of the canvas the paths are drawn on.
 * @since 0.1.0
 */
@Immutable
public class IconGraphic(
    public val name: String,
    paths: List<IconPath>,
    public val viewportSize: Float = LUCIDE_VIEWPORT,
) {
    /**
     * The paths of the icon, drawn in this order. The list cannot be changed.
     *
     * @since 0.1.0
     */
    public val paths: List<IconPath> = buildList { addAll(paths) }

    /**
     * Returns a copy of this graphic with the given values replaced and every other value kept.
     *
     * @param name the new value of [name].
     * @param paths the new value of [paths].
     * @param viewportSize the new value of [viewportSize].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(
        name: String = this.name,
        paths: List<IconPath> = this.paths,
        viewportSize: Float = this.viewportSize,
    ): IconGraphic = IconGraphic(name = name, paths = paths, viewportSize = viewportSize)

    override fun equals(other: Any?): Boolean = other is IconGraphic && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "IconGraphic(name=$name, paths=${paths.size}, viewportSize=$viewportSize)"

    private fun values(): List<Any?> = listOf(name, paths, viewportSize)
}
