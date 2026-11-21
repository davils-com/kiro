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

package com.davils.kiro.ui.window.splash.checklist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.CircleCheck
import com.davils.kiro.ui.icons.lucide.CircleDashed
import com.davils.kiro.ui.icons.lucide.CircleMinus
import com.davils.kiro.ui.icons.lucide.CircleX
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.motion.scene.ScenePresets
import com.davils.kiro.ui.window.splash.SplashMessages
import com.davils.kiro.ui.window.splash.defaultSplashFailureStyle
import com.davils.kiro.ui.window.splash.spinnerOf
import com.davils.kiro.ui.window.textStyleOf

private const val BAND_FRACTION: Float = 0.42f

/**
 * Derives the style of a [ChecklistSplash] from the tokens of a theme.
 *
 * Register an implementation under [ChecklistSplashStyleKey] in the components of a theme to change how every
 * checklist splash looks.
 *
 * @since 1.0.0
 */
public fun interface ChecklistSplashStyles {
    /**
     * Returns the style of a checklist splash for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the checklist splash.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): ChecklistSplashStyle

    /**
     * Holds the default [ChecklistSplashStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a checklist splash.
         *
         * @since 1.0.0
         */
        public val Default: ChecklistSplashStyles = ChecklistSplashStyles(::defaultChecklistSplashStyle)
    }
}

/**
 * The key of the styles of a [ChecklistSplash] in the components of a theme, with
 * [ChecklistSplashStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val ChecklistSplashStyleKey: StyleKey<ChecklistSplashStyles> =
    StyleKey(name = "checklist-splash") { _ -> ChecklistSplashStyles.Default }

/**
 * Returns the style of a [ChecklistSplash] in the current theme, with the overrides of its components.
 *
 * @return the style of the checklist splash.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun checklistSplashStyle(): ChecklistSplashStyle = ChecklistSplashStyleKey.current.resolve(Theme.current)

internal fun defaultChecklistSplashStyle(theme: Theme): ChecklistSplashStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    val typography = theme.typography
    val scale = typography.scale
    val families = typography.families
    return ChecklistSplashStyle(
        size = DpSize(width = spacing(steps = 180f), height = spacing(steps = 110f)),
        scene = ScenePresets.Signal,
        bandFraction = BAND_FRACTION,
        divider = Border(width = theme.borders.thin, color = colors.border),
        background = colors.background,
        content = colors.foreground,
        muted = colors.mutedForeground,
        padding = spacing(steps = 8f),
        gap = spacing(steps = 6f),
        headerGap = spacing(steps = 3f),
        logoSize = spacing(steps = 10f),
        nameStyle = theme.textStyleOf(size = scale.lg, weight = FontWeight.SemiBold, family = families.heading),
        versionStyle = theme.textStyleOf(size = scale.xs, weight = FontWeight.Medium, family = families.mono),
        stepGap = spacing(steps = 2.5f),
        iconGap = spacing(steps = 2.5f),
        iconSize = spacing(steps = 4f),
        stepStyle = theme.textStyleOf(size = scale.sm),
        pendingColor = colors.mutedForeground,
        runningColor = colors.foreground,
        doneColor = colors.status.success.foreground,
        failedColor = colors.destructive,
        skippedColor = colors.mutedForeground,
        pendingIcon = Lucide.CircleDashed,
        doneIcon = Lucide.CircleCheck,
        failedIcon = Lucide.CircleX,
        skippedIcon = Lucide.CircleMinus,
        spinner = theme.spinnerOf(steps = 4f),
        failure = defaultSplashFailureStyle(theme),
        labels = SplashMessages.labels,
    )
}
