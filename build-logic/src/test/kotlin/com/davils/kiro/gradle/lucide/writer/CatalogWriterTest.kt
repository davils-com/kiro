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

package com.davils.kiro.gradle.lucide.writer

import com.davils.kiro.gradle.lucide.LucideIcon
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain

private const val PACKAGE: String = "com.example.icons"

private val PROVENANCE: Provenance = Provenance(release = "lucide-icons 1.71.0", since = "0.1.0")

private fun iconsOf(count: Int): List<LucideIcon> = (0 until count).map { index ->
    LucideIcon(
        name = "icon-$index",
        propertyName = "Icon$index",
        paths = emptyList(),
        tags = listOf("tag"),
        categories = emptyList(),
    )
}

class CatalogWriterTest : FunSpec({
    context("write") {
        test("lists every icon with its tags and categories") {
            val file = CatalogWriter.write(icons = iconsOf(count = 1), packageName = PACKAGE, provenance = PROVENANCE)
            val source = file.toString()

            file.name shouldBe CATALOG_FILE
            source shouldContain "public val Lucide.entries: List<IconEntry>"
            source shouldContain "IconEntry(icon = Lucide.Icon0, tags = listOf(\"tag\"), categories = emptyList())"
        }

        test("splits the entries into functions of one hundred, so no method grows too large") {
            val file = CatalogWriter.write(icons = iconsOf(count = 101), packageName = PACKAGE, provenance = PROVENANCE)
            val source = file.toString()

            source shouldContain "lazy { entries0() + entries1() }"
            source shouldContain "private fun entries1(): List<IconEntry>"
            source shouldNotContain "entries2"
        }

        test("writes an empty catalog without any function") {
            val file = CatalogWriter.write(icons = emptyList(), packageName = PACKAGE, provenance = PROVENANCE)

            file.toString() shouldContain "lazy { emptyList() }"
        }
    }
})
