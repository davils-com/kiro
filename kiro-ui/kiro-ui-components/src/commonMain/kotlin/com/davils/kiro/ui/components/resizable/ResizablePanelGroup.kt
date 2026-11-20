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

package com.davils.kiro.ui.components.resizable

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.primitive.motion.spatialSpring
import com.davils.kiro.ui.components.resizable.junction.HandleActions
import com.davils.kiro.ui.components.resizable.junction.JunctionGrips
import com.davils.kiro.ui.components.resizable.junction.LocalResizableCoordinator
import com.davils.kiro.ui.components.resizable.junction.ResizableCoordinator
import com.davils.kiro.ui.components.theme

private const val TOTAL: Float = 100f

@Composable
public fun ResizablePanelGroup(
    state: ResizableState,
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal,
    isGripShown: Boolean = false,
    style: ResizableStyle = resizableStyle(),
    content: ResizableScopeImpl.() -> Unit,
) {
    val panels = ResizableScope().apply(content).panels
    require(panels.size == state.sizes.size) { "The group has ${panels.size} panels but ${state.sizes.size} sizes." }
    SideEffect { state.panels = panels }
    val parent = LocalResizableCoordinator.current
    val coordinator = parent ?: remember { ResizableCoordinator() }
    val isHorizontal = orientation == Orientation.Horizontal
    val sign = signOf(isReversed = isHorizontal && LocalLayoutDirection.current == LayoutDirection.Rtl)
    val handles = with(LocalDensity.current) { style.thickness.toPx() } * (panels.size - 1)
    val length = remember { mutableFloatStateOf(0f) }
    val conversion = rememberUpdatedState(
        Conversion(sign = sign, available = maxOf(length.floatValue - handles, 1f), step = style.keyboardStep),
    )
    val actions = remember(
        state,
        panels.size,
    ) { List(panels.size - 1) { index -> actionsOf(state, index, conversion) } }
    DisposableEffect(coordinator, actions) { onDispose { actions.forEach(coordinator::remove) } }
    val shown = animatedSizes(target = state.sizes, isInstant = state.isDragging, spec = theme.spatialSpring())
    val root = Modifier.onGloballyPositioned(coordinator::anchor).takeIf { parent == null } ?: Modifier
    CompositionLocalProvider(LocalResizableCoordinator provides coordinator) {
        Box(modifier = modifier.then(root)) {
            Layout(
                content = {
                    panels.forEachIndexed { index, panel ->
                        Box(modifier = Modifier.fillMaxSize().clipToBounds()) { panel.content() }
                        actions.getOrNull(index)?.let { handle ->
                            ResizableHandle(
                                orientation = orientation,
                                isGripShown = isGripShown,
                                actions = handle,
                                onPlaced = { layout ->
                                    coordinator.place(
                                        isVerticalLine = isHorizontal,
                                        coordinates = layout,
                                        actions = handle,
                                    )
                                },
                                style = style,
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxSize().onSizeChanged { measured ->
                    length.floatValue = (measured.width.takeIf { isHorizontal } ?: measured.height).toFloat()
                },
                measurePolicy = ResizableMeasurePolicy(
                    sizes = shown,
                    orientation = orientation,
                    thickness = style.thickness,
                ),
            )
            if (parent == null) JunctionGrips(coordinator = coordinator, isGripShown = isGripShown, style = style)
        }
    }
}

private fun actionsOf(state: ResizableState, index: Int, conversion: State<Conversion>): HandleActions = HandleActions(
    begin = state::begin,
    dragTo = { pixels ->
        val current = conversion.value
        state.dragTo(handle = index, delta = current.sign * pixels / current.available * TOTAL)
    },
    end = state::end,
    nudge = { direction ->
        val current = conversion.value
        state.resize(handle = index, delta = current.sign * direction * current.step)
    },
)

@Composable
private fun animatedSizes(
    target: List<Float>,
    isInstant: Boolean,
    spec: FiniteAnimationSpec<Float>,
): State<List<Float>> {
    val shown = remember { mutableStateOf(target) }
    LaunchedEffect(target) {
        transition(from = shown.value, to = target, isInstant = isInstant, spec = spec) { frame -> shown.value = frame }
    }
    return shown
}

private suspend fun transition(
    from: List<Float>,
    to: List<Float>,
    isInstant: Boolean,
    spec: FiniteAnimationSpec<Float>,
    onFrame: (List<Float>) -> Unit,
) {
    if (isInstant || from.size != to.size) {
        onFrame(to)
        return
    }
    animate(initialValue = 0f, targetValue = 1f, animationSpec = spec) { progress, _ ->
        onFrame(from.zip(to) { start, end -> start + (end - start) * progress })
    }
}

private fun signOf(isReversed: Boolean): Float {
    if (isReversed) return -1f
    return 1f
}
