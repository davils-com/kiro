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

package com.davils.kiro.ui.icons.lucide

import com.davils.kiro.ui.icons.toGeometry
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe

private const val NON_DEPRECATED_ICONS: Int = 2027

class LucideTest : FunSpec({
    val entries = Lucide.entries
    val names = entries.map { entry -> entry.icon.name }

    context("entries") {
        test("lists every icon of Lucide 1.71.0 that is not deprecated") {
            entries.size shouldBe NON_DEPRECATED_ICONS
        }

        test("leaves out the deprecated icons") {
            names shouldNotContain "badge-cent"
            names shouldNotContain "swiss-franc"
        }

        test("lists every icon once, in name order") {
            names.distinct() shouldBe names
            names.sorted() shouldBe names
        }

        test("carries the tags and categories of an icon for search") {
            val activity = entries.single { entry -> entry.icon == Lucide.Activity }

            activity.tags shouldContain "pulse"
            activity.categories shouldContain "medical"
        }

        test("drops the build markers of the lucide repository") {
            val markers = entries.flatMap { entry -> entry.tags + entry.categories }

            markers.filter { value -> value.startsWith("\$group:") || value.startsWith("\$extends:") }.shouldBeEmpty()
        }
    }

    context("icons") {
        test("are named after their lucide name") {
            Lucide.ArrowUpRight.name shouldBe "arrow-up-right"
        }

        test("draw on the 24 by 24 canvas of lucide") {
            val viewport = 24f

            entries.map { entry -> entry.icon.viewportSize }.toSet() shouldBe setOf(viewport)
        }

        test("all parse into paths that draw something") {
            val empty = entries.filter { entry ->
                val geometry = entry.icon.toGeometry()
                geometry.shapes.isEmpty() || geometry.shapes.any { shape -> shape.path.isEmpty }
            }

            empty.shouldBeEmpty()
        }
    }
})
