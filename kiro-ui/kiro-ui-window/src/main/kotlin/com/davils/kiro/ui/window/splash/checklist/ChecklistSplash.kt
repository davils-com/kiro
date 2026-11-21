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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import com.davils.kiro.ui.components.spinner.Spinner
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.window.splash.Splash
import com.davils.kiro.ui.window.splash.SplashBackdrop
import com.davils.kiro.ui.window.splash.SplashFailure
import com.davils.kiro.ui.window.splash.SplashLogo
import com.davils.kiro.ui.window.splash.SplashStep
import com.davils.kiro.ui.window.splash.SplashStepStatus
import com.davils.kiro.ui.window.splash.endDivider

/**
 * Shows the start of an application as a checklist beside a band: the band plays a generative scene with the logo,
 * the name and the version over its top, and the list beside it shows every step with an icon and a color for its
 * status.
 *
 * Once the start failed, the message of the failure, a button that closes and, with [onRetry], a button that starts
 * again show below the list.
 *
 * @param splash what the splash shows.
 * @param modifier the modifier of the splash, which fills the space it gets.
 * @param onClose called by the close button of a failed start.
 * @param onRetry called by the retry button of a failed start, or `null` to show no retry button.
 * @param style how the splash looks.
 * @param logo the logo of the application, which receives the modifier that sizes it.
 * @param stepIcon the icon of a step, which receives the step and the modifier that sizes it.
 * @param backdrop what fills the band behind the logo, by default the scene of the [style], which receives the
 * modifier that fills it.
 * @since 1.0.0
 */
@Composable
public fun ChecklistSplash(
    splash: Splash,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onRetry: (() -> Unit)? = null,
    style: ChecklistSplashStyle = checklistSplashStyle(),
    logo: @Composable (Modifier) -> Unit = { slot -> SplashLogo(modifier = slot) },
    stepIcon: @Composable (SplashStep, Modifier) -> Unit = { step, slot ->
        StepIcon(status = step.status, modifier = slot, style = style)
    },
    backdrop: @Composable (Modifier) -> Unit = { slot ->
        SplashBackdrop(scene = style.scene, splash = splash, modifier = slot)
    },
) {
    Row(modifier = modifier.then(Modifier.fillMaxSize().background(style.background))) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(style.bandFraction)
                .clipToBounds()
                .endDivider(style.divider),
        ) {
            backdrop(Modifier.fillMaxSize())
            Header(splash = splash, logo = logo, style = style)
        }
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight().padding(style.padding),
            verticalArrangement = Arrangement.spacedBy(style.gap),
        ) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                Column(
                    modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(style.stepGap),
                ) {
                    splash.steps.forEach { step ->
                        key(step.id) { StepRow(step = step, icon = stepIcon, style = style) }
                    }
                }
            }
            splash.failure?.let { message ->
                SplashFailure(
                    message = message,
                    onClose = onClose,
                    onRetry = onRetry,
                    labels = style.labels,
                    style = style.failure,
                )
            }
        }
    }
}

@Composable
private fun Header(splash: Splash, logo: @Composable (Modifier) -> Unit, style: ChecklistSplashStyle) {
    Column(
        modifier = Modifier.padding(style.padding),
        verticalArrangement = Arrangement.spacedBy(style.headerGap),
    ) {
        logo(Modifier.size(style.logoSize))
        Column {
            Text(text = splash.name, style = style.nameStyle, color = style.content)
            splash.version?.let { version ->
                Text(text = style.labels.version(version), style = style.versionStyle, color = style.muted)
            }
        }
    }
}

@Composable
private fun StepRow(step: SplashStep, icon: @Composable (SplashStep, Modifier) -> Unit, style: ChecklistSplashStyle) {
    Row(
        modifier = Modifier.semantics(mergeDescendants = true) { liveRegion = LiveRegionMode.Polite },
        horizontalArrangement = Arrangement.spacedBy(style.iconGap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon(step, Modifier.size(style.iconSize))
        Text(
            text = step.label,
            style = style.stepStyle,
            color = style.colorOf(step.status),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun StepIcon(status: SplashStepStatus, modifier: Modifier, style: ChecklistSplashStyle) {
    val description = style.labels.status(status)
    val icon = style.iconOf(status)
    if (icon == null) {
        Spinner(modifier = modifier, contentDescription = description, style = style.spinner)
        return
    }
    Icon(
        icon = icon,
        contentDescription = description,
        modifier = modifier,
        style = IconStyle(size = style.iconSize, color = style.colorOf(status)),
    )
}

private fun ChecklistSplashStyle.iconOf(status: SplashStepStatus): IconGraphic? = when (status) {
    SplashStepStatus.Pending -> pendingIcon
    SplashStepStatus.Running -> null
    SplashStepStatus.Done -> doneIcon
    SplashStepStatus.Failed -> failedIcon
    SplashStepStatus.Skipped -> skippedIcon
}

private fun ChecklistSplashStyle.colorOf(status: SplashStepStatus): Color = when (status) {
    SplashStepStatus.Pending -> pendingColor
    SplashStepStatus.Running -> runningColor
    SplashStepStatus.Done -> doneColor
    SplashStepStatus.Failed -> failedColor
    SplashStepStatus.Skipped -> skippedColor
}
