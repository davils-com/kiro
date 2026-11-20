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

import java.io.ByteArrayInputStream
import java.util.zip.ZipInputStream

private const val ICON_DIRECTORY: String = "icons/"
private const val SVG_SUFFIX: String = ".svg"
private const val METADATA_SUFFIX: String = ".json"

private const val MAX_ENTRY_BYTES: Int = 512 * 1024

private const val MAX_ENTRIES: Int = 20_000

internal object LucideArchive {
    fun read(archive: ByteArray): List<LucideSource> {
        val entries = entriesOf(archive)
        val names = entries.keys.filter { path -> path.endsWith(SVG_SUFFIX) }
        return names.map { path -> sourceOf(path, entries) }
    }

    private fun sourceOf(svgPath: String, entries: Map<String, String>): LucideSource {
        val name = svgPath.removePrefix(ICON_DIRECTORY).removeSuffix(SVG_SUFFIX)
        val metadata = requireNotNull(entries["$ICON_DIRECTORY$name$METADATA_SUFFIX"]) {
            "The icon $name has no metadata in the archive."
        }
        return LucideSource(name = name, svg = entries.getValue(svgPath), metadata = metadata)
    }

    private fun entriesOf(archive: ByteArray): Map<String, String> {
        val entries = mutableMapOf<String, String>()
        ZipInputStream(ByteArrayInputStream(archive)).use { zip ->
            generateSequence { zip.nextEntry }.forEach { entry ->
                val isIcon = entry.name.startsWith(ICON_DIRECTORY) && !entry.isDirectory
                if (isIcon) entries[entry.name] = boundedTextOf(zip = zip, name = entry.name)
                require(entries.size <= MAX_ENTRIES) { "The archive holds more than $MAX_ENTRIES icon files." }
            }
        }
        return entries
    }

    private fun boundedTextOf(zip: ZipInputStream, name: String): String {
        val bytes = zip.readNBytes(MAX_ENTRY_BYTES + 1)
        require(bytes.size <= MAX_ENTRY_BYTES) { "The entry $name is larger than $MAX_ENTRY_BYTES bytes." }
        return bytes.decodeToString()
    }
}
