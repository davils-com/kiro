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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * The elevation of a theme: a scale of shadows from the faintest to the deepest.
 *
 * @property xs2 the faintest shadow, a single hairline below the shape.
 * @property xs an extra small shadow.
 * @property sm a small shadow, such as the one of a card.
 * @property md a medium shadow, such as the one of a popover.
 * @property lg a large shadow, such as the one of a menu.
 * @property xl an extra large shadow.
 * @property xl2 the deepest shadow, such as the one of a dialog.
 * @since 0.1.0
 */
@Immutable
public class Shadows(
    public val xs2: BoxShadow,
    public val xs: BoxShadow,
    public val sm: BoxShadow,
    public val md: BoxShadow,
    public val lg: BoxShadow,
    public val xl: BoxShadow,
    public val xl2: BoxShadow,
) {
    /**
     * Returns a copy of this [Shadows] with the given values replaced and every other value kept.
     *
     * @param xs2 the new value of [xs2].
     * @param xs the new value of [xs].
     * @param sm the new value of [sm].
     * @param md the new value of [md].
     * @param lg the new value of [lg].
     * @param xl the new value of [xl].
     * @param xl2 the new value of [xl2].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(
        xs2: BoxShadow = this.xs2,
        xs: BoxShadow = this.xs,
        sm: BoxShadow = this.sm,
        md: BoxShadow = this.md,
        lg: BoxShadow = this.lg,
        xl: BoxShadow = this.xl,
        xl2: BoxShadow = this.xl2,
    ): Shadows = Shadows(
        xs2 = xs2,
        xs = xs,
        sm = sm,
        md = md,
        lg = lg,
        xl = xl,
        xl2 = xl2,
    )

    override fun equals(other: Any?): Boolean = other is Shadows && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Shadows(" +
        "xs2=$xs2, " +
        "xs=$xs, " +
        "sm=$sm, " +
        "md=$md, " +
        "lg=$lg, " +
        "xl=$xl, " +
        "xl2=$xl2)"

    private fun values(): List<Any?> = listOf(
        xs2,
        xs,
        sm,
        md,
        lg,
        xl,
        xl2,
    )

    /**
     * Builds the elevation of a theme.
     *
     * @since 0.1.0
     */
    public companion object {
        /**
         * Returns the shadows of Kiro, which follow the shadow scale of Tailwind CSS, drawn in the given color.
         *
         * @param color the color of every shadow, usually the shadow color of the theme.
         * @param strength the strengths of the layers, applied as the alpha of [color].
         * @return the shadows.
         * @since 0.1.0
         */
        public fun from(color: Color, strength: ShadowStrength = ShadowStrength()): Shadows {
            val faint = color.copy(alpha = strength.faint)
            val soft = color.copy(alpha = strength.soft)
            val deep = color.copy(alpha = strength.deep)
            return Shadows(
                xs2 = BoxShadow(listOf(ShadowLayer(offsetY = 1.dp, blur = 0.dp, color = faint))),
                xs = BoxShadow(listOf(ShadowLayer(offsetY = 1.dp, blur = 2.dp, color = faint))),
                sm = BoxShadow(
                    listOf(
                        ShadowLayer(offsetY = 1.dp, blur = 3.dp, color = soft),
                        ShadowLayer(offsetY = 1.dp, blur = 2.dp, spread = (-1).dp, color = soft),
                    ),
                ),
                md = BoxShadow(
                    listOf(
                        ShadowLayer(offsetY = 4.dp, blur = 6.dp, spread = (-1).dp, color = soft),
                        ShadowLayer(offsetY = 2.dp, blur = 4.dp, spread = (-2).dp, color = soft),
                    ),
                ),
                lg = BoxShadow(
                    listOf(
                        ShadowLayer(offsetY = 10.dp, blur = 15.dp, spread = (-3).dp, color = soft),
                        ShadowLayer(offsetY = 4.dp, blur = 6.dp, spread = (-4).dp, color = soft),
                    ),
                ),
                xl = BoxShadow(
                    listOf(
                        ShadowLayer(offsetY = 20.dp, blur = 25.dp, spread = (-5).dp, color = soft),
                        ShadowLayer(offsetY = 8.dp, blur = 10.dp, spread = (-6).dp, color = soft),
                    ),
                ),
                xl2 = BoxShadow(
                    listOf(ShadowLayer(offsetY = 25.dp, blur = 50.dp, spread = (-12).dp, color = deep)),
                ),
            )
        }
    }
}
