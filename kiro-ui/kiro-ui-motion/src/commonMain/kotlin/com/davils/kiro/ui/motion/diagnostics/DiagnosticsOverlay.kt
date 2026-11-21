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

package com.davils.kiro.ui.motion.diagnostics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Shows the [ArtDiagnostics] of a [com.davils.kiro.ui.motion.host.MotionCanvas] in a small label, such as on top of the
 * canvas while tuning a scene.
 *
 * ```kotlin
 * var diagnostics by remember { mutableStateOf<ArtDiagnostics?>(null) }
 * MotionCanvas(scene = sky, onDiagnostics = { diagnostics = it }) {
 *     diagnostics?.let { DiagnosticsOverlay(diagnostics = it, modifier = Modifier.align(Alignment.TopEnd)) }
 * }
 * ```
 *
 * @param diagnostics the diagnostics to show.
 * @param modifier the modifier of the label.
 * @param style how the label looks.
 * @param text the text of the label for the diagnostics, by default like `60.0 fps · 16.7 ms · 3 layers · High`.
 * @since 1.0.0
 */
@Composable
public fun DiagnosticsOverlay(
    diagnostics: ArtDiagnostics,
    modifier: Modifier = Modifier,
    style: DiagnosticsOverlayStyle = diagnosticsOverlayStyle(),
    text: (ArtDiagnostics) -> String = ::diagnosticsText,
) {
    BasicText(
        text = text(diagnostics),
        modifier = modifier
            .background(color = style.container, shape = style.shape)
            .padding(horizontal = style.horizontalPadding, vertical = style.verticalPadding),
        style = style.textStyle.copy(color = style.content),
    )
}
