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
 * The interaction state of a control, from which a [StateValue] picks its value.
 *
 * @property isHovered whether a pointer hovers the control.
 * @property isPressed whether the control is pressed.
 * @property isFocusVisible whether the control holds the focus of the keyboard.
 * @property isDisabled whether the control is disabled.
 * @property isSelected whether the control is selected or checked.
 * @property isExpanded whether the control is expanded, such as an open trigger.
 * @property isInvalid whether the value of the control is invalid.
 * @since 1.0.0
 */
@Immutable
public class InteractionState(
    public val isHovered: Boolean = false,
    public val isPressed: Boolean = false,
    public val isFocusVisible: Boolean = false,
    public val isDisabled: Boolean = false,
    public val isSelected: Boolean = false,
    public val isExpanded: Boolean = false,
    public val isInvalid: Boolean = false,
) {
    /**
     * Returns a copy of this [InteractionState] with the given values replaced and every other value kept.
     *
     * @param isHovered the new value of [isHovered].
     * @param isPressed the new value of [isPressed].
     * @param isFocusVisible the new value of [isFocusVisible].
     * @param isDisabled the new value of [isDisabled].
     * @param isSelected the new value of [isSelected].
     * @param isExpanded the new value of [isExpanded].
     * @param isInvalid the new value of [isInvalid].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        isHovered: Boolean = this.isHovered,
        isPressed: Boolean = this.isPressed,
        isFocusVisible: Boolean = this.isFocusVisible,
        isDisabled: Boolean = this.isDisabled,
        isSelected: Boolean = this.isSelected,
        isExpanded: Boolean = this.isExpanded,
        isInvalid: Boolean = this.isInvalid,
    ): InteractionState = InteractionState(
        isHovered = isHovered,
        isPressed = isPressed,
        isFocusVisible = isFocusVisible,
        isDisabled = isDisabled,
        isSelected = isSelected,
        isExpanded = isExpanded,
        isInvalid = isInvalid,
    )

    override fun equals(other: Any?): Boolean = other is InteractionState && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "InteractionState(" +
        "isHovered=$isHovered, " +
        "isPressed=$isPressed, " +
        "isFocusVisible=$isFocusVisible, " +
        "isDisabled=$isDisabled, " +
        "isSelected=$isSelected, " +
        "isExpanded=$isExpanded, " +
        "isInvalid=$isInvalid)"

    private fun values(): List<Any?> = listOf(
        isHovered,
        isPressed,
        isFocusVisible,
        isDisabled,
        isSelected,
        isExpanded,
        isInvalid,
    )
}
