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

package com.davils.kiro.ui.motion.export

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.art.ArtSeed
import com.davils.kiro.ui.motion.color.ArtPalettes
import com.davils.kiro.ui.motion.color.artPaletteOf
import com.davils.kiro.ui.motion.fixture.Probe
import com.davils.kiro.ui.motion.fixture.ProbeArt
import com.davils.kiro.ui.motion.fixture.fingerprint
import com.davils.kiro.ui.motion.fixture.pixels
import com.davils.kiro.ui.motion.fixture.textMeasurer
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.mask.Mask
import com.davils.kiro.ui.motion.mask.MaskStop
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.scene.LayerScope
import com.davils.kiro.ui.motion.scene.LayerTransform
import com.davils.kiro.ui.motion.scene.Scene
import com.davils.kiro.ui.motion.scene.scene
import com.davils.kiro.ui.motion.time.ArtTime
import com.davils.kiro.ui.motion.time.TimeRemap
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.floats.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private const val WIDTH: Int = 160
private const val HEIGHT: Int = 80
private const val LEFT: Int = 5
private const val MIDDLE: Int = 40
private const val RIGHT: Int = 150
private const val SECOND_QUARTER: Int = 45
private const val SECOND_THIRD: Int = 60
private const val THIRD_QUARTER: Int = 85
private const val TOLERANCE: Float = 0.03f
private const val HALF: Float = 0.5f
private const val FADED: Float = 0.94f
private const val PROGRESS: Float = 0.4f
private const val POINTER: Float = 120f
private const val SCROLL: Float = 60f
private const val TURN: Float = 90f
private const val FRAMES: Int = 4
private const val SEED: Int = 11
private const val OWN_SEED: Int = 3
private const val NEAR_CENTER: Int = 60
private const val FAINT: Float = 0.08f
private const val STRONG: Float = 0.78f

private val size: IntSize = IntSize(width = WIDTH, height = HEIGHT)
private val theme: Theme = Theme.light()
private val fade: List<MaskStop> = listOf(MaskStop(position = 0f, alpha = 1f), MaskStop(position = 1f, alpha = 0f))

private fun Scene.frame(time: Duration = Duration.ZERO, inputs: ArtInputs = ArtInputs.None): ImageBitmap =
    renderFrame(scene = this, size = size, time = time, theme = theme, options = ExportOptions(inputs = inputs))

private fun Scene.colorAt(x: Int, time: Duration = Duration.ZERO): Color = frame(time = time).colorAt(x = x)

private fun ImageBitmap.colorAt(x: Int, y: Int = MIDDLE): Color = Color(pixels()[y * width + x])

private fun plain(configure: LayerScope.() -> Unit = {}): Scene = scene {
    background = Color.White
    layer(ProbeArt(), configure)
}

private fun blank(mask: Mask): Scene = scene {
    background = Color.Black
    this.mask = mask
}

