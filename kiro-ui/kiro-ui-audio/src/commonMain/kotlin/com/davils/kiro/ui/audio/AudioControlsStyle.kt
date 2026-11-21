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

package com.davils.kiro.ui.audio

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.motion.Motion
import kotlin.time.Duration

/**
 * How the controls of the audio players look and move: the cover, the track information, the transport buttons,
 * the scrubber, the volume, the visualizer and the backdrop tinted by the cover. Every player shares this style; its
 * own layout comes from the style of the player.
 *
 * @property gap the space between the controls.
 * @property titleStyle the text style of a title.
 * @property artistStyle the text style of an artist.
 * @property timeStyle the text style of a time.
 * @property secondaryAlpha the alpha of the content color for secondary texts such as the artist and the times.
 * @property trackHeight the thickness of the track of the scrubber and of the volume slider.
 * @property trackAlpha the alpha of the content color for the empty track of the scrubber and the volume slider.
 * @property bufferAlpha the alpha of the content color for the loaded part of the scrubber.
 * @property thumbSize the size of the thumb of the scrubber and of the volume slider.
 * @property thumbActiveSize the size of the thumb of the scrubber while it is hovered, and the height of the scrubber.
 * @property seekStep how far an arrow key moves the position.
 * @property iconSize the size of the icons of the buttons.
 * @property actionSize the size of an icon button.
 * @property hoverAlpha the alpha of the content color for the background of a hovered icon button.
 * @property idleAlpha the alpha of the icon of an icon button that is neither hovered nor active.
 * @property playSize the size of the play and pause button.
 * @property pressedScale the scale of the play and pause button while it is pressed.
 * @property playContainer the color of the play and pause button when it stands alone.
 * @property playContent the color of the glyph of the play and pause button when it stands alone.
 * @property coverRadius the corner radius of a cover.
 * @property coverElevation the elevation of a cover while the track plays.
 * @property pausedScale the scale of a cover while the track is paused.
 * @property glowAlpha the alpha of the dominant color of the cover for its shadow.
 * @property placeholderIconSize the size of the icon in place of a missing cover.
 * @property backdropBlur the blur of the cover behind a backdrop.
 * @property backdropScrim the share of the muted color of the cover laid over a backdrop.
 * @property backdropSaturation the saturation of the cover behind a backdrop.
 * @property backdropTopAlpha the alpha of the dominant color at the top of the shade of a backdrop.
 * @property backdropBottomAlpha the alpha of the muted color at the bottom of the shade of a backdrop.
 * @property breathScale the smallest scale of the slowly breathing cover behind a backdrop.
 * @property breathDuration the duration of one breath of the cover behind a backdrop.
 * @property visualizerSize the size of the visualizer.
 * @property visualizerBars the number of bars of the visualizer.
 * @property visualizerRest the height of a bar of the visualizer at rest, as a fraction of its size.
 * @property visualizerSpeed the speed of the waves of the visualizer per millisecond.
 * @property visualizerSpread the phase between two bars of the visualizer.
 * @property visualizerWave how much faster each further bar of the visualizer moves.
 * @property visualizerGap the gap between two bars of the visualizer, as a share of the width of a bar.
 * @property volumeWidth the width of the volume slider.
 * @property volumeWheelStep how much one step of the mouse wheel changes the volume.
 * @property volumeQuiet the volume up to which the quiet icon shows.
 * @property volumeMedium the volume up to which the medium icon shows.
 * @property lightContent the content color on a dark cover.
 * @property darkContent the content color on a light cover.
 * @property mutedDepth how far the muted color of a cover is mixed towards dark content.
 * @property vibrantSaturation the saturation above which a color of a cover counts as vibrant.
 * @property slideShare how far a new track slides in, as a share of the width of the track information.
 * @property motion the durations, easings and springs of every animation of the controls.
 * @property icons the icons of the controls.
 * @property labels the texts of the controls.
 * @property time writes a position or a length, such as 3:07.
 * @since 1.0.0
 */
