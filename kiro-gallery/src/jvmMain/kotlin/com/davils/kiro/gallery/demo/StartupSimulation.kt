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

package com.davils.kiro.gallery.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.davils.kiro.ui.window.splash.Splash
import com.davils.kiro.ui.window.splash.SplashStep
import com.davils.kiro.ui.window.splash.SplashStepStatus
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

private val stepDelay = 900.milliseconds
private val labels: List<String> = listOf(
    "Reading the settings",
    "Opening the workspace",
    "Loading the plugins",
    "Indexing the files",
    "Restoring the windows",
)

internal class StartupSimulation(private val failAt: Int?) {
    var position: Int by mutableIntStateOf(0)
        private set

    val splash: Splash
        get() = Splash(
            name = "Kiro Studio",
            version = "1.0.0",
            subtitle = "The workbench of Davils",
            edition = "Enterprise",
            vendor = "Davils",
            copyright = "© 2026 Davils",
            steps = labels.mapIndexed { index, label ->
                SplashStep(
                    id = "step-$index",
                    label = label,
                    status = statusOf(index),
                )
            },
            failure = "The plugin store could not be reached.".takeIf { position == failAt },
        )

    suspend fun run() {
        while (true) {
            delay(stepDelay)
            if (position == failAt) return
            position = (position + 1) % (labels.size + 1)
        }
    }

    fun retry() {
        position += 1
    }

    private fun statusOf(index: Int): SplashStepStatus {
        if (index != position) return statusAround(index)
        return SplashStepStatus.Failed.takeIf { index == failAt } ?: SplashStepStatus.Running
    }

    private fun statusAround(index: Int): SplashStepStatus =
        SplashStepStatus.Done.takeIf { index < position } ?: SplashStepStatus.Pending
}

@Composable
internal fun rememberStartup(failAt: Int? = null): StartupSimulation {
    val simulation = remember { StartupSimulation(failAt = failAt) }
    LaunchedEffect(simulation, simulation.position == failAt) { simulation.run() }
    return simulation
}
