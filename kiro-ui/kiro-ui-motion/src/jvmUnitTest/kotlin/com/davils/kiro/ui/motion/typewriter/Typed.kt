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

import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.core.Theme
import kotlin.time.Duration.Companion.milliseconds

internal const val PHRASE: String = "Hello world"

internal val typedStyle: TypewriterStyle = defaultTypewriterStyle(Theme.light()).copy(
    typeDelay = 100.milliseconds,
    deleteDelay = 50.milliseconds,
    jitter = 0f,
    hold = 1000.milliseconds,
)

internal val typedScript: TypewriterScript = typewriter { type(PHRASE) }

internal class Typed(
    val theme: Theme = Theme.light(),
    val script: TypewriterScript = typedScript,
    val style: TypewriterStyle = typedStyle,
    val direction: LayoutDirection = LayoutDirection.Ltr,
    val onFinished: () -> Unit = {},
)
