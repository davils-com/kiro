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
import com.davils.kiro.ui.components.inputgroup.InputGroup
import com.davils.kiro.ui.components.inputgroup.InputGroupAlign
import com.davils.kiro.ui.components.inputgroup.InputGroupText
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun InputGroupDemo() {
    var domain by remember { mutableStateOf("") }
    InputGroup(modifier = Modifier.width(360.dp)) {
        addon(align = InputGroupAlign.InlineStart) { InputGroupText { Text("https://") } }
        input(value = domain, onValueChange = { typed -> domain = typed }, placeholder = "example.com")
        addon(align = InputGroupAlign.InlineEnd) { InputGroupText { Text(".com") } }
    }
}
