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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.davils.kiro.ui.motion.art.ArtSeed
import com.davils.kiro.ui.motion.diagnostics.ArtDiagnostics
import com.davils.kiro.ui.motion.host.MotionStyle
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.quality.QualityPolicy
import com.davils.kiro.ui.motion.time.ArtPlayback

@Stable
internal class PlaygroundState(spec: ArtSpec<*>) {
    var spec: ArtSpec<*> by mutableStateOf(spec)
        private set

    var settings: Map<String, Setting> by mutableStateOf(emptyMap())
        private set

    var palette: PalettePreset by mutableStateOf(PalettePreset.Refined)

    var seed: Int? by mutableStateOf(null)

    var quality: QualityLevel? by mutableStateOf(null)

    var isDiagnosticsShown: Boolean by mutableStateOf(false)

    var diagnostics: ArtDiagnostics? by mutableStateOf(null)

    val playback: ArtPlayback = ArtPlayback()

    fun select(spec: ArtSpec<*>) {
        this.spec = spec
        settings = emptyMap()
    }

    fun change(name: String, setting: Setting) {
        settings = settings + (name to setting)
    }

    fun reset() {
        settings = emptyMap()
        palette = PalettePreset.Refined
        seed = null
        quality = null
        playback.speed = 1f
    }

    fun styleOf(base: MotionStyle): MotionStyle {
        val seeded = seed?.let { value -> base.copy(seed = ArtSeed(value = value)) } ?: base
        return quality?.let { level -> seeded.copy(quality = QualityPolicy.Fixed(level = level)) } ?: seeded
    }
}

@Composable
internal fun rememberPlaygroundState(): PlaygroundState = remember { PlaygroundState(spec = artSpecs.first()) }
