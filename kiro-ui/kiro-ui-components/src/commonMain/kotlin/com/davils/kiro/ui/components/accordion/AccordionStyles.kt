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

package com.davils.kiro.ui.components.accordion

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.collapsible.CollapsibleStyle
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.motion.fadeTransition
import com.davils.kiro.ui.components.primitive.motion.sizeTransition
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.ChevronDown
import com.davils.kiro.ui.icons.lucide.ChevronUp
import com.davils.kiro.ui.icons.lucide.Lucide

private const val HALF: Float = 0.5f

/**
 * Derives the [AccordionStyle] of an [Accordion] from a theme; the value of [AccordionStyleKey] in the component styles
 * of a theme.
 *
 * @since 1.0.0
 */
public fun interface AccordionStyles {
    /**
     * Returns the style of an [Accordion] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the accordion.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): AccordionStyle

    /**
     * Holds the default styles of an [Accordion].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of an [Accordion], derived only from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: AccordionStyles = AccordionStyles(::defaultAccordionStyle)
    }
}

/**
 * Returns the style of an [Accordion] in the theme of the composition, with the overrides of its component styles.
 *
 * @return the style of the accordion.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun accordionStyle(): AccordionStyle = AccordionStyleKey.current.resolve(theme)

internal fun defaultAccordionStyle(theme: Theme): AccordionStyle = AccordionStyle(
    triggerPadding = 10.dp,
    triggerShape = RoundedCornerShape(theme.radii.lg),
    triggerTextStyle = theme.textStyleOf(size = theme.typography.scale.sm, weight = FontWeight.Medium),
    isUnderlinedOnHover = true,
    iconSize = 16.dp,
    iconColor = theme.colors.mutedForeground,
    expandIcon = Lucide.ChevronDown,
    collapseIcon = Lucide.ChevronUp,
    contentTextStyle = theme.textStyleOf(size = theme.typography.scale.sm),
    contentPadding = 10.dp,
    divider = Border(width = 1.dp, color = theme.colors.border),
    focusBorder = Border(width = 1.dp, color = theme.colors.ring),
    focusRing = Border(width = theme.focusRing.width, color = theme.colors.ring.copy(alpha = HALF)),
    disabledAlpha = HALF,
    colorAnimation = theme.colorTransition(),
    motion = accordionMotion(theme),
)

private fun accordionMotion(theme: Theme): CollapsibleStyle {
    if (theme.motion.isReduced) return CollapsibleStyle(enter = EnterTransition.None, exit = ExitTransition.None)
    return CollapsibleStyle(
        enter = expandVertically(
            animationSpec = theme.sizeTransition(),
            expandFrom = Alignment.Top,
        ) +
            fadeIn(animationSpec = theme.fadeTransition()),
        exit = shrinkVertically(
            animationSpec = theme.sizeTransition(),
            shrinkTowards = Alignment.Top,
        ) +
            fadeOut(animationSpec = theme.fadeTransition()),
    )
}

/**
 * The key of the styles of an [Accordion] in the component styles of a theme.
 *
 * @since 1.0.0
 */
public val AccordionStyleKey: StyleKey<AccordionStyles> = StyleKey(name = "accordion") { _ -> AccordionStyles.Default }
