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

package com.davils.kiro.ui.components.command.search

private val separators: Regex = Regex("[ ._/-]")

private val rules: List<(String, String) -> Boolean> = listOf(
    { term, needle -> term.equals(needle, ignoreCase = true) },
    { term, needle -> term.startsWith(needle, ignoreCase = true) },
    { term, needle -> term.split(separators).any { word -> word.startsWith(needle, ignoreCase = true) } },
    { term, needle -> term.contains(needle, ignoreCase = true) },
)

internal fun <T> ranked(items: List<T>, query: String, terms: (T) -> List<String>): List<T> {
    val needle = query.trim()
    if (needle.isEmpty()) return items
    val scored = items.mapIndexedNotNull { index, item ->
        rankOf(terms = terms(item), needle = needle)?.let { rank -> Scored(item = item, rank = rank, index = index) }
    }
    val ordered = scored.sortedWith(compareBy<Scored<T>> { it.rank }.thenBy { it.index })
    return ordered.map { it.item }
}

internal fun rankOf(terms: List<String>, needle: String): Int? {
    val ranks = terms.mapNotNull { term -> rankOf(term = term, needle = needle) }
    return ranks.minOrNull()
}

private fun rankOf(term: String, needle: String): Int? {
    val rank = rules.indexOfFirst { rule -> rule(term, needle) }
    return rank.takeIf { it >= 0 }
}
