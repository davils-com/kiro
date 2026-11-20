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
import com.davils.kiro.ui.components.datatable.DataTable
import com.davils.kiro.ui.components.datatable.DataTableColumn
import com.davils.kiro.ui.components.text.Text

private val payments: List<Payment> = listOf(
    Payment(email = "ada@example.com", status = "Success", amount = "316.00"),
    Payment(email = "alan@example.com", status = "Processing", amount = "242.00"),
    Payment(email = "grace@example.com", status = "Failed", amount = "837.00"),
)

@Composable
internal fun DataTableDemo() {
    DataTable(
        data = payments,
        columns = listOf(
            DataTableColumn(header = "Email") { payment -> Text(payment.email) },
            DataTableColumn(header = "Status") { payment -> Text(payment.status) },
            DataTableColumn(header = "Amount") { payment -> Text(payment.amount) },
        ),
        key = { payment -> payment.email },
        modifier = Modifier.width(560.dp),
        isSelectable = true,
    )
}
