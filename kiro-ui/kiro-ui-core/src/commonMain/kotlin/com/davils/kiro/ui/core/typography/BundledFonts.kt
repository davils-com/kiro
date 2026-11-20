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

package com.davils.kiro.ui.core.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.davils.kiro.ui.core.resources.Res
import com.davils.kiro.ui.core.resources.archivo
import com.davils.kiro.ui.core.resources.geist_mono
import com.davils.kiro.ui.core.resources.inter
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.Font as ResourceFont

private const val HEADING_WIDTH: Float = 125f

private val weights: List<FontWeight> = listOf(
    FontWeight.Thin,
    FontWeight.ExtraLight,
    FontWeight.Light,
    FontWeight.Normal,
    FontWeight.Medium,
    FontWeight.SemiBold,
    FontWeight.Bold,
    FontWeight.ExtraBold,
    FontWeight.Black,
)

@Composable
internal fun bundledFontFamilies(): FontFamilies = FontFamilies(
    sans = variableFamily(resource = Res.font.inter, width = null),
    heading = variableFamily(resource = Res.font.archivo, width = HEADING_WIDTH),
    mono = variableFamily(resource = Res.font.geist_mono, width = null),
)

@Composable
private fun variableFamily(resource: FontResource, width: Float?): FontFamily {
    val fonts: List<Font> = weights.map { weight ->
        ResourceFont(resource = resource, weight = weight, variationSettings = axesOf(weight = weight, width = width))
    }
    return FontFamily(fonts)
}

private fun axesOf(weight: FontWeight, width: Float?): FontVariation.Settings {
    val weightAxis = FontVariation.weight(weight.weight)
    if (width == null) return FontVariation.Settings(weightAxis)
    return FontVariation.Settings(weightAxis, FontVariation.width(width))
}
