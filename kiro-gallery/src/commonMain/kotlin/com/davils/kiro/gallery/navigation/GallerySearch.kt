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

package com.davils.kiro.gallery.navigation

import com.davils.kiro.gallery.page.GalleryCategory
import com.davils.kiro.gallery.page.GalleryDemo

internal fun matchesOf(demos: List<GalleryDemo>, query: String): Map<GalleryCategory, List<GalleryDemo>> {
    val needle = query.trim()
    val found = demos.filter { demo -> isMatch(demo = demo, needle = needle) }
    val grouped = found.groupBy { demo -> demo.category }
    return GalleryCategory.entries
        .mapNotNull { category -> grouped[category]?.let { list -> category to list } }
        .toMap()
}

private fun isMatch(demo: GalleryDemo, needle: String): Boolean {
    if (needle.isEmpty()) return true
    return demo.name.contains(needle, ignoreCase = true) || demo.category.label.contains(needle, ignoreCase = true)
}
