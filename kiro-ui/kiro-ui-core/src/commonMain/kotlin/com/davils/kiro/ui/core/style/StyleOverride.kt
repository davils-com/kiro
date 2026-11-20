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

package com.davils.kiro.ui.core.style

import com.davils.kiro.ui.core.Theme

internal class StyleOverride<T : Any>(val key: StyleKey<T>, private val transform: (Theme, T) -> T) {
    fun <R : Any> applyTo(key: StyleKey<R>, theme: Theme, style: R): R {
        if (key !== this.key) return style
        val own = ownStyleOf(style)
        return sameStyleOf(transform(theme, own))
    }

    @Suppress("UNCHECKED_CAST")
    private fun ownStyleOf(style: Any): T = style as T

    @Suppress("UNCHECKED_CAST")
    private fun <R : Any> sameStyleOf(style: T): R = style as R
}
