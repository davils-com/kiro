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

import com.davils.kiro.ui.motion.art.tiling.BauhausArt
import com.davils.kiro.ui.motion.art.tiling.BauhausArtStyle
import com.davils.kiro.ui.motion.art.tiling.BauhausArtStyles
import com.davils.kiro.ui.motion.art.tiling.CheckerMorphArt
import com.davils.kiro.ui.motion.art.tiling.CheckerMorphArtStyle
import com.davils.kiro.ui.motion.art.tiling.CheckerMorphArtStyles
import com.davils.kiro.ui.motion.art.tiling.HexFlipArt
import com.davils.kiro.ui.motion.art.tiling.HexFlipArtStyle
import com.davils.kiro.ui.motion.art.tiling.HexFlipArtStyles
import com.davils.kiro.ui.motion.art.tiling.TruchetArt
import com.davils.kiro.ui.motion.art.tiling.TruchetArtStyle
import com.davils.kiro.ui.motion.art.tiling.TruchetArtStyles
import com.davils.kiro.ui.motion.art.tiling.WeaveArt
import com.davils.kiro.ui.motion.art.tiling.WeaveArtStyle
import com.davils.kiro.ui.motion.art.tiling.WeaveArtStyles

internal val tilingSpecs: List<ArtSpec<*>> = listOf(
    artSpec(name = "Bauhaus", defaults = BauhausArtStyles.Default::resolve, create = ::BauhausArt) {
        dp(BauhausArtStyle::cell, range = 30f..200f) { copy(cell = it) }
        dp(BauhausArtStyle::gap, range = 0f..40f) { copy(gap = it) }
        dp(BauhausArtStyle::radius, range = 0f..32f) { copy(radius = it) }
        fixed(BauhausArtStyle::weights)
        float(BauhausArtStyle::circle, range = 0f..1f) { copy(circle = it) }
        float(BauhausArtStyle::ring, range = 0f..1f) { copy(ring = it) }
        float(BauhausArtStyle::ringWidth, range = 0f..1f) { copy(ringWidth = it) }
        float(BauhausArtStyle::dot, range = 0f..1f) { copy(dot = it) }
        int(BauhausArtStyle::stripes, range = 1..6) { copy(stripes = it) }
        float(BauhausArtStyle::stripeWidth, range = 0f..1f) { copy(stripeWidth = it) }
        float(BauhausArtStyle::tileTint, range = 0f..1f) { copy(tileTint = it) }
        float(BauhausArtStyle::shapeTint, range = 0f..1f) { copy(shapeTint = it) }
        float(BauhausArtStyle::accentShare, range = 0f..1f) { copy(accentShare = it) }
        dp(BauhausArtStyle::outlineWidth, range = 0f..4f) { copy(outlineWidth = it) }
        float(BauhausArtStyle::outlineAlpha, range = 0f..1f) { copy(outlineAlpha = it) }
        duration(BauhausArtStyle::period, range = 3f..50f) { copy(period = it) }
        float(BauhausArtStyle::stagger, range = 0f..1f) { copy(stagger = it) }
        float(BauhausArtStyle::turn, range = 0f..1f) { copy(turn = it) }
        float(BauhausArtStyle::pop, range = 0f..1f) { copy(pop = it) }
        pick(BauhausArtStyle::easing, choices = easingChoices) { copy(easing = it) }
        int(BauhausArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(BauhausArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(BauhausArtStyle::outline, choices = colorChoices) { copy(outline = it) }
        pick(BauhausArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
    artSpec(name = "Checker Morph", defaults = CheckerMorphArtStyles.Default::resolve, create = ::CheckerMorphArt) {
        dp(CheckerMorphArtStyle::cell, range = 15f..100f) { copy(cell = it) }
        float(CheckerMorphArtStyle::inset, range = 0f..1f) { copy(inset = it) }
        float(CheckerMorphArtStyle::circle, range = 0f..1f) { copy(circle = it) }
        float(CheckerMorphArtStyle::fillAlpha, range = 0f..1f) { copy(fillAlpha = it) }
        float(CheckerMorphArtStyle::accentAlpha, range = 0f..1f) { copy(accentAlpha = it) }
        dp(CheckerMorphArtStyle::gridWidth, range = 0f..4f) { copy(gridWidth = it) }
        float(CheckerMorphArtStyle::gridAlpha, range = 0f..1f) { copy(gridAlpha = it) }
        float(CheckerMorphArtStyle::outlineAlpha, range = 0f..1f) { copy(outlineAlpha = it) }
        choice(CheckerMorphArtStyle::outlineQuality) { copy(outlineQuality = it) }
        int(CheckerMorphArtStyle::band, range = 1..10) { copy(band = it) }
        float(CheckerMorphArtStyle::lag, range = 0f..1f) { copy(lag = it) }
        float(CheckerMorphArtStyle::jitter, range = 0f..1f) { copy(jitter = it) }
        float(CheckerMorphArtStyle::morph, range = 0f..1f) { copy(morph = it) }
        duration(CheckerMorphArtStyle::period, range = 3f..50f) { copy(period = it) }
        pick(CheckerMorphArtStyle::easing, choices = easingChoices) { copy(easing = it) }
        int(CheckerMorphArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(CheckerMorphArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(CheckerMorphArtStyle::line, choices = colorChoices) { copy(line = it) }
        pick(CheckerMorphArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
    artSpec(name = "Hex Flip", defaults = HexFlipArtStyles.Default::resolve, create = ::HexFlipArt) {
        dp(HexFlipArtStyle::radius, range = 12f..64f) { copy(radius = it) }
        dp(HexFlipArtStyle::gap, range = 0f..10f) { copy(gap = it) }
        dp(HexFlipArtStyle::outlineWidth, range = 0f..4f) { copy(outlineWidth = it) }
        float(HexFlipArtStyle::outlineAlpha, range = 0f..1f) { copy(outlineAlpha = it) }
        float(HexFlipArtStyle::frontAlpha, range = 0f..1f) { copy(frontAlpha = it) }
        float(HexFlipArtStyle::backAlpha, range = 0f..1f) { copy(backAlpha = it) }
        float(HexFlipArtStyle::edgeAlpha, range = 0f..1f) { copy(edgeAlpha = it) }
        float(HexFlipArtStyle::detail, range = 0f..1f) { copy(detail = it) }
        float(HexFlipArtStyle::detailAlpha, range = 0f..1f) { copy(detailAlpha = it) }
        choice(HexFlipArtStyle::detailQuality) { copy(detailQuality = it) }
        float(HexFlipArtStyle::shading, range = 0f..1f) { copy(shading = it) }
        float(HexFlipArtStyle::share, range = 0f..1f) { copy(share = it) }
        int(HexFlipArtStyle::shades, range = 2..32) { copy(shades = it) }
        float(HexFlipArtStyle::centerX, range = 0f..1f) { copy(centerX = it) }
        float(HexFlipArtStyle::centerY, range = 0f..1f) { copy(centerY = it) }
        float(HexFlipArtStyle::spread, range = 0f..1f) { copy(spread = it) }
        float(HexFlipArtStyle::turn, range = 0f..1f) { copy(turn = it) }
        duration(HexFlipArtStyle::period, range = 3f..50f) { copy(period = it) }
        pick(HexFlipArtStyle::easing, choices = easingChoices) { copy(easing = it) }
        int(HexFlipArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(HexFlipArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(HexFlipArtStyle::line, choices = colorChoices) { copy(line = it) }
        pick(HexFlipArtStyle::shade, choices = colorChoices) { copy(shade = it) }
        pick(HexFlipArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
    artSpec(name = "Truchet", defaults = TruchetArtStyles.Default::resolve, create = ::TruchetArt) {
        dp(TruchetArtStyle::cell, range = 20f..150f) { copy(cell = it) }
        dp(TruchetArtStyle::strokeWidth, range = 0f..5f) { copy(strokeWidth = it) }
        pick(TruchetArtStyle::cap, choices = capChoices) { copy(cap = it) }
        float(TruchetArtStyle::fillAlpha, range = 0f..1f) { copy(fillAlpha = it) }
        choice(TruchetArtStyle::fillQuality) { copy(fillQuality = it) }
        int(TruchetArtStyle::band, range = 1..8) { copy(band = it) }
        int(TruchetArtStyle::accentEvery, range = 1..8) { copy(accentEvery = it) }
        float(TruchetArtStyle::lineAlpha, range = 0f..1f) { copy(lineAlpha = it) }
        float(TruchetArtStyle::accentAlpha, range = 0f..1f) { copy(accentAlpha = it) }
        choice(TruchetArtStyle::variant) { copy(variant = it) }
        float(TruchetArtStyle::lineShare, range = 0f..1f) { copy(lineShare = it) }
        float(TruchetArtStyle::flip, range = 0f..1f) { copy(flip = it) }
        duration(TruchetArtStyle::period, range = 3f..50f) { copy(period = it) }
        float(TruchetArtStyle::lag, range = 0f..1f) { copy(lag = it) }
        float(TruchetArtStyle::turn, range = 0f..1f) { copy(turn = it) }
        pick(TruchetArtStyle::easing, choices = easingChoices) { copy(easing = it) }
        dp(TruchetArtStyle::pointerRadius, range = 0f..400f) { copy(pointerRadius = it) }
        float(TruchetArtStyle::pointerLead, range = 0f..1f) { copy(pointerLead = it) }
        int(TruchetArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(TruchetArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(TruchetArtStyle::line, choices = colorChoices) { copy(line = it) }
        pick(TruchetArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
    artSpec(name = "Weave", defaults = WeaveArtStyles.Default::resolve, create = ::WeaveArt) {
        choice(WeaveArtStyle::pattern) { copy(pattern = it) }
        dp(WeaveArtStyle::cell, range = 20f..150f) { copy(cell = it) }
        dp(WeaveArtStyle::frameWidth, range = 0f..4f) { copy(frameWidth = it) }
        float(WeaveArtStyle::frameAlpha, range = 0f..1f) { copy(frameAlpha = it) }
        dp(WeaveArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        float(WeaveArtStyle::lineAlpha, range = 0f..1f) { copy(lineAlpha = it) }
        float(WeaveArtStyle::panelShare, range = 0f..1f) { copy(panelShare = it) }
        float(WeaveArtStyle::panelAlpha, range = 0f..1f) { copy(panelAlpha = it) }
        int(WeaveArtStyle::arcSteps, range = 2..24) { copy(arcSteps = it) }
        dp(WeaveArtStyle::band, range = 0f..400f) { copy(band = it) }
        int(WeaveArtStyle::bands, range = 1..5) { copy(bands = it) }
        float(WeaveArtStyle::angle, range = -90f..90f) { copy(angle = it) }
        dp(WeaveArtStyle::highlightWidth, range = 0f..4f) { copy(highlightWidth = it) }
        float(WeaveArtStyle::highlightAlpha, range = 0f..1f) { copy(highlightAlpha = it) }
        dp(WeaveArtStyle::glowWidth, range = 0f..15f) { copy(glowWidth = it) }
        float(WeaveArtStyle::glowAlpha, range = 0f..1f) { copy(glowAlpha = it) }
        choice(WeaveArtStyle::glowQuality) { copy(glowQuality = it) }
        duration(WeaveArtStyle::period, range = 3f..40f) { copy(period = it) }
        pick(WeaveArtStyle::easing, choices = easingChoices) { copy(easing = it) }
        int(WeaveArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(WeaveArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(WeaveArtStyle::line, choices = colorChoices) { copy(line = it) }
        pick(WeaveArtStyle::accent, choices = colorChoices) { copy(accent = it) }
        pick(WeaveArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
)
