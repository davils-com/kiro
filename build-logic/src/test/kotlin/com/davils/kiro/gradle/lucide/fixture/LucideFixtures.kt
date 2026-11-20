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

package com.davils.kiro.gradle.lucide.fixture

import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

internal const val ARROW_SVG: String = """<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
  fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
  <path d="M7 7h10v10" />
  <circle cx="12" cy="12" r="2" fill="currentColor" />
</svg>"""

internal const val ARROW_METADATA: String = """{"tags": ["direction", "${'$'}group:arrow"], "categories": ["arrows"]}"""

internal const val DEPRECATED_METADATA: String = """{"deprecated": true, "tags": [], "categories": []}"""

internal fun svgOf(vararg elements: String): String {
    val body = elements.joinToString(separator = "\n")
    return """<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">$body</svg>"""
}

internal fun archiveOf(entries: Map<String, String>): ByteArray {
    val bytes = ByteArrayOutputStream()
    ZipOutputStream(bytes).use { zip ->
        zip.putNextEntry(ZipEntry("icons/"))
        zip.closeEntry()
        entries.forEach { (name, content) ->
            zip.putNextEntry(ZipEntry(name))
            zip.write(content.toByteArray())
            zip.closeEntry()
        }
    }
    return bytes.toByteArray()
}
