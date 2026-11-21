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

package com.davils.kiro.ui.motion.shader.grain

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.motion.shader.ShaderBackend
import kotlin.time.Duration

/**
 * How a [GrainArt] looks: a fine film grain of deep and soft specks, meant as the top layer of a scene at a low
 * alpha.
 *
 * @property shade the color of the deep specks, or `null` for the shade of the palette of the frame.
 * @property tone the color of the soft specks, or `null` for the ink of the palette of the frame, which stands out
against a dark background.
 * @property cell the size of one speck.
 * @property amount the alpha of the strongest speck, from `0` to `1`.
 * @property balance the share of [shade] specks, from `0` for only [tone] specks to `1` for only [shade] specks.
 * @property refresh how long one pattern of grain stays before the next, or `null` for a grain that never
changes.
 * @property dots the most specks the canvas fallback draws at the highest quality.
 * @property backend whether the art draws with its shader where it can or always with its canvas fallback.
 * @since 1.0.0
 */
@Immutable
public class GrainArtStyle(
    public val shade: Color?,
    public val tone: Color?,
    public val cell: Dp,
    public val amount: Float,
    public val balance: Float,
    public val refresh: Duration?,
    public val dots: Int,
    public val backend: ShaderBackend,
) {
    /**
     * Returns a copy of this [GrainArtStyle] with the given values replaced and every other value kept.
     *
     * @param shade the new value of [shade].
     * @param tone the new value of [tone].
     * @param cell the new value of [cell].
     * @param amount the new value of [amount].
     * @param balance the new value of [balance].
     * @param refresh the new value of [refresh].
     * @param dots the new value of [dots].
     * @param backend the new value of [backend].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        shade: Color? = this.shade,
        tone: Color? = this.tone,
        cell: Dp = this.cell,
        amount: Float = this.amount,
        balance: Float = this.balance,
        refresh: Duration? = this.refresh,
        dots: Int = this.dots,
        backend: ShaderBackend = this.backend,
    ): GrainArtStyle = GrainArtStyle(
        shade = shade,
        tone = tone,
        cell = cell,
        amount = amount,
        balance = balance,
        refresh = refresh,
        dots = dots,
        backend = backend,
    )

    override fun equals(other: Any?): Boolean = other is GrainArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "GrainArtStyle(" +
        "shade=${shade ?: "null"}, " +
        "tone=${tone ?: "null"}, " +
        "cell=$cell, " +
        "amount=$amount, " +
        "balance=$balance, " +
        "refresh=${refresh ?: "null"}, " +
        "dots=$dots, " +
        "backend=$backend)"

    private fun values(): List<Any?> = listOf(
        shade,
        tone,
        cell,
        amount,
        balance,
        refresh,
        dots,
        backend,
    )
}
