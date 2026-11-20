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

import androidx.compose.runtime.Stable
import kotlinx.coroutines.delay

@Stable
internal class MenuChain {
    private var hovered: Int = 0

    private val controllers = mutableMapOf<Int, MenuController>()

    val isPointerInside: Boolean
        get() = hovered > 0

    fun hover(isOver: Boolean) {
        hovered = (hovered + (1.takeIf { isOver } ?: -1)).coerceAtLeast(0)
    }

    fun register(depth: Int, controller: MenuController) {
        controllers[depth] = controller
    }

    fun unregister(depth: Int, controller: MenuController) {
        if (controllers[depth] === controller) controllers.remove(depth)
    }

    suspend fun closeFromDeepest(pause: Long) {
        val deepestFirst = controllers.entries.sortedByDescending { entry -> entry.key }
        val opened = deepestFirst.map { entry -> entry.value }.filter { controller -> controller.openSub != null }
        opened.forEach { controller ->
            controller.close()
            delay(pause)
        }
    }
}
