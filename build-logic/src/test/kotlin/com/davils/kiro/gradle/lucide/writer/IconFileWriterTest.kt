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

import com.davils.kiro.gradle.lucide.GeneratedPath
import com.davils.kiro.gradle.lucide.LucideIcon
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

private const val PACKAGE: String = "com.example.icons"

private val PROVENANCE: Provenance = Provenance(release = "lucide-icons 1.71.0", since = "1.0.0")

class IconFileWriterTest : FunSpec({
    val icon = LucideIcon(
        name = "arrow-up",
        propertyName = "ArrowUp",
        paths = listOf(
            GeneratedPath(data = "M1 1", isFilled = false),
            GeneratedPath(data = "M2 \$2", isFilled = true),
        ),
        tags = emptyList(),
        categories = emptyList(),
    )

    context("write") {
        val file = IconFileWriter.write(icon = icon, packageName = PACKAGE, provenance = PROVENANCE)
        val source = file.toString()

        test("names the file after the property in the given package") {
            file.name shouldBe "ArrowUp"
            file.packageName shouldBe PACKAGE
        }

        test("declares the icon as a public extension of Lucide") {
            source shouldContain "public val Lucide.ArrowUp: IconGraphic"
            source shouldContain "get() = arrowUpIcon"
        }

        test("builds the icon once and lazily") {
            source shouldContain "private val arrowUpIcon: IconGraphic by lazy {"
            source shouldContain "IconGraphic(name = \"arrow-up\""
        }

        test("imports the model types") {
            source shouldContain "import com.davils.kiro.ui.icons.IconGraphic"
            source shouldContain "import com.davils.kiro.ui.icons.IconPath"
        }

        test("marks only the filled paths and escapes dollar signs") {
            source shouldContain "IconPath(data = \"M1 1\")"
            source shouldContain "IconPath(data = \"M2 \${'$'}2\", isFilled = true)"
        }

        test("documents the icon as public API with the release it was drawn from") {
            source shouldContain "The Lucide icon `arrow-up`, drawn from its SVG in lucide-icons 1.71.0."
            source shouldContain "@since 1.0.0"
        }

        test("marks the icon and its backing property as generated, with the SVG it came from") {
            val annotation = "@DavilsGenerated(\n    by = \"$GENERATOR\",\n    from = \"lucide-icons 1.71.0, icons/arrow-up.svg\",\n)"

            source.split(annotation).size shouldBe 3
        }

        test("indents with four spaces") {
            source shouldContain "\n    get() = arrowUpIcon"
        }
    }
})
