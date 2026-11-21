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

package com.davils.kiro.ui.window.splash

import androidx.compose.ui.text.font.FontWeight
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.progress.ProgressStyle
import com.davils.kiro.ui.components.progress.ProgressStyleKey
import com.davils.kiro.ui.components.spinner.SpinnerStyle
import com.davils.kiro.ui.components.spinner.SpinnerStyleKey
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.icons.lucide.CircleX
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.window.textStyleOf

private const val FAILURE_LINES: Int = 4

internal fun defaultSplashFailureStyle(theme: Theme): SplashFailureStyle {
    val spacing = theme.spacing
    return SplashFailureStyle(
        icon = Lucide.CircleX,
        color = theme.colors.destructive,
        textStyle = theme.textStyleOf(size = theme.typography.scale.sm, weight = FontWeight.Normal),
        maxLines = FAILURE_LINES,
        gap = spacing(steps = 3f),
        actionGap = spacing(steps = 2f),
        closeVariant = ButtonVariant.Outline,
        retryVariant = ButtonVariant.Default,
        buttonSize = ButtonSize.Sm,
    )
}

internal fun Theme.spinnerOf(steps: Float): SpinnerStyle {
    val spinner = components.resolve(key = SpinnerStyleKey, theme = this)
    return spinner.resolve(this).copy(size = spacing(steps = steps))
}

internal fun Theme.progressStyle(): ProgressStyle {
    val progress = components.resolve(key = ProgressStyleKey, theme = this)
    return progress.resolve(this)
}
