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

/**
 * A region that shows or hides its content when its trigger is clicked.
 *
 * Place a [CollapsibleTrigger] and a [CollapsibleContent] in [content]; the content is shown while [expanded] is
 * `true` and enters and exits with the transitions of [style].
 *
 * @param expanded whether the content is shown.
 * @param onExpandedChange called with the new value when the trigger asks to show or hide the content.
 * @param modifier the modifier of the column that holds the trigger and the content.
 * @param enabled whether the trigger reacts to clicks.
 * @param style the style of the collapsible, by default the style of the theme.
 * @param content the trigger and the content of the collapsible.
 * @since 1.0.0
 */
@Composable
public fun Collapsible(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: CollapsibleStyle = collapsibleStyle(),
    content: @Composable CollapsibleScope.() -> Unit,
) {
    val scope = CollapsibleScopeImpl(
        isExpanded = expanded,
        isEnabled = enabled,
        style = style,
        onExpandedChange = onExpandedChange,
    )
    Column(modifier = modifier) { scope.content() }
}

/**
 * The row of a [Collapsible] that shows or hides its content when it is clicked, announced as a button that can be
 * expanded or collapsed.
 *
 * @param modifier the modifier of the row.
 * @param content the content of the row, such as a label and an icon.
 * @since 1.0.0
 */
@Composable
public fun CollapsibleScope.CollapsibleTrigger(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val scope = this as CollapsibleScopeImpl
    val toggled = {
        scope.toggle()
        true
    }
    val states = Modifier.semantics {
        if (scope.isExpanded) collapse(action = toggled)
        if (!scope.isExpanded) expand(action = toggled)
    }
    Row(
        modifier = modifier.then(
            Modifier
                .then(states)
                .pressableCursor(enabled = scope.isEnabled)
                .clickable(enabled = scope.isEnabled, role = Role.Button, onClick = scope::toggle),
        ),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

/**
 * The content of a [Collapsible], shown while the collapsible is expanded with the transitions of its style.
 *
 * @param modifier the modifier of the content.
 * @param content the content.
 * @since 1.0.0
 */
@Composable
public fun CollapsibleScope.CollapsibleContent(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scope = this as CollapsibleScopeImpl
    AnimatedVisibility(
        visible = scope.isExpanded,
        modifier = modifier,
        enter = scope.style.enter,
        exit = scope.style.exit,
    ) {
        Column(content = content)
    }
}
