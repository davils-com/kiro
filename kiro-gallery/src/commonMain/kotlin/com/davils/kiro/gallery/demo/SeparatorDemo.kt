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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.separator.Separator
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun SeparatorDemo() {
    Column(modifier = Modifier.width(320.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("kide UI")
        Separator()
        Row(
            modifier = Modifier.height(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Blog")
            Separator(orientation = Orientation.Vertical)
            Text("Docs")
            Separator(orientation = Orientation.Vertical)
            Text("Source")
        }
    }
}
