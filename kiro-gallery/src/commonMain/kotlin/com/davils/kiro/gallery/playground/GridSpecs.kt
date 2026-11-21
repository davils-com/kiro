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

import com.davils.kiro.ui.motion.art.grid.FlickeringGridArt
import com.davils.kiro.ui.motion.art.grid.FlickeringGridArtStyle
import com.davils.kiro.ui.motion.art.grid.FlickeringGridArtStyles
import com.davils.kiro.ui.motion.art.grid.GridPatternArt
import com.davils.kiro.ui.motion.art.grid.GridPatternArtStyle
import com.davils.kiro.ui.motion.art.grid.GridPatternArtStyles
import com.davils.kiro.ui.motion.art.grid.MatrixArt
import com.davils.kiro.ui.motion.art.grid.MatrixArtStyle
import com.davils.kiro.ui.motion.art.grid.MatrixArtStyles
import com.davils.kiro.ui.motion.art.grid.RetroGridArt
import com.davils.kiro.ui.motion.art.grid.RetroGridArtStyle
import com.davils.kiro.ui.motion.art.grid.RetroGridArtStyles

internal val gridSpecs: List<ArtSpec<*>> = listOf(
    artSpec(
        name = "Flickering Grid",
        defaults = FlickeringGridArtStyles.Default::resolve,
        create = ::FlickeringGridArt,
    ) {
        dp(FlickeringGridArtStyle::square, range = 2f..15f) { copy(square = it) }
        dp(FlickeringGridArtStyle::gap, range = 0f..20f) { copy(gap = it) }
        int(FlickeringGridArtStyle::levels, range = 2..12) { copy(levels = it) }
        float(FlickeringGridArtStyle::minAlpha, range = 0f..0.7f) { copy(minAlpha = it) }
        float(FlickeringGridArtStyle::maxAlpha, range = 0.7f..1f) { copy(maxAlpha = it) }
        duration(FlickeringGridArtStyle::period, range = 0.6f..10f) { copy(period = it) }
        float(FlickeringGridArtStyle::structure, range = 0f..1f) { copy(structure = it) }
        dp(FlickeringGridArtStyle::fieldSize, range = 80f..600f) { copy(fieldSize = it) }
        duration(FlickeringGridArtStyle::drift, range = 3f..50f) { copy(drift = it) }
        float(FlickeringGridArtStyle::accentShare, range = 0f..1f) { copy(accentShare = it) }
        int(FlickeringGridArtStyle::maxCells, range = 1_000..20_000) { copy(maxCells = it) }
        int(FlickeringGridArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(FlickeringGridArtStyle::color, choices = colorChoices) { copy(color = it) }
        pick(FlickeringGridArtStyle::accent, choices = colorChoices) { copy(accent = it) }
        pick(FlickeringGridArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
    artSpec(name = "Grid Pattern", defaults = GridPatternArtStyles.Default::resolve, create = ::GridPatternArt) {
        choice(GridPatternArtStyle::kind) { copy(kind = it) }
        dp(GridPatternArtStyle::cell, range = 20f..150f) { copy(cell = it) }
        dp(GridPatternArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        dp(GridPatternArtStyle::mark, range = 0f..6f) { copy(mark = it) }
        float(GridPatternArtStyle::lineAlpha, range = 0f..1f) { copy(lineAlpha = it) }
        int(GridPatternArtStyle::majorEvery, range = 2..8) { copy(majorEvery = it) }
        float(GridPatternArtStyle::majorAlpha, range = 0f..1f) { copy(majorAlpha = it) }
        int(GridPatternArtStyle::highlights, range = 0..16) { copy(highlights = it) }
        float(GridPatternArtStyle::highlightAlpha, range = 0f..1f) { copy(highlightAlpha = it) }
        dp(GridPatternArtStyle::radius, range = 0f..16f) { copy(radius = it) }
        dp(GridPatternArtStyle::inset, range = 0f..6f) { copy(inset = it) }
        duration(GridPatternArtStyle::period, range = 2f..30f) { copy(period = it) }
        pick(GridPatternArtStyle::easing, choices = easingChoices) { copy(easing = it) }
        float(GridPatternArtStyle::pointerAlpha, range = 0f..1f) { copy(pointerAlpha = it) }
        float(GridPatternArtStyle::pointerOutlineAlpha, range = 0f..1f) { copy(pointerOutlineAlpha = it) }
        int(GridPatternArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(GridPatternArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(GridPatternArtStyle::line, choices = colorChoices) { copy(line = it) }
        pick(GridPatternArtStyle::pointer, choices = colorChoices) { copy(pointer = it) }
        pick(GridPatternArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
    artSpec(name = "Matrix", defaults = MatrixArtStyles.Default::resolve, create = ::MatrixArt) {
        dp(MatrixArtStyle::cell, range = 10f..60f) { copy(cell = it) }
        fixed(MatrixArtStyle::textStyle)
        fixed(MatrixArtStyle::glyphs)
        float(MatrixArtStyle::columnShare, range = 0f..1f) { copy(columnShare = it) }
        int(MatrixArtStyle::trail, range = 4..32) { copy(trail = it) }
        int(MatrixArtStyle::pause, range = 0..30) { copy(pause = it) }
        duration(MatrixArtStyle::period, range = 0.08f..1.5f) { copy(period = it) }
        float(MatrixArtStyle::speedSpread, range = 0f..1f) { copy(speedSpread = it) }
        duration(MatrixArtStyle::changePeriod, range = 0.5f..8f) { copy(changePeriod = it) }
        float(MatrixArtStyle::trailAlpha, range = 0f..1f) { copy(trailAlpha = it) }
        float(MatrixArtStyle::headAlpha, range = 0f..1f) { copy(headAlpha = it) }
        float(MatrixArtStyle::glyphWidth, range = 0f..1f) { copy(glyphWidth = it) }
        float(MatrixArtStyle::glyphHeight, range = 0f..1f) { copy(glyphHeight = it) }
        int(MatrixArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(MatrixArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(MatrixArtStyle::head, choices = colorChoices) { copy(head = it) }
        pick(MatrixArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
    artSpec(name = "Retro Grid", defaults = RetroGridArtStyles.Default::resolve, create = ::RetroGridArt) {
        float(RetroGridArtStyle::horizon, range = 0.1f..0.8f) { copy(horizon = it) }
        dp(RetroGridArtStyle::cell, range = 40f..250f) { copy(cell = it) }
        float(RetroGridArtStyle::depth, range = 0f..1f) { copy(depth = it) }
        dp(RetroGridArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        float(RetroGridArtStyle::lineAlpha, range = 0f..1f) { copy(lineAlpha = it) }
        float(RetroGridArtStyle::fade, range = 0f..1f) { copy(fade = it) }
        int(RetroGridArtStyle::maxRows, range = 8..96) { copy(maxRows = it) }
        duration(RetroGridArtStyle::period, range = 0.8f..15f) { copy(period = it) }
        float(RetroGridArtStyle::glowHeight, range = 0f..1f) { copy(glowHeight = it) }
        float(RetroGridArtStyle::glowAlpha, range = 0f..1f) { copy(glowAlpha = it) }
        int(RetroGridArtStyle::pulses, range = 0..8) { copy(pulses = it) }
        float(RetroGridArtStyle::pulseLength, range = 0f..1f) { copy(pulseLength = it) }
        dp(RetroGridArtStyle::pulseWidth, range = 0f..6f) { copy(pulseWidth = it) }
        float(RetroGridArtStyle::pulseAlpha, range = 0f..1f) { copy(pulseAlpha = it) }
        duration(RetroGridArtStyle::pulsePeriod, range = 1.5f..20f) { copy(pulsePeriod = it) }
        int(RetroGridArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(RetroGridArtStyle::line, choices = colorChoices) { copy(line = it) }
        pick(RetroGridArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(RetroGridArtStyle::glow, choices = colorChoices) { copy(glow = it) }
        pick(RetroGridArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
)
