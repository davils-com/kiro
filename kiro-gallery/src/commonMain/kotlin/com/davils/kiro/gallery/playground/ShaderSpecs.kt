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
)
