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
import com.davils.kiro.ui.components.field.Field
import com.davils.kiro.ui.components.field.FieldDescription
import com.davils.kiro.ui.components.field.FieldError
import com.davils.kiro.ui.components.field.FieldGroup
import com.davils.kiro.ui.components.field.FieldLabel
import com.davils.kiro.ui.components.field.FieldLegend
import com.davils.kiro.ui.components.field.FieldSet
import com.davils.kiro.ui.components.input.Input

@Composable
internal fun FieldDemo() {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("ada") }
    FieldSet(modifier = Modifier.width(420.dp)) {
        FieldLegend(text = "Profile")
        FieldGroup {
            Field {
                FieldLabel(text = "Full name")
                Input(value = name, onValueChange = { typed -> name = typed }, placeholder = "Ada Lovelace")
                FieldDescription(text = "This appears on your invoices.")
            }
            Field(isInvalid = true) {
                FieldLabel(text = "Username")
                Input(value = username, onValueChange = { typed -> username = typed }, isInvalid = true)
                FieldError(messages = listOf("This username is already taken."))
            }
        }
    }
}
