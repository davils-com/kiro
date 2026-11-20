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

package com.davils.kiro.gradle.lucide

import com.davils.kiro.gradle.lucide.archive.LucideSource
import com.davils.kiro.gradle.lucide.fixture.ARROW_METADATA
import com.davils.kiro.gradle.lucide.fixture.ARROW_SVG
import com.davils.kiro.gradle.lucide.fixture.DEPRECATED_METADATA
import com.davils.kiro.gradle.lucide.writer.CATALOG_FILE
import com.davils.kiro.gradle.lucide.writer.Provenance
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain

private const val PACKAGE: String = "com.example.icons"

private val PROVENANCE: Provenance = Provenance(release = "lucide-icons 1.71.0", since = "0.1.0")
private const val ARROW: String = "arrow"

class IconGenerationTest : FunSpec({
    context("generate") {
        test("writes one file per icon in name order and the catalog last") {
            val sources = listOf(
                LucideSource(name = "zap", svg = ARROW_SVG, metadata = ARROW_METADATA),
                LucideSource(name = ARROW, svg = ARROW_SVG, metadata = ARROW_METADATA),
            )

            val files = IconGeneration.generate(sources = sources, packageName = PACKAGE, provenance = PROVENANCE)

            files.map { file -> file.name } shouldBe listOf("Arrow", "Zap", CATALOG_FILE)
        }

        test("turns every shape into path data and keeps its fill") {
            val sources = listOf(LucideSource(name = ARROW, svg = ARROW_SVG, metadata = ARROW_METADATA))

            val files = IconGeneration.generate(sources = sources, packageName = PACKAGE, provenance = PROVENANCE)
            val icon = files.first().toString()

            icon shouldContain "IconPath(data = \"M7 7h10v10\")"
            icon shouldContain "A 2 2 0 1 0 14 12"
            icon shouldContain "isFilled = true"
        }

        test("leaves out deprecated icons") {
            val sources = listOf(
                LucideSource(name = ARROW, svg = ARROW_SVG, metadata = ARROW_METADATA),
                LucideSource(name = "badge-cent", svg = ARROW_SVG, metadata = DEPRECATED_METADATA),
            )

            val files = IconGeneration.generate(sources = sources, packageName = PACKAGE, provenance = PROVENANCE)

            files.map { file -> file.name } shouldBe listOf("Arrow", CATALOG_FILE)
            files.last().toString() shouldNotContain "BadgeCent"
        }

        test("refuses icons whose property names clash") {
            val sources = listOf(
                LucideSource(name = "arrow-up", svg = ARROW_SVG, metadata = ARROW_METADATA),
                LucideSource(name = "arrow-Up", svg = ARROW_SVG, metadata = ARROW_METADATA),
            )

            shouldThrow<IllegalArgumentException> {
                IconGeneration.generate(sources = sources, packageName = PACKAGE, provenance = PROVENANCE)
            }.message shouldContain "ArrowUp"
        }
    }
})
