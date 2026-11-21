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

import androidx.compose.animation.core.withInfiniteAnimationFrameNanos
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.core.Theme

private const val NANOS_PER_MILLI: Double = 1_000_000.0

/**
 * Types a [script] character by character, with a blinking caret, pauses and deletions, such as the phrases of a
 * welcome screen.
 *
 * The keystrokes vary a little in their rhythm, drawn from [seed] so that the same seed types the same way, and pause
 * after punctuation. Accessibility services read the whole phrase the text is typing, once, instead of every
 * character. While the theme asks for reduced motion, or [TypewriterStyle.isAnimated] is off, the text shows the
 * finished phrase at once: the last text of a script that ends, or the first phrase of one that repeats.
 *
 * ```kotlin
 * TypewriterText(script = typewriterCycle(phrases = listOf("Design.", "Build.", "Ship.")))
 * ```
 *
 * @param script what the text types.
 * @param modifier the modifier of the text.
 * @param style how the text looks and types.
 * @param seed the seed of the rhythm of the keystrokes.
 * @param onFinished called once when a script that does not repeat has played to its end.
 * @since 1.0.0
 */
@Composable
public fun TypewriterText(
    script: TypewriterScript,
    modifier: Modifier = Modifier,
    style: TypewriterStyle = typewriterStyle(),
    seed: Int = 0,
    onFinished: () -> Unit = {},
) {
    val timeline = remember(script, style, seed) { timelineOf(script = script, style = style, seed = seed) }
    val isAnimated = style.isAnimated && !Theme.current.motion.isReduced
    val elapsed = remember(timeline) { mutableLongStateOf(0L) }
    val finished = rememberUpdatedState(onFinished)
    LaunchedEffect(timeline, isAnimated, style.speed) {
        play(
            timeline = timeline,
            isAnimated = isAnimated,
            speed = style.speed,
            elapsed = elapsed,
            onFinished = finished,
        )
    }
    val mark by remember(timeline, isAnimated) {
        derivedStateOf { markOf(timeline = timeline, isAnimated = isAnimated, elapsed = elapsed.longValue) }
    }
    val layout = remember { mutableStateOf<TextLayoutResult?>(null) }
    val textStyle = style.textStyle.copy(color = style.color)
    val reserve = style.caretGap + style.caretWidth
    Box(modifier = modifier.clearAndSetSemantics { text = AnnotatedString(mark.target) }) {
        if (style.isSpaceReserved) {
            BasicText(
                text = mark.target,
                modifier = Modifier
                    .padding(end = reserve)
                    .drawWithContent {},
                style = textStyle,
            )
        }
        BasicText(
            text = mark.text,
            modifier = Modifier
                .padding(end = reserve)
                .drawWithContent {
                    drawContent()
                    val alpha = caretAlpha(
                        style = style,
                        timeline = timeline,
                        mark = mark,
                        isAnimated = isAnimated,
                        elapsed = elapsed.longValue,
                    )
                    drawCaret(style = style, layout = layout.value, offset = mark.text.length, alpha = alpha)
                },
            style = textStyle,
            onTextLayout = { result -> layout.value = result },
        )
    }
}

private suspend fun play(
    timeline: TypewriterTimeline,
    isAnimated: Boolean,
    speed: Float,
    elapsed: MutableLongState,
    onFinished: State<() -> Unit>,
) {
    var isReported = timeline.isRepeating
    if (!isAnimated) return reportOnce(isReported = isReported, onFinished = onFinished)
    var last = -1L
    var played = elapsed.longValue * NANOS_PER_MILLI
    while (true) {
        withInfiniteAnimationFrameNanos { nanos ->
            if (last >= 0L) played += (nanos - last) * speed.toDouble()
            last = nanos
            elapsed.longValue = (played / NANOS_PER_MILLI).toLong()
        }
        if (!isReported && timeline.isFinished(elapsed.longValue)) {
            isReported = true
            onFinished.value()
        }
    }
}

private fun reportOnce(isReported: Boolean, onFinished: State<() -> Unit>) {
    if (!isReported) onFinished.value()
}

private fun markOf(timeline: TypewriterTimeline, isAnimated: Boolean, elapsed: Long): TypewriterMark {
    if (!isAnimated) return TypewriterMark(at = 0L, text = timeline.resting, target = timeline.resting)
    return timeline.markAt(timeline.positionOf(elapsed))
}

private fun caretAlpha(
    style: TypewriterStyle,
    timeline: TypewriterTimeline,
    mark: TypewriterMark,
    isAnimated: Boolean,
    elapsed: Long,
): Float {
    if (!style.hasCaret) return 0f
    return blinkingAlpha(style = style, timeline = timeline, mark = mark, isAnimated = isAnimated, elapsed = elapsed)
}

private fun blinkingAlpha(
    style: TypewriterStyle,
    timeline: TypewriterTimeline,
    mark: TypewriterMark,
    isAnimated: Boolean,
    elapsed: Long,
): Float {
    if (!isAnimated) return 1f
    return caretAlphaOf(
        sinceChange = timeline.positionOf(elapsed) - mark.at,
        period = style.caretPeriod.inWholeMilliseconds,
        fade = style.caretFade.inWholeMilliseconds,
        easing = style.caretEasing,
    )
}

private fun ContentDrawScope.drawCaret(style: TypewriterStyle, layout: TextLayoutResult?, offset: Int, alpha: Float) {
    if (layout == null || alpha <= 0f) return
    val cursor = layout.getCursorRect(offset.coerceAtMost(maximumValue = layout.layoutInput.text.length))
    val width = style.caretWidth.toPx()
    val height = cursor.height * style.caretHeight
    val left = caretLeftOf(cursor = cursor, gap = style.caretGap.toPx(), width = width, direction = layoutDirection)
    val outline = style.caretShape.createOutline(size = Size(width = width, height = height), layoutDirection, this)
    translate(left = left, top = cursor.top + (cursor.height - height) / 2f) {
        drawOutline(outline = outline, color = style.caretColor, alpha = alpha)
    }
}

private fun caretLeftOf(cursor: Rect, gap: Float, width: Float, direction: LayoutDirection): Float {
    if (direction == LayoutDirection.Rtl) return cursor.left - gap - width
    return cursor.left + gap
}
