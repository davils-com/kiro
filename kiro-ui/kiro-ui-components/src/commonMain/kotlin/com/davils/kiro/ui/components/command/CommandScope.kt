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

import com.davils.kiro.ui.icons.IconGraphic

internal class CommandScope : SectionsScope(), CommandScopeImpl {
    private val collectedTabs = mutableListOf<CommandTab>()
    private var all: CommandTab? = null

    val tabs: List<CommandTab>
        get() {
            val combined = all?.copy(sections = collectedTabs.flatMap { tab -> tab.sections })
            return listOfNotNull(combined) + collectedTabs
        }

    override fun tab(
        label: String,
        icon: IconGraphic?,
        placeholder: String?,
        content: CommandSectionsScope.() -> Unit,
    ) {
        val sections = SectionsScope().apply(content).sections
        collectedTabs += CommandTab(label = label, icon = icon, placeholder = placeholder, sections = sections)
    }

    override fun allTab(label: String?, icon: IconGraphic?) {
        all = CommandTab(label = label.orEmpty(), icon = icon, placeholder = null, sections = emptyList())
    }
}
