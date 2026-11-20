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

package com.davils.kiro.gallery.navigation

import com.davils.kiro.gallery.demo.demos
import com.davils.kiro.gallery.page.GalleryCategory
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GallerySearchTest : FunSpec({
    context("search") {
        test("groups every demo by its category without a query") {
            val matches = matchesOf(demos = demos, query = " ")

            matches.values.sumOf { found -> found.size } shouldBe demos.size
            matches.keys.toList() shouldBe GalleryCategory.entries
        }

        test("finds demos by name and by category") {
            val menus = matchesOf(demos = demos, query = "menu").values.flatten()
            menus.map { demo -> demo.name } shouldBe
                listOf("Context Menu", "Dropdown Menu", "Menubar", "Navigation Menu")
            matchesOf(demos = demos, query = "conversation").keys shouldBe setOf(GalleryCategory.Conversation)
        }
    }
})
