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
import androidx.compose.runtime.remember

@Composable
internal fun <T> latched(value: T, isOpen: Boolean): T {
    val held = remember { LatchedValue(value) }
    return held.follow(next = value, isOpen = isOpen)
}

internal class LatchedValue<T>(initial: T) {
    private var held: T = initial

    fun follow(next: T, isOpen: Boolean): T {
        if (isOpen) held = next
        return held
    }
}
