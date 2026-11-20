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

package com.davils.kiro.ui.components.questionnaire

import androidx.compose.runtime.Immutable

@Immutable
public sealed interface Answer {
    public val isEmpty: Boolean

    public class Single(
        public val value: String,
    ) : Answer {
        override val isEmpty: Boolean
            get() = false

        /**
         * Returns a copy of this [Single] with the given values replaced and every other value kept.
         *
         * @since 0.1.0
         */
        public fun copy(value: String = this.value): Single = Single(
            value = value,
        )

        override fun equals(other: Any?): Boolean = other is Single && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Single(" +
            "value=$value)"

        private fun values(): List<Any?> = listOf(
            value,
        )
    }

    public class Multiple(
        public val values: Set<String>,
    ) : Answer {
        override val isEmpty: Boolean
            get() = values.isEmpty()

        /**
         * Returns a copy of this [Multiple] with the given values replaced and every other value kept.
         *
         * @since 0.1.0
         */
        public fun copy(values: Set<String> = this.values): Multiple = Multiple(
            values = values,
        )

        override fun equals(other: Any?): Boolean = other is Multiple && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Multiple(" +
            "values=$values)"

        private fun values(): List<Any?> = listOf(
            values,
        )
    }

    public class Text(
        public val text: String,
    ) : Answer {
        override val isEmpty: Boolean
            get() = text.isBlank()

        /**
         * Returns a copy of this [Text] with the given values replaced and every other value kept.
         *
         * @since 0.1.0
         */
        public fun copy(text: String = this.text): Text = Text(
            text = text,
        )

        override fun equals(other: Any?): Boolean = other is Text && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Text(" +
            "text=$text)"

        private fun values(): List<Any?> = listOf(
            text,
        )
    }

    public data object Skipped : Answer {
        override val isEmpty: Boolean
            get() = true
    }
}
