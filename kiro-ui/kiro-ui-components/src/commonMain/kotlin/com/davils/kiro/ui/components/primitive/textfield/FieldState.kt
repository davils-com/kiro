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

package com.davils.kiro.ui.components.primitive.textfield

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.davils.kiro.ui.core.state.InteractionState

@Composable
internal fun InteractionSource.collectFieldState(isDisabled: Boolean, isInvalid: Boolean): InteractionState {
    val isHovered by collectIsHoveredAsState()
    val isFocused by collectIsFocusedAsState()
    return InteractionState(
        isHovered = isHovered,
        isFocusVisible = isFocused,
        isDisabled = isDisabled,
        isInvalid = isInvalid,
    )
}
