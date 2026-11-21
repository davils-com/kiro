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

package com.davils.kiro.ui.motion.typewriter

import kotlin.time.Duration

/**
 * Writes a [TypewriterScript].
 *
 * ```kotlin
 * val script = typewriter {
 *     type("Hello")
 *     pause()
 *     delete(count = 5)
 *     type("Welcome to Kiro")
 * }
 * ```
 *
 * @param block the steps of the script.
 * @return the script.
 * @since 1.0.0
 */
public fun typewriter(block: TypewriterScriptBuilder.() -> Unit): TypewriterScript {
    val builder = TypewriterScriptBuilder()
    builder.block()
    return builder.build()
}

/**
 * Writes a [TypewriterScript] that types each phrase, holds it, deletes it and goes on with the next one, forever.
 *
 * @param phrases the phrases in the order they are typed.
 * @param hold how long a phrase stays, or `null` for the [TypewriterStyle.hold] of the style.
 * @return the script.
 * @since 1.0.0
 */
public fun typewriterCycle(phrases: List<String>, hold: Duration? = null): TypewriterScript = typewriter {
    for (phrase in phrases) {
        type(phrase)
        pause(hold)
        delete()
    }
    repeat()
}
