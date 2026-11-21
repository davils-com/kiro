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

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.color.ArtPalettes
import com.davils.kiro.ui.motion.diagnostics.ArtDiagnostics
import com.davils.kiro.ui.motion.export.ExportOptions
import com.davils.kiro.ui.motion.export.renderFrame
import com.davils.kiro.ui.motion.fixture.FakeWindow
import com.davils.kiro.ui.motion.fixture.Probe
import com.davils.kiro.ui.motion.fixture.ProbeArt
import com.davils.kiro.ui.motion.fixture.TAG
import com.davils.kiro.ui.motion.fixture.Themed
import com.davils.kiro.ui.motion.fixture.fingerprint
import com.davils.kiro.ui.motion.fixture.pixels
import com.davils.kiro.ui.motion.fixture.reducedMotion
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.input.rememberArtInputs
import com.davils.kiro.ui.motion.mask.Mask
import com.davils.kiro.ui.motion.mask.MaskStop
import com.davils.kiro.ui.motion.power.PowerPolicy
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.quality.QualityPolicy
import com.davils.kiro.ui.motion.scene.LayerTransform
import com.davils.kiro.ui.motion.scene.Scene
import com.davils.kiro.ui.motion.scene.scene
import com.davils.kiro.ui.motion.time.ArtPlayback
import com.davils.kiro.ui.motion.time.ArtTime
import com.davils.kiro.ui.motion.time.TimeRemap
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.shouldBe
import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds

private const val SECOND: Long = 1_000L
private const val FRAME: Long = 16L
private const val SHORT: Long = 100L
private const val ROUNDING: Int = 12
private const val CHANNEL: Int = 0xff
private const val CHANNELS: Int = 4
private const val CHANNEL_BITS: Int = 8
private const val CAP: Int = 10
private const val INTERVAL: Int = 10
private const val PROGRESS: Float = 0.4f
private const val NEAR: Float = 60f
private const val FAR: Float = 100f
private const val CORNER: Float = 20f
private const val STILL_SECONDS: Int = 3
private const val TURN: Float = 12f
private const val SHRINK: Float = 0.8f
private const val TRANSLUCENT: Float = 0.7f

private val cappedFrames: IntRange = 8..13
private val freeFrames: IntRange = 50..64
private val measuredRates: IntRange = 55..65
private val fade: List<MaskStop> = listOf(MaskStop(position = 0f, alpha = 1f), MaskStop(position = 1f, alpha = 0.2f))
private val paused: ArtPlayback
    get() = ArtPlayback(start = 1500.milliseconds, isPlaying = false)

private fun Scene.exported(time: Duration): ImageBitmap = renderFrame(
    scene = this,
    size = IntSize(width = CANVAS_WIDTH, height = CANVAS_HEIGHT),
    time = time,
    theme = Theme.light(),
    options = ExportOptions(seed = motionStyleOf(Theme.light()).seed),
)

private fun channelDistance(first: Int, second: Int): Int = (0 until CHANNELS).maxOf { channel ->
    val shift = channel * CHANNEL_BITS
    abs((first shr shift and CHANNEL) - (second shr shift and CHANNEL))
}

