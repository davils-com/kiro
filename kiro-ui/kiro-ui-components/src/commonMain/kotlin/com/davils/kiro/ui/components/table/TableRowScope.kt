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

package com.davils.kiro.ui.components.table

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

/**
 * The scope in which a row of a [Table] declares its cells, from the first column to the last.
 *
 * @since 1.0.0
 */
public interface TableRowScope {
    /**
     * Adds a cell to the row, vertically centered in it.
     *
     * @param span the number of columns the cell covers, at least one.
     * @param alignment the horizontal alignment of the content in the cell.
     * @param content the content of the cell.
     * @throws IllegalArgumentException if [span] is less than one.
     * @since 1.0.0
     */
    public fun cell(span: Int = 1, alignment: Alignment.Horizontal = Alignment.Start, content: @Composable () -> Unit)
}
