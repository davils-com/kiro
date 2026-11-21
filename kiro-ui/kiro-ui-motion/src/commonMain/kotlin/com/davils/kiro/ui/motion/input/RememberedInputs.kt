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

package com.davils.kiro.ui.motion.input

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Remembers the inputs an application feeds into a [com.davils.kiro.ui.motion.host.MotionCanvas]: the progress of a
 * task, the scroll and the signals. The canvas adds the pointer itself.
 *
 * @param progress the progress of a task between `0` and `1`, or `null` when there is none.
 * @param scroll the scroll position that layers follow with their parallax, in pixels.
 * @param signals the values of the signals.
 * @return the inputs, the same object as long as the values stay the same.
 * @since 1.0.0
 */
@Composable
public fun rememberArtInputs(
    progress: Float? = null,
    scroll: Float = 0f,
    signals: Map<SignalKey, Float> = emptyMap(),
): ArtInputs = remember(key1 = progress, key2 = scroll, key3 = signals) {
    ArtInputs.None.copy(progress = progress, scroll = scroll, signals = signals)
}
