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

package com.davils.kiro.ui.window.splash.centered

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.motion.scene.Scene
import com.davils.kiro.ui.window.splash.SplashFailureStyle
import com.davils.kiro.ui.window.splash.SplashLabels
import kotlin.time.Duration

/**
 * How a [CenteredSplash] looks: a generative scene over the whole window and, in its center, the logo inside a
 * ring that shows the progress, with the name and the current step below.
 *
 * @property size the recommended size of the [com.davils.kiro.ui.window.splash.SplashWindow] that shows the splash.
 * @property scene the scene the default backdrop plays behind the whole splash; its center is the center of the ring.
 * @property content the color of the name.
 * @property muted the color of the subtitle, the current step, the version and the copyright.
 * @property veil the color of the soft veil behind the texts that calms the scene where they stand.
 * @property veilSpread how far the veil reaches beyond the texts.
 * @property veilHold the share of the veil from its center outwards that keeps the full [veil] before it fades.
 * @property padding the padding of the content.
 * @property gap the space between the ring, the titles, the current step and the failure.
 * @property titleGap the space between the name and the subtitle.
 * @property footerGap the space between the version and the copyright.
 * @property logoSize the size of the logo in the middle of the ring.
 * @property ringGap the space between the logo and the ring.
 * @property ringWidth the width of the stroke of the ring.
 * @property ringTrack the color of the full circle behind the progress.
 * @property ringColor the color of the arc that shows the progress.
 * @property ringCap the cap of the ends of the arc.
 * @property ringStart the angle in degrees the arc starts at, clockwise from the right; `-90` is the top.
 * @property ringSweep the angle in degrees the arc covers while the progress is unknown.
 * @property ringPeriod how long the arc takes for one turn while the progress is unknown.
 * @property ringDuration how long the arc takes to follow a change of the progress.
 * @property ringEasing the easing of the arc when it follows a change of the progress.
 * @property nameStyle the text style of the name.
 * @property subtitleStyle the text style of the subtitle.
 * @property stepStyle the text style of the current step.
 * @property footerStyle the text style of the version and the copyright.
 * @property failure how the message and the actions of a failed start look.
 * @property labels the texts the splash shows and announces.
 * @since 1.0.0
 */
