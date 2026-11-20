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

package com.davils.kiro.ui.components.menu

import com.davils.kiro.ui.components.primitive.roving.RovingMove
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

private const val LAST: Int = 4

class MenuNavigationTest : FunSpec({
    val scope = MenuScopeImpl()
    scope.label(text = "Account")
    scope.item(label = "Profile", onClick = {})
    scope.separator()
    scope.item(label = "Billing", onClick = {}, enabled = false)
    scope.checkbox(label = "Bookmarks", checked = true, onCheckedChange = {})
    val entries = scope.entries

    context("highlight") {
        test("enters at the ends and skips disabled entries") {
            highlightAfter(entries = entries, current = null, move = RovingMove.Next) shouldBe 1
            highlightAfter(entries = entries, current = null, move = RovingMove.Last) shouldBe LAST
            highlightAfter(entries = entries, current = 1, move = RovingMove.Next) shouldBe LAST
            highlightAfter(entries = entries, current = LAST, move = RovingMove.Next) shouldBe 1
            highlightAfter(entries = listOf(MenuEntry.Separator), current = null, move = RovingMove.Next).shouldBeNull()
        }
    }

    context("typeahead") {
        test("jumps to the next enabled entry with the typed letter") {
            typeaheadTarget(entries = entries, current = null, char = 'b') shouldBe LAST
            typeaheadTarget(entries = entries, current = 1, char = 'P') shouldBe 1
            typeaheadTarget(entries = entries, current = null, char = 'z').shouldBeNull()
        }
    }
})
