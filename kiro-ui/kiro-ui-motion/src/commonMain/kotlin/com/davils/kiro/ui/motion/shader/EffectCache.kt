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

package com.davils.kiro.ui.motion.shader

import org.jetbrains.skia.RuntimeEffect

private const val CAPACITY: Int = 16

internal class EffectCache<T : Any>(
    private val capacity: Int,
    private val compile: (String) -> T,
    private val release: (T) -> Unit,
) {
    private val entries: LinkedHashMap<String, Result<T>> = LinkedHashMap()

    val size: Int get() = entries.size

    fun effectOf(source: String): T? {
        val entry = entries.remove(source) ?: runCatching { compile(source) }
        entries[source] = entry
        trim()
        return entry.getOrNull()
    }

    fun compiles(source: String): Boolean = effectOf(source = source) != null

    fun contains(source: String): Boolean = entries.containsKey(source)

    fun clear() {
        for (entry in entries.values) releaseOf(entry)
        entries.clear()
    }

    private fun trim() {
        while (entries.size > capacity.coerceAtLeast(minimumValue = 1)) {
            val oldest = entries.keys.first()
            releaseOf(entries.remove(oldest))
        }
    }

    private fun releaseOf(entry: Result<T>?) {
        val effect = entry?.getOrNull() ?: return
        release(effect)
    }
}

internal val shaderEffects: EffectCache<RuntimeEffect> = EffectCache(
    capacity = CAPACITY,
    compile = RuntimeEffect::makeForShader,
    release = RuntimeEffect::close,
)
