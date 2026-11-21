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

import androidx.compose.animation.core.withInfiniteAnimationFrameNanos
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.WindowInfo
import com.davils.kiro.ui.motion.diagnostics.ArtDiagnostics
import com.davils.kiro.ui.motion.diagnostics.FrameMeter
import com.davils.kiro.ui.motion.input.PointerTracker
import com.davils.kiro.ui.motion.power.FrameGate
import com.davils.kiro.ui.motion.power.PowerState
import com.davils.kiro.ui.motion.power.SKIPPED_FRAME
import com.davils.kiro.ui.motion.time.ArtPlayback
import kotlinx.coroutines.flow.collectLatest

internal class FrameClock(
    private val playback: ArtPlayback,
    private val tracker: PointerTracker,
    private val config: State<HostConfig>,
    private val window: WindowInfo,
    private val visibility: State<Boolean>,
    private val report: State<((ArtDiagnostics) -> Unit)?>,
) {
    suspend fun run() {
        snapshotFlow(::planOf).collectLatest { plan -> tick(plan) }
    }

    private fun planOf(): ClockPlan {
        val current = config.value
        val style = current.style
        val state = PowerState(
            isAnimated = style.isAnimated,
            isFocused = window.isWindowFocused,
            isVisible = visibility.value,
            isReducedMotion = current.isReducedMotion,
            isStatic = current.isStatic,
            isPlaying = playback.isPlaying,
        )
        val mode = clockModeOf(policy = style.power, state = state, isSettling = tracker.isSettling)
        return ClockPlan(
            mode = mode,
            maxFrameRate = style.power.maxFrameRate,
            diagnosticsInterval = style.diagnosticsInterval,
        )
    }

    private suspend fun tick(plan: ClockPlan) {
        if (plan.mode == ClockMode.Stopped) return
        val gate = FrameGate(maxFrameRate = plan.maxFrameRate)
        val meter = FrameMeter(interval = plan.diagnosticsInterval)
        while (true) {
            withInfiniteAnimationFrameNanos { nanos -> frame(plan = plan, delta = gate.admit(nanos), meter = meter) }
        }
    }

    private fun frame(plan: ClockPlan, delta: Long, meter: FrameMeter) {
        if (delta == SKIPPED_FRAME) return
        val current = config.value
        val style = current.style
        if (plan.mode == ClockMode.Running) playback.advance(nanos = (delta * style.speed.toDouble()).toLong())
        tracker.step(deltaNanos = delta, smoothing = style.pointerSmoothing)
        if (delta > 0L) current.budget.record(frameNanos = delta)
        val diagnostics = meter.record(frameNanos = delta, layers = current.layers, quality = current.quality)
        if (diagnostics != null) report.value?.invoke(diagnostics)
    }
}
