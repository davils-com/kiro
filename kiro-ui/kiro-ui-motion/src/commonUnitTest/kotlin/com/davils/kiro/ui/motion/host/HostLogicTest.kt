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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntSize
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.input.PointerTracker
import com.davils.kiro.ui.motion.power.PowerPolicy
import com.davils.kiro.ui.motion.power.PowerState
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.quality.QualityPolicy
import com.davils.kiro.ui.motion.quality.frameBudgetOf
import com.davils.kiro.ui.motion.time.ArtPlayback
import com.davils.kiro.ui.motion.time.ArtTime
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val WINDOW: Int = 100
private const val EDGE: Float = 90f
private const val SIDE: Float = 30f
private const val PROGRESS: Float = 0.5f
private const val POINTER: Float = 7f

private val awake: PowerState = PowerState(
    isAnimated = true,
    isFocused = true,
    isVisible = true,
    isReducedMotion = false,
    isStatic = false,
    isPlaying = true,
)

private val config: HostConfig = HostConfig(
    style = defaultMotionStyle(Theme.light()).copy(stillTime = 2.seconds),
    isReducedMotion = false,
    isStatic = false,
    external = ArtInputs.None.copy(pointer = Offset(x = 1f, y = 1f), progress = PROGRESS),
    layers = 1,
    quality = QualityLevel.High,
    budget = frameBudgetOf(policy = QualityPolicy.Fixed(level = QualityLevel.High), floor = Duration.ZERO),
)

class HostLogicTest : FunSpec({
    test("runs the clock, tracks the pointer only, or stops") {
        val policy = PowerPolicy()

        clockModeOf(policy = policy, state = awake, isSettling = false) shouldBe ClockMode.Running
        val static = awake.copy(isStatic = true)
        val paused = awake.copy(isPlaying = false)
        val unfocused = awake.copy(isFocused = false)

        clockModeOf(policy = policy, state = static, isSettling = true) shouldBe ClockMode.Pointer
        clockModeOf(policy = policy, state = paused, isSettling = false) shouldBe ClockMode.Stopped
        clockModeOf(policy = policy, state = unfocused, isSettling = true) shouldBe ClockMode.Stopped
    }

    test("is shown while some of it lies inside the window") {
        val window = IntSize(width = WINDOW, height = WINDOW)
        val corner = Rect(offset = Offset(x = EDGE, y = EDGE), size = Size(width = SIDE, height = SIDE))
        val beside = Rect(offset = Offset(x = WINDOW.toFloat(), y = 0f), size = Size(width = SIDE, height = SIDE))
        val flat = Rect(offset = Offset.Zero, size = Size(width = 0f, height = SIDE))
        val unknown = IntSize(width = WINDOW, height = 0)
        val line = Rect(offset = Offset.Zero, size = Size(width = SIDE, height = 0f))

        isShown(bounds = corner, window = window) shouldBe true
        isShown(bounds = beside, window = window) shouldBe false
        isShown(bounds = flat, window = window) shouldBe false
        isShown(bounds = line, window = window) shouldBe false
        isShown(bounds = beside, window = IntSize.Zero) shouldBe true
        isShown(bounds = beside, window = unknown) shouldBe true
    }

    test("shows the time of the playback, or the still time while still") {
        val playback = ArtPlayback(start = 5.seconds)

        val still = config.copy(style = config.style.copy(isAnimated = false))
        val reduced = config.copy(isReducedMotion = true)

        config.timeOf(playback) shouldBe ArtTime.of(5.seconds)
        still.timeOf(playback) shouldBe ArtTime.of(2.seconds)
        reduced.timeOf(playback) shouldBe ArtTime.of(2.seconds)
    }

    test("adds the pointer only while it is enabled") {
        val tracker = PointerTracker()
        val position = Offset(x = POINTER, y = POINTER)
        val reduced = config.copy(isReducedMotion = true)
        val passive = config.copy(style = config.style.copy(isInteractive = false))
        tracker.move(position = position, isPressed = true)
        tracker.step(deltaNanos = 1L, smoothing = 1f)

        config.inputsOf(tracker).pointer shouldBe position
        reduced.inputsOf(tracker) shouldBe ArtInputs.None.copy(progress = PROGRESS)
        passive.inputsOf(tracker).pointer shouldBe null
    }

    test("the style is a value derived from the theme") {
        val light = defaultMotionStyle(Theme.light())
        val reduced = Theme.light().let { theme -> theme.copy(motion = theme.motion.copy(isReduced = true)) }

        light.isAnimated shouldBe true
        defaultMotionStyle(reduced).isAnimated shouldBe false
        motionStyleOf(Theme.light()) shouldBe light
        light.copy() shouldBe light
        light.copy().hashCode() shouldBe light.hashCode()
        light.copy(speed = 2f) shouldNotBe light
        light.equals(light.seed) shouldBe false
        light.toString() shouldStartWith "MotionStyle("
    }
})
