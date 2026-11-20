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

package com.davils.kiro.ui.components

internal object Messages {
    object Calendar {
        const val nextMonth: String = "Go to the next month"

        const val previousMonth: String = "Go to the previous month"
    }

    object Carousel {
        const val next: String = "Next slide"

        const val previous: String = "Previous slide"
    }

    object CodeBlock {
        const val copied: String = "Copied"

        const val copy: String = "Copy"
    }

    object Combobox {
        const val empty: String = "No items found."

        const val placeholder: String = "Select an item..."

        const val search: String = "Search..."
    }

    object Command {
        const val all: String = "All"

        const val empty: String = "No results found."

        const val placeholder: String = "Type a command or search..."
    }

    object DataTable {
        const val empty: String = "No results."
    }

    object DatePicker {
        const val placeholder: String = "Pick a date"
    }

    object Dialog {
        const val close: String = "Close"
    }

    object MessageScroller {
        const val scrollToEnd: String = "Scroll to end"
    }

    object Pagination {
        const val next: String = "Next"

        const val previous: String = "Previous"
    }

    object Questionnaire {
        const val next: String = "Next"

        const val previous: String = "Previous"

        fun progress(current: Int, total: Int): String = "Question $current of $total"

        const val required: String = "Please answer this question."

        const val skip: String = "Skip"

        const val submit: String = "Submit"
    }

    object Resizable {
        const val junction: String = "Resize in both directions"
    }

    object Sidebar {
        const val toggle: String = "Toggle Sidebar"
    }

    object Spinner {
        const val loading: String = "Loading"
    }

    object Toast {
        const val close: String = "Close"
    }
}
