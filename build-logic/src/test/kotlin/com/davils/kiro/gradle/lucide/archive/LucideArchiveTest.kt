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

package com.davils.kiro.gradle.lucide.archive

import com.davils.kiro.gradle.lucide.fixture.ARROW_METADATA
import com.davils.kiro.gradle.lucide.fixture.ARROW_SVG
import com.davils.kiro.gradle.lucide.fixture.archiveOf
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class LucideArchiveTest : FunSpec({
    context("read") {
        test("pairs every icon with its metadata") {
            val archive = archiveOf(mapOf("icons/arrow.svg" to ARROW_SVG, "icons/arrow.json" to ARROW_METADATA))

            LucideArchive.read(archive) shouldBe listOf(
                LucideSource(name = "arrow", svg = ARROW_SVG, metadata = ARROW_METADATA),
            )
        }

        test("ignores everything outside the icon directory") {
            val archive = archiveOf(mapOf("LICENSE" to "ISC", "docs/arrow.svg" to ARROW_SVG))

            LucideArchive.read(archive).shouldBeEmpty()
        }

        test("refuses an icon without metadata") {
            val archive = archiveOf(mapOf("icons/arrow.svg" to ARROW_SVG))

            val failure = shouldThrow<IllegalArgumentException> { LucideArchive.read(archive) }

            failure.message shouldContain "arrow"
        }

        test("refuses an entry larger than the limit instead of reading it into memory") {
            val huge = "x".repeat(512 * 1024 + 1)
            val archive = archiveOf(mapOf("icons/huge.svg" to huge, "icons/huge.json" to ARROW_METADATA))

            val failure = shouldThrow<IllegalArgumentException> { LucideArchive.read(archive) }

            failure.message shouldContain "icons/huge.svg"
        }
    }
})
