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

package com.davils.kiro.ui.components.pagination

import androidx.compose.runtime.Immutable

@Immutable
public sealed interface PaginationItem {
    @Immutable
    public class Page(
        public val number: Int,
    ) : PaginationItem {
        /**
         * Returns a copy of this [Page] with the given values replaced and every other value kept.
         *
         * @since 0.1.0
         */
        public fun copy(number: Int = this.number): Page = Page(
            number = number,
        )

        override fun equals(other: Any?): Boolean = other is Page && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Page(" +
            "number=$number)"

        private fun values(): List<Any?> = listOf(
            number,
        )
    }

    @Immutable
    public class Ellipsis(
        public val position: Int,
    ) : PaginationItem {
        /**
         * Returns a copy of this [Ellipsis] with the given values replaced and every other value kept.
         *
         * @since 0.1.0
         */
        public fun copy(position: Int = this.position): Ellipsis = Ellipsis(
            position = position,
        )

        override fun equals(other: Any?): Boolean = other is Ellipsis && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Ellipsis(" +
            "position=$position)"

        private fun values(): List<Any?> = listOf(
            position,
        )
    }
}
