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

package com.davils.kiro.ui.motion.shader

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.motion.art.gradient.GradientArt
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.colors
import com.davils.kiro.ui.motion.fixture.drawEmpty
import com.davils.kiro.ui.motion.fixture.drawInto
import com.davils.kiro.ui.motion.fixture.fingerprint
import com.davils.kiro.ui.motion.fixture.pixels
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.render
import com.davils.kiro.ui.motion.fixture.rendererOf
import com.davils.kiro.ui.motion.input.ArtInputs
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.time.Duration.Companion.seconds

private const val TINTED: String = """
uniform float2 size;
uniform float4 tint;
uniform float strength;
uniform float2 shift;
uniform float time;
uniform float3 pointer;
uniform float progress;

half4 main(float2 p) {
    float wave = 0.5 + 0.5 * sin(6.2831853 * (time + p.x / size.x) + shift.x);
    float near = pointer.z * exp(-length(p - pointer.xy) / 40.0);
    float level = clamp(strength * wave + near + progress * shift.y, 0.0, 1.0);
    return half4(half3(tint.rgb * tint.a * level), half(tint.a * level));
}
"""

private const val FEW: Int = 8
private const val SHIFT: String = "shift"
private const val HALF: Float = 0.5f
private const val STRENGTH: Float = 0.2f
private const val NARROW: Int = 200
private const val NEAR: Float = 20f
private const val CROWD: Int = 20

private val period = 4.seconds

private fun tinted(
    color: Color = Color.Red,
    strength: Float = 1f,
    shift: Vec2Uniform = Vec2Uniform(name = SHIFT, x = 0f, y = 0f),
    backend: ShaderBackend = ShaderBackend.Auto,
): ShaderArt = ShaderArt(
    source = TINTED,
    uniforms = listOf(
        SizeUniform(name = "size"),
        ColorUniform(name = "tint", color = color),
        FloatUniform(name = "strength", value = strength),
        shift,
        TimeUniform(name = "time", period = period),
        PointerUniform(name = "pointer"),
        ProgressUniform(name = "progress", missing = 0f),
    ),
    fallback = GradientArt(),
    backend = backend,
)

private fun ShaderArt.center(render: Render = Render()): Int {
    val image = render(render = render)
    return image.pixels()[image.width * (image.height / 2) + image.width / 2]
}

class ShaderArtTest : FunSpec({
    val base = tinted().print()

    test("hands every typed uniform to its source") {
        val art = tinted()
        art.rendererOf().shouldBeInstanceOf<ShaderRenderer>()
        art.colors() shouldBeGreaterThan FEW
        tinted(color = Color.Blue).print() shouldNotBe base
        tinted(color = Color.Red.copy(alpha = HALF)).center() shouldNotBe tinted().center()
        tinted(strength = STRENGTH).print() shouldNotBe base
        tinted(shift = Vec2Uniform(name = SHIFT, x = 1f, y = 0f)).print() shouldNotBe base
        tinted().print(Render(width = NARROW)) shouldNotBe base
    }

    test("follows the time in loops of its period") {
        tinted().print(Render(time = 1.seconds)) shouldNotBe base
        tinted().print(Render(time = period)) shouldBe base
    }

    test("follows the pointer and the progress") {
        val pointer = ArtInputs.None.copy(pointer = Offset(x = NEAR, y = NEAR))
        val progress = ArtInputs.None.copy(progress = 1f)
        val shifted = tinted(shift = Vec2Uniform(name = SHIFT, x = 0f, y = 1f))

        tinted().print(Render(inputs = pointer)) shouldNotBe base
        shifted.print(Render(inputs = progress)) shouldNotBe shifted.print()
    }

    test("falls back to the canvas when its source does not compile or its backend asks for it") {
        val broken = ShaderArt(source = "half4 main(float2 p) { return nonsense; }", uniforms = emptyList())
        val canvas = tinted(backend = ShaderBackend.Canvas)

        broken.rendererOf().shouldBeInstanceOf<WashRenderer>()
        broken.colors() shouldBeGreaterThan FEW
        canvas.print() shouldBe GradientArt().print()
        broken.copy(fallback = GradientArt()).print() shouldBe GradientArt().print()
        shaderEffects.contains(source = broken.source) shouldBe true
    }

    test("shares the compiled effect and compiles it again after it left the cache") {
        val renderer = tinted().rendererOf()
        val before = renderer.drawInto(Render()).fingerprint()
        shaderEffects.contains(source = TINTED) shouldBe true

        shaderEffects.clear()
        shaderEffects.size shouldBe 0
        renderer.drawInto(Render()).fingerprint() shouldBe before
        shaderEffects.contains(source = TINTED) shouldBe true
        renderer.drawEmpty()
    }

    test("releases the effects beyond the capacity of its cache") {
        val sources = (0..CROWD).map { index -> TINTED.replace(oldValue = "40.0", newValue = "$index.5") }
        sources.forEach { source -> ShaderArt(source = source, uniforms = emptyList()).rendererOf() }

        shaderEffects.size shouldBeLessThanOrEqual CROWD
        shaderEffects.contains(source = sources.first()) shouldBe false
        shaderEffects.contains(source = sources.last()) shouldBe true
    }

    test("is static only without uniforms that move") {
        val still = ShaderArt(source = TINTED, uniforms = listOf(SizeUniform(name = "size")))

        still.isStatic shouldBe true
        still.copy(fallback = GradientArt()).isStatic shouldBe false
        tinted().isStatic shouldBe false
        still.copy(uniforms = listOf(PointerUniform(name = "pointer"))).isStatic shouldBe false
        still.copy(uniforms = listOf(ProgressUniform(name = "progress", missing = 0f))).isStatic shouldBe false
    }

    test("is a value") {
        val art = tinted()

        art shouldBe tinted()
        art.hashCode() shouldBe tinted().hashCode()
        art.copy() shouldBe art
        art.copy(backend = ShaderBackend.Canvas) shouldNotBe art
        art.toString() shouldStartWith "ShaderArt(source="
        ShaderArt(source = "", uniforms = emptyList()).toString() shouldBe
            "ShaderArt(source=, uniforms=[], fallback=null, backend=Auto)"
    }

    test("keeps its uniforms as values") {
        val float = FloatUniform(name = "a", value = 1f)
        val vec2 = Vec2Uniform(name = "a", x = 1f, y = 2f)
        val color = ColorUniform(name = "a", color = Color.Red)
        val time = TimeUniform(name = "a", period = period)
        val size = SizeUniform(name = "a")
        val pointer = PointerUniform(name = "a")
        val progress = ProgressUniform(name = "a", missing = 0f)
        val cases: List<Triple<Uniform, Uniform, Uniform>> = listOf(
            Triple(first = float, second = float.copy(), third = float.copy(value = 2f)),
            Triple(first = vec2, second = vec2.copy(), third = vec2.copy(x = 2f)),
            Triple(first = color, second = color.copy(), third = color.copy(color = Color.Blue)),
            Triple(first = time, second = time.copy(), third = time.copy(period = 1.seconds)),
            Triple(first = size, second = size.copy(), third = size.copy(name = "b")),
            Triple(first = pointer, second = pointer.copy(), third = pointer.copy(name = "b")),
            Triple(first = progress, second = progress.copy(), third = progress.copy(missing = 1f)),
        )

        cases.forEach { (uniform, copy, other) ->
            copy shouldBe uniform
            copy.hashCode() shouldBe uniform.hashCode()
            other shouldNotBe uniform
            uniform.name shouldBe "a"
            uniform.toString() shouldContain "Uniform(name=a"
        }
    }
})
