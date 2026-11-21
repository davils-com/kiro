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

package com.davils.kiro.ui.motion.art.geometry

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.motion.quality.QualityLevel
import kotlin.time.Duration

/**
 * How a [BlueprintArt] looks: a drafting sheet: a fine grid with stronger major lines, crosshair marks where they
 * meet, corner brackets and, when the context can measure text, ruler labels, while a scan line slowly passes and
 * lights the marks it touches.
 *
 * @property cell the size of a cell of the fine grid.
 * @property major how many cells lie between two major lines.
 * @property lineWidth the width of the lines of the grid.
 * @property minorAlpha the alpha of the fine lines.
 * @property majorAlpha the alpha of the major lines.
 * @property mark the length of an arm of a crosshair mark.
 * @property markAlpha the alpha of a crosshair mark.
 * @property margin the distance of the corner brackets from the edges.
 * @property bracket the length of an arm of a corner bracket.
 * @property bracketWidth the width of a corner bracket.
 * @property bracketAlpha the alpha of a corner bracket.
 * @property isRulerShown whether the major lines carry ruler labels, when the context can measure text.
 * @property labelStyle the text style of a ruler label.
 * @property labelAlpha the alpha of a ruler label.
 * @property labelPadding the space between a ruler label and its line.
 * @property labelStep the value one major cell stands for on the ruler.
 * @property label the text of the ruler label of a value.
 * @property scanWidth the width of the scan line.
 * @property scanAlpha the alpha of the scan line.
 * @property scanBand the height of the soft band of light that trails the scan line.
 * @property bandAlpha the alpha of the band of light next to the scan line.
 * @property bandQuality the lowest quality that draws the band of light.
 * @property litAlpha the alpha of a crosshair mark that the scan line touches.
 * @property scanPeriod how long the scan line takes from the top to the bottom.
 * @property easing the easing of the travel of the scan line.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property line the color of the lines, or `null` for the ink of the palette of the frame.
 * @property accent the color of the accent, or `null` for the last accent of the palette of the frame.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class BlueprintArtStyle(
    public val cell: Dp,
    public val major: Int,
    public val lineWidth: Dp,
    public val minorAlpha: Float,
    public val majorAlpha: Float,
    public val mark: Dp,
    public val markAlpha: Float,
    public val margin: Dp,
    public val bracket: Dp,
    public val bracketWidth: Dp,
    public val bracketAlpha: Float,
    public val isRulerShown: Boolean,
    public val labelStyle: TextStyle,
    public val labelAlpha: Float,
    public val labelPadding: Dp,
    public val labelStep: Int,
    public val label: (Int) -> String,
    public val scanWidth: Dp,
    public val scanAlpha: Float,
    public val scanBand: Dp,
    public val bandAlpha: Float,
    public val bandQuality: QualityLevel,
    public val litAlpha: Float,
    public val scanPeriod: Duration,
    public val easing: Easing,
    public val salt: Int,
    public val line: Color?,
    public val accent: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [BlueprintArtStyle] with the given values replaced and every other value kept.
     *
     * @param cell the new value of [cell].
     * @param major the new value of [major].
     * @param lineWidth the new value of [lineWidth].
     * @param minorAlpha the new value of [minorAlpha].
     * @param majorAlpha the new value of [majorAlpha].
     * @param mark the new value of [mark].
     * @param markAlpha the new value of [markAlpha].
     * @param margin the new value of [margin].
     * @param bracket the new value of [bracket].
     * @param bracketWidth the new value of [bracketWidth].
     * @param bracketAlpha the new value of [bracketAlpha].
     * @param isRulerShown the new value of [isRulerShown].
     * @param labelStyle the new value of [labelStyle].
     * @param labelAlpha the new value of [labelAlpha].
     * @param labelPadding the new value of [labelPadding].
     * @param labelStep the new value of [labelStep].
     * @param label the new value of [label].
     * @param scanWidth the new value of [scanWidth].
     * @param scanAlpha the new value of [scanAlpha].
     * @param scanBand the new value of [scanBand].
     * @param bandAlpha the new value of [bandAlpha].
     * @param bandQuality the new value of [bandQuality].
     * @param litAlpha the new value of [litAlpha].
     * @param scanPeriod the new value of [scanPeriod].
     * @param easing the new value of [easing].
     * @param salt the new value of [salt].
     * @param line the new value of [line].
     * @param accent the new value of [accent].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        cell: Dp = this.cell,
        major: Int = this.major,
        lineWidth: Dp = this.lineWidth,
        minorAlpha: Float = this.minorAlpha,
        majorAlpha: Float = this.majorAlpha,
        mark: Dp = this.mark,
        markAlpha: Float = this.markAlpha,
        margin: Dp = this.margin,
        bracket: Dp = this.bracket,
        bracketWidth: Dp = this.bracketWidth,
        bracketAlpha: Float = this.bracketAlpha,
        isRulerShown: Boolean = this.isRulerShown,
        labelStyle: TextStyle = this.labelStyle,
        labelAlpha: Float = this.labelAlpha,
        labelPadding: Dp = this.labelPadding,
        labelStep: Int = this.labelStep,
        label: (Int) -> String = this.label,
        scanWidth: Dp = this.scanWidth,
        scanAlpha: Float = this.scanAlpha,
        scanBand: Dp = this.scanBand,
        bandAlpha: Float = this.bandAlpha,
        bandQuality: QualityLevel = this.bandQuality,
        litAlpha: Float = this.litAlpha,
        scanPeriod: Duration = this.scanPeriod,
        easing: Easing = this.easing,
        salt: Int = this.salt,
        line: Color? = this.line,
        accent: Color? = this.accent,
        background: Color? = this.background,
    ): BlueprintArtStyle = BlueprintArtStyle(
        cell = cell,
        major = major,
        lineWidth = lineWidth,
        minorAlpha = minorAlpha,
        majorAlpha = majorAlpha,
        mark = mark,
        markAlpha = markAlpha,
        margin = margin,
        bracket = bracket,
        bracketWidth = bracketWidth,
        bracketAlpha = bracketAlpha,
        isRulerShown = isRulerShown,
        labelStyle = labelStyle,
        labelAlpha = labelAlpha,
        labelPadding = labelPadding,
        labelStep = labelStep,
        label = label,
        scanWidth = scanWidth,
        scanAlpha = scanAlpha,
        scanBand = scanBand,
        bandAlpha = bandAlpha,
        bandQuality = bandQuality,
        litAlpha = litAlpha,
        scanPeriod = scanPeriod,
        easing = easing,
        salt = salt,
        line = line,
        accent = accent,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is BlueprintArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "BlueprintArtStyle(" +
        "cell=$cell, " +
        "major=$major, " +
        "lineWidth=$lineWidth, " +
        "minorAlpha=$minorAlpha, " +
        "majorAlpha=$majorAlpha, " +
        "mark=$mark, " +
        "markAlpha=$markAlpha, " +
        "margin=$margin, " +
        "bracket=$bracket, " +
        "bracketWidth=$bracketWidth, " +
        "bracketAlpha=$bracketAlpha, " +
        "isRulerShown=$isRulerShown, " +
        "labelStyle=$labelStyle, " +
        "labelAlpha=$labelAlpha, " +
        "labelPadding=$labelPadding, " +
        "labelStep=$labelStep, " +
        "label=$label, " +
        "scanWidth=$scanWidth, " +
        "scanAlpha=$scanAlpha, " +
        "scanBand=$scanBand, " +
        "bandAlpha=$bandAlpha, " +
        "bandQuality=$bandQuality, " +
        "litAlpha=$litAlpha, " +
        "scanPeriod=$scanPeriod, " +
        "easing=$easing, " +
        "salt=$salt, " +
        "line=${line ?: "null"}, " +
        "accent=${accent ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        cell,
        major,
        lineWidth,
        minorAlpha,
        majorAlpha,
        mark,
        markAlpha,
        margin,
        bracket,
        bracketWidth,
        bracketAlpha,
        isRulerShown,
        labelStyle,
        labelAlpha,
        labelPadding,
        labelStep,
        label,
        scanWidth,
        scanAlpha,
        scanBand,
        bandAlpha,
        bandQuality,
        litAlpha,
        scanPeriod,
        easing,
        salt,
        line,
        accent,
        background,
    )
}