@Immutable
public class AudioControlsStyle(
    public val gap: Dp,
    public val titleStyle: TextStyle,
    public val artistStyle: TextStyle,
    public val timeStyle: TextStyle,
    public val secondaryAlpha: Float,
    public val trackHeight: Dp,
    public val trackAlpha: Float,
    public val bufferAlpha: Float,
    public val thumbSize: Dp,
    public val thumbActiveSize: Dp,
    public val seekStep: Duration,
    public val iconSize: Dp,
    public val actionSize: Dp,
    public val hoverAlpha: Float,
    public val idleAlpha: Float,
    public val playSize: Dp,
    public val pressedScale: Float,
    public val playContainer: Color,
    public val playContent: Color,
    public val coverRadius: Dp,
    public val coverElevation: Dp,
    public val pausedScale: Float,
    public val glowAlpha: Float,
    public val placeholderIconSize: Dp,
    public val backdropBlur: Dp,
    public val backdropScrim: Float,
    public val backdropSaturation: Float,
    public val backdropTopAlpha: Float,
    public val backdropBottomAlpha: Float,
    public val breathScale: Float,
    public val breathDuration: Duration,
    public val visualizerSize: Dp,
    public val visualizerBars: Int,
    public val visualizerRest: Float,
    public val visualizerSpeed: Float,
    public val visualizerSpread: Float,
    public val visualizerWave: Float,
    public val visualizerGap: Float,
    public val volumeWidth: Dp,
    public val volumeWheelStep: Float,
    public val volumeQuiet: Float,
    public val volumeMedium: Float,
    public val lightContent: Color,
    public val darkContent: Color,
    public val mutedDepth: Float,
    public val vibrantSaturation: Float,
    public val slideShare: Float,
    public val motion: Motion,
    public val icons: AudioIcons,
    public val labels: AudioLabels,
    public val time: (Duration) -> String,
) {
    /**
     * Returns a copy of this [AudioControlsStyle] with the given values replaced and every other value kept.
     *
     * @param gap the new value of [gap].
     * @param titleStyle the new value of [titleStyle].
     * @param artistStyle the new value of [artistStyle].
     * @param timeStyle the new value of [timeStyle].
     * @param secondaryAlpha the new value of [secondaryAlpha].
     * @param trackHeight the new value of [trackHeight].
     * @param trackAlpha the new value of [trackAlpha].
     * @param bufferAlpha the new value of [bufferAlpha].
     * @param thumbSize the new value of [thumbSize].
     * @param thumbActiveSize the new value of [thumbActiveSize].
     * @param seekStep the new value of [seekStep].
     * @param iconSize the new value of [iconSize].
     * @param actionSize the new value of [actionSize].
     * @param hoverAlpha the new value of [hoverAlpha].
     * @param idleAlpha the new value of [idleAlpha].
     * @param playSize the new value of [playSize].
     * @param pressedScale the new value of [pressedScale].
     * @param playContainer the new value of [playContainer].
     * @param playContent the new value of [playContent].
     * @param coverRadius the new value of [coverRadius].
     * @param coverElevation the new value of [coverElevation].
     * @param pausedScale the new value of [pausedScale].
     * @param glowAlpha the new value of [glowAlpha].
     * @param placeholderIconSize the new value of [placeholderIconSize].
     * @param backdropBlur the new value of [backdropBlur].
     * @param backdropScrim the new value of [backdropScrim].
     * @param backdropSaturation the new value of [backdropSaturation].
     * @param backdropTopAlpha the new value of [backdropTopAlpha].
     * @param backdropBottomAlpha the new value of [backdropBottomAlpha].
     * @param breathScale the new value of [breathScale].
     * @param breathDuration the new value of [breathDuration].
     * @param visualizerSize the new value of [visualizerSize].
     * @param visualizerBars the new value of [visualizerBars].
     * @param visualizerRest the new value of [visualizerRest].
     * @param visualizerSpeed the new value of [visualizerSpeed].
     * @param visualizerSpread the new value of [visualizerSpread].
     * @param visualizerWave the new value of [visualizerWave].
     * @param visualizerGap the new value of [visualizerGap].
     * @param volumeWidth the new value of [volumeWidth].
     * @param volumeWheelStep the new value of [volumeWheelStep].
     * @param volumeQuiet the new value of [volumeQuiet].
     * @param volumeMedium the new value of [volumeMedium].
     * @param lightContent the new value of [lightContent].
     * @param darkContent the new value of [darkContent].
     * @param mutedDepth the new value of [mutedDepth].
     * @param vibrantSaturation the new value of [vibrantSaturation].
     * @param slideShare the new value of [slideShare].
     * @param motion the new value of [motion].
     * @param icons the new value of [icons].
     * @param labels the new value of [labels].
     * @param time the new value of [time].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        gap: Dp = this.gap,
        titleStyle: TextStyle = this.titleStyle,
        artistStyle: TextStyle = this.artistStyle,
        timeStyle: TextStyle = this.timeStyle,
        secondaryAlpha: Float = this.secondaryAlpha,
        trackHeight: Dp = this.trackHeight,
        trackAlpha: Float = this.trackAlpha,
        bufferAlpha: Float = this.bufferAlpha,
        thumbSize: Dp = this.thumbSize,
        thumbActiveSize: Dp = this.thumbActiveSize,
        seekStep: Duration = this.seekStep,
        iconSize: Dp = this.iconSize,
        actionSize: Dp = this.actionSize,
        hoverAlpha: Float = this.hoverAlpha,
        idleAlpha: Float = this.idleAlpha,
        playSize: Dp = this.playSize,
        pressedScale: Float = this.pressedScale,
        playContainer: Color = this.playContainer,
        playContent: Color = this.playContent,
        coverRadius: Dp = this.coverRadius,
        coverElevation: Dp = this.coverElevation,
        pausedScale: Float = this.pausedScale,
        glowAlpha: Float = this.glowAlpha,
        placeholderIconSize: Dp = this.placeholderIconSize,
        backdropBlur: Dp = this.backdropBlur,
        backdropScrim: Float = this.backdropScrim,
        backdropSaturation: Float = this.backdropSaturation,
        backdropTopAlpha: Float = this.backdropTopAlpha,
        backdropBottomAlpha: Float = this.backdropBottomAlpha,
        breathScale: Float = this.breathScale,
        breathDuration: Duration = this.breathDuration,
        visualizerSize: Dp = this.visualizerSize,
        visualizerBars: Int = this.visualizerBars,
        visualizerRest: Float = this.visualizerRest,
        visualizerSpeed: Float = this.visualizerSpeed,
        visualizerSpread: Float = this.visualizerSpread,
        visualizerWave: Float = this.visualizerWave,
        visualizerGap: Float = this.visualizerGap,
        volumeWidth: Dp = this.volumeWidth,
        volumeWheelStep: Float = this.volumeWheelStep,
        volumeQuiet: Float = this.volumeQuiet,
        volumeMedium: Float = this.volumeMedium,
        lightContent: Color = this.lightContent,
        darkContent: Color = this.darkContent,
        mutedDepth: Float = this.mutedDepth,
        vibrantSaturation: Float = this.vibrantSaturation,
        slideShare: Float = this.slideShare,
        motion: Motion = this.motion,
        icons: AudioIcons = this.icons,
        labels: AudioLabels = this.labels,
        time: (Duration) -> String = this.time,
    ): AudioControlsStyle = AudioControlsStyle(
        gap = gap,
        titleStyle = titleStyle,
        artistStyle = artistStyle,
        timeStyle = timeStyle,
        secondaryAlpha = secondaryAlpha,
        trackHeight = trackHeight,
        trackAlpha = trackAlpha,
        bufferAlpha = bufferAlpha,
        thumbSize = thumbSize,
        thumbActiveSize = thumbActiveSize,
        seekStep = seekStep,
        iconSize = iconSize,
        actionSize = actionSize,
        hoverAlpha = hoverAlpha,
        idleAlpha = idleAlpha,
        playSize = playSize,
        pressedScale = pressedScale,
        playContainer = playContainer,
        playContent = playContent,
        coverRadius = coverRadius,
        coverElevation = coverElevation,
        pausedScale = pausedScale,
        glowAlpha = glowAlpha,
        placeholderIconSize = placeholderIconSize,
        backdropBlur = backdropBlur,
        backdropScrim = backdropScrim,
        backdropSaturation = backdropSaturation,
        backdropTopAlpha = backdropTopAlpha,
        backdropBottomAlpha = backdropBottomAlpha,
        breathScale = breathScale,
        breathDuration = breathDuration,
        visualizerSize = visualizerSize,
        visualizerBars = visualizerBars,
        visualizerRest = visualizerRest,
        visualizerSpeed = visualizerSpeed,
        visualizerSpread = visualizerSpread,
        visualizerWave = visualizerWave,
        visualizerGap = visualizerGap,
        volumeWidth = volumeWidth,
        volumeWheelStep = volumeWheelStep,
        volumeQuiet = volumeQuiet,
        volumeMedium = volumeMedium,
        lightContent = lightContent,
        darkContent = darkContent,
        mutedDepth = mutedDepth,
        vibrantSaturation = vibrantSaturation,
        slideShare = slideShare,
        motion = motion,
        icons = icons,
        labels = labels,
        time = time,
    )

    override fun equals(other: Any?): Boolean = other is AudioControlsStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AudioControlsStyle(" +
        "gap=$gap, " +
        "titleStyle=$titleStyle, " +
        "artistStyle=$artistStyle, " +
        "timeStyle=$timeStyle, " +
        "secondaryAlpha=$secondaryAlpha, " +
        "trackHeight=$trackHeight, " +
        "trackAlpha=$trackAlpha, " +
        "bufferAlpha=$bufferAlpha, " +
        "thumbSize=$thumbSize, " +
        "thumbActiveSize=$thumbActiveSize, " +
        "seekStep=$seekStep, " +
        "iconSize=$iconSize, " +
        "actionSize=$actionSize, " +
        "hoverAlpha=$hoverAlpha, " +
        "idleAlpha=$idleAlpha, " +
        "playSize=$playSize, " +
        "pressedScale=$pressedScale, " +
        "playContainer=$playContainer, " +
        "playContent=$playContent, " +
        "coverRadius=$coverRadius, " +
        "coverElevation=$coverElevation, " +
        "pausedScale=$pausedScale, " +
        "glowAlpha=$glowAlpha, " +
        "placeholderIconSize=$placeholderIconSize, " +
        "backdropBlur=$backdropBlur, " +
        "backdropScrim=$backdropScrim, " +
        "backdropSaturation=$backdropSaturation, " +
        "backdropTopAlpha=$backdropTopAlpha, " +
        "backdropBottomAlpha=$backdropBottomAlpha, " +
        "breathScale=$breathScale, " +
        "breathDuration=$breathDuration, " +
        "visualizerSize=$visualizerSize, " +
        "visualizerBars=$visualizerBars, " +
        "visualizerRest=$visualizerRest, " +
        "visualizerSpeed=$visualizerSpeed, " +
        "visualizerSpread=$visualizerSpread, " +
        "visualizerWave=$visualizerWave, " +
        "visualizerGap=$visualizerGap, " +
        "volumeWidth=$volumeWidth, " +
        "volumeWheelStep=$volumeWheelStep, " +
        "volumeQuiet=$volumeQuiet, " +
        "volumeMedium=$volumeMedium, " +
        "lightContent=$lightContent, " +
        "darkContent=$darkContent, " +
        "mutedDepth=$mutedDepth, " +
        "vibrantSaturation=$vibrantSaturation, " +
        "slideShare=$slideShare, " +
        "motion=$motion, " +
        "icons=$icons, " +
        "labels=$labels, " +
        "time=$time)"

    private fun values(): List<Any?> = listOf(
        gap,
        titleStyle,
        artistStyle,
        timeStyle,
        secondaryAlpha,
        trackHeight,
        trackAlpha,
        bufferAlpha,
        thumbSize,
        thumbActiveSize,
        seekStep,
        iconSize,
        actionSize,
        hoverAlpha,
        idleAlpha,
        playSize,
        pressedScale,
        playContainer,
        playContent,
        coverRadius,
        coverElevation,
        pausedScale,
        glowAlpha,
        placeholderIconSize,
        backdropBlur,
        backdropScrim,
        backdropSaturation,
        backdropTopAlpha,
        backdropBottomAlpha,
        breathScale,
        breathDuration,
        visualizerSize,
        visualizerBars,
        visualizerRest,
        visualizerSpeed,
        visualizerSpread,
        visualizerWave,
        visualizerGap,
        volumeWidth,
        volumeWheelStep,
        volumeQuiet,
        volumeMedium,
        lightContent,
        darkContent,
        mutedDepth,
        vibrantSaturation,
        slideShare,
        motion,
        icons,
        labels,
        time,
    )
}
