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

package com.davils.kiro.ui.components.menubar.native

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut

private const val SEPARATOR: Char = '+'

private val symbols: Map<Char, ShortcutModifier> = mapOf(
    '⌘' to ShortcutModifier.Meta,
    '⌃' to ShortcutModifier.Ctrl,
    '⇧' to ShortcutModifier.Shift,
    '⌥' to ShortcutModifier.Alt,
)

private val names: Map<String, ShortcutModifier> = mapOf(
    "cmd" to ShortcutModifier.Meta,
    "command" to ShortcutModifier.Meta,
    "meta" to ShortcutModifier.Meta,
    "ctrl" to ShortcutModifier.Ctrl,
    "control" to ShortcutModifier.Ctrl,
    "shift" to ShortcutModifier.Shift,
    "alt" to ShortcutModifier.Alt,
    "option" to ShortcutModifier.Alt,
    "opt" to ShortcutModifier.Alt,
)

private val letters: List<Key> = listOf(
    Key.A, Key.B, Key.C, Key.D, Key.E, Key.F, Key.G, Key.H, Key.I, Key.J, Key.K, Key.L, Key.M,
    Key.N, Key.O, Key.P, Key.Q, Key.R, Key.S, Key.T, Key.U, Key.V, Key.W, Key.X, Key.Y, Key.Z,
)

private val digits: List<Key> = listOf(
    Key.Zero, Key.One, Key.Two, Key.Three, Key.Four, Key.Five, Key.Six, Key.Seven, Key.Eight, Key.Nine,
)

private val specials: Map<String, Key> = mapOf(
    "←" to Key.DirectionLeft,
    "→" to Key.DirectionRight,
    "↑" to Key.DirectionUp,
    "↓" to Key.DirectionDown,
    "left" to Key.DirectionLeft,
    "right" to Key.DirectionRight,
    "up" to Key.DirectionUp,
    "down" to Key.DirectionDown,
    "," to Key.Comma,
    "." to Key.Period,
)

private val keys: Map<String, Key> = keysOf(chars = 'a'..'z', values = letters) +
    keysOf(chars = '0'..'9', values = digits) +
    specials

internal object ShortcutParser {
    fun parse(text: String): KeyShortcut? {
        val trimmed = text.trim()
        val symbolic = trimmed.takeWhile { char -> char in symbols }
        val parts = trimmed.drop(symbolic.length).split(SEPARATOR)
        val tokens = parts.map(String::trim).filter(String::isNotEmpty)
        val key = tokens.lastOrNull()?.let { token -> keys[token.lowercase()] }
        val named = tokens.dropLast(1).map { token -> names[token.lowercase()] }
        val modifiers = symbolic.map(symbols::getValue) + named.filterNotNull()
        if (key == null || null in named || modifiers.isEmpty()) return null
        return KeyShortcut(
            key = key,
            ctrl = ShortcutModifier.Ctrl in modifiers,
            meta = ShortcutModifier.Meta in modifiers,
            alt = ShortcutModifier.Alt in modifiers,
            shift = ShortcutModifier.Shift in modifiers,
        )
    }
}

private fun keysOf(chars: CharRange, values: List<Key>): Map<String, Key> {
    val texts = chars.map(Char::toString)
    return texts.zip(values).toMap()
}
