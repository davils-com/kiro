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

package com.davils.kiro.ui.core.shape

import androidx.compose.runtime.Immutable

/**
 * The factors by which the radii of a theme grow from its base radius.
 *
 * @property sm the factor of the small radius.
 * @property md the factor of the medium radius.
 * @property xl the factor of the extra large radius.
 * @property xl2 the factor of the radius twice extra large.
 * @property xl3 the factor of the radius three times extra large.
 * @property xl4 the factor of the radius four times extra large.
 * @since 0.1.0
 */
@Immutable
public class RadiusFactors(
    public val sm: Float = 0.6f,
    public val md: Float = 0.8f,
    public val xl: Float = 1.4f,
    public val xl2: Float = 1.8f,
    public val xl3: Float = 2.2f,
    public val xl4: Float = 2.6f,
) {
    /**
     * Returns a copy of this [RadiusFactors] with the given values replaced and every other value kept.
     *
     * @param sm the new value of [sm].
     * @param md the new value of [md].
     * @param xl the new value of [xl].
     * @param xl2 the new value of [xl2].
     * @param xl3 the new value of [xl3].
     * @param xl4 the new value of [xl4].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(
        sm: Float = this.sm,
        md: Float = this.md,
        xl: Float = this.xl,
        xl2: Float = this.xl2,
        xl3: Float = this.xl3,
        xl4: Float = this.xl4,
    ): RadiusFactors = RadiusFactors(
        sm = sm,
        md = md,
        xl = xl,
        xl2 = xl2,
        xl3 = xl3,
        xl4 = xl4,
    )

    override fun equals(other: Any?): Boolean = other is RadiusFactors && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "RadiusFactors(" +
        "sm=$sm, " +
        "md=$md, " +
        "xl=$xl, " +
        "xl2=$xl2, " +
        "xl3=$xl3, " +
        "xl4=$xl4)"

    private fun values(): List<Any?> = listOf(
        sm,
        md,
        xl,
        xl2,
        xl3,
        xl4,
    )
}
