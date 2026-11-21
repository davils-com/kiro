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

import com.davils.kiro.ui.motion.shader.grain.GrainArt
import com.davils.kiro.ui.motion.shader.grain.GrainArtStyle
import com.davils.kiro.ui.motion.shader.grain.GrainArtStyles
import com.davils.kiro.ui.motion.shader.mesh.MeshGradientArt
import com.davils.kiro.ui.motion.shader.mesh.MeshGradientArtStyle
import com.davils.kiro.ui.motion.shader.mesh.MeshGradientArtStyles
import com.davils.kiro.ui.motion.shader.silk.SilkArt
import com.davils.kiro.ui.motion.shader.silk.SilkArtStyle
import com.davils.kiro.ui.motion.shader.silk.SilkArtStyles

internal val shaderSpecs: List<ArtSpec<*>> = listOf(
    artSpec(name = "Grain", defaults = GrainArtStyles.Default::resolve, create = ::GrainArt) {
        pick(GrainArtStyle::shade, choices = colorChoices) { copy(shade = it) }
        pick(GrainArtStyle::tone, choices = colorChoices) { copy(tone = it) }
        dp(GrainArtStyle::cell, range = 1f..4f) { copy(cell = it) }
        float(GrainArtStyle::amount, range = 0f..1f) { copy(amount = it) }
        float(GrainArtStyle::balance, range = 0f..1f) { copy(balance = it) }
        fixed(GrainArtStyle::refresh)
        int(GrainArtStyle::dots, range = 1_000..12_000) { copy(dots = it) }
        choice(GrainArtStyle::backend) { copy(backend = it) }
    },
    artSpec(name = "Mesh Gradient", defaults = MeshGradientArtStyles.Default::resolve, create = ::MeshGradientArt) {
        pick(MeshGradientArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(MeshGradientArtStyle::background, choices = colorChoices) { copy(background = it) }
        int(MeshGradientArtStyle::anchors, range = 2..8) { copy(anchors = it) }
        float(MeshGradientArtStyle::spread, range = 0f..1f) { copy(spread = it) }
        float(MeshGradientArtStyle::radius, range = 0f..1f) { copy(radius = it) }
        float(MeshGradientArtStyle::softness, range = 0.5f..4f) { copy(softness = it) }
        float(MeshGradientArtStyle::intensity, range = 0f..1f) { copy(intensity = it) }
        float(MeshGradientArtStyle::drift, range = 0f..1f) { copy(drift = it) }
        duration(MeshGradientArtStyle::period, range = 8f..150f) { copy(period = it) }
        float(MeshGradientArtStyle::pointerPull, range = 0f..1f) { copy(pointerPull = it) }
        float(MeshGradientArtStyle::dither, range = 0f..1f) { copy(dither = it) }
        choice(MeshGradientArtStyle::backend) { copy(backend = it) }
    },
    artSpec(name = "Silk", defaults = SilkArtStyles.Default::resolve, create = ::SilkArt) {
        pick(SilkArtStyle::background, choices = colorChoices) { copy(background = it) }
        pick(SilkArtStyle::color, choices = colorChoices) { copy(color = it) }
        pick(SilkArtStyle::shadow, choices = colorChoices) { copy(shadow = it) }
        pick(SilkArtStyle::sheen, choices = colorChoices) { copy(sheen = it) }
        float(SilkArtStyle::intensity, range = 0f..1f) { copy(intensity = it) }
        float(SilkArtStyle::shadowAlpha, range = 0f..1f) { copy(shadowAlpha = it) }
        float(SilkArtStyle::sheenAlpha, range = 0f..1f) { copy(sheenAlpha = it) }
        float(SilkArtStyle::scale, range = 0.01f..0.15f) { copy(scale = it) }
        float(SilkArtStyle::warp, range = 0f..3f) { copy(warp = it) }
        float(SilkArtStyle::relief, range = 0f..2f) { copy(relief = it) }
        float(SilkArtStyle::shine, range = 4f..64f) { copy(shine = it) }
        float(SilkArtStyle::angle, range = -90f..90f) { copy(angle = it) }
        duration(SilkArtStyle::period, range = 10f..150f) { copy(period = it) }
        float(SilkArtStyle::pointerLight, range = 0f..1f) { copy(pointerLight = it) }
        float(SilkArtStyle::dither, range = 0f..1f) { copy(dither = it) }
        choice(SilkArtStyle::backend) { copy(backend = it) }
    },
)
