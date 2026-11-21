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

package com.davils.kiro.ui.window.splash.checklist

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.components.spinner.SpinnerStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.motion.scene.Scene
import com.davils.kiro.ui.window.splash.SplashFailureStyle
import com.davils.kiro.ui.window.splash.SplashLabels

/**
 * How a [ChecklistSplash] looks: a band with a generative scene and the application beside the list of every
 * step with its status.
 *
 * @property size the recommended size of the [com.davils.kiro.ui.window.splash.SplashWindow] that shows the splash.
 * @property scene the scene the default backdrop plays in the band.
 * @property bandFraction the share of the width of the splash the band takes, from `0` to `1`.
 * @property divider the line between the band and the list.
 * @property background the color behind the list.
 * @property content the color of the name.
 * @property muted the color of the version.
 * @property padding the padding of the band and of the list.
 * @property gap the space between the list and the failure.
 * @property headerGap the space between the logo and the name.
 * @property logoSize the size of the logo.
 * @property nameStyle the text style of the name.
 * @property versionStyle the text style of the version.
 * @property stepGap the space between the steps.
 * @property iconGap the space between the icon and the label of a step.
 * @property iconSize the size of the icon of a step.
 * @property stepStyle the text style of the label of a step.
 * @property pendingColor the color of a step that has not run yet.
 * @property runningColor the color of the running step.
 * @property doneColor the color of a step that is done.
 * @property failedColor the color of a step that failed.
 * @property skippedColor the color of a step that was skipped.
 * @property pendingIcon the icon of a step that has not run yet.
 * @property doneIcon the icon of a step that is done.
 * @property failedIcon the icon of a step that failed.
 * @property skippedIcon the icon of a step that was skipped.
 * @property spinner the style of the spinner of the running step.
 * @property failure how the message and the actions of a failed start look.
 * @property labels the texts the splash shows and announces.
 * @since 1.0.0
 */
