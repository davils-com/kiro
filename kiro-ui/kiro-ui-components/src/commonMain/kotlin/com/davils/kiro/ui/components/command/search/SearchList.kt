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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.menu.MenuController
import com.davils.kiro.ui.components.menu.MenuEntry
import com.davils.kiro.ui.components.menu.MenuRow
import com.davils.kiro.ui.components.scrollarea.ScrollArea
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun SearchList(
    sections: List<SearchSection>,
    controller: MenuController,
    emptyText: String,
    look: SearchLook,
    onCloseAll: () -> Unit,
) {
    val entries = entriesOf(sections)
    ScrollArea(modifier = Modifier.fillMaxWidth().heightIn(max = look.listMaxHeight)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (entries.isEmpty()) {
                Text(
                    text = emptyText,
                    modifier = Modifier.fillMaxWidth().padding(vertical = look.emptyPadding),
                    style = look.emptyStyle.copy(textAlign = TextAlign.Center),
                    color = look.emptyColor,
                )
            }
            val starts = sections.runningFold(0) { start, section -> start + sizeOf(section) }
            sections.forEachIndexed { position, section ->
                SectionView(
                    section = section,
                    first = starts[position],
                    entries = entries,
                    controller = controller,
                    look = look,
                    onCloseAll = onCloseAll,
                )
            }
        }
    }
}

private fun sizeOf(section: SearchSection): Int {
    if (section !is SearchSection.Group) return 0
    return section.entries.size
}

internal fun entriesOf(sections: List<SearchSection>): List<MenuEntry.Interactive> =
    sections.flatMap { section -> (section as? SearchSection.Group)?.entries.orEmpty() }

@Composable
private fun SectionView(
    section: SearchSection,
    first: Int,
    entries: List<MenuEntry.Interactive>,
    controller: MenuController,
    look: SearchLook,
    onCloseAll: () -> Unit,
) {
    when (section) {
        SearchSection.Separator -> Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(look.separatorColor))

        is SearchSection.Group -> Column(modifier = Modifier.padding(look.groupPadding)) {
            section.heading?.let { heading -> Heading(text = heading, look = look) }
            section.entries.forEachIndexed { position, entry ->
                val index = first + position
                MenuRow(
                    entry = entry,
                    isHighlighted = controller.highlighted == index,
                    style = look.item,
                    onHover = { controller.highlighted = index },
                    onActivate = { controller.activate(entries = entries, index = index, onCloseAll = onCloseAll) },
                )
            }
        }
    }
}

@Composable
private fun Heading(text: String, look: SearchLook) {
    Text(
        text = text,
        modifier = Modifier.padding(horizontal = look.headingHorizontalPadding, vertical = look.headingVerticalPadding),
        style = look.headingStyle,
        color = look.headingColor,
    )
}