class MotionCanvasTest : FunSpec({
    test("plays the clock while running and holds it while paused") {
        val playback = ArtPlayback()
        val probe = Probe()

        hosted(scene = scene { layer(ProbeArt(probe = probe)) }, playback = playback) {
            mainClock.advanceTimeBy(SECOND)
            playback.elapsed shouldBeGreaterThan (SECOND - SHORT).milliseconds
            playback.elapsed shouldBeLessThan (SECOND + 1).milliseconds
            probe.last.time shouldBe playback.time

            playback.pause()
            mainClock.advanceTimeBy(SECOND)
            val held = playback.elapsed
            mainClock.advanceTimeBy(SECOND)
            playback.elapsed shouldBe held

            playback.play()
            mainClock.advanceTimeBy(SECOND)
            playback.elapsed shouldBeGreaterThan held
            onNodeWithText(ON_TOP).assertExists()
        }
    }

    test("plays at the speed of its style") {
        val playback = ArtPlayback()

        val setup = styled { style -> style.copy(speed = 0f) }

        hosted(scene = scene { layer(ProbeArt()) }, playback = playback, setup = setup) {
            mainClock.advanceTimeBy(SECOND)
            playback.elapsed shouldBe Duration.ZERO
        }
    }

    test("shows the still time under reduced motion and ignores the pointer") {
        val playback = ArtPlayback()
        val probe = Probe()
        val setup = HostSetup(theme = reducedMotion, style = { style -> style.copy(stillTime = STILL_SECONDS.seconds) })

        hosted(scene = scene { layer(ProbeArt(probe = probe)) }, playback = playback, setup = setup) {
            onNodeWithTag(TAG).performMouseInput { moveTo(Offset(x = NEAR, y = CORNER)) }
            mainClock.advanceTimeBy(SECOND)
            playback.elapsed shouldBe Duration.ZERO
            probe.last.time shouldBe ArtTime.of(STILL_SECONDS.seconds)
            probe.last.inputs.pointer shouldBe null
        }
    }

    test("pauses while the window is unfocused, unless its policy keeps it running") {
        val window = FakeWindow(isFocused = false)
        val held = ArtPlayback()
        val running = ArtPlayback()
        val awake = HostSetup(
            window = FakeWindow(isFocused = false),
            style = { style -> style.copy(power = PowerPolicy(isPausedWhenUnfocused = false)) },
        )

        hosted(scene = scene { layer(ProbeArt()) }, playback = held, setup = HostSetup(window = window)) {
            mainClock.advanceTimeBy(SECOND)
            held.elapsed shouldBe Duration.ZERO
            window.isWindowFocused = true
            mainClock.advanceTimeBy(SECOND)
            held.elapsed shouldBeGreaterThan Duration.ZERO
        }
        hosted(scene = scene { layer(ProbeArt()) }, playback = running, setup = awake) {
            mainClock.advanceTimeBy(SECOND)
            running.elapsed shouldBeGreaterThan Duration.ZERO
        }
    }

    test("pauses while outside the window, unless its policy keeps it running") {
        val hidden = ArtPlayback()
        val running = ArtPlayback()
        val outside = Modifier.offset(x = (CANVAS_WIDTH * CANVAS_WIDTH).dp)
        val awake = HostSetup(
            modifier = outside,
            style = { style -> style.copy(power = PowerPolicy(isPausedWhenHidden = false)) },
        )

        hosted(scene = scene { layer(ProbeArt()) }, playback = hidden, setup = HostSetup(modifier = outside)) {
            mainClock.advanceTimeBy(SECOND)
            hidden.elapsed shouldBe Duration.ZERO
        }
        hosted(scene = scene { layer(ProbeArt()) }, playback = running, setup = awake) {
            mainClock.advanceTimeBy(SECOND)
            running.elapsed shouldBeGreaterThan Duration.ZERO
        }
    }

    test("draws no more frames than its cap") {
        val capped = Probe()
        val free = Probe()
        val setup = styled { style -> style.copy(power = PowerPolicy(maxFrameRate = CAP)) }

        hosted(scene = scene { layer(ProbeArt(probe = capped)) }, setup = setup) { mainClock.advanceTimeBy(SECOND) }
        hosted(scene = scene { layer(ProbeArt(probe = free)) }) { mainClock.advanceTimeBy(SECOND) }

        capped.frames.size shouldBeInRange cappedFrames
        free.frames.size shouldBeInRange freeFrames
    }

    test("stops the clock of a static scene") {
        val still = ArtPlayback()
        val frozen = ArtPlayback()
        val frozenScene = scene { layer(ProbeArt()) { time = TimeRemap.Identity.copy(isFrozen = true) } }

        hosted(scene = scene { layer(ProbeArt(isStatic = true)) }, playback = still) {
            mainClock.advanceTimeBy(SECOND)
            still.elapsed shouldBe Duration.ZERO
        }
        hosted(scene = frozenScene, playback = frozen) {
            mainClock.advanceTimeBy(SECOND)
            frozen.elapsed shouldBe Duration.ZERO
        }
    }

    test("hands the inputs to interactive layers only and follows the pointer") {
        val reacts = Probe()
        val ignores = Probe()
        val subject = scene {
            layer(ProbeArt(probe = reacts))
            layer(ProbeArt(probe = ignores)) { isInteractive = false }
        }

        hosted(scene = subject, setup = HostSetup(inputs = ArtInputs.None.copy(progress = PROGRESS))) {
            onNodeWithTag(TAG).performMouseInput { moveTo(Offset(x = NEAR, y = CORNER)) }
            mainClock.advanceTimeBy(SECOND)
            reacts.last.inputs.progress shouldBe PROGRESS
            reacts.last.inputs.pointer shouldBe Offset(x = NEAR, y = CORNER)
            ignores.last.inputs shouldBe ArtInputs.None

            onNodeWithTag(TAG).performMouseInput { moveTo(Offset(x = FAR, y = CORNER)) }
            mainClock.advanceTimeBy(FRAME)
            val between = reacts.last.inputs.pointer ?: Offset.Zero
            between.x shouldBeGreaterThan NEAR
            between.x shouldBeLessThan FAR
            mainClock.advanceTimeBy(SECOND)
            reacts.last.inputs.pointer shouldBe Offset(x = FAR, y = CORNER)

            onNodeWithTag(TAG).performMouseInput { exit() }
            mainClock.advanceTimeBy(SECOND)
            reacts.last.inputs.pointer shouldBe null
        }
    }

    test("follows the pointer of a static scene until it settles") {
        val probe = Probe()

        hosted(scene = scene { layer(ProbeArt(probe = probe, isStatic = true)) }) {
            onNodeWithTag(TAG).performMouseInput { moveTo(Offset(x = CORNER, y = CORNER)) }
            mainClock.advanceTimeBy(SECOND)
            onNodeWithTag(TAG).performMouseInput { moveTo(Offset(x = FAR, y = CORNER)) }
            mainClock.advanceTimeBy(SECOND)
            probe.last.inputs.pointer shouldBe Offset(x = FAR, y = CORNER)
            val drawn = probe.frames.size
            mainClock.advanceTimeBy(SECOND)
            probe.frames.size shouldBe drawn
        }
    }

    test("does not track the pointer when it is not interactive") {
        val probe = Probe()
        val setup = styled { style -> style.copy(isInteractive = false) }

        hosted(scene = scene { layer(ProbeArt(probe = probe)) }, setup = setup) {
            onNodeWithTag(TAG).performMouseInput { moveTo(Offset(x = CORNER, y = CORNER)) }
            mainClock.advanceTimeBy(SECOND)
            probe.last.inputs.pointer shouldBe null
        }
    }

    test("reports its diagnostics while the clock runs") {
        val reports = mutableListOf<ArtDiagnostics>()
        val fixed = QualityPolicy.Fixed(level = QualityLevel.Balanced)
        val subject = scene {
            layer(ProbeArt())
            layer(ProbeArt())
        }
        val setup = HostSetup(
            style = { style -> style.copy(diagnosticsInterval = INTERVAL, quality = fixed) },
            onDiagnostics = { diagnostics -> reports += diagnostics },
        )

        hosted(scene = subject, setup = setup) { mainClock.advanceTimeBy(SECOND) }
        val last = reports.last()

        last.layers shouldBe 2
        last.quality shouldBe QualityLevel.Balanced
        last.framesPerSecond.toInt() shouldBeInRange measuredRates
    }

    test("steps its quality down when frames take too long") {
        val probe = Probe()
        val slow = QualityPolicy.Adaptive(
            budget = 1.nanoseconds,
            initial = QualityLevel.High,
            minimum = QualityLevel.Low,
            maximum = QualityLevel.High,
            headroom = 0.5f,
            smoothing = 1f,
            patience = 1,
        )

        val setup = styled { style -> style.copy(quality = slow) }

        hosted(scene = scene { layer(ProbeArt(probe = probe)) }, setup = setup) {
            mainClock.advanceTimeBy(SECOND)
        }

        probe.contexts.first().quality shouldBe QualityLevel.High
        probe.contexts.last().quality shouldBe QualityLevel.Low
        probe.last.quality shouldBe QualityLevel.Low
    }

    test("draws exactly like renderFrame") {
        val subject = scene {
            palette = ArtPalettes.Pastel.resolve(Theme.light())
            mask = Mask.Vignette(strength = 0.6f, extent = 0.3f)
            layer(ProbeArt(hasStatic = true))
            layer(ProbeArt(color = Color.Green)) {
                blend = BlendMode.Multiply
                mask = Mask.Linear(angle = 0f, stops = fade)
                transform = LayerTransform.Identity.copy(offset = Offset(x = 0.25f, y = 0.1f), scale = SHRINK)
                time = TimeRemap.Identity.copy(speed = 2f)
            }
        }

        hosted(scene = subject, playback = paused, setup = HostSetup(hasContent = false)) {
            mainClock.advanceTimeBy(SECOND)
            val hosted = onNodeWithTag(TAG).captureToImage()

            hosted.fingerprint() shouldBe subject.exported(time = paused.elapsed).fingerprint()
        }
    }

    test("draws translucent and turned layers like renderFrame, up to rounding") {
        val subject = scene {
            layer(ProbeArt(hasStatic = true)) { opacity = TRANSLUCENT }
            layer(ProbeArt(color = Color.Green)) { transform = LayerTransform.Identity.copy(rotation = TURN) }
        }

        hosted(scene = subject, playback = paused, setup = HostSetup(hasContent = false)) {
            mainClock.advanceTimeBy(SECOND)
            val hosted = onNodeWithTag(TAG).captureToImage()
            val drawn = hosted.pixels()
            val exported = subject.exported(time = paused.elapsed).pixels()
            val distance = drawn.indices.maxOf { index ->
                channelDistance(first = drawn[index], second = exported[index])
            }

            distance shouldBeLessThan ROUNDING
        }
    }

    test("keeps its layers while it recomposes with the same values") {
        val probe = Probe()
        val subject = scene { layer(ProbeArt(probe = probe)) }
        val style = motionStyleOf(Theme.light())
        var unrelated by mutableIntStateOf(0)

        runComposeUiTest {
            mainClock.autoAdvance = false
            setContent {
                Themed {
                    BasicText(text = "$unrelated")
                    MotionCanvas(scene = subject, modifier = Modifier.size(size = CANVAS_HEIGHT.dp), style = style)
                }
            }
            mainClock.advanceTimeBy(SHORT)
            unrelated = 1
            mainClock.advanceTimeBy(SHORT)
        }

        probe.contexts.size shouldBe 1
    }

    test("prepares its layers again for another scene") {
        val first = Probe()
        val second = Probe()
        var current by mutableStateOf(scene { layer(ProbeArt(probe = first)) })

        runComposeUiTest {
            mainClock.autoAdvance = false
            setContent { Themed { MotionCanvas(scene = current, modifier = Modifier.size(size = CANVAS_HEIGHT.dp)) } }
            mainClock.advanceTimeBy(SHORT)
            current = scene { layer(ProbeArt(probe = second)) }
            mainClock.advanceTimeBy(SHORT)
        }

        first.contexts.size shouldBe 1
        second.contexts.size shouldBe 1
        second.frames.shouldNotBeEmpty()
    }

    test("remembers the inputs of the application while they stay the same") {
        val seen = mutableListOf<ArtInputs>()
        var recompositions by mutableIntStateOf(0)

        runComposeUiTest {
            setContent {
                BasicText(text = "$recompositions")
                seen += rememberArtInputs(progress = PROGRESS)
            }
            recompositions = 1
            waitForIdle()
        }

        seen.all { inputs -> inputs === seen.first() } shouldBe true
        seen.first() shouldBe ArtInputs.None.copy(progress = PROGRESS)
        seen.size shouldBeGreaterThan 1
    }
})
