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

package com.davils.kiro.ui.components.command

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.inputgroup.defaultInputGroupStyle
import com.davils.kiro.ui.components.menu.defaultMenuStyle
import com.davils.kiro.ui.components.primitive.overlay.PanelStyle
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.tabs.TabsVariant
import com.davils.kiro.ui.components.tabs.defaultTabsStyle
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.elevation.BoxShadow
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Search

private const val SOFT: Float = 0.3f
private const val HALF: Float = 0.5f
private const val SCRIM: Float = 0.5f

/**
 * Resolves the style of a [Command] menu for a theme.
 *
 * Replace it in the components of a theme under [CommandStyleKey] to change the style of every command menu.
 *
 * @since 1.0.0
 */
public fun interface CommandStyles {
    /**
     * Returns the style of a [Command] menu in a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the command menu.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): CommandStyle

    /**
     * Holds the default [CommandStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [Command] menu, derived from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: CommandStyles = CommandStyles(::defaultCommandStyle)
    }
}

/**
 * Returns the style of a [Command] menu in the theme of the composition, resolved by the [CommandStyles] under
 * [CommandStyleKey].
 *
 * @return the style of the command menu.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun commandStyle(): CommandStyle = CommandStyleKey.current.resolve(theme)

internal fun defaultCommandStyle(theme: Theme): CommandStyle {
    val colors = theme.colors
    val soft = colors.input.copy(alpha = SOFT)
    val input = defaultInputGroupStyle(theme)
    val menu = defaultMenuStyle(theme)
    return CommandStyle(
        panel = PanelStyle(
            container = colors.popover,
            content = colors.popoverForeground,
            shape = RoundedCornerShape(theme.radii.xl),
            ring = Border(width = 0.dp, color = Color.Transparent),
            shadow = BoxShadow.None,
            padding = 4.dp,
        ),
        input = input.copy(
            colors = input.colors.copy(
                container = StateValue(default = soft),
                border = input.colors.border.copy(default = soft),
            ),
        ),
        searchIcon = Lucide.Search,
        searchIconAlpha = HALF,
        listMaxHeight = 288.dp,
        groupPadding = 4.dp,
        headingStyle = theme.textStyleOf(size = theme.typography.scale.xs, weight = FontWeight.Medium),
        headingColor = colors.mutedForeground,
        headingHorizontalPadding = 8.dp,
        headingVerticalPadding = 6.dp,
        item = menu.copy(
            highlight = colors.muted,
            highlightContent = colors.foreground,
            itemShape = RoundedCornerShape(theme.radii.sm),
            itemHorizontalPadding = 8.dp,
            itemVerticalPadding = 6.dp,
            gap = 8.dp,
        ),
        emptyStyle = theme.textStyleOf(size = theme.typography.scale.sm),
        emptyPadding = 24.dp,
        separatorColor = colors.border,
        gap = 4.dp,
        tabs = defaultTabsStyle(theme = theme, variant = TabsVariant.Line),
        pathStyle = theme.textStyleOf(size = theme.typography.scale.sm, weight = FontWeight.Medium),
        pathColor = colors.mutedForeground,
        scrim = Color.Black.copy(alpha = SCRIM),
        dialogPanel = PanelStyle(
            container = colors.popover,
            content = colors.popoverForeground,
            shape = RoundedCornerShape(theme.radii.xl),
            ring = Border(width = 1.dp, color = colors.border),
            shadow = theme.shadows.lg,
            padding = 4.dp,
        ),
        dialogWidth = 560.dp,
        dialogOffset = 96.dp,
    )
}

/**
 * The key of the [CommandStyles] of a [Command] menu in the components of a theme; its default is
 * [CommandStyles.Default].
 *
 * @since 1.0.0
 */
public val CommandStyleKey: StyleKey<CommandStyles> = StyleKey(name = "command") { _ -> CommandStyles.Default }
