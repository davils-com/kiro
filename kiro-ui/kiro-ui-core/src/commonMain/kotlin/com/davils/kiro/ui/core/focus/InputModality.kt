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

package com.davils.kiro.ui.core.focus

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Remembers whether the user last worked with the keyboard or with a pointer, so that controls show their focus ring
 * only to users of the keyboard, like the CSS pseudo class `:focus-visible`.
 *
 * A [com.davils.kiro.ui.core.ThemeProvider] creates one for its content and feeds it from the key and pointer events
 * it sees. The state is snapshot state, so reading [isKeyboard] in a composition recomposes it on a change. It is not
 * safe to change from several threads at once; change it from the thread of the composition.
 *
 * @since 1.0.0
 */
@Stable
public class InputModality {
    /**
     * Whether the last input came from the keyboard; false until the first key is pressed and after every press of a
     * pointer.
     *
     * @since 1.0.0
     */
    public var isKeyboard: Boolean by mutableStateOf(false)
        private set

    /**
     * Records that a key was pressed.
     *
     * @since 1.0.0
     */
    public fun onKeyboard() {
        isKeyboard = true
    }

    /**
     * Records that a pointer was pressed.
     *
     * @since 1.0.0
     */
    public fun onPointer() {
        isKeyboard = false
    }
}
