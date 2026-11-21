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

package com.davils.kiro.ui.motion.art.particle

/**
 * What picks the color of a line of a [FlowFieldArt].
 *
 * @since 1.0.0
 */
public enum class FlowColorMode {
    /**
     * The direction of the line at each step.
     *
     * @since 1.0.0
     */
    Angle,

    /**
     * Where the step lies across the area, from the top left to the bottom right.
     *
     * @since 1.0.0
     */
    Position,

    /**
     * Each line keeps one color of its own.
     *
     * @since 1.0.0
     */
    Line,
}
