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

package com.davils.kiro.ui.core.elevation

import androidx.compose.runtime.Immutable

/**
 * The strengths of the layers of the shadows of a theme, applied as the alpha of the shadow color.
 *
 * @property faint the alpha of the faint layers of the smallest shadows.
 * @property soft the alpha of the layers of the medium shadows.
 * @property deep the alpha of the layer of the deepest shadow.
 * @since 0.1.0
 */
@Immutable
public class ShadowStrength(
    public val faint: Float = 0.05f,
    public val soft: Float = 0.1f,
    public val deep: Float = 0.25f,
) {
    /**
     * Returns a copy of this [ShadowStrength] with the given values replaced and every other value kept.
     *
     * @param faint the new value of [faint].
     * @param soft the new value of [soft].
     * @param deep the new value of [deep].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(faint: Float = this.faint, soft: Float = this.soft, deep: Float = this.deep): ShadowStrength =
        ShadowStrength(
            faint = faint,
            soft = soft,
            deep = deep,
        )

    override fun equals(other: Any?): Boolean = other is ShadowStrength && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ShadowStrength(" +
        "faint=$faint, " +
        "soft=$soft, " +
        "deep=$deep)"

    private fun values(): List<Any?> = listOf(
        faint,
        soft,
        deep,
    )
}
