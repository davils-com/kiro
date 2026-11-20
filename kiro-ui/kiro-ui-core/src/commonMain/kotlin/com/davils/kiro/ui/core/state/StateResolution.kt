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

/**
 * Returns the value of this [StateValue] for an interaction state.
 *
 * When several states apply, the first of this order that has a value wins: disabled, invalid, pressed, focus
 * visible, hovered, expanded, selected. Without a value for any state that applies, the result is
 * [StateValue.default].
 *
 * @param T the type of the value.
 * @param state the interaction state of the control.
 * @return the value for [state].
 * @since 1.0.0
 */
public fun <T> StateValue<T>.resolve(state: InteractionState): T = disabled.whenever(state.isDisabled)
    ?: invalid.whenever(state.isInvalid)
    ?: pressed.whenever(state.isPressed)
    ?: focusVisible.whenever(state.isFocusVisible)
    ?: hovered.whenever(state.isHovered)
    ?: expanded.whenever(state.isExpanded)
    ?: selected.whenever(state.isSelected)
    ?: default

private fun <T> T?.whenever(isActive: Boolean): T? {
    if (!isActive) return null
    return this
}
