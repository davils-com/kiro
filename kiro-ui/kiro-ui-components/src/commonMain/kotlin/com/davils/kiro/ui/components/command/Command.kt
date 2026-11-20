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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.key.onPreviewKeyEvent
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.command.search.SearchField
import com.davils.kiro.ui.components.command.search.SearchList
import com.davils.kiro.ui.components.command.search.SearchLook
import com.davils.kiro.ui.components.command.search.SearchSection
import com.davils.kiro.ui.components.command.search.entriesOf
import com.davils.kiro.ui.components.menu.MenuController
import com.davils.kiro.ui.components.menu.MenuEntry
import com.davils.kiro.ui.components.menu.MenuKeys
import com.davils.kiro.ui.components.primitive.overlay.PanelStyle
import com.davils.kiro.ui.components.primitive.overlay.panel
import com.davils.kiro.ui.components.tabs.Tabs
import com.davils.kiro.ui.components.tabs.TabsVariant
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor

@Composable
public fun Command(
    modifier: Modifier = Modifier,
    state: CommandState = rememberCommandState(),
    placeholder: String = Messages.Command.placeholder,
    emptyText: String = Messages.Command.empty,
    style: CommandStyle = commandStyle(),
    content: CommandScope.() -> Unit,
) {
    CommandLayout(
        modifier = modifier,
        state = state,
        texts = CommandTexts(placeholder = placeholder, empty = emptyText),
        panel = style.panel,
        style = style,
        focus = remember { FocusRequester() },
        onClose = {},
        content = content,
    )
}

@Composable
internal fun CommandLayout(
    modifier: Modifier,
    state: CommandState,
    texts: CommandTexts,
    panel: PanelStyle,
    style: CommandStyle,
    focus: FocusRequester,
    onClose: () -> Unit,
    content: CommandScope.() -> Unit,
) {
    val scope = CommandScopeImpl().apply(content)
    val tabs = scope.tabs
    val tab = tabs.getOrNull(state.tab)
    val page = pageOf(sections = tab?.sections ?: scope.sections, path = state.path)
    val visible = visibleSections(sections = page, query = state.query)
    val items = itemsOf(visible)
    val sections = searchSectionsOf(visible)
    val entries = entriesOf(sections)
    val controller = remember { MenuController() }
    LaunchedEffect(state.query, state.path, state.tab) { controller.highlighted = 0.takeIf { entries.isNotEmpty() } }
    LaunchedEffect(controller.openSub) { openPage(controller = controller, items = items, state = state) }
    val menuKeys = MenuKeys(
        entries = entries,
        controller = controller,
        isReversed = false,
        onCloseAll = onClose,
        onCloseSelf = null,
        isTextInput = true,
    )
    val keys = CommandKeys(state = state, tabCount = tabs.size, menu = menuKeys)
    CompositionLocalProvider(LocalContentColor provides panel.content) {
        Column(
            modifier = modifier.panel(panel).onPreviewKeyEvent(keys::handle),
            verticalArrangement = Arrangement.spacedBy(style.gap),
        ) {
            SearchField(
                query = state.query,
                onQueryChange = { typed -> state.query = typed },
                placeholder = tab?.placeholder ?: texts.placeholder,
                focus = focus,
                style = style.input,
                icon = style.searchIcon,
                iconAlpha = style.searchIconAlpha,
                leading = pathOf(state = state, style = style),
            )
            CommandTabs(tabs = tabs, selected = state.tab, style = style) { index ->
                state.select(index)
                focus.requestFocus()
            }
            SearchList(
                sections = sections,
                controller = controller,
                emptyText = texts.empty,
                look = lookOf(style),
                onCloseAll = onClose,
            )
        }
    }
}

@Composable
private fun CommandTabs(tabs: List<CommandTab>, selected: Int, style: CommandStyle, onSelect: (Int) -> Unit) {
    if (tabs.size < 2) return
    Tabs(selected = selected, onSelectedChange = onSelect, variant = TabsVariant.Line, style = style.tabs) {
        tabs.forEachIndexed { index, tab ->
            tab(value = index) {
                tab.icon?.let { icon -> Icon(icon = icon, placement = IconPlacement.Leading) }
                Text(tab.label.ifEmpty { Messages.Command.all })
            }
        }
    }
}

private fun pathOf(state: CommandState, style: CommandStyle): (@Composable () -> Unit)? {
    if (state.path.isEmpty()) return null
    val path = state.path.joinToString(separator = " / ", postfix = " /")
    return { Text(text = path, style = style.pathStyle, color = style.pathColor, maxLines = 1) }
}

private fun openPage(controller: MenuController, items: List<CommandItem>, state: CommandState) {
    val index = controller.openSub ?: return
    val item = items.getOrNull(index)
    val page = item?.takeIf { candidate -> candidate.page != null }
    page?.let { opened -> state.open(opened.label) }
    controller.close()
}

private fun searchSectionsOf(sections: List<CommandSection>): List<SearchSection> = sections.map { section ->
    when (section) {
        CommandSection.Separator -> SearchSection.Separator

        is CommandSection.Group -> SearchSection.Group(
            heading = section.heading,
            entries = section.items.map(::entryOf),
        )
    }
}

private fun entryOf(item: CommandItem): MenuEntry.Interactive {
    if (item.page != null) {
        return MenuEntry.Sub(
            label = item.label,
            icon = item.icon,
            isEnabled = item.isEnabled,
            isInset = false,
            entries = emptyList(),
        )
    }
    return MenuEntry.Action(
        label = item.label,
        onClick = item.onSelect,
        isEnabled = item.isEnabled,
        icon = item.icon,
        shortcut = item.shortcut ?: item.detail,
        isDestructive = false,
        isInset = false,
    )
}

private fun lookOf(style: CommandStyle): SearchLook = SearchLook(
    item = style.item,
    listMaxHeight = style.listMaxHeight,
    groupPadding = style.groupPadding,
    headingStyle = style.headingStyle,
    headingColor = style.headingColor,
    headingHorizontalPadding = style.headingHorizontalPadding,
    headingVerticalPadding = style.headingVerticalPadding,
    emptyStyle = style.emptyStyle,
    emptyColor = style.headingColor,
    emptyPadding = style.emptyPadding,
    separatorColor = style.separatorColor,
)
