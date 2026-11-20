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

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.trace.Trace
import com.davils.kiro.ui.components.trace.TraceCollapsible
import com.davils.kiro.ui.components.trace.TraceCollapsibleContent
import com.davils.kiro.ui.components.trace.TraceCollapsibleTrigger
import com.davils.kiro.ui.components.trace.TraceDescription
import com.davils.kiro.ui.components.trace.TraceLine
import com.davils.kiro.ui.components.trace.TraceStatus
import com.davils.kiro.ui.components.trace.TraceTitle

@Composable
internal fun TraceDemo() {
    var isExpanded by remember { mutableStateOf(false) }
    Trace(modifier = Modifier.width(420.dp)) {
        TraceLine(status = TraceStatus.Done) {
            TraceTitle(text = "Read the inventory")
            TraceDescription(text = "1.2 s")
        }
        TraceLine(status = TraceStatus.Running) { TraceTitle(text = "Checking the ports") }
        TraceLine(status = TraceStatus.Pending) { TraceTitle(text = "Write the report") }
        TraceCollapsible(expanded = isExpanded, onExpandedChange = { open -> isExpanded = open }) {
            TraceCollapsibleTrigger { TraceTitle(text = "Thought for 3 seconds") }
            TraceCollapsibleContent { Text("Compared the firewall rules with the audit.") }
        }
    }
}
