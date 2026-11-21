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

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.art.ArtContext
import com.davils.kiro.ui.motion.art.ArtRenderer

/**
 * Art drawn by a shader in SkSL, the shading language of Skia, on the JVM and in the browser alike.
 *
 * The source declares `half4 main(float2 coord)`, where `coord` is the position of the pixel in pixels, and returns a
 * premultiplied color. Every [Uniform] hands one typed value to a `uniform` of the source in every frame. The compiled
 * effect is shared by every art with the same source in a small cache, and the shader of a frame is only rebuilt when
 * a uniform changes. Where the platform cannot compile the source, or [backend] asks for it, the [fallback] draws
 * instead with the canvas.
 *
 * ```kotlin
 * val glow = ShaderArt(
 *     source = "uniform float2 size; uniform half4 tint; half4 main(float2 p) { return tint * (p.y / size.y); }",
 *     uniforms = listOf(SizeUniform(name = "size"), ColorUniform(name = "tint", color = Color.White)),
 * )
 * ```
 *
 * @property source the SkSL source of the shader.
 * @property uniforms the values the source receives.
 * @property fallback the art that draws where the shader cannot, or `null` for a soft wash of the first accent
of the palette over its background.
 * @property backend whether the art draws with the shader where it can or always with [fallback].
 * @since 1.0.0
 */
@Immutable
public class ShaderArt(
    public val source: String,
    public val uniforms: List<Uniform>,
    public val fallback: Art? = null,
    public val backend: ShaderBackend = ShaderBackend.Auto,
) : Art {
    override val isStatic: Boolean
        get() = uniforms.none { uniform -> uniform.isMoving() } && fallback?.isStatic != false

    /**
     * Returns a copy of this [ShaderArt] with the given values replaced and every other value kept.
     *
     * @param source the new value of [source].
     * @param uniforms the new value of [uniforms].
     * @param fallback the new value of [fallback].
     * @param backend the new value of [backend].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        source: String = this.source,
        uniforms: List<Uniform> = this.uniforms,
        fallback: Art? = this.fallback,
        backend: ShaderBackend = this.backend,
    ): ShaderArt = ShaderArt(
        source = source,
        uniforms = uniforms,
        fallback = fallback,
        backend = backend,
    )

    override fun equals(other: Any?): Boolean = other is ShaderArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ShaderArt(" +
        "source=$source, " +
        "uniforms=$uniforms, " +
        "fallback=${fallback ?: "null"}, " +
        "backend=$backend)"

    private fun values(): List<Any?> = listOf(
        source,
        uniforms,
        fallback,
        backend,
    )

    override fun prepare(context: ArtContext): ArtRenderer = shaderRendererOf(
        source = source,
        backend = backend,
        binding = uniforms.bindingOf(),
        fallback = { fallback?.prepare(context) ?: WashRenderer() },
    )
}

private fun Uniform.isMoving(): Boolean = this is TimeUniform || this is PointerUniform || this is ProgressUniform
