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

import com.davils.kiro.ui.core.Theme

private const val PALETTE_USE: String = "palette."
private const val INDENT: String = "    "

internal fun codeOf(state: PlaygroundState, theme: Theme): String {
    val palette = state.palette.palettes.resolve(theme)
    val changes = state.spec.changesOf(theme = theme, palette = palette, settings = state.settings)
    val isPaletteChanged = state.palette != PalettePreset.Refined
    val lines = buildList {
        addAll(paletteLines(preset = state.palette, isUsed = changes.any { change -> PALETTE_USE in change }))
        add("MotionCanvas(")
        add("${INDENT}scene = scene {")
        if (isPaletteChanged) add("$INDENT${INDENT}this.palette = palette")
        addAll(layerLines(spec = state.spec, changes = changes).map { line -> "$INDENT$INDENT$line" })
        add("$INDENT},")
        addAll(playbackLines(speed = state.playback.speed))
        addAll(styleLines(state = state))
        add(")")
    }
    return lines.joinToString(separator = "\n")
}

private fun paletteLines(preset: PalettePreset, isUsed: Boolean): List<String> {
    val custom = "val palette = ArtPalettes.${preset.name}.resolve(Theme.current)"
    if (preset != PalettePreset.Refined) return listOf(custom)
    return listOfNotNull("val palette = artPalette()".takeIf { isUsed })
}

private fun layerLines(spec: ArtSpec<*>, changes: List<String>): List<String> {
    if (changes.isEmpty()) return listOf("layer(${spec.art}())")
    val fields = changes.map { change -> "$INDENT$INDENT$INDENT$change," }
    val opening = listOf("layer(", "$INDENT${spec.art}(", "$INDENT${INDENT}style = ${spec.styleFactory}.copy(")
    val closing = listOf("$INDENT$INDENT),", "$INDENT),", ")")
    return opening + fields + closing
}

private fun playbackLines(speed: Float): List<String> {
    if (speed == 1f) return emptyList()
    return listOf("${INDENT}playback = rememberArtPlayback(speed = ${floatCode(speed)}),")
}

private fun styleLines(state: PlaygroundState): List<String> {
    val entries = listOfNotNull(
        state.seed?.let { seed -> "seed = ArtSeed(value = $seed)" },
        state.quality?.let { level -> "quality = QualityPolicy.Fixed(level = QualityLevel.${level.name})" },
    )
    if (entries.size < 2) return entries.map { entry -> "${INDENT}style = motionStyle().copy($entry)," }
    val fields = entries.map { entry -> "$INDENT$INDENT$entry," }
    return listOf("${INDENT}style = motionStyle().copy(") + fields + "$INDENT),"
}
