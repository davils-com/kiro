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

package com.davils.kiro.gallery.playground

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.demo.artFrame
import com.davils.kiro.gallery.theme
import com.davils.kiro.ui.components.codeblock.CodeBlock
import com.davils.kiro.ui.components.codeblock.CodeBlockContent
import com.davils.kiro.ui.components.codeblock.CodeBlockCopyButton
import com.davils.kiro.ui.components.codeblock.CodeBlockHeader
import com.davils.kiro.ui.components.codeblock.CodeBlockTitle
import com.davils.kiro.ui.motion.color.ArtPalette
import com.davils.kiro.ui.motion.diagnostics.ArtDiagnostics
import com.davils.kiro.ui.motion.diagnostics.DiagnosticsOverlay
import com.davils.kiro.ui.motion.host.MotionCanvas
import com.davils.kiro.ui.motion.host.motionStyle
import com.davils.kiro.ui.motion.scene.scene

@Composable
internal fun Playground(state: PlaygroundState, modifier: Modifier = Modifier) {
    val current = theme
    val palette = state.palette.palettes.resolve(current)
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        PlaygroundToolbar(state = state)
        PlaygroundStage(state = state, palette = palette)
        PlaybackControls(state = state)
        ParameterGrid(state = state, palette = palette)
        PlaygroundCode(code = codeOf(state = state, theme = current))
    }
}

@Composable
private fun PlaygroundStage(state: PlaygroundState, palette: ArtPalette) {
    val current = theme
    val art = state.spec.artOf(theme = current, palette = palette, settings = state.settings)
    val stage = remember(art, palette) {
        scene {
            this.palette = palette
            layer(art)
        }
    }
    val report: (ArtDiagnostics) -> Unit = { diagnostics -> state.diagnostics = diagnostics }
    MotionCanvas(
        scene = stage,
        modifier = Modifier.artFrame(theme = current, height = 360.dp),
        playback = state.playback,
        style = state.styleOf(motionStyle()),
        onDiagnostics = report.takeIf { state.isDiagnosticsShown },
    ) {
        state.diagnostics?.takeIf { state.isDiagnosticsShown }?.let { diagnostics ->
            DiagnosticsOverlay(
                diagnostics = diagnostics,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
            )
        }
    }
}

@Composable
private fun PlaygroundCode(code: String) {
    CodeBlock(modifier = Modifier.fillMaxWidth()) {
        CodeBlockHeader {
            CodeBlockTitle(text = "Kotlin", modifier = Modifier.weight(1f))
            CodeBlockCopyButton(value = code)
        }
        CodeBlockContent(code = code)
    }
}
