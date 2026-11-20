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
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.joinToCode

private const val ICON_KDOC: String = "The Lucide icon `%L`, drawn from its SVG in %L.\n\n@since %L"

internal object IconFileWriter {
    fun write(icon: LucideIcon, packageName: String, provenance: Provenance): FileSpec {
        val input = "icons/${icon.name}.svg"
        val backing = backingOf(icon = icon, annotation = provenance, input = input)
        val getter = FunSpec.getterBuilder()
        getter.addStatement("return %N", backing)
        val property = PropertySpec.builder(icon.propertyName, IconModel.icon, KModifier.PUBLIC)
        property.receiver(IconModel.lucide(packageName))
        property.addKdoc(ICON_KDOC, icon.name, provenance.release, provenance.since)
        property.addAnnotation(provenance.annotation(input))
        property.getter(getter.build())
        val file = FileSpec.builder(packageName, icon.fileName)
        file.indent(INDENT)
        file.addProperty(property.build())
        file.addProperty(backing)
        return file.build()
    }

    private fun backingOf(icon: LucideIcon, annotation: Provenance, input: String): PropertySpec {
        val paths = icon.paths.map(::pathOf)
        val list = paths.joinToCode(separator = ",\n", prefix = "listOf(\n⇥", suffix = ",\n⇤)")
        val initializer = CodeBlock.of("lazy {\n⇥%T(name = %S, paths = %L)\n⇤}", IconModel.icon, icon.name, list)
        val backing = PropertySpec.builder(backingNameOf(icon), IconModel.icon, KModifier.PRIVATE)
        backing.addAnnotation(annotation.annotation(input))
        backing.delegate(initializer)
        return backing.build()
    }

    private fun backingNameOf(icon: LucideIcon): String {
        val name = icon.propertyName
        return "${name.take(1).lowercase()}${name.drop(1)}Icon"
    }

    private fun pathOf(path: GeneratedPath): CodeBlock {
        if (!path.isFilled) return CodeBlock.of("%T(data = %S)", IconModel.path, path.data)
        return CodeBlock.of("%T(data = %S, isFilled = true)", IconModel.path, path.data)
    }
}
