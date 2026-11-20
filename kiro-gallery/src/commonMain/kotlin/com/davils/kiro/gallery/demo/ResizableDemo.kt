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

package com.davils.kiro.gallery.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.resizable.ResizablePanelGroup
import com.davils.kiro.ui.components.resizable.ResizableState
import com.davils.kiro.ui.components.resizable.rememberResizableState
import com.davils.kiro.ui.components.text.Text

private const val TREE: Float = 22f
private const val EDITOR: Float = 56f
private const val PANEL: Float = 22f
private const val CODE: Float = 65f
private const val CONSOLE: Float = 35f
private const val HALF: Float = 50f
private const val MIN: Float = 10f

@Composable
internal fun ResizableDemo() {
    val columns = rememberResizableState(TREE, EDITOR, PANEL)
    val left = rememberResizableState(HALF, HALF)
    val center = rememberResizableState(CODE, CONSOLE)
    val right = rememberResizableState(HALF, HALF)
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Toggle(label = "Project", state = columns, panel = 0)
            Toggle(label = "Terminal", state = center, panel = 1)
            Toggle(label = "Inspector", state = columns, panel = 2)
        }
        ResizablePanelGroup(
            state = columns,
            modifier = Modifier.size(width = 720.dp, height = 400.dp),
            isGripShown = true,
        ) {
            panel(minSize = MIN, isCollapsible = true) {
                ResizablePanelGroup(state = left, orientation = Orientation.Vertical, isGripShown = true) {
                    panel(minSize = MIN) { Area("Project") }
                    panel(minSize = MIN) { Area("Structure") }
                }
            }
            panel(minSize = MIN * 2) {
                ResizablePanelGroup(state = center, orientation = Orientation.Vertical, isGripShown = true) {
                    panel(minSize = MIN) { Area("core-01.cfg") }
                    panel(minSize = MIN, isCollapsible = true) { Area("Terminal") }
                }
            }
            panel(minSize = MIN, isCollapsible = true) {
                ResizablePanelGroup(state = right, orientation = Orientation.Vertical, isGripShown = true) {
                    panel(minSize = MIN) { Area("Inspector") }
                    panel(minSize = MIN) { Area("Problems") }
                }
            }
        }
    }
}

@Composable
private fun Toggle(label: String, state: ResizableState, panel: Int) {
    Button(
        onClick = { toggle(state = state, panel = panel) },
        variant = ButtonVariant.Outline,
        size = ButtonSize.Sm,
    ) { Text("Toggle $label") }
}

private fun toggle(state: ResizableState, panel: Int) {
    if (state.isCollapsed(panel)) {
        state.expand(panel)
        return
    }
    state.collapse(panel)
}

@Composable
private fun Area(label: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(label) }
}
