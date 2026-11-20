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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.table.Table
import com.davils.kiro.ui.components.text.Text

private val invoices: List<Pair<String, String>> = listOf(
    "INV001" to "250.00",
    "INV002" to "150.00",
    "INV003" to "350.00",
)

@Composable
internal fun TableDemo() {
    Table(modifier = Modifier.width(480.dp)) {
        caption("A list of your recent invoices.")
        header {
            cell { Text("Invoice") }
            cell(alignment = Alignment.End) { Text("Amount") }
        }
        invoices.forEach { (invoice, amount) ->
            row {
                cell { Text(invoice) }
                cell(alignment = Alignment.End) { Text(amount) }
            }
        }
    }
}
