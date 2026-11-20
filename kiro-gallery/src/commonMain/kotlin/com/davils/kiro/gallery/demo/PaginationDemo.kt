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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.davils.kiro.ui.components.pagination.Pagination
import com.davils.kiro.ui.components.pagination.PaginationEllipsis
import com.davils.kiro.ui.components.pagination.PaginationLink
import com.davils.kiro.ui.components.pagination.PaginationNext
import com.davils.kiro.ui.components.pagination.PaginationPrevious
import com.davils.kiro.ui.components.text.Text

private const val PAGES: Int = 3

@Composable
internal fun PaginationDemo() {
    var page by remember { mutableIntStateOf(1) }
    Pagination {
        PaginationPrevious(onClick = { page = (page - 1).coerceAtLeast(minimumValue = 1) }, enabled = page > 1)
        for (number in 1..PAGES) {
            PaginationLink(onClick = { page = number }, isActive = number == page) { Text("$number") }
        }
        PaginationEllipsis(contentDescription = "More pages")
        PaginationNext(onClick = { page = (page + 1).coerceAtMost(maximumValue = PAGES) }, enabled = page < PAGES)
    }
}