@Immutable
public class CenteredSplashStyle(
    public val size: DpSize,
    public val scene: Scene,
    public val content: Color,
    public val muted: Color,
    public val veil: Color,
    public val veilSpread: Dp,
    public val veilHold: Float,
    public val padding: Dp,
    public val gap: Dp,
    public val titleGap: Dp,
    public val footerGap: Dp,
    public val logoSize: Dp,
    public val ringGap: Dp,
    public val ringWidth: Dp,
    public val ringTrack: Color,
    public val ringColor: Color,
    public val ringCap: StrokeCap,
    public val ringStart: Float,
    public val ringSweep: Float,
    public val ringPeriod: Duration,
    public val ringDuration: Duration,
    public val ringEasing: Easing,
    public val nameStyle: TextStyle,
    public val subtitleStyle: TextStyle,
    public val stepStyle: TextStyle,
    public val footerStyle: TextStyle,
    public val failure: SplashFailureStyle,
    public val labels: SplashLabels,
) {
    /**
     * Returns a copy of this [CenteredSplashStyle] with the given values replaced and every other value kept.
     *
     * @param size the new value of [size].
     * @param scene the new value of [scene].
     * @param content the new value of [content].
     * @param muted the new value of [muted].
     * @param veil the new value of [veil].
     * @param veilSpread the new value of [veilSpread].
     * @param veilHold the new value of [veilHold].
     * @param padding the new value of [padding].
     * @param gap the new value of [gap].
     * @param titleGap the new value of [titleGap].
     * @param footerGap the new value of [footerGap].
     * @param logoSize the new value of [logoSize].
     * @param ringGap the new value of [ringGap].
     * @param ringWidth the new value of [ringWidth].
     * @param ringTrack the new value of [ringTrack].
     * @param ringColor the new value of [ringColor].
     * @param ringCap the new value of [ringCap].
     * @param ringStart the new value of [ringStart].
     * @param ringSweep the new value of [ringSweep].
     * @param ringPeriod the new value of [ringPeriod].
     * @param ringDuration the new value of [ringDuration].
     * @param ringEasing the new value of [ringEasing].
     * @param nameStyle the new value of [nameStyle].
     * @param subtitleStyle the new value of [subtitleStyle].
     * @param stepStyle the new value of [stepStyle].
     * @param footerStyle the new value of [footerStyle].
     * @param failure the new value of [failure].
     * @param labels the new value of [labels].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        size: DpSize = this.size,
        scene: Scene = this.scene,
        content: Color = this.content,
        muted: Color = this.muted,
        veil: Color = this.veil,
        veilSpread: Dp = this.veilSpread,
        veilHold: Float = this.veilHold,
        padding: Dp = this.padding,
        gap: Dp = this.gap,
        titleGap: Dp = this.titleGap,
        footerGap: Dp = this.footerGap,
        logoSize: Dp = this.logoSize,
        ringGap: Dp = this.ringGap,
        ringWidth: Dp = this.ringWidth,
        ringTrack: Color = this.ringTrack,
        ringColor: Color = this.ringColor,
        ringCap: StrokeCap = this.ringCap,
        ringStart: Float = this.ringStart,
        ringSweep: Float = this.ringSweep,
        ringPeriod: Duration = this.ringPeriod,
        ringDuration: Duration = this.ringDuration,
        ringEasing: Easing = this.ringEasing,
        nameStyle: TextStyle = this.nameStyle,
        subtitleStyle: TextStyle = this.subtitleStyle,
        stepStyle: TextStyle = this.stepStyle,
        footerStyle: TextStyle = this.footerStyle,
        failure: SplashFailureStyle = this.failure,
        labels: SplashLabels = this.labels,
    ): CenteredSplashStyle = CenteredSplashStyle(
        size = size,
        scene = scene,
        content = content,
        muted = muted,
        veil = veil,
        veilSpread = veilSpread,
        veilHold = veilHold,
        padding = padding,
        gap = gap,
        titleGap = titleGap,
        footerGap = footerGap,
        logoSize = logoSize,
        ringGap = ringGap,
        ringWidth = ringWidth,
        ringTrack = ringTrack,
        ringColor = ringColor,
        ringCap = ringCap,
        ringStart = ringStart,
        ringSweep = ringSweep,
        ringPeriod = ringPeriod,
        ringDuration = ringDuration,
        ringEasing = ringEasing,
        nameStyle = nameStyle,
        subtitleStyle = subtitleStyle,
        stepStyle = stepStyle,
        footerStyle = footerStyle,
        failure = failure,
        labels = labels,
    )

    override fun equals(other: Any?): Boolean = other is CenteredSplashStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CenteredSplashStyle(" +
        "size=$size, " +
        "scene=$scene, " +
        "content=$content, " +
        "muted=$muted, " +
        "veil=$veil, " +
        "veilSpread=$veilSpread, " +
        "veilHold=$veilHold, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "titleGap=$titleGap, " +
        "footerGap=$footerGap, " +
        "logoSize=$logoSize, " +
        "ringGap=$ringGap, " +
        "ringWidth=$ringWidth, " +
        "ringTrack=$ringTrack, " +
        "ringColor=$ringColor, " +
        "ringCap=$ringCap, " +
        "ringStart=$ringStart, " +
        "ringSweep=$ringSweep, " +
        "ringPeriod=$ringPeriod, " +
        "ringDuration=$ringDuration, " +
        "ringEasing=$ringEasing, " +
        "nameStyle=$nameStyle, " +
        "subtitleStyle=$subtitleStyle, " +
        "stepStyle=$stepStyle, " +
        "footerStyle=$footerStyle, " +
        "failure=$failure, " +
        "labels=$labels)"

    private fun values(): List<Any?> = listOf(
        size,
        scene,
        content,
        muted,
        veil,
        veilSpread,
        veilHold,
        padding,
        gap,
        titleGap,
        footerGap,
        logoSize,
        ringGap,
        ringWidth,
        ringTrack,
        ringColor,
        ringCap,
        ringStart,
        ringSweep,
        ringPeriod,
        ringDuration,
        ringEasing,
        nameStyle,
        subtitleStyle,
        stepStyle,
        footerStyle,
        failure,
        labels,
    )
}
