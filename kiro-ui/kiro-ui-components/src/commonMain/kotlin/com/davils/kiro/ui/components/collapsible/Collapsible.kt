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

package com.davils.kiro.ui.components.collapsible

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.collapse
import androidx.compose.ui.semantics.expand
import androidx.compose.ui.semantics.semantics
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor

@Composable
public fun Collapsible(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: CollapsibleStyle = collapsibleStyle(),
    content: @Composable CollapsibleScopeImpl.() -> Unit,
) {
    val scope = CollapsibleScope(
        isExpanded = expanded,
        isEnabled = enabled,
        style = style,
        onExpandedChange = onExpandedChange,
    )
    Column(modifier = modifier) { scope.content() }
}

@Composable
public fun CollapsibleScopeImpl.CollapsibleTrigger(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val scope = this as CollapsibleScope
    val toggled = {
        scope.toggle()
        true
    }
    val states = Modifier.semantics {
        if (scope.isExpanded) collapse(action = toggled)
        if (!scope.isExpanded) expand(action = toggled)
    }
    Row(
        modifier = modifier
            .then(states)
            .pressableCursor(enabled = scope.isEnabled)
            .clickable(enabled = scope.isEnabled, role = Role.Button, onClick = scope::toggle),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
public fun CollapsibleScopeImpl.CollapsibleContent(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scope = this as CollapsibleScope
    AnimatedVisibility(
        visible = scope.isExpanded,
        modifier = modifier,
        enter = scope.style.enter,
        exit = scope.style.exit,
    ) {
        Column(content = content)
    }
}
