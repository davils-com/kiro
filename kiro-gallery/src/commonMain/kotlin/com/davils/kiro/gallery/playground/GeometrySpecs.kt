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

import com.davils.kiro.ui.motion.art.geometry.BlueprintArt
import com.davils.kiro.ui.motion.art.geometry.BlueprintArtStyle
import com.davils.kiro.ui.motion.art.geometry.BlueprintArtStyles
import com.davils.kiro.ui.motion.art.geometry.IsometricCityArt
import com.davils.kiro.ui.motion.art.geometry.IsometricCityArtStyle
import com.davils.kiro.ui.motion.art.geometry.IsometricCityArtStyles
import com.davils.kiro.ui.motion.art.geometry.LissajousArt
import com.davils.kiro.ui.motion.art.geometry.LissajousArtStyle
import com.davils.kiro.ui.motion.art.geometry.LissajousArtStyles
import com.davils.kiro.ui.motion.art.geometry.OrbitArt
import com.davils.kiro.ui.motion.art.geometry.OrbitArtStyle
import com.davils.kiro.ui.motion.art.geometry.OrbitArtStyles
import com.davils.kiro.ui.motion.art.geometry.PolygonsArt
import com.davils.kiro.ui.motion.art.geometry.PolygonsArtStyle
import com.davils.kiro.ui.motion.art.geometry.PolygonsArtStyles
import com.davils.kiro.ui.motion.art.geometry.RadarArt
import com.davils.kiro.ui.motion.art.geometry.RadarArtStyle
import com.davils.kiro.ui.motion.art.geometry.RadarArtStyles
import com.davils.kiro.ui.motion.art.geometry.SpirographArt
import com.davils.kiro.ui.motion.art.geometry.SpirographArtStyle
import com.davils.kiro.ui.motion.art.geometry.SpirographArtStyles
import com.davils.kiro.ui.motion.art.geometry.WireframeArt
import com.davils.kiro.ui.motion.art.geometry.WireframeArtStyle
import com.davils.kiro.ui.motion.art.geometry.WireframeArtStyles

