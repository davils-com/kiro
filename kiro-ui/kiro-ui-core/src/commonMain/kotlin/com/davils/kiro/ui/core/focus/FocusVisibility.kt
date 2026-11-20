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

package com.davils.kiro.ui.core.focus

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput

/**
 * The [InputModality] of the composition, or null outside of a [com.davils.kiro.ui.core.ThemeProvider], where every
 * focus counts as visible.
 *
 * @since 1.0.0
 */
public val LocalInputModality: ProvidableCompositionLocal<InputModality?> = staticCompositionLocalOf { null }

/**
 * Returns whether the control of this interaction source holds the focus and the user works with the keyboard, which
 * is when the control shows its focus ring.
 *
 * @return a state that is true while the focus is visible.
 * @since 1.0.0
 */
@Composable
public fun InteractionSource.collectIsFocusVisibleAsState(): State<Boolean> {
    val isFocused by collectIsFocusedAsState()
    val modality = LocalInputModality.current
    return remember(this, modality) {
        derivedStateOf { isFocused && modality?.isKeyboard != false }
    }
}

internal fun Modifier.trackInputModality(modality: InputModality): Modifier {
    val keyed = onPreviewKeyEvent { event ->
        if (event.type == KeyEventType.KeyDown) modality.onKeyboard()
        false
    }
    return keyed.pointerInput(modality) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent(PointerEventPass.Initial)
                if (event.type == PointerEventType.Press) modality.onPointer()
            }
        }
    }
}
