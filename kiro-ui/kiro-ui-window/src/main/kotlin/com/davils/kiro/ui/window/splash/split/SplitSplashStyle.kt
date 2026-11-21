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

package com.davils.kiro.ui.window.splash.split

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.components.spinner.SpinnerStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.motion.scene.Scene
import com.davils.kiro.ui.window.splash.SplashFailureStyle
import com.davils.kiro.ui.window.splash.SplashLabels

/**
 * How a [SplitSplash] looks: a panel with the application on one half and a generative scene with the vendor on
 * the other.
 *
 * @property size the recommended size of the [com.davils.kiro.ui.window.splash.SplashWindow] that shows the splash.
 * @property panel the color of the panel with the application.
 * @property divider the line between the panel and the background.
 * @property scene the scene the default background plays beside the panel.
 * @property content the color of the name, the edition and the current step.
 * @property muted the color of the subtitle, the version and the copyright.
 * @property padding the padding of the panel and of the vendor.
 * @property gap the space between the progress and the copyright.
 * @property headerGap the space between the logo and the name.
 * @property titleGap the space between the name and the subtitle.
 * @property stepGap the space between the current step, the failure and the version.
 * @property indicatorGap the space between the spinner and the current step.
 * @property logoSize the size of the logo.
 * @property nameStyle the text style of the name.
 * @property editionStyle the text style of the edition.
 * @property subtitleStyle the text style of the subtitle.
 * @property stepStyle the text style of the current step.
 * @property versionStyle the text style of the version.
 * @property footerStyle the text style of the copyright.
 * @property vendorStyle the text style of the vendor.
 * @property vendorColor the color of the vendor.
 * @property vendorAlpha the alpha of the vendor and its logo.
 * @property vendorSaturation the saturation of the logo of the vendor, from `0` for gray to `1` for its colors.
 * @property vendorRotation the angle in degrees the vendor and its logo are turned clockwise.
 * @property vendorGap the space between the vendor and its logo.
 * @property vendorLogoSize the size of the logo of the vendor.
 * @property spinner the style of the spinner beside the current step.
 * @property failure how the message and the actions of a failed start look.
 * @property labels the texts the splash shows and announces.
 * @since 1.0.0
 */
