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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.empty.Empty
import com.davils.kiro.ui.components.empty.EmptyContent
import com.davils.kiro.ui.components.empty.EmptyDescription
import com.davils.kiro.ui.components.empty.EmptyHeader
import com.davils.kiro.ui.components.empty.EmptyMedia
import com.davils.kiro.ui.components.empty.EmptyMediaVariant
import com.davils.kiro.ui.components.empty.EmptyTitle
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.lucide.Inbox
import com.davils.kiro.ui.icons.lucide.Lucide

@Composable
internal fun EmptyDemo() {
    Empty(modifier = Modifier.width(420.dp)) {
        EmptyHeader {
            EmptyMedia(
                variant = EmptyMediaVariant.Icon,
            ) { Icon(icon = Lucide.Inbox, contentDescription = null) }
            EmptyTitle(text = "No projects yet")
            EmptyDescription(
                text = "You have not created any projects yet. Get started by creating your first project.",
            )
        }
        EmptyContent { Button(onClick = {}) { Text("Create project") } }
    }
}
