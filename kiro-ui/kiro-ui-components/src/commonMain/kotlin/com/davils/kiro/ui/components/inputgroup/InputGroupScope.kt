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

package com.davils.kiro.ui.components.inputgroup

import androidx.compose.runtime.Composable
import com.davils.kiro.ui.components.input.InputOptions
import com.davils.kiro.ui.components.primitive.textfield.FieldText

internal class InputGroupScope : InputGroupScopeImpl {
    private val collected = mutableListOf<GroupAddon>()
    private var placed: GroupControl? = null

    val addons: List<GroupAddon> get() = collected.toList()

    val control: GroupControl get() = requireNotNull(placed) { "An input group needs an input or a textarea." }

    override fun addon(align: InputGroupAlign, content: @Composable () -> Unit) {
        collected += GroupAddon(align = align, content = content)
    }

    override fun input(value: String, onValueChange: (String) -> Unit, placeholder: String?, options: InputOptions) {
        place(text = FieldText(value, onValueChange, placeholder), options = options, isSingleLine = true)
    }

    override fun textarea(
        value: String,
        onValueChange: (String) -> Unit,
        placeholder: String?,
        options: InputOptions,
    ) {
        place(text = FieldText(value, onValueChange, placeholder), options = options, isSingleLine = false)
    }

    private fun place(text: FieldText, options: InputOptions, isSingleLine: Boolean) {
        check(placed == null) { "An input group holds exactly one input or textarea." }
        placed = GroupControl(text = text, options = options, isSingleLine = isSingleLine)
    }
}
