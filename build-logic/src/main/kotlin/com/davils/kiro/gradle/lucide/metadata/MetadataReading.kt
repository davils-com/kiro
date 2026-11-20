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

import kotlinx.serialization.json.Json

private val BUILD_MARKERS: List<String> = listOf("\$group:", "\$extends:")

private val metadataFormat: Json = Json { ignoreUnknownKeys = true }

internal fun readMetadata(text: String): LucideMetadata {
    val metadata = metadataFormat.decodeFromString(LucideMetadata.serializer(), text)
    return metadata.copy(tags = withoutMarkers(metadata.tags), categories = withoutMarkers(metadata.categories))
}

private fun withoutMarkers(values: List<String>): List<String> =
    values.filterNot { value -> BUILD_MARKERS.any { marker -> value.startsWith(marker) } }
