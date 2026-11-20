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

package com.davils.kiro.ui.icons

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private const val LUCIDE_CANVAS: Float = 24f

private const val RULE: String = "rule"

private const val SHAPES: String = "shapes"

private val line: IconPath = IconPath(data = "M0 12H24")

class IconGraphicTest : FunSpec({
    context("graphic") {
        test("copies its paths, so a change to the list given does not reach it") {
            val paths = mutableListOf(line)
            val graphic = IconGraphic(name = "line", paths = paths)

            paths.clear()

            graphic.paths shouldHaveSize 1
        }

        test("draws on the canvas of Lucide by default") {
            IconGraphic(name = "line", paths = listOf(line)).viewportSize shouldBe LUCIDE_CANVAS
        }

        test("compares by value and names itself with its number of paths") {
            val graphic = IconGraphic(name = "line", paths = listOf(line))

            graphic.copy() shouldBe graphic
            graphic.copy().hashCode() shouldBe graphic.hashCode()
            graphic.copy(name = "other") shouldNotBe graphic
            graphic.toString() shouldBe "IconGraphic(name=line, paths=1, viewportSize=24.0)"
        }
    }

    context("path") {
        test("is stroked only unless it says so") {
            line.isFilled shouldBe false
            line.copy(isFilled = true) shouldNotBe line
            line.toString() shouldBe "IconPath(data=M0 12H24, isFilled=false)"
        }
    }

    context("entry") {
        test("copies its tags and categories and compares by value") {
            val graphic = IconGraphic(name = "line", paths = listOf(line))
            val tags = mutableListOf(RULE)
            val entry = IconEntry(icon = graphic, tags = tags, categories = listOf(SHAPES))
            val same = IconEntry(icon = graphic, tags = listOf(RULE), categories = listOf(SHAPES))

            tags.clear()

            entry.tags shouldBe listOf(RULE)
            entry shouldBe same
            entry.hashCode() shouldBe same.hashCode()
            entry.toString() shouldBe "IconEntry(icon=line, tags=[rule], categories=[shapes])"
        }
    }
})
