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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.marker.Marker
import com.davils.kiro.ui.components.marker.MarkerVariant
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun MarkerDemo() {
    Column(modifier = Modifier.width(420.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Marker { Text("Conversation started") }
        Marker(variant = MarkerVariant.Separator) { Text("Today") }
        Marker(variant = MarkerVariant.Border) { Text("Earlier") }
    }
}
