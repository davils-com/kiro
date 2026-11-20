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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.accordion.Accordion
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun AccordionDemo() {
    var expanded by remember { mutableStateOf(setOf("shipping")) }
    Box(modifier = Modifier.size(width = 360.dp, height = 240.dp), contentAlignment = Alignment.TopStart) {
        Accordion(
            expanded = expanded,
            onExpandedChange = { values -> expanded = values },
        ) {
            item(value = "shipping", trigger = { Text("What are your shipping options?") }) {
                Text("We offer standard shipping in five days and express shipping in two.")
            }
            item(value = "returns", trigger = { Text("What is your return policy?") }) {
                Text("Returns are accepted within thirty days of delivery.")
            }
            item(value = "support", trigger = { Text("How can I contact support?") }) {
                Text("Write to our support team at any time.")
            }
        }
    }
}
