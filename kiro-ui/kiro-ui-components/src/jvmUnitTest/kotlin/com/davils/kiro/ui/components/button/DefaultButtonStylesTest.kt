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

package com.davils.kiro.ui.components.button

import androidx.compose.animation.core.SnapSpec
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.mix
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf

class DefaultButtonStylesTest : FunSpec({
    val light = Theme.light()
    val dark = Theme.dark()

    fun style(
        variant: ButtonVariant = ButtonVariant.Default,
        size: ButtonSize = ButtonSize.Default,
        theme: Theme = light,
    ): ButtonStyle = ButtonStyles.Default.resolve(theme = theme, variant = variant, size = size)

    context("sizes") {
        test("default is h-8 px-2.5 gap-1.5 rounded-lg with text-sm medium and size-4 icons") {
            val default = style()

            default.height shouldBe 32.dp
            default.horizontalPadding shouldBe 10.dp
            default.iconEdgePadding shouldBe 8.dp
            default.gap shouldBe 6.dp
            default.shape shouldBe RoundedCornerShape(10.dp)
            default.textStyle.fontSize shouldBe 14.sp
            default.textStyle.fontWeight shouldBe FontWeight.Medium
            default.iconSize shouldBe 16.dp
            default.minWidth shouldBe 0.dp
        }

        test("xs is h-6 px-2 gap-1 text-xs with size-3 icons and a radius of at most 10px") {
            val xs = style(size = ButtonSize.Xs)

            xs.height shouldBe 24.dp
            xs.horizontalPadding shouldBe 8.dp
            xs.iconEdgePadding shouldBe 6.dp
            xs.textStyle.fontSize shouldBe 12.sp
            xs.iconSize shouldBe 12.dp
            xs.shape shouldBe RoundedCornerShape(8.dp)
        }

        test("sm is h-7 with text of 0.8rem and size-3.5 icons") {
            val sm = style(size = ButtonSize.Sm)

            sm.height shouldBe 28.dp
            sm.textStyle.fontSize shouldBe 12.8.sp
            sm.iconSize shouldBe 14.dp
            sm.iconEdgePadding shouldBe 6.dp
        }

        test("lg is h-9") {
            style(size = ButtonSize.Lg).height shouldBe 36.dp
        }

        test("icon sizes are square") {
            style(size = ButtonSize.Icon).minWidth shouldBe 32.dp
            style(size = ButtonSize.IconXs).minWidth shouldBe 24.dp
            style(size = ButtonSize.IconSm).minWidth shouldBe 28.dp
            style(size = ButtonSize.IconLg).minWidth shouldBe 36.dp
            style(size = ButtonSize.IconXs).iconSize shouldBe 12.dp
            style(size = ButtonSize.Icon).horizontalPadding shouldBe 0.dp
        }

        test("cap the radius at the medium radius of the theme") {
            val small = light.copy(radii = light.radii.copy(md = 4.dp))

            style(size = ButtonSize.Sm, theme = small).shape shouldBe RoundedCornerShape(4.dp)
        }
    }

    context("variants") {
        test("default fills with the primary color and lightens it on hover") {
            val colors = style().colors

            colors.container.default shouldBe light.colors.primary
            colors.container.hovered shouldBe light.colors.primary.copy(alpha = 0.8f)
            colors.content.default shouldBe light.colors.primaryForeground
        }

        test("outline swaps the fill and the border of the input in dark mode") {
            val lightOutline = style(variant = ButtonVariant.Outline).colors
            val darkOutline = style(variant = ButtonVariant.Outline, theme = dark).colors

            lightOutline.container.default shouldBe light.colors.background
            lightOutline.container.hovered shouldBe light.colors.muted
            lightOutline.border.default shouldBe light.colors.border
            darkOutline.container.default shouldBe dark.colors.input
            darkOutline.container.hovered shouldBe dark.colors.input.copy(alpha = 0.25f)
            darkOutline.border.default shouldBe dark.colors.input.copy(alpha = 0.3f)
        }

        test("secondary mixes five percent of the foreground into its hover") {
            val colors = style(variant = ButtonVariant.Secondary).colors

            colors.container.hovered shouldBe light.colors.secondary.mix(light.colors.foreground, amount = 0.05f)
        }

        test("ghost is transparent and shows the muted color at half strength in dark mode") {
            style(variant = ButtonVariant.Ghost).colors.container.default shouldBe Color.Transparent
            style(variant = ButtonVariant.Ghost, theme = dark).colors.container.hovered shouldBe
                dark.colors.muted.copy(alpha = 0.5f)
        }

        test("destructive tints the destructive color more strongly in dark mode") {
            val lightColors = style(variant = ButtonVariant.Destructive).colors
            val darkColors = style(variant = ButtonVariant.Destructive, theme = dark).colors

            lightColors.container.default shouldBe light.colors.destructive.copy(alpha = 0.1f)
            darkColors.container.hovered shouldBe dark.colors.destructive.copy(alpha = 0.3f)
            lightColors.ring.focusVisible shouldBe light.colors.destructive.copy(alpha = 0.2f)
            darkColors.ring.focusVisible shouldBe dark.colors.destructive.copy(alpha = 0.4f)
        }

        test("link is underlined on hover in the primary color") {
            val link = style(variant = ButtonVariant.Link)

            link.isUnderlinedOnHover shouldBe true
            link.colors.content.default shouldBe light.colors.primary
            style().isUnderlinedOnHover shouldBe false
        }
    }

    context("states") {
        test("show a ring of half the ring color around a keyboard focused button") {
            val colors = style().colors

            colors.border.focusVisible shouldBe light.colors.ring
            colors.ring.focusVisible shouldBe light.colors.ring.copy(alpha = 0.5f)
            colors.ring.default shouldBe Color.Transparent
        }

        test("mark an invalid button in the destructive color") {
            val lightColors = style().colors
            val darkColors = style(theme = dark).colors

            lightColors.border.invalid shouldBe light.colors.destructive
            darkColors.border.invalid shouldBe dark.colors.destructive.copy(alpha = 0.5f)
            darkColors.ring.invalid shouldBe dark.colors.destructive.copy(alpha = 0.4f)
        }

        test("animate colors unless motion is reduced") {
            style(theme = reducedMotion).colorAnimation.shouldBeInstanceOf<SnapSpec<Color>>()
        }

        test("press down by one pixel and fade to half when disabled") {
            style().pressOffset shouldBe 1.dp
            val half = 0.5f

            style().disabledAlpha shouldBe half
        }
    }

    context("custom variants and sizes") {
        test("need a style of their own") {
            shouldThrow<IllegalArgumentException> { style(variant = Unknown) }.message shouldContain "variant"
            shouldThrow<IllegalArgumentException> { style(size = Unknown) }.message shouldContain "size"
        }
    }
})
