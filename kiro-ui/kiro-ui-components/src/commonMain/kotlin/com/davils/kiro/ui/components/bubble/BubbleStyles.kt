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

package com.davils.kiro.ui.components.bubble

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.darkAware
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.mix
import com.davils.kiro.ui.core.color.toColor
import com.davils.kiro.ui.core.color.toOklch
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val HALF: Float = 0.5f
private const val HOVER: Float = 0.8f
private const val SHADE: Float = 0.05f
private const val INPUT: Float = 0.3f
private const val MAX_WIDTH: Float = 0.8f
private const val OVERLAP: Float = 0.75f

private val paints: Map<BubbleVariant, (Theme) -> BubblePaint> = mapOf(
    BubbleVariant.Default to { theme ->
        val colors = theme.colors
        BubblePaint(
            container = colors.primary,
            content = colors.primaryForeground,
            hover = colors.primary.copy(alpha = HOVER),
        )
    },
    BubbleVariant.Secondary to { theme ->
        val colors = theme.colors
        BubblePaint(
            container = colors.secondary,
            content = colors.secondaryForeground,
            hover = colors.secondary.mix(colors.foreground, SHADE),
        )
    },
    BubbleVariant.Muted to { theme ->
        val colors = theme.colors
        BubblePaint(
            container = colors.muted,
            content = colors.foreground,
            hover = colors.muted.mix(colors.foreground, SHADE),
        )
    },
    BubbleVariant.Tinted to { theme ->
        BubblePaint(
            container = theme.tint(light = 0.93, dark = 0.3, chroma = 0.4),
            content = theme.colors.foreground,
            hover = theme.tint(light = 0.88, dark = 0.35, chroma = 0.5),
        )
    },
    BubbleVariant.Outline to { theme ->
        val colors = theme.colors
        val hover = colors.input.copy(alpha = INPUT).takeIf { theme.isDark } ?: colors.muted
        BubblePaint(container = colors.background, content = colors.foreground, hover = hover)
    },
    BubbleVariant.Ghost to { theme ->
        val colors = theme.colors
        val hover = colors.muted.copy(alpha = HALF).takeIf { theme.isDark } ?: colors.muted
        BubblePaint(container = Color.Transparent, content = colors.foreground, hover = hover)
    },
    BubbleVariant.Destructive to { theme ->
        val destructive = theme.colors.destructive
        BubblePaint(
            container = destructive.copy(alpha = theme.darkAware(light = 0.1f, dark = 0.2f)),
            content = destructive,
            hover = destructive.copy(alpha = theme.darkAware(light = 0.2f, dark = 0.3f)),
        )
    },
)

/**
 * Derives the [BubbleStyle] of a [Bubble] of a variant from a theme; the value of [BubbleStyleKey] in the component
 * styles of a theme.
 *
 * @since 1.0.0
 */
public fun interface BubbleStyles {
    /**
     * Returns the style of a [Bubble] of a variant for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @param variant the variant of the bubble.
     * @return the style of the bubble.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, variant: BubbleVariant): BubbleStyle

    /**
     * Holds the default styles of a [Bubble].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a [Bubble], derived only from the tokens of the theme; they fail with an
         * [IllegalArgumentException] for a variant other than those of [BubbleVariant].
         *
         * @since 1.0.0
         */
        public val Default: BubbleStyles = BubbleStyles(::defaultBubbleStyle)
    }
}

/**
 * Returns the style of a [Bubble] of a variant in the theme of the composition, with the overrides of its component
 * styles.
 *
 * @param variant the variant of the bubble.
 * @return the style of the bubble.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun bubbleStyle(variant: BubbleVariant): BubbleStyle = BubbleStyleKey.current.resolve(theme, variant)

internal fun defaultBubbleStyle(theme: Theme, variant: BubbleVariant): BubbleStyle {
    val paint = requireNotNull(paints[variant]) { "No default style for the bubble variant $variant." }(theme)
    val colors = theme.colors
    val isGhost = variant == BubbleVariant.Ghost
    val size = theme.typography.scale.sm
    return BubbleStyle(
        shape = RectangleShape.takeIf { isGhost } ?: RoundedCornerShape(theme.radii.xl),
        container = paint.container,
        content = paint.content,
        hover = paint.hover,
        border = colors.border.takeIf { variant == BubbleVariant.Outline } ?: Color.Transparent,
        focusBorder = colors.ring,
        ring = colors.ring.copy(alpha = HALF),
        ringWidth = 3.dp,
        padding = PaddingValues(0.dp).takeIf { isGhost } ?: PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        textStyle = theme.textStyleOf(size = size, lineHeight = theme.typography.leading.relaxed),
        maxWidthFraction = 1f.takeIf { isGhost } ?: MAX_WIDTH,
        gap = 4.dp,
        groupGap = 8.dp,
        reactions = BubbleReactionsStyle(
            shape = CircleShape,
            container = colors.muted,
            content = colors.foreground,
            ring = colors.card,
            ringWidth = 3.dp,
            padding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
            gap = 4.dp,
            inset = 12.dp,
            overlap = OVERLAP,
            textStyle = theme.textStyleOf(size = size),
        ),
    )
}

private fun Theme.tint(light: Double, dark: Double, chroma: Double): Color {
    val primary = colors.primary.toOklch()
    val lightness = dark.takeIf { isDark } ?: light
    return primary.copy(lightness = lightness, chroma = primary.chroma * chroma).toColor()
}

/**
 * The key of the styles of a [Bubble] in the component styles of a theme.
 *
 * @since 1.0.0
 */
public val BubbleStyleKey: StyleKey<BubbleStyles> = StyleKey(name = "bubble") { _ -> BubbleStyles.Default }
