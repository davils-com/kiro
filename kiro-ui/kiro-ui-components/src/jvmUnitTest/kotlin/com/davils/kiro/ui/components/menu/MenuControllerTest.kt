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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val SUB: Int = 0
private const val NEXT: Int = 1
private val subPanel = Rect(left = 100f, top = 0f, right = 200f, bottom = 100f)
private val onRow = Offset(x = 50f, y = 10f)
private val towardsPanel = Offset(x = 90f, y = 20f)
private val awayFromPanel = Offset(x = 50f, y = 40f)

private fun entries(): List<MenuEntry> {
    val scope = MenuScopeImpl()
    scope.sub(label = "More") {}
    scope.item(label = "Next", onClick = {})
    return scope.entries
}

private fun opened(): MenuController {
    val controller = MenuController()
    val menu = entries()
    controller.hover(entries = menu, index = SUB)
    controller.settle()
    controller.subBounds = subPanel
    controller.track(entries = menu, point = onRow)
    return controller
}

class MenuControllerTest : FunSpec({
    context("hover intent") {
        test("wants a sub menu on hover and opens it once it settles") {
            val controller = MenuController()
            controller.hover(entries = entries(), index = SUB)

            controller.wantedSub shouldBe SUB
            controller.openSub shouldBe null
            controller.settle()
            controller.openSub shouldBe SUB
        }

        test("holds back the next row while the pointer heads for the sub menu") {
            val controller = opened()
            controller.track(entries = entries(), point = towardsPanel)
            controller.hover(entries = entries(), index = NEXT)

            controller.highlighted shouldBe SUB
            controller.deferred shouldBe NEXT
        }

        test("moves on as soon as the pointer leaves the safe area") {
            val controller = opened()
            controller.track(entries = entries(), point = towardsPanel)
            controller.hover(entries = entries(), index = NEXT)
            controller.track(entries = entries(), point = awayFromPanel)

            controller.highlighted shouldBe NEXT
            controller.wantedSub shouldBe null
        }

        test("moves on when the pointer rests in the safe area") {
            val controller = opened()
            controller.track(entries = entries(), point = towardsPanel)
            controller.hover(entries = entries(), index = NEXT)
            controller.release(entries())

            controller.highlighted shouldBe NEXT
        }

        test("ignores a release without a held back row") {
            val controller = opened()
            controller.release(entries())

            controller.highlighted shouldBe SUB
        }
    }

    context("closing") {
        test("forgets the sub menu and its bounds") {
            val controller = opened()
            controller.close()

            controller.openSub shouldBe null
            controller.wantedSub shouldBe null
            controller.subBounds shouldBe null
        }
    }
})
