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

package com.davils.kiro.ui.motion.quality

/**
 * How much detail art draws, from few shapes on weak hardware to every shape on strong hardware.
 *
 * @property density the share of shapes, particles and lines an art draws compared to [High].
 * @since 1.0.0
 */
public enum class QualityLevel(public val density: Float) {
    /**
     * A quarter of the detail, for weak hardware or many scenes at once.
     *
     * @since 1.0.0
     */
    Low(density = 0.25f),

    /**
     * Half of the detail.
     *
     * @since 1.0.0
     */
    Medium(density = 0.5f),

    /**
     * Three quarters of the detail.
     *
     * @since 1.0.0
     */
    Balanced(density = 0.75f),

    /**
     * All of the detail the style of an art asks for.
     *
     * @since 1.0.0
     */
    High(density = 1f),
}
