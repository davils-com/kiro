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
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class ShortcutParserTest : FunSpec({
    context("symbols") {
        test("reads the command symbol") {
            ShortcutParser.parse("⌘B") shouldBe KeyShortcut(key = Key.B, meta = true)
        }

        test("reads stacked symbols in any order") {
            ShortcutParser.parse("⇧⌘P") shouldBe KeyShortcut(key = Key.P, meta = true, shift = true)
            ShortcutParser.parse("⌃⌥1") shouldBe KeyShortcut(key = Key.One, ctrl = true, alt = true)
        }

        test("reads the comma and the period") {
            ShortcutParser.parse("⌘,") shouldBe KeyShortcut(key = Key.Comma, meta = true)
            ShortcutParser.parse("⌘.") shouldBe KeyShortcut(key = Key.Period, meta = true)
        }
    }

    context("names") {
        test("reads modifiers joined by plus") {
            ShortcutParser.parse("Ctrl+Shift+T") shouldBe KeyShortcut(key = Key.T, ctrl = true, shift = true)
            ShortcutParser.parse("Cmd + Option + 9") shouldBe KeyShortcut(key = Key.Nine, meta = true, alt = true)
        }

        test("reads arrows as symbols and as names") {
            ShortcutParser.parse("Alt+←") shouldBe KeyShortcut(key = Key.DirectionLeft, alt = true)
            ShortcutParser.parse("Alt+→") shouldBe KeyShortcut(key = Key.DirectionRight, alt = true)
            ShortcutParser.parse("Ctrl+Up") shouldBe KeyShortcut(key = Key.DirectionUp, ctrl = true)
            ShortcutParser.parse("Ctrl+↓") shouldBe KeyShortcut(key = Key.DirectionDown, ctrl = true)
        }

        test("mixes symbols with named modifiers") {
            ShortcutParser.parse("⌘+Shift+,") shouldBe KeyShortcut(key = Key.Comma, meta = true, shift = true)
        }
    }

    context("unparseable") {
        test("gives no shortcut without a key") {
            ShortcutParser.parse("").shouldBeNull()
            ShortcutParser.parse("⌘").shouldBeNull()
            ShortcutParser.parse("Ctrl+").shouldBeNull()
        }

        test("gives no shortcut for an unknown key or modifier") {
            ShortcutParser.parse("⌘Delete").shouldBeNull()
            ShortcutParser.parse("Ctrl+F2").shouldBeNull()
            ShortcutParser.parse("Hyper+T").shouldBeNull()
        }

        test("gives no shortcut for a key without a modifier") {
            ShortcutParser.parse("T").shouldBeNull()
        }
    }
})
