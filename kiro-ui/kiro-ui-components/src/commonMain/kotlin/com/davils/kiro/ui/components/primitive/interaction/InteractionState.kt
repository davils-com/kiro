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

package com.davils.kiro.ui.components.primitive.interaction

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.davils.kiro.ui.core.focus.collectIsFocusVisibleAsState
import com.davils.kiro.ui.core.state.InteractionState

@Composable
public fun InteractionSource.collectInteractionState(
    isDisabled: Boolean = false,
    isSelected: Boolean = false,
    isExpanded: Boolean = false,
    isInvalid: Boolean = false,
): InteractionState {
    val isHovered by collectIsHoveredAsState()
    val isPressed by collectIsPressedAsState()
    val isFocusVisible by collectIsFocusVisibleAsState()
    return InteractionState(
        isHovered = isHovered,
        isPressed = isPressed,
        isFocusVisible = isFocusVisible,
        isDisabled = isDisabled,
        isSelected = isSelected,
        isExpanded = isExpanded,
        isInvalid = isInvalid,
    )
}
