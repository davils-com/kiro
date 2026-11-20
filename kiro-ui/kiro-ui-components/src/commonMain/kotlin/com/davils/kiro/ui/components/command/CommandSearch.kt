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

package com.davils.kiro.ui.components.command

import com.davils.kiro.ui.components.command.search.ranked

internal fun pageOf(sections: List<CommandSection>, path: List<String>): List<CommandSection> =
    path.fold(sections) { current, label -> childOf(sections = current, label = label) ?: current }

internal fun visibleSections(sections: List<CommandSection>, query: String): List<CommandSection> {
    if (query.isBlank()) return sections
    return sections.mapNotNull { section -> filteredGroup(section = section, query = query) }
}

internal fun itemsOf(sections: List<CommandSection>): List<CommandItem> =
    sections.flatMap { section -> (section as? CommandSection.Group)?.items.orEmpty() }

private fun childOf(sections: List<CommandSection>, label: String): List<CommandSection>? {
    val item = itemsOf(sections).firstOrNull { candidate -> candidate.label == label }
    return item?.page
}

private fun filteredGroup(section: CommandSection, query: String): CommandSection? {
    val group = section as? CommandSection.Group ?: return null
    val items = ranked(items = group.items, query = query) { item -> item.keywords + item.label }
    return group.copy(items = items).takeIf { items.isNotEmpty() }
}
