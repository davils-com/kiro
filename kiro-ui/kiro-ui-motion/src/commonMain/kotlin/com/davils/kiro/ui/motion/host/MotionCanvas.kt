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

package com.davils.kiro.ui.motion.host

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.rememberTextMeasurer
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.color.artPalette
import com.davils.kiro.ui.motion.diagnostics.ArtDiagnostics
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.input.PointerTracker
import com.davils.kiro.ui.motion.input.trackPointer
import com.davils.kiro.ui.motion.power.frameIntervalOf
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.quality.frameBudgetOf
import com.davils.kiro.ui.motion.render.PreparedScene
import com.davils.kiro.ui.motion.render.SceneSetup
import com.davils.kiro.ui.motion.render.drawScene
import com.davils.kiro.ui.motion.render.prepareScene
import com.davils.kiro.ui.motion.scene.Scene
import com.davils.kiro.ui.motion.time.ArtPlayback
import com.davils.kiro.ui.motion.time.rememberArtPlayback

/**
 * Draws a [Scene] and plays it: every layer at its opacity, blend mode, transform, mask and time, over the background
 * of the scene, with [content] on top.
 *
 * The canvas prepares every visible layer once for its theme, palette, quality and density, records the parts of the
 * layers that never move once for every size, and then only draws the moving parts in every frame. Time and inputs are
 * read while drawing, so the canvas does not recompose from frame to frame. Its clock stops while the scene is static,
 * the [playback] is paused or the [MotionStyle.power] policy asks for it, such as while the window is unfocused or the
 * canvas hidden; while the theme asks for reduced motion it shows the frame at [MotionStyle.stillTime].
 *
 * The art is decorative: it is hidden from accessibility services, while [content] is not.
 *
 * ```kotlin
 * MotionCanvas(scene = scene { layer(OrbitArt()) }, modifier = Modifier.fillMaxSize()) {
 *     Text("Welcome", modifier = Modifier.align(Alignment.Center))
 * }
 * ```
 *
 * @param scene the scene to draw.
 * @param modifier the modifier of the canvas, which usually gives it its size.
 * @param playback the clock of the scene, to pause, seek or loop it.
 * @param inputs the progress, the scroll and the signals an application feeds in, such as from
 * [com.davils.kiro.ui.motion.input.rememberArtInputs], or `null` for none; the canvas adds the pointer.
 * @param style how the canvas plays the scene.
 * @param onDiagnostics receives the frame rate, the frame time, the number of layers and the quality every
 * [MotionStyle.diagnosticsInterval] frames while the clock runs, or `null` to measure nothing.
 * @param content what the canvas shows on top of the scene.
 * @since 1.0.0
 */
@Composable
public fun MotionCanvas(
    scene: Scene,
    modifier: Modifier = Modifier,
    playback: ArtPlayback = rememberArtPlayback(),
    inputs: ArtInputs? = null,
    style: MotionStyle = motionStyle(),
    onDiagnostics: ((ArtDiagnostics) -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit = {},
) {
    val theme = Theme.current
    val budget = remember(style.quality, style.power.maxFrameRate) {
        frameBudgetOf(policy = style.quality, floor = frameIntervalOf(style.power.maxFrameRate))
    }
    val quality = budget.level
    val prepared = rememberPreparedScene(scene = scene, style = style, quality = quality)
    val config = rememberUpdatedState(
        HostConfig(
            style = style,
            isReducedMotion = theme.motion.isReduced,
            isStatic = scene.isStatic,
            external = inputs ?: ArtInputs.None,
            layers = prepared.layers.size,
            quality = quality,
            budget = budget,
        ),
    )
    val report = rememberUpdatedState(onDiagnostics)
    val window = LocalWindowInfo.current
    val tracker = remember { PointerTracker() }
    val visibility = remember { mutableStateOf(true) }
    val isPointerEnabled = config.value.isPointerEnabled
    LaunchedEffect(isPointerEnabled, tracker) { if (!isPointerEnabled) tracker.exit() }
    LaunchedEffect(playback, tracker, window) {
        val clock = FrameClock(
            playback = playback,
            tracker = tracker,
            config = config,
            window = window,
            visibility = visibility,
            report = report,
        )
        clock.run()
    }
    val placed: (LayoutCoordinates) -> Unit = { coordinates ->
        visibility.value = isShown(bounds = coordinates.boundsInWindow(), window = window.containerSize)
    }
    Box(
        modifier = modifier
            .onGloballyPositioned(onGloballyPositioned = placed)
            .trackPointer(tracker = tracker, isEnabled = isPointerEnabled),
    ) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .sceneDrawing(prepared = prepared, config = config, playback = playback, tracker = tracker),
        )
        content()
    }
}

@Composable
private fun rememberPreparedScene(scene: Scene, style: MotionStyle, quality: QualityLevel): PreparedScene {
    val theme = Theme.current
    val palette = scene.palette ?: artPalette()
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    return remember(scene, theme, palette, quality, density, style.seed, textMeasurer) {
        val setup = SceneSetup(
            theme = theme,
            palette = palette,
            quality = quality,
            seed = style.seed,
            textMeasurer = textMeasurer,
        )
        prepareScene(scene = scene, setup = setup)
    }
}

private fun Modifier.sceneDrawing(
    prepared: PreparedScene,
    config: State<HostConfig>,
    playback: ArtPlayback,
    tracker: PointerTracker,
): Modifier = clearAndSetSemantics {}
    .drawWithCache {
        val statics = RecordedStatics(scope = this, scene = prepared)
        onDrawBehind {
            val current = config.value
            drawScene(
                scene = prepared,
                time = current.timeOf(playback),
                inputs = current.inputsOf(tracker),
                statics = statics,
            )
        }
    }
