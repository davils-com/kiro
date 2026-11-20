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
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LIST
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.joinToCode

internal const val CATALOG_FILE: String = "LucideCatalog"

private const val ENTRIES_PER_CHUNK: Int = 100

private const val BACKING_NAME: String = "lucideEntries"

private const val CATALOG_INPUT: String = "icons/*.json"

private const val ENTRIES_KDOC: String = "Every icon of %L that is not deprecated, in name order, with the tags and " +
    "categories Lucide gives it, for search and pickers.\n\nThe list is built on first access and never changes." +
    "\n\n@since %L"

internal object CatalogWriter {
    private val entriesType: TypeName = LIST.parameterizedBy(IconModel.entry)

    fun write(icons: List<LucideIcon>, packageName: String, provenance: Provenance): FileSpec {
        val lucide = IconModel.lucide(packageName)
        val groups = icons.chunked(ENTRIES_PER_CHUNK)
        val chunks = groups.mapIndexed { index, group -> chunkOf(index = index, icons = group, lucide = lucide) }
        val generated = provenance.annotation(CATALOG_INPUT)
        val file = FileSpec.builder(packageName, CATALOG_FILE)
        file.indent(INDENT)
        file.addProperty(entriesOf(lucide = lucide, provenance = provenance))
        file.addProperty(backingOf(chunks = chunks, generated = generated))
        chunks.forEach { chunk -> file.addFunction(chunk.toBuilder().addAnnotation(generated).build()) }
        return file.build()
    }

    private fun entriesOf(lucide: ClassName, provenance: Provenance): PropertySpec {
        val getter = FunSpec.getterBuilder()
        getter.addStatement("return %L", BACKING_NAME)
        val property = PropertySpec.builder("entries", entriesType, KModifier.PUBLIC)
        property.receiver(lucide)
        property.addKdoc(ENTRIES_KDOC, provenance.release, provenance.since)
        property.addAnnotation(provenance.annotation(CATALOG_INPUT))
        property.getter(getter.build())
        return property.build()
    }

    private fun backingOf(chunks: List<FunSpec>, generated: AnnotationSpec): PropertySpec {
        val backing = PropertySpec.builder(BACKING_NAME, entriesType, KModifier.PRIVATE)
        backing.addAnnotation(generated)
        backing.delegate(CodeBlock.of("lazy { %L }", combined(chunks)))
        return backing.build()
    }

    private fun combined(chunks: List<FunSpec>): CodeBlock {
        if (chunks.isEmpty()) return CodeBlock.of("emptyList()")
        val calls = chunks.map { chunk -> CodeBlock.of("%N()", chunk) }
        return calls.joinToCode(separator = " + ")
    }

    private fun chunkOf(index: Int, icons: List<LucideIcon>, lucide: ClassName): FunSpec {
        val entries = icons.map { icon -> entryOf(icon = icon, lucide = lucide) }
        val list = entries.joinToCode(separator = ",\n", prefix = "listOf(\n⇥", suffix = ",\n⇤)")
        val function = FunSpec.builder("entries$index")
        function.addModifiers(KModifier.PRIVATE)
        function.returns(entriesType)
        function.addStatement("return %L", list)
        return function.build()
    }

    private fun entryOf(icon: LucideIcon, lucide: ClassName): CodeBlock = CodeBlock.of(
        "%T(icon = %T.%L, tags = %L, categories = %L)",
        IconModel.entry,
        lucide,
        icon.propertyName,
        literalsOf(icon.tags),
        literalsOf(icon.categories),
    )

    private fun literalsOf(values: List<String>): CodeBlock {
        if (values.isEmpty()) return CodeBlock.of("emptyList()")
        val quoted = values.map { value -> CodeBlock.of("%S", value) }
        return quoted.joinToCode(separator = ", ", prefix = "listOf(", suffix = ")")
    }
}
