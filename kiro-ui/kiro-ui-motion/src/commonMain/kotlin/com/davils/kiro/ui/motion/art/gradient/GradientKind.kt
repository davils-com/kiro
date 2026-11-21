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

package com.davils.kiro.ui.motion.art.gradient

/**
 * The shape of a [GradientArt].
 *
 * @since 1.0.0
 */
public enum class GradientKind {
    /**
     * Bands of color along a line that slowly turns around the middle of the gradient.
     *
     * @since 1.0.0
     */
    Linear,

    /**
     * Rings of color around a middle that slowly circles around the middle of the area.
     *
     * @since 1.0.0
     */
    Radial,

    /**
     * Colors that sweep around a middle like the hand of a clock and slowly turn.
     *
     * @since 1.0.0
     */
    Sweep,
}
