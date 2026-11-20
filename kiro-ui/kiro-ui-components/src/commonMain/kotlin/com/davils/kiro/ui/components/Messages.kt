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
        const val NEXT_MONTH: String = "Go to the next month"

        const val PREVIOUS_MONTH: String = "Go to the previous month"
    }

    object Carousel {
        const val NEXT: String = "Next slide"

        const val PREVIOUS: String = "Previous slide"
    }

    object CodeBlock {
        const val COPIED: String = "Copied"

        const val COPY: String = "Copy"
    }

    object Combobox {
        const val EMPTY: String = "No items found."

        const val PLACEHOLDER: String = "Select an item..."

        const val SEARCH: String = "Search..."
    }

    object Command {
        const val ALL: String = "All"

        const val EMPTY: String = "No results found."

        const val PLACEHOLDER: String = "Type a command or search..."
    }

    object DataTable {
        const val EMPTY: String = "No results."
    }

    object DatePicker {
        const val PLACEHOLDER: String = "Pick a date"
    }

    object Dialog {
        const val CLOSE: String = "Close"
    }

    object MessageScroller {
        const val SCROLL_TO_END: String = "Scroll to end"
    }

    object Pagination {
        const val NEXT: String = "Next"

        const val PREVIOUS: String = "Previous"
    }

    object Questionnaire {
        const val NEXT: String = "Next"

        const val PREVIOUS: String = "Previous"

        const val REQUIRED: String = "Please answer this question."

        const val SKIP: String = "Skip"

        const val SUBMIT: String = "Submit"

        fun progress(current: Int, total: Int): String = "Question $current of $total"
    }

    object Resizable {
        const val JUNCTION: String = "Resize in both directions"
    }

    object Sidebar {
        const val TOGGLE: String = "Toggle Sidebar"
    }

    object Spinner {
        const val LOADING: String = "Loading"
    }

    object Toast {
        const val CLOSE: String = "Close"
    }
}
