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

package com.davils.kiro.gradle.lucide.metadata

import com.davils.kiro.gradle.lucide.fixture.ARROW_METADATA
import com.davils.kiro.gradle.lucide.fixture.DEPRECATED_METADATA
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json

class MetadataReadingTest : FunSpec({
    context("readMetadata") {
        test("drops the build markers of the lucide repository from the tags") {
            readMetadata(ARROW_METADATA) shouldBe LucideMetadata(
                isDeprecated = false,
                tags = listOf("direction"),
                categories = listOf("arrows"),
            )
        }

        test("keeps a plain dollar sign, which is a tag of its own") {
            readMetadata("""{"tags": ["${'$'}"]}""").tags shouldBe listOf("$")
        }

        test("drops extends markers from the categories") {
            val metadata = readMetadata("""{"categories": ["${'$'}extends:arrow-up", "arrows"]}""")

            metadata.categories shouldBe listOf("arrows")
        }

        test("reads the deprecation and ignores fields it does not need") {
            val metadata = readMetadata(DEPRECATED_METADATA.replace("{", """{"contributors": ["someone"], """))

            metadata.isDeprecated shouldBe true
        }

        test("defaults every field") {
            readMetadata("{}") shouldBe LucideMetadata()
        }
    }

    context("serialization") {
        val metadata = LucideMetadata(isDeprecated = true, tags = listOf("a"), categories = listOf("b"))

        test("writes the deprecation under the name lucide uses") {
            val text = Json.encodeToString(LucideMetadata.serializer(), metadata)

            text shouldBe """{"deprecated":true,"tags":["a"],"categories":["b"]}"""
        }

        test("leaves out defaults unless asked to write them") {
            val everything = Json { encodeDefaults = true }

            Json.encodeToString(LucideMetadata.serializer(), LucideMetadata()) shouldBe "{}"
            everything.encodeToString(LucideMetadata.serializer(), LucideMetadata()) shouldBe
                """{"deprecated":false,"tags":[],"categories":[]}"""
        }
    }
})