@Immutable
public class SplitSplashStyle(
    public val size: DpSize,
    public val panel: Color,
    public val divider: Border,
    public val scene: Scene,
    public val content: Color,
    public val muted: Color,
    public val padding: Dp,
    public val gap: Dp,
    public val headerGap: Dp,
    public val titleGap: Dp,
    public val stepGap: Dp,
    public val indicatorGap: Dp,
    public val logoSize: Dp,
    public val nameStyle: TextStyle,
    public val editionStyle: TextStyle,
    public val subtitleStyle: TextStyle,
    public val stepStyle: TextStyle,
    public val versionStyle: TextStyle,
    public val footerStyle: TextStyle,
    public val vendorStyle: TextStyle,
    public val vendorColor: Color,
    public val vendorAlpha: Float,
    public val vendorSaturation: Float,
    public val vendorRotation: Float,
    public val vendorGap: Dp,
    public val vendorLogoSize: Dp,
    public val spinner: SpinnerStyle,
    public val failure: SplashFailureStyle,
    public val labels: SplashLabels,
) {
    /**
     * Returns a copy of this [SplitSplashStyle] with the given values replaced and every other value kept.
     *
     * @param size the new value of [size].
     * @param panel the new value of [panel].
     * @param divider the new value of [divider].
     * @param scene the new value of [scene].
     * @param content the new value of [content].
     * @param muted the new value of [muted].
     * @param padding the new value of [padding].
     * @param gap the new value of [gap].
     * @param headerGap the new value of [headerGap].
     * @param titleGap the new value of [titleGap].
     * @param stepGap the new value of [stepGap].
     * @param indicatorGap the new value of [indicatorGap].
     * @param logoSize the new value of [logoSize].
     * @param nameStyle the new value of [nameStyle].
     * @param editionStyle the new value of [editionStyle].
     * @param subtitleStyle the new value of [subtitleStyle].
     * @param stepStyle the new value of [stepStyle].
     * @param versionStyle the new value of [versionStyle].
     * @param footerStyle the new value of [footerStyle].
     * @param vendorStyle the new value of [vendorStyle].
     * @param vendorColor the new value of [vendorColor].
     * @param vendorAlpha the new value of [vendorAlpha].
     * @param vendorSaturation the new value of [vendorSaturation].
     * @param vendorRotation the new value of [vendorRotation].
     * @param vendorGap the new value of [vendorGap].
     * @param vendorLogoSize the new value of [vendorLogoSize].
     * @param spinner the new value of [spinner].
     * @param failure the new value of [failure].
     * @param labels the new value of [labels].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        size: DpSize = this.size,
        panel: Color = this.panel,
        divider: Border = this.divider,
        scene: Scene = this.scene,
        content: Color = this.content,
        muted: Color = this.muted,
        padding: Dp = this.padding,
        gap: Dp = this.gap,
        headerGap: Dp = this.headerGap,
        titleGap: Dp = this.titleGap,
        stepGap: Dp = this.stepGap,
        indicatorGap: Dp = this.indicatorGap,
        logoSize: Dp = this.logoSize,
        nameStyle: TextStyle = this.nameStyle,
        editionStyle: TextStyle = this.editionStyle,
        subtitleStyle: TextStyle = this.subtitleStyle,
        stepStyle: TextStyle = this.stepStyle,
        versionStyle: TextStyle = this.versionStyle,
        footerStyle: TextStyle = this.footerStyle,
        vendorStyle: TextStyle = this.vendorStyle,
        vendorColor: Color = this.vendorColor,
        vendorAlpha: Float = this.vendorAlpha,
        vendorSaturation: Float = this.vendorSaturation,
        vendorRotation: Float = this.vendorRotation,
        vendorGap: Dp = this.vendorGap,
        vendorLogoSize: Dp = this.vendorLogoSize,
        spinner: SpinnerStyle = this.spinner,
        failure: SplashFailureStyle = this.failure,
        labels: SplashLabels = this.labels,
    ): SplitSplashStyle = SplitSplashStyle(
        size = size,
        panel = panel,
        divider = divider,
        scene = scene,
        content = content,
        muted = muted,
        padding = padding,
        gap = gap,
        headerGap = headerGap,
        titleGap = titleGap,
        stepGap = stepGap,
        indicatorGap = indicatorGap,
        logoSize = logoSize,
        nameStyle = nameStyle,
        editionStyle = editionStyle,
        subtitleStyle = subtitleStyle,
        stepStyle = stepStyle,
        versionStyle = versionStyle,
        footerStyle = footerStyle,
        vendorStyle = vendorStyle,
        vendorColor = vendorColor,
        vendorAlpha = vendorAlpha,
        vendorSaturation = vendorSaturation,
        vendorRotation = vendorRotation,
        vendorGap = vendorGap,
        vendorLogoSize = vendorLogoSize,
        spinner = spinner,
        failure = failure,
        labels = labels,
    )

    override fun equals(other: Any?): Boolean = other is SplitSplashStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SplitSplashStyle(" +
        "size=$size, " +
        "panel=$panel, " +
        "divider=$divider, " +
        "scene=$scene, " +
        "content=$content, " +
        "muted=$muted, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "headerGap=$headerGap, " +
        "titleGap=$titleGap, " +
        "stepGap=$stepGap, " +
        "indicatorGap=$indicatorGap, " +
        "logoSize=$logoSize, " +
        "nameStyle=$nameStyle, " +
        "editionStyle=$editionStyle, " +
        "subtitleStyle=$subtitleStyle, " +
        "stepStyle=$stepStyle, " +
        "versionStyle=$versionStyle, " +
        "footerStyle=$footerStyle, " +
        "vendorStyle=$vendorStyle, " +
        "vendorColor=$vendorColor, " +
        "vendorAlpha=$vendorAlpha, " +
        "vendorSaturation=$vendorSaturation, " +
        "vendorRotation=$vendorRotation, " +
        "vendorGap=$vendorGap, " +
        "vendorLogoSize=$vendorLogoSize, " +
        "spinner=$spinner, " +
        "failure=$failure, " +
        "labels=$labels)"

    private fun values(): List<Any?> = listOf(
        size,
        panel,
        divider,
        scene,
        content,
        muted,
        padding,
        gap,
        headerGap,
        titleGap,
        stepGap,
        indicatorGap,
        logoSize,
        nameStyle,
        editionStyle,
        subtitleStyle,
        stepStyle,
        versionStyle,
        footerStyle,
        vendorStyle,
        vendorColor,
        vendorAlpha,
        vendorSaturation,
        vendorRotation,
        vendorGap,
        vendorLogoSize,
        spinner,
        failure,
        labels,
    )
}
