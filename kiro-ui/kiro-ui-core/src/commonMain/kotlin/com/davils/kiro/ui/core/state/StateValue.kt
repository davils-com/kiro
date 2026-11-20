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

package com.davils.kiro.ui.core.state

import androidx.compose.runtime.Immutable

/**
 * A value that depends on the interaction state of a control. A state without a value of its own falls back to
 * [default]; when several states apply, [resolve] decides which wins.
 *
 * @param T the type of the value.
 * @property default the value when no other state applies.
 * @property hovered the value while hovered.
 * @property pressed the value while pressed.
 * @property focusVisible the value while focused by the keyboard.
 * @property disabled the value while disabled.
 * @property selected the value while selected.
 * @property expanded the value while expanded.
 * @property invalid the value while invalid.
 * @since 0.1.0
 */
@Immutable
public class StateValue<out T>(
    public val default: T,
    public val hovered: T? = null,
    public val pressed: T? = null,
    public val focusVisible: T? = null,
    public val disabled: T? = null,
    public val selected: T? = null,
    public val expanded: T? = null,
    public val invalid: T? = null,
) {
    /**
     * Returns a copy of this [StateValue] with the given values replaced and every other value kept.
     *
     * @param default the new value of [default].
     * @param hovered the new value of [hovered].
     * @param pressed the new value of [pressed].
     * @param focusVisible the new value of [focusVisible].
     * @param disabled the new value of [disabled].
     * @param selected the new value of [selected].
     * @param expanded the new value of [expanded].
     * @param invalid the new value of [invalid].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(
        default: @UnsafeVariance T = this.default,
        hovered: @UnsafeVariance T? = this.hovered,
        pressed: @UnsafeVariance T? = this.pressed,
        focusVisible: @UnsafeVariance T? = this.focusVisible,
        disabled: @UnsafeVariance T? = this.disabled,
        selected: @UnsafeVariance T? = this.selected,
        expanded: @UnsafeVariance T? = this.expanded,
        invalid: @UnsafeVariance T? = this.invalid,
    ): StateValue<T> = StateValue(
        default = default,
        hovered = hovered,
        pressed = pressed,
        focusVisible = focusVisible,
        disabled = disabled,
        selected = selected,
        expanded = expanded,
        invalid = invalid,
    )

    override fun equals(other: Any?): Boolean = other is StateValue<*> && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "StateValue(" +
        "default=${default ?: "null"}, " +
        "hovered=${hovered ?: "null"}, " +
        "pressed=${pressed ?: "null"}, " +
        "focusVisible=${focusVisible ?: "null"}, " +
        "disabled=${disabled ?: "null"}, " +
        "selected=${selected ?: "null"}, " +
        "expanded=${expanded ?: "null"}, " +
        "invalid=${invalid ?: "null"})"

    private fun values(): List<Any?> = listOf(
        default,
        hovered,
        pressed,
        focusVisible,
        disabled,
        selected,
        expanded,
        invalid,
    )
}
