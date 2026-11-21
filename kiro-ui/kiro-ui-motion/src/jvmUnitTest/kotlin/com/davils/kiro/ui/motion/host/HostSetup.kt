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

@file:OptIn(ExperimentalTestApi::class)

package com.davils.kiro.ui.motion.host

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.diagnostics.ArtDiagnostics
import com.davils.kiro.ui.motion.fixture.TAG
import com.davils.kiro.ui.motion.fixture.Themed
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.scene.Scene
import com.davils.kiro.ui.motion.time.ArtPlayback

internal const val CANVAS_WIDTH: Int = 160
internal const val CANVAS_HEIGHT: Int = 80
internal const val ON_TOP: String = "On top"

internal class HostSetup(
    val theme: Theme = Theme.light(),
    val window: WindowInfo? = null,
    val modifier: Modifier = Modifier,
    val inputs: ArtInputs? = null,
    val style: (MotionStyle) -> MotionStyle = { current -> current },
    val onDiagnostics: ((ArtDiagnostics) -> Unit)? = null,
    val hasContent: Boolean = true,
)

internal fun styled(style: (MotionStyle) -> MotionStyle): HostSetup = HostSetup(style = style)

internal fun hosted(
    scene: Scene,
    playback: ArtPlayback = ArtPlayback(),
    setup: HostSetup = HostSetup(),
    check: suspend ComposeUiTest.() -> Unit,
) = runComposeUiTest {
    mainClock.autoAdvance = false
    setContent {
        Themed(theme = setup.theme) {
            Windowed(window = setup.window) {
                MotionCanvas(
                    scene = scene,
                    modifier = setup.modifier.size(width = CANVAS_WIDTH.dp, height = CANVAS_HEIGHT.dp).testTag(TAG),
                    playback = playback,
                    inputs = setup.inputs,
                    style = setup.style(motionStyle()),
                    onDiagnostics = setup.onDiagnostics,
                ) { if (setup.hasContent) BasicText(text = ON_TOP) }
            }
        }
    }
    check()
}

@Composable
private fun Windowed(window: WindowInfo?, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalWindowInfo provides (window ?: LocalWindowInfo.current), content = content)
}
