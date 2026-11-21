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

package com.davils.kiro.ui.motion.art.tiling

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.motion.fixture.ArtCase
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.ValueCase
import com.davils.kiro.ui.motion.fixture.artChecks
import com.davils.kiro.ui.motion.fixture.colors
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.fingerprint
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.render
import com.davils.kiro.ui.motion.fixture.save
import com.davils.kiro.ui.motion.fixture.valueChecks
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration

private const val FEW_STRIPES: Int = 5

@OptIn(ExperimentalTestApi::class)
class BauhausArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultBauhausArtStyle(theme)
    val base = BauhausArt().print()

    artChecks(
        ArtCase(
            name = "bauhaus",
            art = BauhausArt(),
            changed = BauhausArt(style = style.copy(gap = 0.dp, radius = 0.dp)),
            recolored = BauhausArt(
                style.copy(colors = listOf(Color.Red, Color.Blue), outline = Color.Green, background = Color.Black),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "BauhausArt",
            value = BauhausArt(style),
            copy = BauhausArt(style).copy(),
            changed = BauhausArt(),
        ),
    )
    valueChecks(
        ValueCase(name = "BauhausArtStyle", value = style, copy = style.copy(), changed = style.copy(cell = 1.dp)),
    )

    test("draws every shape on its own") {
        val prints = BauhausShape.entries.map { shape ->
            val alone = style.copy(weights = mapOf(shape to 1f), stripes = FEW_STRIPES, accentShare = 1f)
            val image = BauhausArt(alone).render()
            image.save(name = "bauhaus-shape-${shape.name}")
            image.fingerprint()
        }

        prints.toSet() shouldHaveSize BauhausShape.entries.size
    }

    test("leaves its tiles plain without weights") {
        val plain = BauhausArt(style.copy(weights = emptyMap()))

        plain.print() shouldNotBe base
        plain.colors() shouldBeLessThan BauhausArt().colors()
    }

    test("shows every shape in full color, or none, and hides the borders") {
        BauhausArt(style.copy(accentShare = 1f)).print() shouldNotBe base
        BauhausArt(style.copy(accentShare = 0f)).print() shouldNotBe base
        BauhausArt(style.copy(outlineAlpha = 0f)).print() shouldNotBe base
    }

    test("draws nothing but its background without colors") {
        BauhausArt(style.copy(colors = emptyList())).colors() shouldBe 1
    }

    test("is static only when its tiles never turn") {
        BauhausArt(style.copy(period = Duration.INFINITE)).isStatic shouldBe true
        BauhausArt(style).isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(BauhausArtStyleKey) { _, _ ->
                BauhausArtStyles { current -> defaultBauhausArtStyle(current).copy(gap = 0.dp) }
            },
        )

        BauhausArt().print(Render(theme = custom)) shouldNotBe base
        BauhausArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: BauhausArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = bauhausArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "BauhausArt", style = style) { variant -> BauhausArt(style = variant) }
})
