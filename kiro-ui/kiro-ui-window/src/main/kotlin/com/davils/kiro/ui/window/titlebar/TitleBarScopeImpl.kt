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

package com.davils.kiro.ui.window.titlebar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

internal class TitleBarScopeImpl : TitleBarScope {
    private val starts = mutableListOf<@Composable RowScope.() -> Unit>()
    private val centers = mutableListOf<@Composable RowScope.() -> Unit>()
    private val ends = mutableListOf<@Composable RowScope.() -> Unit>()

    val slots: TitleBarSlots
        get() = TitleBarSlots(start = starts.toList(), center = centers.toList(), end = ends.toList())

    override fun start(content: @Composable RowScope.() -> Unit) {
        starts += content
    }

    override fun center(content: @Composable RowScope.() -> Unit) {
        centers += content
    }

    override fun end(content: @Composable RowScope.() -> Unit) {
        ends += content
    }
}
