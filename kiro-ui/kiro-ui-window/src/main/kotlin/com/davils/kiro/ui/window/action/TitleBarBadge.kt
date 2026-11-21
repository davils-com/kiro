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

package com.davils.kiro.ui.window.action

import androidx.compose.runtime.Immutable

/**
 * A mark on a [TitleBarButton] that draws attention to it.
 *
 * @since 1.0.0
 */
@Immutable
public sealed interface TitleBarBadge {
    /**
     * A dot without a count.
     *
     * @since 1.0.0
     */
    public data object Dot : TitleBarBadge

    /**
     * A count, which shows the limit of the style of the title bar with a plus once it is larger.
     *
     * @property value the count.
     * @since 1.0.0
     */
    @Immutable
    public class Count(public val value: Int) : TitleBarBadge {
        /**
         * Returns a copy of this [Count] with the given value replaced.
         *
         * @param value the new value of [value].
         * @return the copy.
         * @since 1.0.0
         */
        public fun copy(value: Int = this.value): Count = Count(value = value)

        override fun equals(other: Any?): Boolean = other is Count && value == other.value

        override fun hashCode(): Int = value.hashCode()

        override fun toString(): String = "Count(value=$value)"
    }
}
