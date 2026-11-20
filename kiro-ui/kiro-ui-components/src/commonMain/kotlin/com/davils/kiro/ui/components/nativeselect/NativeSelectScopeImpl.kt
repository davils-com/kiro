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

internal class NativeSelectScopeImpl<T> : NativeSelectScope<T> {
    private val collected = mutableListOf<NativeSelectEntry<T>>()

    val entries: List<NativeSelectEntry<T>> get() = collected.toList()

    override fun option(value: T, label: String, enabled: Boolean) {
        collected += NativeSelectEntry.Option(value = value, label = label, isEnabled = enabled)
    }

    override fun group(label: String, content: NativeSelectScope<T>.() -> Unit) {
        collected += NativeSelectEntry.Group(label = label)
        content()
    }
}
