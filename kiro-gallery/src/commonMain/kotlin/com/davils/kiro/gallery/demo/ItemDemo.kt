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
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.item.Item
import com.davils.kiro.ui.components.item.ItemActions
import com.davils.kiro.ui.components.item.ItemContent
import com.davils.kiro.ui.components.item.ItemDescription
import com.davils.kiro.ui.components.item.ItemTitle
import com.davils.kiro.ui.components.item.ItemVariant
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun ItemDemo() {
    Item(modifier = Modifier.width(420.dp), variant = ItemVariant.Outline) {
        ItemContent {
            ItemTitle(text = "Two-factor authentication")
            ItemDescription(text = "Verify via email or phone number.")
        }
        ItemActions { Button(onClick = {}, size = ButtonSize.Sm) { Text("Enable") } }
    }
}
