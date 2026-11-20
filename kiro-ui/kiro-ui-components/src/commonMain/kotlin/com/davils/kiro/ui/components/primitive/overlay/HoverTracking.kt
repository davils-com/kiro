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

package com.davils.kiro.ui.components.primitive.overlay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay
import kotlin.time.Duration

private val hoverEvents: Map<PointerEventType, Boolean> = mapOf(
    PointerEventType.Enter to true,
    PointerEventType.Exit to false,
)

internal fun Modifier.hoverTracking(onHover: (Boolean) -> Unit, onPress: () -> Unit = {}): Modifier = pointerInput(
    onHover,
    onPress,
) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent(PointerEventPass.Initial)
            hoverEvents[event.type]?.let(onHover)
            if (event.type == PointerEventType.Press) onPress()
        }
    }
}

@Composable
internal fun delayedVisibility(isWanted: Boolean, openDelay: Duration, closeDelay: Duration): Boolean {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(isWanted) {
        delay(openDelay.takeIf { isWanted } ?: closeDelay)
        isVisible = isWanted
    }
    return isVisible
}
