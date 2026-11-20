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
import com.davils.kiro.gradle.lucide.metadata.readMetadata
import com.davils.kiro.gradle.lucide.svg.SvgReader
import com.davils.kiro.gradle.lucide.svg.toPathData
import com.davils.kiro.gradle.lucide.writer.CatalogWriter
import com.davils.kiro.gradle.lucide.writer.IconFileWriter
import com.davils.kiro.gradle.lucide.writer.Provenance
import com.squareup.kotlinpoet.FileSpec

internal object IconGeneration {
    fun generate(sources: List<LucideSource>, packageName: String, provenance: Provenance): List<FileSpec> {
        val named = sources.mapNotNull(::iconOf).sortedBy { icon -> icon.name }
        requireUniqueProperties(named)
        val icons = withDistinctFileNames(named)
        val iconFiles = icons.map { icon -> IconFileWriter.write(icon = icon, packageName = packageName, provenance = provenance) }
        return iconFiles + CatalogWriter.write(icons = icons, packageName = packageName, provenance = provenance)
    }

    private fun iconOf(source: LucideSource): LucideIcon? {
        val metadata = readMetadata(source.metadata)
        if (metadata.isDeprecated) return null
        val shapes = SvgReader.read(source.svg)
        return LucideIcon(
            name = source.name,
            propertyName = propertyNameOf(source.name),
            paths = shapes.map { shape -> GeneratedPath(data = shape.toPathData(), isFilled = shape.isFilled) },
            tags = metadata.tags,
            categories = metadata.categories,
        )
    }

    private fun withDistinctFileNames(icons: List<LucideIcon>): List<LucideIcon> {
        val folded = icons.groupBy { icon -> icon.propertyName.lowercase() }
        return icons.map { icon ->
            val group = folded.getValue(icon.propertyName.lowercase())
            icon.takeIf { group.size == 1 } ?: icon.copy(fileName = fileNameOf(icon.name))
        }
    }

    private fun requireUniqueProperties(icons: List<LucideIcon>) {
        val clashes = icons.groupBy { icon -> icon.propertyName }.filterValues { group -> group.size > 1 }
        require(clashes.isEmpty()) { "Several icons share the property names ${clashes.keys}." }
    }
}
