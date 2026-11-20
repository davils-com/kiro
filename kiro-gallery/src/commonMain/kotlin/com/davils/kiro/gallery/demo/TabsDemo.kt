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
import com.davils.kiro.ui.components.tabs.Tabs
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun TabsDemo() {
    var tab by remember { mutableStateOf("account") }
    Tabs(selected = tab, onSelectedChange = { chosen -> tab = chosen }, modifier = Modifier.width(400.dp)) {
        tab(value = "account", content = { Text("Make changes to your account here.") }) { Text("Account") }
        tab(value = "password", content = { Text("Change your password here.") }) { Text("Password") }
    }
}
