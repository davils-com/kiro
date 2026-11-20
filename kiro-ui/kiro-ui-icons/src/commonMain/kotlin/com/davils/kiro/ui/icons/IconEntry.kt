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

package com.davils.kiro.ui.icons

import androidx.compose.runtime.Immutable

/**
 * An icon of a catalog together with the words it can be found by, for search fields and icon pickers.
 *
 * @property icon the icon.
 * @param tags the words that describe the icon; the list is copied.
 * @param categories the categories the icon belongs to; the list is copied.
 * @since 1.0.0
 */
@Immutable
public class IconEntry(public val icon: IconGraphic, tags: List<String>, categories: List<String>) {
    /**
     * The words that describe the icon. The list cannot be changed.
     *
     * @since 1.0.0
     */
    public val tags: List<String> = buildList { addAll(tags) }

    /**
     * The categories the icon belongs to. The list cannot be changed.
     *
     * @since 1.0.0
     */
    public val categories: List<String> = buildList { addAll(categories) }

    override fun equals(other: Any?): Boolean = other is IconEntry && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "IconEntry(icon=${icon.name}, tags=$tags, categories=$categories)"

    private fun values(): List<Any?> = listOf(icon, tags, categories)
}
