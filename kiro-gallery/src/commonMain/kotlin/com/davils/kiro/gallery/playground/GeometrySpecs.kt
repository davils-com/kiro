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

import com.davils.kiro.ui.motion.art.geometry.IsometricCityArt
import com.davils.kiro.ui.motion.art.geometry.IsometricCityArtStyle
import com.davils.kiro.ui.motion.art.geometry.IsometricCityArtStyles
import com.davils.kiro.ui.motion.art.geometry.OrbitArt
import com.davils.kiro.ui.motion.art.geometry.OrbitArtStyle
import com.davils.kiro.ui.motion.art.geometry.OrbitArtStyles
import com.davils.kiro.ui.motion.art.geometry.WireframeArt
import com.davils.kiro.ui.motion.art.geometry.WireframeArtStyle
import com.davils.kiro.ui.motion.art.geometry.WireframeArtStyles

internal val geometrySpecs: List<ArtSpec<*>> = listOf(
    artSpec(name = "Isometric City", defaults = IsometricCityArtStyles.Default::resolve, create = ::IsometricCityArt) {
        dp(IsometricCityArtStyle::tile, range = 25f..200f) { copy(tile = it) }
        float(IsometricCityArtStyle::inset, range = 0f..1f) { copy(inset = it) }
        float(IsometricCityArtStyle::vacancy, range = 0f..1f) { copy(vacancy = it) }
        dp(IsometricCityArtStyle::minHeight, range = 0f..40f) { copy(minHeight = it) }
        dp(IsometricCityArtStyle::maxHeight, range = 40f..80f) { copy(maxHeight = it) }
        float(IsometricCityArtStyle::jitter, range = 0f..1f) { copy(jitter = it) }
        float(IsometricCityArtStyle::frequency, range = 0.02f..0.3f) { copy(frequency = it) }
        duration(IsometricCityArtStyle::period, range = 3f..50f) { copy(period = it) }
        float(IsometricCityArtStyle::originX, range = 0f..1f) { copy(originX = it) }
        float(IsometricCityArtStyle::originY, range = 0f..1f) { copy(originY = it) }
        float(IsometricCityArtStyle::tint, range = 0f..1f) { copy(tint = it) }
        float(IsometricCityArtStyle::colorBias, range = 0.5f..4f) { copy(colorBias = it) }
        float(IsometricCityArtStyle::leftShade, range = 0f..0.3f) { copy(leftShade = it) }
        float(IsometricCityArtStyle::rightShade, range = 0f..0.3f) { copy(rightShade = it) }
        dp(IsometricCityArtStyle::outlineWidth, range = 0f..4f) { copy(outlineWidth = it) }
        float(IsometricCityArtStyle::outlineAlpha, range = 0f..1f) { copy(outlineAlpha = it) }
        dp(IsometricCityArtStyle::floor, range = 0f..25f) { copy(floor = it) }
        dp(IsometricCityArtStyle::window, range = 1f..18f) { copy(window = it) }
        dp(IsometricCityArtStyle::windowHeight, range = 0f..6f) { copy(windowHeight = it) }
        dp(IsometricCityArtStyle::windowGap, range = 0f..20f) { copy(windowGap = it) }
        float(IsometricCityArtStyle::windowAlpha, range = 0f..1f) { copy(windowAlpha = it) }
        choice(IsometricCityArtStyle::detailQuality) { copy(detailQuality = it) }
        int(IsometricCityArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(IsometricCityArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(IsometricCityArtStyle::shade, choices = colorChoices) { copy(shade = it) }
        pick(IsometricCityArtStyle::base, choices = colorChoices) { copy(base = it) }
        pick(IsometricCityArtStyle::detail, choices = colorChoices) { copy(detail = it) }
        pick(IsometricCityArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
    artSpec(name = "Orbit", defaults = OrbitArtStyles.Default::resolve, create = ::OrbitArt) {
        float(OrbitArtStyle::centerX, range = 0f..1f) { copy(centerX = it) }
        float(OrbitArtStyle::centerY, range = 0f..1f) { copy(centerY = it) }
        dp(OrbitArtStyle::firstRadius, range = 30f..200f) { copy(firstRadius = it) }
        dp(OrbitArtStyle::ringSpacing, range = 20f..150f) { copy(ringSpacing = it) }
        int(OrbitArtStyle::rings, range = 1..10) { copy(rings = it) }
        dp(OrbitArtStyle::ringWidth, range = 0f..4f) { copy(ringWidth = it) }
        float(OrbitArtStyle::ringAlpha, range = 0f..1f) { copy(ringAlpha = it) }
        dp(OrbitArtStyle::dash, range = 1f..8f) { copy(dash = it) }
        dp(OrbitArtStyle::dashGap, range = 0f..15f) { copy(dashGap = it) }
        duration(OrbitArtStyle::drift, range = 50f..800f) { copy(drift = it) }
        int(OrbitArtStyle::minPlanets, range = 0..2) { copy(minPlanets = it) }
        int(OrbitArtStyle::maxPlanets, range = 2..5) { copy(maxPlanets = it) }
        dp(OrbitArtStyle::minPlanet, range = 0f..10f) { copy(minPlanet = it) }
        dp(OrbitArtStyle::maxPlanet, range = 10f..20f) { copy(maxPlanet = it) }
        float(OrbitArtStyle::shine, range = 0f..1f) { copy(shine = it) }
        float(OrbitArtStyle::shineSize, range = 0f..1f) { copy(shineSize = it) }
        float(OrbitArtStyle::trail, range = 0f..180f) { copy(trail = it) }
        int(OrbitArtStyle::trailSegments, range = 4..40) { copy(trailSegments = it) }
        float(OrbitArtStyle::trailAlpha, range = 0f..1f) { copy(trailAlpha = it) }
        float(OrbitArtStyle::moonChance, range = 0f..1f) { copy(moonChance = it) }
        dp(OrbitArtStyle::moon, range = 0f..10f) { copy(moon = it) }
        dp(OrbitArtStyle::moonDistance, range = 0f..25f) { copy(moonDistance = it) }
        duration(OrbitArtStyle::moonPeriod, range = 2f..30f) { copy(moonPeriod = it) }
        dp(OrbitArtStyle::glowRadius, range = 0f..400f) { copy(glowRadius = it) }
        float(OrbitArtStyle::glowAlpha, range = 0f..1f) { copy(glowAlpha = it) }
        duration(OrbitArtStyle::period, range = 6f..100f) { copy(period = it) }
        toggle(OrbitArtStyle::isAlternating) { copy(isAlternating = it) }
        int(OrbitArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(OrbitArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(OrbitArtStyle::glow, choices = colorChoices) { copy(glow = it) }
        pick(OrbitArtStyle::ring, choices = colorChoices) { copy(ring = it) }
        pick(OrbitArtStyle::highlight, choices = colorChoices) { copy(highlight = it) }
        pick(OrbitArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
    artSpec(name = "Wireframe", defaults = WireframeArtStyles.Default::resolve, create = ::WireframeArt) {
        choice(WireframeArtStyle::solid) { copy(solid = it) }
        float(WireframeArtStyle::size, range = 0.1f..0.5f) { copy(size = it) }
        float(WireframeArtStyle::centerX, range = 0f..1f) { copy(centerX = it) }
        float(WireframeArtStyle::centerY, range = 0f..1f) { copy(centerY = it) }
        float(WireframeArtStyle::perspective, range = 1.5f..8f) { copy(perspective = it) }
        float(WireframeArtStyle::tilt, range = 0f..90f) { copy(tilt = it) }
        float(WireframeArtStyle::wobble, range = 0f..45f) { copy(wobble = it) }
        dp(WireframeArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        float(WireframeArtStyle::nearAlpha, range = 0f..1f) { copy(nearAlpha = it) }
        float(WireframeArtStyle::farAlpha, range = 0f..1f) { copy(farAlpha = it) }
        dp(WireframeArtStyle::dot, range = 0f..15f) { copy(dot = it) }
        float(WireframeArtStyle::farDot, range = 0f..1f) { copy(farDot = it) }
        float(WireframeArtStyle::dotAlpha, range = 0f..1f) { copy(dotAlpha = it) }
        choice(WireframeArtStyle::dotQuality) { copy(dotQuality = it) }
        float(WireframeArtStyle::guideAlpha, range = 0f..1f) { copy(guideAlpha = it) }
        duration(WireframeArtStyle::period, range = 6f..100f) { copy(period = it) }
        duration(WireframeArtStyle::wobblePeriod, range = 10f..200f) { copy(wobblePeriod = it) }
        int(WireframeArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(WireframeArtStyle::line, choices = colorChoices) { copy(line = it) }
        pick(WireframeArtStyle::accent, choices = colorChoices) { copy(accent = it) }
        pick(WireframeArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
)
