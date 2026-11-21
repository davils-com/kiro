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

package com.davils.kiro.ui.motion.typewriter

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [TypewriterText] looks and types: its text, its caret and the rhythm of its keystrokes.
 *
 * @property textStyle the style of the text.
 * @property color the color of the text.
 * @property caretColor the color of the caret.
 * @property caretWidth the width of the caret.
 * @property caretHeight the height of the caret, as a fraction of the height of a line.
 * @property caretGap the space between the last character and the caret.
 * @property caretShape the shape of the caret.
 * @property caretPeriod how long one blink of the caret takes; the caret stays on while characters are
typed or deleted.
 * @property caretFade how long the caret takes to fade in or out within a blink.
 * @property caretEasing the easing of the fade of the caret.
 * @property hasCaret whether the caret is shown.
 * @property typeDelay the time between two typed characters on average.
 * @property deleteDelay the time between two deleted characters.
 * @property punctuationDelay the extra time after a character of [punctuation], as a person pauses there.
 * @property punctuation the characters after which the typing pauses for [punctuationDelay].
 * @property jitter how much the time between two typed characters varies, as a fraction of [typeDelay].
 * @property hold how long a [PauseStep] without a duration waits.
 * @property speed how fast the script plays; `1` plays it at its own pace.
 * @property isSpaceReserved whether the text takes the space of the whole phrase it types from the first
character on, so that the layout around it does not move while it types.
 * @property isAnimated whether the text types at all; without animation it shows the finished text.
 * @since 1.0.0
 */
@Immutable
public class TypewriterStyle(
    public val textStyle: TextStyle,
    public val color: Color,
    public val caretColor: Color,
    public val caretWidth: Dp,
    public val caretHeight: Float,
    public val caretGap: Dp,
    public val caretShape: Shape,
    public val caretPeriod: Duration,
    public val caretFade: Duration,
    public val caretEasing: Easing,
    public val hasCaret: Boolean,
    public val typeDelay: Duration,
    public val deleteDelay: Duration,
    public val punctuationDelay: Duration,
    public val punctuation: String,
    public val jitter: Float,
    public val hold: Duration,
    public val speed: Float,
    public val isSpaceReserved: Boolean,
    public val isAnimated: Boolean,
) {
    /**
     * Returns a copy of this [TypewriterStyle] with the given values replaced and every other value kept.
     *
     * @param textStyle the new value of [textStyle].
     * @param color the new value of [color].
     * @param caretColor the new value of [caretColor].
     * @param caretWidth the new value of [caretWidth].
     * @param caretHeight the new value of [caretHeight].
     * @param caretGap the new value of [caretGap].
     * @param caretShape the new value of [caretShape].
     * @param caretPeriod the new value of [caretPeriod].
     * @param caretFade the new value of [caretFade].
     * @param caretEasing the new value of [caretEasing].
     * @param hasCaret the new value of [hasCaret].
     * @param typeDelay the new value of [typeDelay].
     * @param deleteDelay the new value of [deleteDelay].
     * @param punctuationDelay the new value of [punctuationDelay].
     * @param punctuation the new value of [punctuation].
     * @param jitter the new value of [jitter].
     * @param hold the new value of [hold].
     * @param speed the new value of [speed].
     * @param isSpaceReserved the new value of [isSpaceReserved].
     * @param isAnimated the new value of [isAnimated].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        textStyle: TextStyle = this.textStyle,
        color: Color = this.color,
        caretColor: Color = this.caretColor,
        caretWidth: Dp = this.caretWidth,
        caretHeight: Float = this.caretHeight,
        caretGap: Dp = this.caretGap,
        caretShape: Shape = this.caretShape,
        caretPeriod: Duration = this.caretPeriod,
        caretFade: Duration = this.caretFade,
        caretEasing: Easing = this.caretEasing,
        hasCaret: Boolean = this.hasCaret,
        typeDelay: Duration = this.typeDelay,
        deleteDelay: Duration = this.deleteDelay,
        punctuationDelay: Duration = this.punctuationDelay,
        punctuation: String = this.punctuation,
        jitter: Float = this.jitter,
        hold: Duration = this.hold,
        speed: Float = this.speed,
        isSpaceReserved: Boolean = this.isSpaceReserved,
        isAnimated: Boolean = this.isAnimated,
    ): TypewriterStyle = TypewriterStyle(
        textStyle = textStyle,
        color = color,
        caretColor = caretColor,
        caretWidth = caretWidth,
        caretHeight = caretHeight,
        caretGap = caretGap,
        caretShape = caretShape,
        caretPeriod = caretPeriod,
        caretFade = caretFade,
        caretEasing = caretEasing,
        hasCaret = hasCaret,
        typeDelay = typeDelay,
        deleteDelay = deleteDelay,
        punctuationDelay = punctuationDelay,
        punctuation = punctuation,
        jitter = jitter,
        hold = hold,
        speed = speed,
        isSpaceReserved = isSpaceReserved,
        isAnimated = isAnimated,
    )

    override fun equals(other: Any?): Boolean = other is TypewriterStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TypewriterStyle(" +
        "textStyle=$textStyle, " +
        "color=$color, " +
        "caretColor=$caretColor, " +
        "caretWidth=$caretWidth, " +
        "caretHeight=$caretHeight, " +
        "caretGap=$caretGap, " +
        "caretShape=$caretShape, " +
        "caretPeriod=$caretPeriod, " +
        "caretFade=$caretFade, " +
        "caretEasing=$caretEasing, " +
        "hasCaret=$hasCaret, " +
        "typeDelay=$typeDelay, " +
        "deleteDelay=$deleteDelay, " +
        "punctuationDelay=$punctuationDelay, " +
        "punctuation=$punctuation, " +
        "jitter=$jitter, " +
        "hold=$hold, " +
        "speed=$speed, " +
        "isSpaceReserved=$isSpaceReserved, " +
        "isAnimated=$isAnimated)"

    private fun values(): List<Any?> = listOf(
        textStyle,
        color,
        caretColor,
        caretWidth,
        caretHeight,
        caretGap,
        caretShape,
        caretPeriod,
        caretFade,
        caretEasing,
        hasCaret,
        typeDelay,
        deleteDelay,
        punctuationDelay,
        punctuation,
        jitter,
        hold,
        speed,
        isSpaceReserved,
        isAnimated,
    )
}
