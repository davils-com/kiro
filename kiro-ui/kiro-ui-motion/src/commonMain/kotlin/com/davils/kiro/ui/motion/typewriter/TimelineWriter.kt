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

import com.davils.kiro.ui.motion.noise.rangeOf
import kotlin.time.Duration

internal class TimelineWriter(private val style: TypewriterStyle, private val seed: Int, firstTarget: String) {
    private val text: StringBuilder = StringBuilder()
    private val written: MutableList<TypewriterMark> =
        mutableListOf(TypewriterMark(at = 0L, text = "", target = firstTarget))
    private var target: String = firstTarget
    private var keystrokes: Int = 0
    private var previous: Char? = null

    var elapsed: Long = 0L
        private set

    val marks: List<TypewriterMark> get() = written.toList()

    val current: String get() = text.toString()

    fun play(step: TypewriterStep) {
        when (step) {
            is TypeStep -> type(step.text)
            is PauseStep -> elapsed += millisOf(step.duration ?: style.hold)
            is DeleteStep -> repeat(times = step.count ?: text.length) { delete() }
        }
    }

    private fun type(phrase: String) {
        target = current + phrase
        var index = 0
        while (index < phrase.length) {
            val length = glyphLength(text = phrase, index = index)
            text.append(phrase, index, index + length)
            elapsed += delayOf(previous = previous)
            previous = phrase[index + length - 1]
            keystrokes += 1
            note()
            index += length
        }
    }

    private fun delete() {
        if (text.isEmpty()) return
        val length = lastGlyphLength(text = text)
        text.setLength(text.length - length)
        previous = null
        elapsed += millisOf(style.deleteDelay)
        note()
    }

    private fun delayOf(previous: Char?): Long {
        val wobble = 1f + style.jitter * rangeOf(from = -1f, to = 1f, x = keystrokes, seed = seed)
        val typed = (style.typeDelay.inWholeMilliseconds * wobble).toLong()
        val isPunctuation = previous != null && previous in style.punctuation
        val pause = millisOf(style.punctuationDelay).takeIf { isPunctuation } ?: 0L
        return (typed + pause).coerceAtLeast(minimumValue = 0L)
    }

    private fun note() {
        written += TypewriterMark(at = elapsed, text = current, target = target)
    }
}

private fun millisOf(duration: Duration): Long = duration.inWholeMilliseconds.coerceAtLeast(minimumValue = 0L)

internal fun glyphLength(text: CharSequence, index: Int): Int {
    val isPair = text[index].isHighSurrogate() && index + 1 < text.length && text[index + 1].isLowSurrogate()
    if (isPair) return 2
    return 1
}

internal fun lastGlyphLength(text: CharSequence): Int {
    val last = text.length - 1
    val isPair = text[last].isLowSurrogate() && last > 0 && text[last - 1].isHighSurrogate()
    if (isPair) return 2
    return 1
}
