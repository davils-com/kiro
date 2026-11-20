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

package com.davils.kiro.ui.components.input

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Immutable

@Immutable
public class InputOptions(
    public val isSecure: Boolean = false,
    public val isReadOnly: Boolean = false,
    public val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    /**
     * Returns a copy of this [InputOptions] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        isSecure: Boolean = this.isSecure,
        isReadOnly: Boolean = this.isReadOnly,
        keyboardOptions: KeyboardOptions = this.keyboardOptions,
    ): InputOptions = InputOptions(
        isSecure = isSecure,
        isReadOnly = isReadOnly,
        keyboardOptions = keyboardOptions,
    )

    override fun equals(other: Any?): Boolean = other is InputOptions && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "InputOptions(" +
        "isSecure=$isSecure, " +
        "isReadOnly=$isReadOnly, " +
        "keyboardOptions=$keyboardOptions)"

    private fun values(): List<Any?> = listOf(
        isSecure,
        isReadOnly,
        keyboardOptions,
    )
}