@Immutable
public class ChecklistSplashStyle(
    public val size: DpSize,
    public val scene: Scene,
    public val bandFraction: Float,
    public val divider: Border,
    public val background: Color,
    public val content: Color,
    public val muted: Color,
    public val padding: Dp,
    public val gap: Dp,
    public val headerGap: Dp,
    public val logoSize: Dp,
    public val nameStyle: TextStyle,
    public val versionStyle: TextStyle,
    public val stepGap: Dp,
    public val iconGap: Dp,
    public val iconSize: Dp,
    public val stepStyle: TextStyle,
    public val pendingColor: Color,
    public val runningColor: Color,
    public val doneColor: Color,
    public val failedColor: Color,
    public val skippedColor: Color,
    public val pendingIcon: IconGraphic,
    public val doneIcon: IconGraphic,
    public val failedIcon: IconGraphic,
    public val skippedIcon: IconGraphic,
    public val spinner: SpinnerStyle,
    public val failure: SplashFailureStyle,
    public val labels: SplashLabels,
) {
    /**
     * Returns a copy of this [ChecklistSplashStyle] with the given values replaced and every other value kept.
     *
     * @param size the new value of [size].
     * @param scene the new value of [scene].
     * @param bandFraction the new value of [bandFraction].
     * @param divider the new value of [divider].
     * @param background the new value of [background].
     * @param content the new value of [content].
     * @param muted the new value of [muted].
     * @param padding the new value of [padding].
     * @param gap the new value of [gap].
     * @param headerGap the new value of [headerGap].
     * @param logoSize the new value of [logoSize].
     * @param nameStyle the new value of [nameStyle].
     * @param versionStyle the new value of [versionStyle].
     * @param stepGap the new value of [stepGap].
     * @param iconGap the new value of [iconGap].
     * @param iconSize the new value of [iconSize].
     * @param stepStyle the new value of [stepStyle].
     * @param pendingColor the new value of [pendingColor].
     * @param runningColor the new value of [runningColor].
     * @param doneColor the new value of [doneColor].
     * @param failedColor the new value of [failedColor].
     * @param skippedColor the new value of [skippedColor].
     * @param pendingIcon the new value of [pendingIcon].
     * @param doneIcon the new value of [doneIcon].
     * @param failedIcon the new value of [failedIcon].
     * @param skippedIcon the new value of [skippedIcon].
     * @param spinner the new value of [spinner].
     * @param failure the new value of [failure].
     * @param labels the new value of [labels].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        size: DpSize = this.size,
        scene: Scene = this.scene,
        bandFraction: Float = this.bandFraction,
        divider: Border = this.divider,
        background: Color = this.background,
        content: Color = this.content,
        muted: Color = this.muted,
        padding: Dp = this.padding,
        gap: Dp = this.gap,
        headerGap: Dp = this.headerGap,
        logoSize: Dp = this.logoSize,
        nameStyle: TextStyle = this.nameStyle,
        versionStyle: TextStyle = this.versionStyle,
        stepGap: Dp = this.stepGap,
        iconGap: Dp = this.iconGap,
        iconSize: Dp = this.iconSize,
        stepStyle: TextStyle = this.stepStyle,
        pendingColor: Color = this.pendingColor,
        runningColor: Color = this.runningColor,
        doneColor: Color = this.doneColor,
        failedColor: Color = this.failedColor,
        skippedColor: Color = this.skippedColor,
        pendingIcon: IconGraphic = this.pendingIcon,
        doneIcon: IconGraphic = this.doneIcon,
        failedIcon: IconGraphic = this.failedIcon,
        skippedIcon: IconGraphic = this.skippedIcon,
        spinner: SpinnerStyle = this.spinner,
        failure: SplashFailureStyle = this.failure,
        labels: SplashLabels = this.labels,
    ): ChecklistSplashStyle = ChecklistSplashStyle(
        size = size,
        scene = scene,
        bandFraction = bandFraction,
        divider = divider,
        background = background,
        content = content,
        muted = muted,
        padding = padding,
        gap = gap,
        headerGap = headerGap,
        logoSize = logoSize,
        nameStyle = nameStyle,
        versionStyle = versionStyle,
        stepGap = stepGap,
        iconGap = iconGap,
        iconSize = iconSize,
        stepStyle = stepStyle,
        pendingColor = pendingColor,
        runningColor = runningColor,
        doneColor = doneColor,
        failedColor = failedColor,
        skippedColor = skippedColor,
        pendingIcon = pendingIcon,
        doneIcon = doneIcon,
        failedIcon = failedIcon,
        skippedIcon = skippedIcon,
        spinner = spinner,
        failure = failure,
        labels = labels,
    )

    override fun equals(other: Any?): Boolean = other is ChecklistSplashStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ChecklistSplashStyle(" +
        "size=$size, " +
        "scene=$scene, " +
        "bandFraction=$bandFraction, " +
        "divider=$divider, " +
        "background=$background, " +
        "content=$content, " +
        "muted=$muted, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "headerGap=$headerGap, " +
        "logoSize=$logoSize, " +
        "nameStyle=$nameStyle, " +
        "versionStyle=$versionStyle, " +
        "stepGap=$stepGap, " +
        "iconGap=$iconGap, " +
        "iconSize=$iconSize, " +
        "stepStyle=$stepStyle, " +
        "pendingColor=$pendingColor, " +
        "runningColor=$runningColor, " +
        "doneColor=$doneColor, " +
        "failedColor=$failedColor, " +
        "skippedColor=$skippedColor, " +
        "pendingIcon=$pendingIcon, " +
        "doneIcon=$doneIcon, " +
        "failedIcon=$failedIcon, " +
        "skippedIcon=$skippedIcon, " +
        "spinner=$spinner, " +
        "failure=$failure, " +
        "labels=$labels)"

    private fun values(): List<Any?> = listOf(
        size,
        scene,
        bandFraction,
        divider,
        background,
        content,
        muted,
        padding,
        gap,
        headerGap,
        logoSize,
        nameStyle,
        versionStyle,
        stepGap,
        iconGap,
        iconSize,
        stepStyle,
        pendingColor,
        runningColor,
        doneColor,
        failedColor,
        skippedColor,
        pendingIcon,
        doneIcon,
        failedIcon,
        skippedIcon,
        spinner,
        failure,
        labels,
    )
}
