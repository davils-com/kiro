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

/**
 * The scope in which a [Table] declares its rows, in the order they are shown, and its caption.
 *
 * @since 1.0.0
 */
public interface TableScope {
    /**
     * Adds a header row, at least as high as the head height of the style, in the text style and color of the head.
     *
     * @param content declares the cells of the row.
     * @since 1.0.0
     */
    public fun header(content: TableRowScope.() -> Unit)

    /**
     * Adds a body row.
     *
     * @param isSelected whether the row is selected, which its background shows.
     * @param onClick called when the row is clicked, or `null` for a row that cannot be clicked.
     * @param content declares the cells of the row.
     * @since 1.0.0
     */
    public fun row(isSelected: Boolean = false, onClick: (() -> Unit)? = null, content: TableRowScope.() -> Unit)

    /**
     * Adds a footer row on the footer background, in the footer text style, with a line above the first footer row.
     *
     * @param content declares the cells of the row.
     * @since 1.0.0
     */
    public fun footer(content: TableRowScope.() -> Unit)

    /**
     * Sets the caption shown centered below the table; when it is called more than once, the last text is shown.
     *
     * @param text the text of the caption.
     * @since 1.0.0
     */
    public fun caption(text: String)
}
