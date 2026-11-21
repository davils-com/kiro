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

package com.davils.kiro.ui.window.splash.minimal

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.components.spinner.SpinnerStyle
import com.davils.kiro.ui.core.elevation.BoxShadow
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.motion.scene.Scene
import com.davils.kiro.ui.window.splash.SplashFailureStyle
import com.davils.kiro.ui.window.splash.SplashLabels

/**
 * How a [MinimalSplash] looks: a generative scene over the whole window and a floating card with only the logo,
 * a spinner and, if wanted, the name.
 *
 * @property size the recommended size of the [com.davils.kiro.ui.window.splash.SplashWindow] that shows the splash.
 * @property scene the scene the default backdrop plays behind the card.
 * @property card the color of the card.
 * @property border the border of the card.
 * @property cornerRadius the radius of the corners of the card.
 * @property shadow the shadow the card casts on the scene.
 * @property cardPadding the padding inside the card.
 * @property content the color of the name.
 * @property padding the least space between the card and the edges of the splash.
 * @property gap the space between the logo, the name, the spinner and the failure.
 * @property logoSize the size of the logo.
 * @property nameStyle the text style of the name.
 * @property isNameShown whether the name shows below the logo.
 * @property spinner the style of the spinner.
 * @property failure how the message and the actions of a failed start look.
 * @property labels the texts the splash shows and announces.
 * @since 1.0.0
 */
@Immutable
public class MinimalSplashStyle(
    public val size: DpSize,
    public val scene: Scene,
    public val card: Color,
    public val border: Border,
    public val cornerRadius: Dp,
    public val shadow: BoxShadow,
    public val cardPadding: Dp,
    public val content: Color,
    public val padding: Dp,
    public val gap: Dp,
    public val logoSize: Dp,
    public val nameStyle: TextStyle,
    public val isNameShown: Boolean,
    public val spinner: SpinnerStyle,
    public val failure: SplashFailureStyle,
    public val labels: SplashLabels,
) {
    /**
     * Returns a copy of this [MinimalSplashStyle] with the given values replaced and every other value kept.
     *
     * @param size the new value of [size].
     * @param scene the new value of [scene].
     * @param card the new value of [card].
     * @param border the new value of [border].
     * @param cornerRadius the new value of [cornerRadius].
     * @param shadow the new value of [shadow].
     * @param cardPadding the new value of [cardPadding].
     * @param content the new value of [content].
     * @param padding the new value of [padding].
     * @param gap the new value of [gap].
     * @param logoSize the new value of [logoSize].
     * @param nameStyle the new value of [nameStyle].
     * @param isNameShown the new value of [isNameShown].
     * @param spinner the new value of [spinner].
     * @param failure the new value of [failure].
     * @param labels the new value of [labels].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        size: DpSize = this.size,
        scene: Scene = this.scene,
        card: Color = this.card,
        border: Border = this.border,
        cornerRadius: Dp = this.cornerRadius,
        shadow: BoxShadow = this.shadow,
        cardPadding: Dp = this.cardPadding,
        content: Color = this.content,
        padding: Dp = this.padding,
        gap: Dp = this.gap,
        logoSize: Dp = this.logoSize,
        nameStyle: TextStyle = this.nameStyle,
        isNameShown: Boolean = this.isNameShown,
        spinner: SpinnerStyle = this.spinner,
        failure: SplashFailureStyle = this.failure,
        labels: SplashLabels = this.labels,
    ): MinimalSplashStyle = MinimalSplashStyle(
        size = size,
        scene = scene,
        card = card,
        border = border,
        cornerRadius = cornerRadius,
        shadow = shadow,
        cardPadding = cardPadding,
        content = content,
        padding = padding,
        gap = gap,
        logoSize = logoSize,
        nameStyle = nameStyle,
        isNameShown = isNameShown,
        spinner = spinner,
        failure = failure,
        labels = labels,
    )

    override fun equals(other: Any?): Boolean = other is MinimalSplashStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MinimalSplashStyle(" +
        "size=$size, " +
        "scene=$scene, " +
        "card=$card, " +
        "border=$border, " +
        "cornerRadius=$cornerRadius, " +
        "shadow=$shadow, " +
        "cardPadding=$cardPadding, " +
        "content=$content, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "logoSize=$logoSize, " +
        "nameStyle=$nameStyle, " +
        "isNameShown=$isNameShown, " +
        "spinner=$spinner, " +
        "failure=$failure, " +
        "labels=$labels)"

    private fun values(): List<Any?> = listOf(
        size,
        scene,
        card,
        border,
        cornerRadius,
        shadow,
        cardPadding,
        content,
        padding,
        gap,
        logoSize,
        nameStyle,
        isNameShown,
        spinner,
        failure,
        labels,
    )
}
