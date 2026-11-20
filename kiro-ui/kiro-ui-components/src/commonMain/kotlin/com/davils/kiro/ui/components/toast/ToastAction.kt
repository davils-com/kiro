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

package com.davils.kiro.ui.components.toast

import androidx.compose.runtime.Immutable

@Immutable
public class ToastAction(
    public val label: String,
    public val onClick: () -> Unit,
) {
    /**
     * Returns a copy of this [ToastAction] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(label: String = this.label, onClick: () -> Unit = this.onClick): ToastAction = ToastAction(
        label = label,
        onClick = onClick,
    )

    override fun equals(other: Any?): Boolean = other is ToastAction && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ToastAction(" +
        "label=$label, " +
        "onClick=$onClick)"

    private fun values(): List<Any?> = listOf(
        label,
        onClick,
    )
}
