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

/**
 * A typed value that a [ShaderArt] hands to a `uniform` of its SkSL source in every frame.
 *
 * Fixed values are [FloatUniform], [Vec2Uniform] and [ColorUniform]; values that follow the frame are [TimeUniform],
 * [SizeUniform], [PointerUniform] and [ProgressUniform]. A uniform whose name the source does not declare, or whose
 * type does not match the declaration, is ignored.
 *
 * @since 1.0.0
 */
public sealed interface Uniform {
    /**
     * The name of the uniform in the SkSL source.
     *
     * @since 1.0.0
     */
    public val name: String
}
