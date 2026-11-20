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

package com.davils.kiro.ui.core.typography

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.sp

/**
 * The type scale of a theme, which follows the font sizes of Tailwind CSS.
 *
 * @property xs the extra small size.
 * @property sm the small size.
 * @property base the base size of body text.
 * @property lg the large size.
 * @property xl the extra large size.
 * @property xl2 the size twice extra large.
 * @property xl3 the size three times extra large.
 * @property xl4 the size four times extra large.
 * @property xl5 the size five times extra large.
 * @property xl6 the size six times extra large.
 * @property xl7 the size seven times extra large.
 * @property xl8 the size eight times extra large.
 * @property xl9 the size nine times extra large.
 * @since 1.0.0
 */
@Immutable
public class TextScale(
    public val xs: TextSize = TextSize(fontSize = 12.sp, lineHeight = 16.sp),
    public val sm: TextSize = TextSize(fontSize = 14.sp, lineHeight = 20.sp),
    public val base: TextSize = TextSize(fontSize = 16.sp, lineHeight = 24.sp),
    public val lg: TextSize = TextSize(fontSize = 18.sp, lineHeight = 28.sp),
    public val xl: TextSize = TextSize(fontSize = 20.sp, lineHeight = 28.sp),
    public val xl2: TextSize = TextSize(fontSize = 24.sp, lineHeight = 32.sp),
    public val xl3: TextSize = TextSize(fontSize = 30.sp, lineHeight = 36.sp),
    public val xl4: TextSize = TextSize(fontSize = 36.sp, lineHeight = 40.sp),
    public val xl5: TextSize = TextSize(fontSize = 48.sp, lineHeight = 48.sp),
    public val xl6: TextSize = TextSize(fontSize = 60.sp, lineHeight = 60.sp),
    public val xl7: TextSize = TextSize(fontSize = 72.sp, lineHeight = 72.sp),
    public val xl8: TextSize = TextSize(fontSize = 96.sp, lineHeight = 96.sp),
    public val xl9: TextSize = TextSize(fontSize = 128.sp, lineHeight = 128.sp),
) {
    /**
     * Returns a copy of this [TextScale] with the given values replaced and every other value kept.
     *
     * @param xs the new value of [xs].
     * @param sm the new value of [sm].
     * @param base the new value of [base].
     * @param lg the new value of [lg].
     * @param xl the new value of [xl].
     * @param xl2 the new value of [xl2].
     * @param xl3 the new value of [xl3].
     * @param xl4 the new value of [xl4].
     * @param xl5 the new value of [xl5].
     * @param xl6 the new value of [xl6].
     * @param xl7 the new value of [xl7].
     * @param xl8 the new value of [xl8].
     * @param xl9 the new value of [xl9].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        xs: TextSize = this.xs,
        sm: TextSize = this.sm,
        base: TextSize = this.base,
        lg: TextSize = this.lg,
        xl: TextSize = this.xl,
        xl2: TextSize = this.xl2,
        xl3: TextSize = this.xl3,
        xl4: TextSize = this.xl4,
        xl5: TextSize = this.xl5,
        xl6: TextSize = this.xl6,
        xl7: TextSize = this.xl7,
        xl8: TextSize = this.xl8,
        xl9: TextSize = this.xl9,
    ): TextScale = TextScale(
        xs = xs,
        sm = sm,
        base = base,
        lg = lg,
        xl = xl,
        xl2 = xl2,
        xl3 = xl3,
        xl4 = xl4,
        xl5 = xl5,
        xl6 = xl6,
        xl7 = xl7,
        xl8 = xl8,
        xl9 = xl9,
    )

    override fun equals(other: Any?): Boolean = other is TextScale && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TextScale(" +
        "xs=$xs, " +
        "sm=$sm, " +
        "base=$base, " +
        "lg=$lg, " +
        "xl=$xl, " +
        "xl2=$xl2, " +
        "xl3=$xl3, " +
        "xl4=$xl4, " +
        "xl5=$xl5, " +
        "xl6=$xl6, " +
        "xl7=$xl7, " +
        "xl8=$xl8, " +
        "xl9=$xl9)"

    private fun values(): List<Any?> = listOf(
        xs,
        sm,
        base,
        lg,
        xl,
        xl2,
        xl3,
        xl4,
        xl5,
        xl6,
        xl7,
        xl8,
        xl9,
    )
}
