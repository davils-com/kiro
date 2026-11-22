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

import com.davils.kiro.ui.motion.art.line.BeamsArt
import com.davils.kiro.ui.motion.art.line.BeamsArtStyle
import com.davils.kiro.ui.motion.art.line.BeamsArtStyles

internal val lineSpecs: List<ArtSpec<*>> = listOf(
    artSpec(name = "Beams", defaults = BeamsArtStyles.Default::resolve, create = ::BeamsArt) {
        int(BeamsArtStyle::beams, range = 1..18) { copy(beams = it) }
        float(BeamsArtStyle::spread, range = 0f..1f) { copy(spread = it) }
        float(BeamsArtStyle::bend, range = 0f..1f) { copy(bend = it) }
        dp(BeamsArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        float(BeamsArtStyle::guideAlpha, range = 0f..1f) { copy(guideAlpha = it) }
        dp(BeamsArtStyle::beamWidth, range = 0f..6f) { copy(beamWidth = it) }
        float(BeamsArtStyle::beamAlpha, range = 0f..1f) { copy(beamAlpha = it) }
        dp(BeamsArtStyle::glowWidth, range = 0f..25f) { copy(glowWidth = it) }
        float(BeamsArtStyle::glowAlpha, range = 0f..1f) { copy(glowAlpha = it) }
        float(BeamsArtStyle::segment, range = 0f..1f) { copy(segment = it) }
        duration(BeamsArtStyle::period, range = 2f..30f) { copy(period = it) }
        float(BeamsArtStyle::periodSpread, range = 0f..1f) { copy(periodSpread = it) }
        int(BeamsArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(BeamsArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(BeamsArtStyle::guide, choices = colorChoices) { copy(guide = it) }
        pick(BeamsArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
)
