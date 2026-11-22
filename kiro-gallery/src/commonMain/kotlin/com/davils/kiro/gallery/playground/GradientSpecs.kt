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

import com.davils.kiro.ui.motion.art.gradient.GradientArt
import com.davils.kiro.ui.motion.art.gradient.GradientArtStyle
import com.davils.kiro.ui.motion.art.gradient.GradientArtStyles

internal val gradientSpecs: List<ArtSpec<*>> = listOf(
    artSpec(name = "Gradient", defaults = GradientArtStyles.Default::resolve, create = ::GradientArt) {
        choice(GradientArtStyle::kind) { copy(kind = it) }
        float(GradientArtStyle::strength, range = 0f..1f) { copy(strength = it) }
        int(GradientArtStyle::steps, range = 4..48) { copy(steps = it) }
        duration(GradientArtStyle::rotation, range = 20f..300f) { copy(rotation = it) }
        dp(GradientArtStyle::orbit, range = 0f..400f) { copy(orbit = it) }
        duration(GradientArtStyle::orbitPeriod, range = 10f..200f) { copy(orbitPeriod = it) }
        float(GradientArtStyle::reach, range = 0f..1f) { copy(reach = it) }
        pick(GradientArtStyle::colors, choices = colorListChoices) { copy(colors = it) }
        pick(GradientArtStyle::background, choices = colorChoices) { copy(background = it) }
    },
)
