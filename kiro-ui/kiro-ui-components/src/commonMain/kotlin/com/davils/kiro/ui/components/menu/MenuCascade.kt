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

package com.davils.kiro.ui.components.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import com.davils.kiro.ui.components.theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
internal class MenuCascade(
    private val scope: CoroutineScope,
    private val pause: Long,
    private val onClosed: () -> Unit,
) {
    val chain = MenuChain()

    private var isClosing = false

    fun dismiss() {
        if (isClosing) return
        isClosing = true
        scope.launch {
            chain.closeFromDeepest(pause)
            onClosed()
            isClosing = false
        }
    }
}

@Composable
internal fun rememberMenuCascade(onClosed: () -> Unit): MenuCascade {
    val scope = rememberCoroutineScope()
    val motion = theme.motion
    val pause = 0L.takeIf { motion.isReduced } ?: motion.overlay.exitDuration.inWholeMilliseconds
    val latest by rememberUpdatedState(onClosed)
    return remember(scope, pause) { MenuCascade(scope = scope, pause = pause, onClosed = { latest() }) }
}
