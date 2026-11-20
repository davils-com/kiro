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

package com.davils.kiro.ui.core.style

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private val borderKey: StyleKey<Border> = StyleKey(name = "border") { theme ->
    Border(width = theme.borders.thin, color = theme.colors.border)
}

private val ringKey: StyleKey<Color> = StyleKey(name = "ring") { theme -> theme.colors.ring }

class ComponentStylesTest : FunSpec({
    val theme = Theme.light()

    context("resolve") {
        test("returns the default of the key without an override") {
            ComponentStyles.Default.resolve(borderKey, theme) shouldBe Border(width = 1.dp, color = theme.colors.border)
        }

        test("derives the default from the tokens of the given theme") {
            ComponentStyles.Default.resolve(ringKey, Theme.dark()) shouldBe Theme.dark().colors.ring
        }

        test("replaces the style as a whole") {
            val styles = ComponentStyles.Default.with(ringKey) { _, _ -> Color.Red }

            styles.resolve(ringKey, theme) shouldBe Color.Red
        }

        test("changes single values of the default") {
            val styles = ComponentStyles.Default.with(borderKey) { _, style -> style.copy(width = 3.dp) }

            styles.resolve(borderKey, theme) shouldBe Border(width = 3.dp, color = theme.colors.border)
        }

        test("hands the theme to the override") {
            val styles = ComponentStyles.Default.with(ringKey) { current, _ -> current.colors.destructive }

            styles.resolve(ringKey, theme) shouldBe theme.colors.destructive
        }

        test("stacks the overrides of a key in the order they were added") {
            val wide = ComponentStyles.Default.with(borderKey) { _, style -> style.copy(width = 3.dp) }
            val styles = wide.with(borderKey) { _, style -> style.copy(width = style.width * 2) }

            styles.resolve(borderKey, theme).width shouldBe 6.dp
        }

        test("leaves the styles of other keys alone") {
            val styles = ComponentStyles.Default.with(ringKey) { _, _ -> Color.Red }

            styles.resolve(borderKey, theme) shouldBe borderKey.defaultFor(theme)
        }
    }

    context("without") {
        test("brings back the default of a key") {
            val styles = ComponentStyles.Default.with(ringKey) { _, _ -> Color.Red }.without(ringKey)

            styles.resolve(ringKey, theme) shouldBe theme.colors.ring
            styles.keys.shouldBeEmpty()
        }
    }

    context("keys") {
        test("lists every overridden key once, in the order of its first override") {
            val red = ComponentStyles.Default.with(ringKey) { _, _ -> Color.Red }
            val bordered = red.with(borderKey) { _, style -> style }
            val styles = bordered.with(ringKey) { _, _ -> Color.Blue }

            styles.keys shouldBe listOf(ringKey, borderKey)
        }
    }

    context("identity") {
        test("tells keys with the same name apart") {
            val other: StyleKey<Color> = StyleKey(name = "ring") { _ -> Color.Green }
            val styles = ComponentStyles.Default.with(other) { _, _ -> Color.Red }

            styles.resolve(ringKey, theme) shouldBe theme.colors.ring
            ringKey.toString() shouldBe "StyleKey(ring)"
        }

        test("compares styles by their overrides") {
            val styles = ComponentStyles.Default.with(ringKey) { _, _ -> Color.Red }

            ComponentStyles.Default shouldBe ComponentStyles.Default.without(ringKey)
            styles shouldNotBe ComponentStyles.Default
            styles.hashCode() shouldBe styles.hashCode()
            styles.toString() shouldBe "ComponentStyles(keys=[StyleKey(ring)])"
        }
    }
})
