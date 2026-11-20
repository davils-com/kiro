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

import com.squareup.kotlinpoet.AnnotationSpec

internal const val GENERATOR: String = "kiro build-logic, GenerateLucideIcons"

internal const val INDENT: String = "    "

internal class Provenance(val release: String, val since: String) {
    fun annotation(input: String): AnnotationSpec {
        val annotation = AnnotationSpec.builder(IconModel.generated)
        annotation.addMember("by = %S", GENERATOR)
        annotation.addMember("from = %S", "$release, $input")
        return annotation.build()
    }
}
