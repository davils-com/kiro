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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * The corner radii of a theme, from small to extra large.
 *
 * @property sm the small radius.
 * @property md the medium radius.
 * @property lg the large radius, the base of the scale.
 * @property xl the extra large radius.
 * @property xl2 the radius twice extra large.
 * @property xl3 the radius three times extra large.
 * @property xl4 the radius four times extra large.
 * @since 0.1.0
 */
@Immutable
public class Radii(
    public val sm: Dp,
    public val md: Dp,
    public val lg: Dp,
    public val xl: Dp,
    public val xl2: Dp,
    public val xl3: Dp,
    public val xl4: Dp,
) {
    /**
     * Returns a copy of this [Radii] with the given values replaced and every other value kept.
     *
     * @param sm the new value of [sm].
     * @param md the new value of [md].
     * @param lg the new value of [lg].
     * @param xl the new value of [xl].
     * @param xl2 the new value of [xl2].
     * @param xl3 the new value of [xl3].
     * @param xl4 the new value of [xl4].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(
        sm: Dp = this.sm,
        md: Dp = this.md,
        lg: Dp = this.lg,
        xl: Dp = this.xl,
        xl2: Dp = this.xl2,
        xl3: Dp = this.xl3,
        xl4: Dp = this.xl4,
    ): Radii = Radii(
        sm = sm,
        md = md,
        lg = lg,
        xl = xl,
        xl2 = xl2,
        xl3 = xl3,
        xl4 = xl4,
    )

    override fun equals(other: Any?): Boolean = other is Radii && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Radii(" +
        "sm=$sm, " +
        "md=$md, " +
        "lg=$lg, " +
        "xl=$xl, " +
        "xl2=$xl2, " +
        "xl3=$xl3, " +
        "xl4=$xl4)"

    private fun values(): List<Any?> = listOf(
        sm,
        md,
        lg,
        xl,
        xl2,
        xl3,
        xl4,
    )

    /**
     * Builds the radii of a theme.
     *
     * @since 0.1.0
     */
    public companion object {
        /**
         * The base radius of Kiro, the radius [lg] of the default radii.
         *
         * @since 0.1.0
         */
        public val DefaultBase: Dp = 10.dp

        /**
         * Returns radii that grow from a base radius by the factors of the radius scale of shadcn/ui.
         *
         * @param base the radius [lg]; every other radius is a multiple of it.
         * @param factors the factors of the other radii.
         * @return the radii.
         * @since 0.1.0
         */
        public fun from(base: Dp = DefaultBase, factors: RadiusFactors = RadiusFactors()): Radii = Radii(
            sm = base * factors.sm,
            md = base * factors.md,
            lg = base,
            xl = base * factors.xl,
            xl2 = base * factors.xl2,
            xl3 = base * factors.xl3,
            xl4 = base * factors.xl4,
        )
    }
}
