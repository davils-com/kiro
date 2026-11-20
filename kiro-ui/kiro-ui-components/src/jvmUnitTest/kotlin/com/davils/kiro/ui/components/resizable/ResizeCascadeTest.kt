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

package com.davils.kiro.ui.components.resizable

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val PANELS: Int = 3
private const val THIRD: Float = 30f
private const val FORTY: Float = 40f
private const val TEN: Float = 10f
private const val TWENTY: Float = 20f
private const val FIFTY: Float = 50f
private const val HUNDRED: Float = 100f

private val loose = List(PANELS) { 0f..HUNDRED }
private val atLeastTen = List(PANELS) { TEN..HUNDRED }

class ResizeCascadeTest : FunSpec({
    context("cascade") {
        test("pushes the next panels once the neighbour reaches its minimum") {
            resized(sizes = listOf(THIRD, THIRD, FORTY), handle = 0, delta = FORTY, limits = atLeastTen) shouldBe
                listOf(THIRD + FORTY, TEN, TWENTY)
        }

        test("pulls back through the earlier panels as well") {
            resized(sizes = listOf(FORTY, THIRD, THIRD), handle = 1, delta = -FIFTY, limits = atLeastTen) shouldBe
                listOf(TEN, TEN, THIRD + FIFTY)
        }

        test("stops when no panel can give way any more") {
            resized(sizes = listOf(THIRD, THIRD, FORTY), handle = 0, delta = HUNDRED, limits = atLeastTen) shouldBe
                listOf(HUNDRED - TWENTY, TEN, TEN)
            resized(sizes = listOf(THIRD, THIRD, FORTY), handle = 0, delta = TEN, limits = loose) shouldBe
                listOf(FORTY, TWENTY, FORTY)
        }
    }

    context("state") {
        test("resizes on request and keeps the drag origin until the drag ends") {
            val state = ResizableState(listOf(THIRD, THIRD, FORTY))
            state.panels = List(
                PANELS,
            ) { ResizablePanel(limits = TEN..HUNDRED, isCollapsible = false, collapsedSize = 0f) {} }
            state.resize(handle = 1, delta = TEN)

            state.sizes shouldBe listOf(THIRD, FORTY, THIRD)
            state.begin()
            state.dragTo(handle = 0, delta = FIFTY)
            state.dragTo(handle = 0, delta = TEN)
            state.end()
            state.sizes shouldBe listOf(FORTY, THIRD, THIRD)
            shouldThrow<IllegalArgumentException> { state.resize(handle = 2, delta = TEN) }
        }

        test("collapses a collapsible panel below its minimum and restores its size") {
            val state = ResizableState(listOf(THIRD, THIRD, FORTY))
            state.panels = listOf(
                ResizablePanel(limits = TWENTY..HUNDRED, isCollapsible = true, collapsedSize = 0f) {},
                ResizablePanel(limits = TEN..HUNDRED, isCollapsible = false, collapsedSize = 0f) {},
                ResizablePanel(limits = TWENTY..HUNDRED, isCollapsible = true, collapsedSize = 0f) {},
            )
            state.collapse(0)
            state.collapse(1)
            state.collapse(2)

            state.isCollapsed(0) shouldBe true
            state.isCollapsed(1) shouldBe false
            state.sizes shouldBe listOf(0f, HUNDRED, 0f)
            state.expand(2)
            state.expand(0)
            state.sizes shouldBe listOf(THIRD, THIRD, FORTY)
            state.expand(1)
            state.isCollapsed(PANELS) shouldBe false
        }
    }
})