class FrameExportTest : FunSpec({
    test("paints the background of the scene, or of the palette") {
        val green = scene { background = Color.Green }
        val empty = scene {}

        green.colorAt(x = LEFT) shouldBe Color.Green
        empty.colorAt(x = LEFT) shouldBe artPaletteOf(theme).background
    }

    test("draws the art of a layer at its time") {
        val start = plain().frame()

        start.colorAt(x = LEFT) shouldBe Color.Red
        start.colorAt(x = SECOND_THIRD) shouldBe Color.White
        plain().colorAt(x = SECOND_QUARTER, time = 1.seconds) shouldBe Color.Red
    }

    test("draws a layer at its opacity and blend mode") {
        val translucent = plain { opacity = HALF }
        val multiplied = scene {
            background = Color.Yellow
            layer(ProbeArt(color = Color.Magenta)) { blend = BlendMode.Multiply }
        }
        val half = translucent.colorAt(x = LEFT)

        half.green shouldBe (HALF plusOrMinus TOLERANCE)
        half.red shouldBe 1f
        multiplied.colorAt(x = LEFT) shouldBe Color.Red
    }

    test("masks a layer and the scene") {
        val layerMask = plain { mask = Mask.Linear(angle = 0f, stops = fade) }
        val sceneMask = plain().copy(mask = Mask.Linear(angle = TURN, stops = fade))
        val faded = sceneMask.frame()

        layerMask.colorAt(x = 1).green shouldBe (0f plusOrMinus TOLERANCE)
        layerMask.colorAt(x = RIGHT, time = 3.seconds).green shouldBe (FADED plusOrMinus TOLERANCE)
        faded.colorAt(x = LEFT, y = 0).alpha shouldBe (1f plusOrMinus TOLERANCE)
        faded.colorAt(x = LEFT, y = HEIGHT - 1).alpha shouldBe (0f plusOrMinus TOLERANCE)
    }

    test("moves, scales and turns a layer") {
        val shifted = plain { transform = LayerTransform.Identity.copy(offset = Offset(x = HALF, y = 0f)) }.frame()
        val scaled = plain { transform = LayerTransform.Identity.copy(scale = HALF) }.frame()
        val turned = plain { transform = LayerTransform.Identity.copy(rotation = TURN) }

        shifted.colorAt(x = THIRD_QUARTER) shouldBe Color.Red
        shifted.colorAt(x = LEFT) shouldBe Color.White
        scaled.colorAt(x = LEFT) shouldBe Color.White
        scaled.colorAt(x = SECOND_QUARTER) shouldBe Color.Red
        val unturned = plain().frame()

        turned.frame().fingerprint() shouldNotBe unturned.fingerprint()
    }

    test("follows the pointer and the scroll with the parallax of a layer") {
        val follows = plain { transform = LayerTransform.Identity.copy(parallax = 1f) }
        val pointed = follows.frame(inputs = ArtInputs.None.copy(pointer = Offset(x = POINTER, y = HEIGHT / 2f)))
        val scrolled = follows.frame(inputs = ArtInputs.None.copy(scroll = SCROLL))

        pointed.colorAt(x = SECOND_QUARTER) shouldBe Color.Red
        scrolled.colorAt(x = LEFT) shouldBe Color.White
    }

    test("remaps the time of a layer") {
        val frozen = plain { time = TimeRemap.Identity.copy(isFrozen = true, offset = 1.seconds) }

        val second = plain().frame(time = 1.seconds)

        frozen.frame(time = 3.seconds).fingerprint() shouldBe second.fingerprint()
        frozen.isStatic shouldBe true
    }

    test("draws the static parts of a layer below its frame") {
        val probe = Probe()
        val image = scene { layer(ProbeArt(probe = probe, hasStatic = true)) }.frame(time = 2.seconds)

        image.colorAt(x = RIGHT, y = LEFT) shouldBe Color.Blue
        val recorded = probe.statics.single()

        recorded.time shouldBe ArtTime.Zero
    }

    test("derives the seed of a layer from its index, whatever the other layers are") {
        val first = Probe()
        val second = Probe()
        val third = Probe()
        val options = ExportOptions(seed = ArtSeed(value = SEED), quality = QualityLevel.Low)
        val before = scene {
            layer(ProbeArt())
            layer(ProbeArt(probe = first))
        }
        val after = scene {
            layer(ProbeArt(color = Color.Blue)) { isVisible = false }
            layer(ProbeArt(probe = second))
            layer(ProbeArt(probe = third)) { seed = ArtSeed(value = OWN_SEED) }
        }
        renderFrame(scene = before, size = size, time = Duration.ZERO, theme = theme, options = options)
        renderFrame(scene = after, size = size, time = Duration.ZERO, theme = theme, options = options)

        first.contexts.single().seed shouldBe ArtSeed(value = SEED).derive(salt = 1)
        second.contexts.single().seed shouldBe first.contexts.single().seed
        third.contexts.single().seed shouldBe ArtSeed(value = OWN_SEED)
        second.contexts.single().quality shouldBe QualityLevel.Low
    }

    test("hands the inputs to interactive layers only") {
        val reacts = Probe()
        val ignores = Probe()
        val inputs = ArtInputs.None.copy(progress = PROGRESS)
        val subject = scene {
            layer(ProbeArt(probe = reacts))
            layer(ProbeArt(probe = ignores)) { isInteractive = false }
        }
        subject.frame(inputs = inputs)

        reacts.last.inputs shouldBe inputs
        ignores.last.inputs shouldBe ArtInputs.None
    }

    test("renders a sequence of frames from one preparation") {
        val probe = Probe()
        val frames = renderFrames(
            scene = scene { layer(ProbeArt(probe = probe)) },
            size = size,
            from = Duration.ZERO,
            to = 1.seconds,
            framesPerSecond = FRAMES,
            theme = theme,
        )
        val fingerprints = frames.map { frame -> frame.fingerprint() }

        fingerprints.toSet() shouldHaveSize FRAMES
        probe.contexts shouldHaveSize 1
        probe.frames.map { frame -> frame.time.duration } shouldBe List(FRAMES) { index -> 250.milliseconds * index }
    }

    test("rejects an empty size") {
        val flat = IntSize(width = 0, height = HEIGHT)

        shouldThrow<IllegalArgumentException> {
            renderFrame(scene = plain(), size = IntSize.Zero, time = Duration.ZERO, theme = theme)
        }
        shouldThrow<IllegalArgumentException> {
            renderFrame(scene = plain(), size = IntSize(width = WIDTH, height = 0), time = Duration.ZERO, theme = theme)
        }
        shouldThrow<IllegalArgumentException> {
            renderFrames(
                scene = plain(),
                size = flat,
                from = Duration.ZERO,
                to = 1.seconds,
                framesPerSecond = 2,
                theme = theme,
            )
        }
    }

    test("fades the corners with a vignette and centers a radial mask") {
        val vignette = blank(mask = Mask.Vignette(strength = 1f, extent = 0.2f)).frame()
        val center = Offset(x = HALF, y = HALF)
        val radial = blank(mask = Mask.Radial(center = center, radius = 0.25f, stops = fade)).frame()

        vignette.colorAt(x = WIDTH / 2).alpha shouldBe 1f
        vignette.colorAt(x = 0, y = 0).alpha shouldBeLessThan FAINT
        vignette.colorAt(x = NEAR_CENTER).alpha shouldBeGreaterThan STRONG
        radial.colorAt(x = WIDTH / 2).alpha shouldBe (1f plusOrMinus TOLERANCE * 2)
        radial.colorAt(x = LEFT * 2).alpha shouldBe 0f
    }

    test("draws with the palette of the scene") {
        val night = ArtPalettes.Night.resolve(theme)

        scene { palette = night }.colorAt(x = LEFT) shouldBe night.background
    }

    test("hands the text measurer to the arts") {
        val probe = Probe()
        val measurer = textMeasurer()
        val options = ExportOptions(textMeasurer = measurer)
        val subject = scene { layer(ProbeArt(probe = probe)) }
        renderFrame(scene = subject, size = size, time = Duration.ZERO, theme = theme, options = options)

        probe.contexts.single().textMeasurer shouldBe measurer
        options.toString() shouldContain "textMeasurer=androidx"
    }

    test("reuses the brush of a mask while the size stays the same") {
        val probe = Probe()
        val subject = scene { layer(ProbeArt(probe = probe)) { mask = Mask.Linear(angle = 0f, stops = fade) } }
        val frames = renderFrames(
            scene = subject,
            size = size,
            from = Duration.ZERO,
            to = 1.seconds,
            framesPerSecond = 2,
            theme = theme,
        )

        frames.toList() shouldHaveSize 2
        probe.frames shouldHaveSize 2
    }

    test("options are values") {
        val options = ExportOptions()

        options.equals(theme) shouldBe false

        options.copy() shouldBe options
        options.copy().hashCode() shouldBe options.hashCode()
        options.copy(density = Density(density = 2f)) shouldNotBe options
        options.toString() shouldContain "seed=null"
        options.copy(seed = ArtSeed(value = SEED)).toString() shouldContain "seed=ArtSeed"
    }
})
