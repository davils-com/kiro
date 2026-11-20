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

package com.davils.kiro.ui.components.nativeselect

import androidx.compose.ui.input.key.Key

internal val arrowSteps: Map<Key, Int> = mapOf(Key.DirectionDown to 1, Key.DirectionUp to -1)

internal fun <T> stepFrom(options: List<NativeSelectEntry.Option<T>>, index: Int, delta: Int): Int? {
    val reachable = generateSequence(index + delta) { next -> next + delta }
    val inside = reachable.takeWhile { next -> next in options.indices }
    return inside.firstOrNull { next -> options[next].isEnabled }
}

internal fun <T> indexOf(options: List<NativeSelectEntry.Option<T>>, value: T): Int =
    options.indexOfFirst { option -> option.value == value }
