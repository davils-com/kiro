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

import com.squareup.kotlinpoet.FileSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import java.nio.file.Files

private const val PACKAGE: String = "com.example.icons"

class SourceFilesTest : FunSpec({
    val file = FileSpec.builder(PACKAGE, "Arrow").build()

    context("render") {
        test("starts with the license header of the project") {
            SourceFiles.render(file) shouldStartWith "/*\n * Copyright 2026 Davils\n *\n * Licensed under the Apache"
        }

        test("keeps one empty line between the header and the package") {
            SourceFiles.render(file).substringAfter(" */\n").substringBefore("package") shouldBe "\n"
        }
    }

    context("write") {
        test("writes the file into the directory of its package") {
            val directory = Files.createTempDirectory("lucide").toFile()

            SourceFiles.write(file = file, directory = directory)

            directory.resolve("com/example/icons/Arrow.kt").readText() shouldBe SourceFiles.render(file)
        }
    }
})
