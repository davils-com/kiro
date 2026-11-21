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

import com.davils.kiro.ui.motion.art.field.ContourArt
import com.davils.kiro.ui.motion.art.field.ContourArtStyle
import com.davils.kiro.ui.motion.art.field.ContourArtStyles
import com.davils.kiro.ui.motion.art.field.HalftoneArt
import com.davils.kiro.ui.motion.art.field.HalftoneArtStyle
import com.davils.kiro.ui.motion.art.field.HalftoneArtStyles
import com.davils.kiro.ui.motion.art.field.LowPolyArt
import com.davils.kiro.ui.motion.art.field.LowPolyArtStyle
import com.davils.kiro.ui.motion.art.field.LowPolyArtStyles
import com.davils.kiro.ui.motion.art.field.RidgeArt
import com.davils.kiro.ui.motion.art.field.RidgeArtStyle
import com.davils.kiro.ui.motion.art.field.RidgeArtStyles
import com.davils.kiro.ui.motion.art.field.VoronoiArt
import com.davils.kiro.ui.motion.art.field.VoronoiArtStyle
import com.davils.kiro.ui.motion.art.field.VoronoiArtStyles

internal val fieldSpecs: List<ArtSpec<*>> = listOf(
    artSpec(name = "Contour", defaults = ContourArtStyles.Default::resolve, create = ::ContourArt) {
        pick(ContourArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        dp(ContourArtStyle::cell, range = 4f..16f) { copy(cell = it) }
        int(ContourArtStyle::levels, range = 2..24) { copy(levels = it) }
        int(ContourArtStyle::indexEvery, range = 2..8) { copy(indexEvery = it) }
        dp(ContourArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        dp(ContourArtStyle::indexWidth, range = 0f..5f) { copy(indexWidth = it) }
        float(ContourArtStyle::lineAlpha, range = 0f..1f) { copy(lineAlpha = it) }
        float(ContourArtStyle::indexAlpha, range = 0f..1f) { copy(indexAlpha = it) }
        pick(ContourArtStyle::ink, choices = colorChoices) { copy(ink = it) }
        float(ContourArtStyle::lineTint, range = 0f..1f) { copy(lineTint = it) }
        float(ContourArtStyle::bandAlpha, range = 0f..1f) { copy(bandAlpha = it) }
        dp(ContourArtStyle::scale, range = 150f..1000f) { copy(scale = it) }
        int(ContourArtStyle::octaves, range = 1..5) { copy(octaves = it) }
        float(ContourArtStyle::warp, range = 0f..2f) { copy(warp = it) }
        float(ContourArtStyle::contrast, range = 0.5f..3f) { copy(contrast = it) }
        float(ContourArtStyle::drift, range = 0f..1f) { copy(drift = it) }
        duration(ContourArtStyle::period, range = 10f..200f) { copy(period = it) }
    },
    artSpec(name = "Halftone", defaults = HalftoneArtStyles.Default::resolve, create = ::HalftoneArt) {
        pick(HalftoneArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        dp(HalftoneArtStyle::pitch, range = 8f..50f) { copy(pitch = it) }
        float(HalftoneArtStyle::maxDot, range = 0.42f..1f) { copy(maxDot = it) }
        float(HalftoneArtStyle::minDot, range = 0f..0.42f) { copy(minDot = it) }
        float(HalftoneArtStyle::alpha, range = 0f..1f) { copy(alpha = it) }
        choice(HalftoneArtStyle::pattern) { copy(pattern = it) }
        choice(HalftoneArtStyle::shape) { copy(shape = it) }
        float(HalftoneArtStyle::angle, range = -90f..90f) { copy(angle = it) }
        float(HalftoneArtStyle::gradientAngle, range = 0f..180f) { copy(gradientAngle = it) }
        int(HalftoneArtStyle::waves, range = 1..4) { copy(waves = it) }
        fixed(HalftoneArtStyle::wavelengths)
        fixed(HalftoneArtStyle::speeds)
        float(HalftoneArtStyle::orbit, range = 0f..1f) { copy(orbit = it) }
        duration(HalftoneArtStyle::period, range = 3f..50f) { copy(period = it) }
        duration(HalftoneArtStyle::wanderPeriod, range = 20f..300f) { copy(wanderPeriod = it) }
        float(HalftoneArtStyle::pointerStrength, range = 0f..1f) { copy(pointerStrength = it) }
        dp(HalftoneArtStyle::pointerRadius, range = 0f..400f) { copy(pointerRadius = it) }
        dp(HalftoneArtStyle::pointerWavelength, range = 0f..150f) { copy(pointerWavelength = it) }
    },
    artSpec(name = "Low Poly", defaults = LowPolyArtStyles.Default::resolve, create = ::LowPolyArt) {
        dp(LowPolyArtStyle::cell, range = 30f..200f) { copy(cell = it) }
        float(LowPolyArtStyle::jitter, range = 0f..1f) { copy(jitter = it) }
        float(LowPolyArtStyle::drift, range = 0f..1f) { copy(drift = it) }
        duration(LowPolyArtStyle::driftPeriod, range = 6f..100f) { copy(driftPeriod = it) }
        float(LowPolyArtStyle::relief, range = 0f..2f) { copy(relief = it) }
        dp(LowPolyArtStyle::reliefScale, range = 100f..1000f) { copy(reliefScale = it) }
        float(LowPolyArtStyle::tint, range = 0f..1f) { copy(tint = it) }
        float(LowPolyArtStyle::contrast, range = 0.5f..2f) { copy(contrast = it) }
        float(LowPolyArtStyle::elevation, range = 5f..85f) { copy(elevation = it) }
        duration(LowPolyArtStyle::lightPeriod, range = 15f..250f) { copy(lightPeriod = it) }
        dp(LowPolyArtStyle::edgeWidth, range = 0f..4f) { copy(edgeWidth = it) }
        float(LowPolyArtStyle::edgeAlpha, range = 0f..1f) { copy(edgeAlpha = it) }
        pick(LowPolyArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(LowPolyArtStyle::background, choices = colorChoices) { copy(background = it) }
        pick(LowPolyArtStyle::edge, choices = colorChoices) { copy(edge = it) }
    },
    artSpec(name = "Ridge", defaults = RidgeArtStyles.Default::resolve, create = ::RidgeArt) {
        pick(RidgeArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(RidgeArtStyle::background, choices = colorChoices) { copy(background = it) }
        int(RidgeArtStyle::lines, range = 4..40) { copy(lines = it) }
        dp(RidgeArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        float(RidgeArtStyle::lineAlpha, range = 0f..1f) { copy(lineAlpha = it) }
        dp(RidgeArtStyle::step, range = 2f..15f) { copy(step = it) }
        dp(RidgeArtStyle::amplitude, range = 0f..150f) { copy(amplitude = it) }
        float(RidgeArtStyle::spread, range = 0f..1f) { copy(spread = it) }
        float(RidgeArtStyle::wander, range = 0f..1f) { copy(wander = it) }
        float(RidgeArtStyle::flat, range = 0f..1f) { copy(flat = it) }
        float(RidgeArtStyle::frequency, range = 1f..12f) { copy(frequency = it) }
        int(RidgeArtStyle::octaves, range = 1..4) { copy(octaves = it) }
        float(RidgeArtStyle::sharpness, range = 0.5f..4f) { copy(sharpness = it) }
        float(RidgeArtStyle::top, range = 0f..1f) { copy(top = it) }
        float(RidgeArtStyle::bottom, range = 0f..1f) { copy(bottom = it) }
        duration(RidgeArtStyle::period, range = 5f..80f) { copy(period = it) }
        duration(RidgeArtStyle::evolution, range = 15f..250f) { copy(evolution = it) }
        choice(RidgeArtStyle::gradient) { copy(gradient = it) }
        toggle(RidgeArtStyle::hasFill) { copy(hasFill = it) }
    },
    artSpec(name = "Voronoi", defaults = VoronoiArtStyles.Default::resolve, create = ::VoronoiArt) {
        dp(VoronoiArtStyle::cell, range = 40f..250f) { copy(cell = it) }
        float(VoronoiArtStyle::jitter, range = 0f..1f) { copy(jitter = it) }
        float(VoronoiArtStyle::drift, range = 0f..1f) { copy(drift = it) }
        duration(VoronoiArtStyle::period, range = 10f..200f) { copy(period = it) }
        dp(VoronoiArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        float(VoronoiArtStyle::lineAlpha, range = 0f..1f) { copy(lineAlpha = it) }
        float(VoronoiArtStyle::tintAlpha, range = 0f..1f) { copy(tintAlpha = it) }
        float(VoronoiArtStyle::tintVariance, range = 0f..1f) { copy(tintVariance = it) }
        float(VoronoiArtStyle::toneJitter, range = 0f..1f) { copy(toneJitter = it) }
        dp(VoronoiArtStyle::seedRadius, range = 0f..5f) { copy(seedRadius = it) }
        float(VoronoiArtStyle::seedAlpha, range = 0f..1f) { copy(seedAlpha = it) }
        float(VoronoiArtStyle::pointerAlpha, range = 0f..1f) { copy(pointerAlpha = it) }
        pick(VoronoiArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(VoronoiArtStyle::line, choices = colorChoices) { copy(line = it) }
    },
)
