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

import com.davils.kiro.ui.motion.art.particle.BokehArt
import com.davils.kiro.ui.motion.art.particle.BokehArtStyle
import com.davils.kiro.ui.motion.art.particle.BokehArtStyles
import com.davils.kiro.ui.motion.art.particle.FlowFieldArt
import com.davils.kiro.ui.motion.art.particle.FlowFieldArtStyle
import com.davils.kiro.ui.motion.art.particle.FlowFieldArtStyles
import com.davils.kiro.ui.motion.art.particle.MeteorsArt
import com.davils.kiro.ui.motion.art.particle.MeteorsArtStyle
import com.davils.kiro.ui.motion.art.particle.MeteorsArtStyles
import com.davils.kiro.ui.motion.art.particle.ParticlesArt
import com.davils.kiro.ui.motion.art.particle.ParticlesArtStyle
import com.davils.kiro.ui.motion.art.particle.ParticlesArtStyles
import com.davils.kiro.ui.motion.art.particle.StarfieldArt
import com.davils.kiro.ui.motion.art.particle.StarfieldArtStyle
import com.davils.kiro.ui.motion.art.particle.StarfieldArtStyles

internal val particleSpecs: List<ArtSpec<*>> = listOf(
    artSpec(name = "Bokeh", defaults = BokehArtStyles.Default::resolve, create = ::BokehArt) {
        int(BokehArtStyle::count, range = 1..24) { copy(count = it) }
        dp(BokehArtStyle::minRadius, range = 0f..120f) { copy(minRadius = it) }
        dp(BokehArtStyle::maxRadius, range = 120f..250f) { copy(maxRadius = it) }
        dp(BokehArtStyle::wander, range = 0f..200f) { copy(wander = it) }
        duration(BokehArtStyle::period, range = 10f..150f) { copy(period = it) }
        float(BokehArtStyle::breathe, range = 0f..1f) { copy(breathe = it) }
        duration(BokehArtStyle::breathePeriod, range = 2f..30f) { copy(breathePeriod = it) }
        float(BokehArtStyle::alpha, range = 0f..1f) { copy(alpha = it) }
        float(BokehArtStyle::alphaFloor, range = 0f..1f) { copy(alphaFloor = it) }
        float(BokehArtStyle::softness, range = 0f..1f) { copy(softness = it) }
        dp(BokehArtStyle::rimWidth, range = 0f..4f) { copy(rimWidth = it) }
        float(BokehArtStyle::rimAlpha, range = 0f..1f) { copy(rimAlpha = it) }
        pick(BokehArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
    },
    artSpec(name = "Flow Field", defaults = FlowFieldArtStyles.Default::resolve, create = ::FlowFieldArt) {
        pick(FlowFieldArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        int(FlowFieldArtStyle::lines, range = 40..600) { copy(lines = it) }
        dp(FlowFieldArtStyle::segment, range = 1.5f..10f) { copy(segment = it) }
        int(FlowFieldArtStyle::steps, range = 20..300) { copy(steps = it) }
        int(FlowFieldArtStyle::trail, range = 10..140) { copy(trail = it) }
        dp(FlowFieldArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        float(FlowFieldArtStyle::alpha, range = 0f..1f) { copy(alpha = it) }
        float(FlowFieldArtStyle::fade, range = 0f..1f) { copy(fade = it) }
        choice(FlowFieldArtStyle::kind) { copy(kind = it) }
        dp(FlowFieldArtStyle::scale, range = 150f..1500f) { copy(scale = it) }
        int(FlowFieldArtStyle::octaves, range = 1..4) { copy(octaves = it) }
        float(FlowFieldArtStyle::turns, range = 0f..3f) { copy(turns = it) }
        duration(FlowFieldArtStyle::evolution, range = 20f..300f) { copy(evolution = it) }
        duration(FlowFieldArtStyle::life, range = 4f..60f) { copy(life = it) }
        choice(FlowFieldArtStyle::colorMode) { copy(colorMode = it) }
        float(FlowFieldArtStyle::pointerStrength, range = 0f..1f) { copy(pointerStrength = it) }
        dp(FlowFieldArtStyle::pointerRadius, range = 0f..400f) { copy(pointerRadius = it) }
    },
    artSpec(name = "Meteors", defaults = MeteorsArtStyles.Default::resolve, create = ::MeteorsArt) {
        int(MeteorsArtStyle::count, range = 1..16) { copy(count = it) }
        float(MeteorsArtStyle::angle, range = -90f..90f) { copy(angle = it) }
        duration(MeteorsArtStyle::period, range = 2.5f..40f) { copy(period = it) }
        float(MeteorsArtStyle::duty, range = 0f..1f) { copy(duty = it) }
        float(MeteorsArtStyle::travel, range = 0f..1f) { copy(travel = it) }
        dp(MeteorsArtStyle::tail, range = 0f..500f) { copy(tail = it) }
        dp(MeteorsArtStyle::width, range = 0f..5f) { copy(width = it) }
        dp(MeteorsArtStyle::headRadius, range = 0f..6f) { copy(headRadius = it) }
        float(MeteorsArtStyle::alpha, range = 0f..1f) { copy(alpha = it) }
        float(MeteorsArtStyle::toneFrom, range = 0f..1f) { copy(toneFrom = it) }
        int(MeteorsArtStyle::stars, range = 0..240) { copy(stars = it) }
        dp(MeteorsArtStyle::starRadius, range = 0f..4f) { copy(starRadius = it) }
        float(MeteorsArtStyle::starAlpha, range = 0f..1f) { copy(starAlpha = it) }
        pick(MeteorsArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(MeteorsArtStyle::star, choices = colorChoices) { copy(star = it) }
    },
    artSpec(name = "Particles", defaults = ParticlesArtStyles.Default::resolve, create = ::ParticlesArt) {
        dp(ParticlesArtStyle::distance, range = 25f..200f) { copy(distance = it) }
        int(ParticlesArtStyle::maxCount, range = 40..720) { copy(maxCount = it) }
        dp(ParticlesArtStyle::minRadius, range = 0f..2.5f) { copy(minRadius = it) }
        dp(ParticlesArtStyle::maxRadius, range = 2.5f..5f) { copy(maxRadius = it) }
        float(ParticlesArtStyle::minAlpha, range = 0f..0.85f) { copy(minAlpha = it) }
        float(ParticlesArtStyle::maxAlpha, range = 0.85f..1f) { copy(maxAlpha = it) }
        dp(ParticlesArtStyle::drift, range = 0f..150f) { copy(drift = it) }
        duration(ParticlesArtStyle::period, range = 2.5f..40f) { copy(period = it) }
        float(ParticlesArtStyle::twinkle, range = 0f..1f) { copy(twinkle = it) }
        duration(ParticlesArtStyle::twinklePeriod, range = 1f..15f) { copy(twinklePeriod = it) }
        dp(ParticlesArtStyle::parallax, range = 0f..50f) { copy(parallax = it) }
        dp(ParticlesArtStyle::linkDistance, range = 0f..300f) { copy(linkDistance = it) }
        dp(ParticlesArtStyle::linkWidth, range = 0f..4f) { copy(linkWidth = it) }
        float(ParticlesArtStyle::linkAlpha, range = 0f..1f) { copy(linkAlpha = it) }
        choice(ParticlesArtStyle::linkQuality) { copy(linkQuality = it) }
        pick(ParticlesArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(ParticlesArtStyle::link, choices = colorChoices) { copy(link = it) }
    },
    artSpec(name = "Starfield", defaults = StarfieldArtStyles.Default::resolve, create = ::StarfieldArt) {
        int(StarfieldArtStyle::count, range = 40..600) { copy(count = it) }
        float(StarfieldArtStyle::centerX, range = 0f..1f) { copy(centerX = it) }
        float(StarfieldArtStyle::centerY, range = 0f..1f) { copy(centerY = it) }
        float(StarfieldArtStyle::spread, range = 0f..1f) { copy(spread = it) }
        float(StarfieldArtStyle::nearest, range = 0f..1f) { copy(nearest = it) }
        duration(StarfieldArtStyle::period, range = 5f..80f) { copy(period = it) }
        float(StarfieldArtStyle::streak, range = 0f..1f) { copy(streak = it) }
        dp(StarfieldArtStyle::minWidth, range = 0f..3f) { copy(minWidth = it) }
        dp(StarfieldArtStyle::maxWidth, range = 3f..6f) { copy(maxWidth = it) }
        float(StarfieldArtStyle::alpha, range = 0f..1f) { copy(alpha = it) }
        float(StarfieldArtStyle::fadeIn, range = 0f..1f) { copy(fadeIn = it) }
        pick(StarfieldArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
    },
)
