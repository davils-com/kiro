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

internal const val NAME: String = "kiro"
internal const val VERSION: String = "Version 0.1.0"
internal const val MOUNTING: String = "Mounting the file system"
internal const val PLUGINS: String = "Loading the plugins"
internal const val CONFIGURING: String = "Reading the configuration"
internal const val WINDOWS: String = "Opening the windows"
internal const val WORKSPACE: String = "Preparing the workspace"
internal const val FAILURE: String = "The configuration could not be read"
internal const val COPYRIGHT: String = "2026 Davils"
internal const val VENDOR: String = "Davils"
internal const val EDITION: String = "Edition"
internal const val SUBTITLE: String = "The interface framework"
internal const val LOGO: String = "logo"
internal const val CLOSE: String = "Close"
internal const val RETRY: String = "Retry"
internal const val BACKGROUND: String = "background"

internal val steps: List<SplashStep> = listOf(
    SplashStep(id = "file-system", label = MOUNTING, status = SplashStepStatus.Done),
    SplashStep(id = "plugins", label = PLUGINS, status = SplashStepStatus.Skipped),
    SplashStep(id = "configuration", label = CONFIGURING, status = SplashStepStatus.Running),
    SplashStep(id = "windows", label = WINDOWS, status = SplashStepStatus.Pending),
)

internal val sample: Splash = Splash(
    name = NAME,
    version = "0.1.0",
    steps = steps,
    subtitle = SUBTITLE,
    edition = EDITION,
    vendor = VENDOR,
    copyright = COPYRIGHT,
)

internal val failed: Splash = sample.copy(
    steps = steps + SplashStep(id = "workspace", label = WORKSPACE, status = SplashStepStatus.Failed),
    failure = FAILURE,
)

internal fun runningAt(index: Int): List<SplashStep> = steps.mapIndexed { position, step ->
    val statuses = listOf(SplashStepStatus.Done, SplashStepStatus.Running, SplashStepStatus.Pending)
    step.copy(status = statuses[position.compareTo(index) + 1])
}