internal val geometrySpecs: List<ArtSpec<*>> = listOf(
    artSpec(name = "Blueprint", defaults = BlueprintArtStyles.Default::resolve, create = ::BlueprintArt) {
        dp(BlueprintArtStyle::cell, range = 8f..50f) { copy(cell = it) }
        int(BlueprintArtStyle::major, range = 2..10) { copy(major = it) }
        dp(BlueprintArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        float(BlueprintArtStyle::minorAlpha, range = 0f..1f) { copy(minorAlpha = it) }
        float(BlueprintArtStyle::majorAlpha, range = 0f..1f) { copy(majorAlpha = it) }
        dp(BlueprintArtStyle::mark, range = 0f..20f) { copy(mark = it) }
        float(BlueprintArtStyle::markAlpha, range = 0f..1f) { copy(markAlpha = it) }
        dp(BlueprintArtStyle::margin, range = 0f..80f) { copy(margin = it) }
        dp(BlueprintArtStyle::bracket, range = 0f..60f) { copy(bracket = it) }
        dp(BlueprintArtStyle::bracketWidth, range = 0f..4f) { copy(bracketWidth = it) }
        float(BlueprintArtStyle::bracketAlpha, range = 0f..1f) { copy(bracketAlpha = it) }
        toggle(BlueprintArtStyle::isRulerShown) { copy(isRulerShown = it) }
        fixed(BlueprintArtStyle::labelStyle)
        float(BlueprintArtStyle::labelAlpha, range = 0f..1f) { copy(labelAlpha = it) }
        dp(BlueprintArtStyle::labelPadding, range = 0f..15f) { copy(labelPadding = it) }
        int(BlueprintArtStyle::labelStep, range = 20..400) { copy(labelStep = it) }
        fixed(BlueprintArtStyle::label)
        dp(BlueprintArtStyle::scanWidth, range = 0f..4f) { copy(scanWidth = it) }
        float(BlueprintArtStyle::scanAlpha, range = 0f..1f) { copy(scanAlpha = it) }
        dp(BlueprintArtStyle::scanBand, range = 0f..150f) { copy(scanBand = it) }
        float(BlueprintArtStyle::bandAlpha, range = 0f..1f) { copy(bandAlpha = it) }
        choice(BlueprintArtStyle::bandQuality) { copy(bandQuality = it) }
        float(BlueprintArtStyle::litAlpha, range = 0f..1f) { copy(litAlpha = it) }
        duration(BlueprintArtStyle::scanPeriod, range = 3f..50f) { copy(scanPeriod = it) }
        pick(BlueprintArtStyle::easing, choices = easingChoices) { copy(easing = it) }
        int(BlueprintArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(BlueprintArtStyle::line, choices = colorChoices) { copy(line = it) }
        pick(BlueprintArtStyle::accent, choices = colorChoices) { copy(accent = it) }
        pick(BlueprintArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
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
    artSpec(name = "Lissajous", defaults = LissajousArtStyles.Default::resolve, create = ::LissajousArt) {
        int(LissajousArtStyle::curves, range = 1..5) { copy(curves = it) }
        float(LissajousArtStyle::centerX, range = 0f..1f) { copy(centerX = it) }
        float(LissajousArtStyle::centerY, range = 0f..1f) { copy(centerY = it) }
        float(LissajousArtStyle::size, range = 0.1f..0.5f) { copy(size = it) }
        float(LissajousArtStyle::stretch, range = 0.5f..3f) { copy(stretch = it) }
        int(LissajousArtStyle::minFrequency, range = 1..3) { copy(minFrequency = it) }
        int(LissajousArtStyle::maxFrequency, range = 3..7) { copy(maxFrequency = it) }
        float(LissajousArtStyle::detune, range = 0f..0.2f) { copy(detune = it) }
        float(LissajousArtStyle::twist, range = 0f..1f) { copy(twist = it) }
        float(LissajousArtStyle::damping, range = 0f..4f) { copy(damping = it) }
        float(LissajousArtStyle::turns, range = 1f..12f) { copy(turns = it) }
        float(LissajousArtStyle::spread, range = 0f..1f) { copy(spread = it) }
        int(LissajousArtStyle::steps, range = 180..1_440) { copy(steps = it) }
        dp(LissajousArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        float(LissajousArtStyle::lineAlpha, range = 0f..1f) { copy(lineAlpha = it) }
        float(LissajousArtStyle::faintAlpha, range = 0f..1f) { copy(faintAlpha = it) }
        dp(LissajousArtStyle::head, range = 0f..15f) { copy(head = it) }
        dp(LissajousArtStyle::headGlow, range = 0f..50f) { copy(headGlow = it) }
        float(LissajousArtStyle::glowAlpha, range = 0f..1f) { copy(glowAlpha = it) }
        float(LissajousArtStyle::guideAlpha, range = 0f..1f) { copy(guideAlpha = it) }
        duration(LissajousArtStyle::drawPeriod, range = 4f..60f) { copy(drawPeriod = it) }
        float(LissajousArtStyle::drawShare, range = 0f..1f) { copy(drawShare = it) }
        duration(LissajousArtStyle::driftPeriod, range = 30f..500f) { copy(driftPeriod = it) }
        int(LissajousArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(LissajousArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(LissajousArtStyle::line, choices = colorChoices) { copy(line = it) }
        pick(LissajousArtStyle::pen, choices = colorChoices) { copy(pen = it) }
        pick(LissajousArtStyle::background, choices = colorChoices) { copy(background = it) }
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
    artSpec(name = "Polygons", defaults = PolygonsArtStyles.Default::resolve, create = ::PolygonsArt) {
        int(PolygonsArtStyle::rings, range = 1..12) { copy(rings = it) }
        float(PolygonsArtStyle::centerX, range = 0f..1f) { copy(centerX = it) }
        float(PolygonsArtStyle::centerY, range = 0f..1f) { copy(centerY = it) }
        dp(PolygonsArtStyle::firstRadius, range = 10f..80f) { copy(firstRadius = it) }
        dp(PolygonsArtStyle::ringSpacing, range = 10f..60f) { copy(ringSpacing = it) }
        int(PolygonsArtStyle::minSides, range = 3..6) { copy(minSides = it) }
        int(PolygonsArtStyle::maxSides, range = 6..12) { copy(maxSides = it) }
        dp(PolygonsArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        float(PolygonsArtStyle::lineAlpha, range = 0f..1f) { copy(lineAlpha = it) }
        dp(PolygonsArtStyle::dot, range = 0f..10f) { copy(dot = it) }
        float(PolygonsArtStyle::dotAlpha, range = 0f..1f) { copy(dotAlpha = it) }
        choice(PolygonsArtStyle::dotQuality) { copy(dotQuality = it) }
        float(PolygonsArtStyle::guideAlpha, range = 0f..1f) { copy(guideAlpha = it) }
        duration(PolygonsArtStyle::period, range = 10f..200f) { copy(period = it) }
        float(PolygonsArtStyle::slowdown, range = 0f..1f) { copy(slowdown = it) }
        toggle(PolygonsArtStyle::isAlternating) { copy(isAlternating = it) }
        duration(PolygonsArtStyle::tracePeriod, range = 2f..30f) { copy(tracePeriod = it) }
        float(PolygonsArtStyle::drawShare, range = 0f..1f) { copy(drawShare = it) }
        dp(PolygonsArtStyle::traceWidth, range = 0f..6f) { copy(traceWidth = it) }
        float(PolygonsArtStyle::traceAlpha, range = 0f..1f) { copy(traceAlpha = it) }
        dp(PolygonsArtStyle::head, range = 0f..15f) { copy(head = it) }
        int(PolygonsArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(PolygonsArtStyle::line, choices = colorChoices) { copy(line = it) }
        pick(PolygonsArtStyle::accent, choices = colorChoices) { copy(accent = it) }
        pick(PolygonsArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
    artSpec(name = "Radar", defaults = RadarArtStyles.Default::resolve, create = ::RadarArt) {
        float(RadarArtStyle::centerX, range = 0f..1f) { copy(centerX = it) }
        float(RadarArtStyle::centerY, range = 0f..1f) { copy(centerY = it) }
        float(RadarArtStyle::size, range = 0.1f..0.5f) { copy(size = it) }
        int(RadarArtStyle::rings, range = 1..8) { copy(rings = it) }
        dp(RadarArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        float(RadarArtStyle::ringAlpha, range = 0f..1f) { copy(ringAlpha = it) }
        float(RadarArtStyle::crossAlpha, range = 0f..1f) { copy(crossAlpha = it) }
        float(RadarArtStyle::crossReach, range = 0.5f..1.5f) { copy(crossReach = it) }
        int(RadarArtStyle::ticks, range = 12..144) { copy(ticks = it) }
        int(RadarArtStyle::majorEvery, range = 2..12) { copy(majorEvery = it) }
        dp(RadarArtStyle::tick, range = 0f..15f) { copy(tick = it) }
        dp(RadarArtStyle::majorTick, range = 0f..25f) { copy(majorTick = it) }
        float(RadarArtStyle::tickAlpha, range = 0f..1f) { copy(tickAlpha = it) }
        float(RadarArtStyle::wedge, range = 0f..180f) { copy(wedge = it) }
        float(RadarArtStyle::wedgeAlpha, range = 0f..1f) { copy(wedgeAlpha = it) }
        float(RadarArtStyle::beamAlpha, range = 0f..1f) { copy(beamAlpha = it) }
        int(RadarArtStyle::blips, range = 0..16) { copy(blips = it) }
        float(RadarArtStyle::inner, range = 0f..1f) { copy(inner = it) }
        dp(RadarArtStyle::blip, range = 0f..15f) { copy(blip = it) }
        float(RadarArtStyle::blipAlpha, range = 0f..1f) { copy(blipAlpha = it) }
        float(RadarArtStyle::fade, range = 0f..1f) { copy(fade = it) }
        dp(RadarArtStyle::halo, range = 0f..50f) { copy(halo = it) }
        float(RadarArtStyle::haloAlpha, range = 0f..1f) { copy(haloAlpha = it) }
        choice(RadarArtStyle::haloQuality) { copy(haloQuality = it) }
        duration(RadarArtStyle::period, range = 2f..30f) { copy(period = it) }
        int(RadarArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(RadarArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(RadarArtStyle::line, choices = colorChoices) { copy(line = it) }
        pick(RadarArtStyle::accent, choices = colorChoices) { copy(accent = it) }
        pick(RadarArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
    artSpec(name = "Spirograph", defaults = SpirographArtStyles.Default::resolve, create = ::SpirographArt) {
        int(SpirographArtStyle::curves, range = 1..6) { copy(curves = it) }
        float(SpirographArtStyle::centerX, range = 0f..1f) { copy(centerX = it) }
        float(SpirographArtStyle::centerY, range = 0f..1f) { copy(centerY = it) }
        float(SpirographArtStyle::size, range = 0.2f..1f) { copy(size = it) }
        float(SpirographArtStyle::minSize, range = 0.1f..0.9f) { copy(minSize = it) }
        float(SpirographArtStyle::minRoller, range = 0f..0.7f) { copy(minRoller = it) }
        float(SpirographArtStyle::maxRoller, range = 0.7f..1f) { copy(maxRoller = it) }
        float(SpirographArtStyle::minPen, range = 0f..0.85f) { copy(minPen = it) }
        float(SpirographArtStyle::maxPen, range = 0.85f..1f) { copy(maxPen = it) }
        int(SpirographArtStyle::loops, range = 2..24) { copy(loops = it) }
        float(SpirographArtStyle::epicycles, range = 0f..1f) { copy(epicycles = it) }
        int(SpirographArtStyle::steps, range = 60..360) { copy(steps = it) }
        dp(SpirographArtStyle::lineWidth, range = 0f..4f) { copy(lineWidth = it) }
        float(SpirographArtStyle::lineAlpha, range = 0f..1f) { copy(lineAlpha = it) }
        duration(SpirographArtStyle::drawPeriod, range = 6f..80f) { copy(drawPeriod = it) }
        float(SpirographArtStyle::drawShare, range = 0f..1f) { copy(drawShare = it) }
        duration(SpirographArtStyle::rotationPeriod, range = 50f..800f) { copy(rotationPeriod = it) }
        dp(SpirographArtStyle::headSize, range = 0f..15f) { copy(headSize = it) }
        dp(SpirographArtStyle::headGlow, range = 0f..50f) { copy(headGlow = it) }
        float(SpirographArtStyle::glowAlpha, range = 0f..1f) { copy(glowAlpha = it) }
        float(SpirographArtStyle::trailAlpha, range = 0f..1f) { copy(trailAlpha = it) }
        int(SpirographArtStyle::echoes, range = 0..5) { copy(echoes = it) }
        float(SpirographArtStyle::echoAngle, range = 0f..15f) { copy(echoAngle = it) }
        int(SpirographArtStyle::salt, range = 0..99) { copy(salt = it) }
        pick(SpirographArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(SpirographArtStyle::head, choices = colorChoices) { copy(head = it) }
        pick(SpirographArtStyle::background, choices = colorChoices) { copy(background = it) }
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
