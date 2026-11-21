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

package com.davils.kiro.ui.window.splash.hero

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.components.progress.ProgressStyle
import com.davils.kiro.ui.motion.scene.Scene
import com.davils.kiro.ui.window.splash.SplashFailureStyle
import com.davils.kiro.ui.window.splash.SplashLabels

/**
 * How a [HeroSplash] looks: a generative scene over the whole window that fades into the background towards the
 * bottom, where the application stands.
 *
 * @property size the recommended size of the [com.davils.kiro.ui.window.splash.SplashWindow] that shows the splash.
 * @property scene the scene the default background plays over the whole splash.
 * @property scrim the color the background fades into towards the bottom.
 * @property scrimStart where the fade starts at the latest, as a fraction of the height from the top.
 * @property scrimEnd where the fade ends at the latest, as a fraction of the height; it ends where the content starts.
 * @property content the color of the name and the current step.
 * @property muted the color of the subtitle, the version and the copyright.
 * @property padding the padding of the content.
 * @property gap the space between the logo, the titles, the progress, the failure and the footer.
 * @property titleGap the space between the name and the subtitle and between the current step and the progress bar.
 * @property logoSize the size of the logo.
 * @property nameStyle the text style of the name.
 * @property subtitleStyle the text style of the subtitle.
 * @property stepStyle the text style of the current step.
 * @property footerStyle the text style of the version and the copyright.
 * @property progress the style of the progress bar.
 * @property failure how the message and the actions of a failed start look.
 * @property labels the texts the splash shows and announces.
 * @since 1.0.0
 */
@Immutable
public class HeroSplashStyle(
    public val size: DpSize,
    public val scene: Scene,
    public val scrim: Color,
    public val scrimStart: Float,
    public val scrimEnd: Float,
    public val content: Color,
    public val muted: Color,
    public val padding: Dp,
    public val gap: Dp,
    public val titleGap: Dp,
    public val logoSize: Dp,
    public val nameStyle: TextStyle,
    public val subtitleStyle: TextStyle,
    public val stepStyle: TextStyle,
    public val footerStyle: TextStyle,
    public val progress: ProgressStyle,
    public val failure: SplashFailureStyle,
    public val labels: SplashLabels,
) {
    /**
     * Returns a copy of this [HeroSplashStyle] with the given values replaced and every other value kept.
     *
     * @param size the new value of [size].
     * @param scene the new value of [scene].
     * @param scrim the new value of [scrim].
     * @param scrimStart the new value of [scrimStart].
     * @param scrimEnd the new value of [scrimEnd].
     * @param content the new value of [content].
     * @param muted the new value of [muted].
     * @param padding the new value of [padding].
     * @param gap the new value of [gap].
     * @param titleGap the new value of [titleGap].
     * @param logoSize the new value of [logoSize].
     * @param nameStyle the new value of [nameStyle].
     * @param subtitleStyle the new value of [subtitleStyle].
     * @param stepStyle the new value of [stepStyle].
     * @param footerStyle the new value of [footerStyle].
     * @param progress the new value of [progress].
     * @param failure the new value of [failure].
     * @param labels the new value of [labels].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        size: DpSize = this.size,
        scene: Scene = this.scene,
        scrim: Color = this.scrim,
        scrimStart: Float = this.scrimStart,
        scrimEnd: Float = this.scrimEnd,
        content: Color = this.content,
        muted: Color = this.muted,
        padding: Dp = this.padding,
        gap: Dp = this.gap,
        titleGap: Dp = this.titleGap,
        logoSize: Dp = this.logoSize,
        nameStyle: TextStyle = this.nameStyle,
        subtitleStyle: TextStyle = this.subtitleStyle,
        stepStyle: TextStyle = this.stepStyle,
        footerStyle: TextStyle = this.footerStyle,
        progress: ProgressStyle = this.progress,
        failure: SplashFailureStyle = this.failure,
        labels: SplashLabels = this.labels,
    ): HeroSplashStyle = HeroSplashStyle(
        size = size,
        scene = scene,
        scrim = scrim,
        scrimStart = scrimStart,
        scrimEnd = scrimEnd,
        content = content,
        muted = muted,
        padding = padding,
        gap = gap,
        titleGap = titleGap,
        logoSize = logoSize,
        nameStyle = nameStyle,
        subtitleStyle = subtitleStyle,
        stepStyle = stepStyle,
        footerStyle = footerStyle,
        progress = progress,
        failure = failure,
        labels = labels,
    )

    override fun equals(other: Any?): Boolean = other is HeroSplashStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "HeroSplashStyle(" +
        "size=$size, " +
        "scene=$scene, " +
        "scrim=$scrim, " +
        "scrimStart=$scrimStart, " +
        "scrimEnd=$scrimEnd, " +
        "content=$content, " +
        "muted=$muted, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "titleGap=$titleGap, " +
        "logoSize=$logoSize, " +
        "nameStyle=$nameStyle, " +
        "subtitleStyle=$subtitleStyle, " +
        "stepStyle=$stepStyle, " +
        "footerStyle=$footerStyle, " +
        "progress=$progress, " +
        "failure=$failure, " +
        "labels=$labels)"

    private fun values(): List<Any?> = listOf(
        size,
        scene,
        scrim,
        scrimStart,
        scrimEnd,
        content,
        muted,
        padding,
        gap,
        titleGap,
        logoSize,
        nameStyle,
        subtitleStyle,
        stepStyle,
        footerStyle,
        progress,
        failure,
        labels,
    )
}
