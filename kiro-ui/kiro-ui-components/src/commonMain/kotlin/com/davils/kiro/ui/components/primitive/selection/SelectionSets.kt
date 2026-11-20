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

package com.davils.kiro.ui.components.primitive.selection

internal fun <T> toggledIn(selected: Set<T>, value: T, isMultiple: Boolean): Set<T> {
    if (value in selected) return selected - value
    return added(selected = selected, value = value, isMultiple = isMultiple)
}

private fun <T> added(selected: Set<T>, value: T, isMultiple: Boolean): Set<T> {
    if (isMultiple) return selected + value
    return setOf(value)
}
