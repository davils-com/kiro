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

package com.davils.kiro.ui.audio.palette

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import com.davils.kiro.ui.audio.AudioControlsStyles
import com.davils.kiro.ui.audio.fixture.coverOf
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val NEAR: Float = 0.02f
private const val SMALL: Float = 0.25f
private val red = Color(0xFFE11D48)
private val grey = Color(0xFF6B7280)
private val style = AudioControlsStyles.Default.resolve(Theme.light())
private val content = style.lightContent to style.darkContent

class CoverPaletteTest : FunSpec({
    context("palette") {
        test("takes the color that covers most of the cover and puts light content on a dark one") {
            val palette = coverPaletteOf(cover = coverOf(red), style = style)

            palette.dominant.red shouldBe (red.red plusOrMinus NEAR)
            palette.vibrant.red shouldBe (red.red plusOrMinus NEAR)
            palette.onDominant shouldBe style.lightContent
        }

        test("finds a vibrant accent next to a grey majority") {
            val palette = coverPaletteOf(cover = coverOf(background = grey, accent = red, accentShare = SMALL), style)

            palette.dominant.blue shouldBe (grey.blue plusOrMinus NEAR)
            palette.vibrant.red shouldBe (red.red plusOrMinus NEAR)
        }

        test("puts dark content on a light cover and falls back between the contents without visible pixels") {
            coverPaletteOf(cover = coverOf(Color.White), style = style).onDominant shouldBe style.darkContent
            val empty = coverPaletteOf(cover = ImageBitmap(2, 2), style = style)

            empty.dominant shouldBe empty.vibrant
        }

        test("picks the content that keeps the most contrast across every background") {
            contentOn(backgrounds = listOf(Color.White, grey), content = content) shouldBe style.darkContent
            contentOn(backgrounds = listOf(Color.Black, red), content = content) shouldBe style.lightContent
        }

        test("builds a fallback from the theme") {
            val colors = Theme.light().colors

            fallbackPaletteOf(colors).vibrant shouldBe colors.primary
            saturationOf(Color.Black) shouldBe 0f
        }

        test("takes the light and the dark content from the theme in both themes") {
            val light = Theme.light().colors
            val dark = AudioControlsStyles.Default.resolve(Theme.dark())

            style.lightContent shouldBe light.background
            style.darkContent shouldBe light.foreground
            dark.lightContent shouldBe Theme.dark().colors.foreground
        }
    }
})
