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

package com.davils.kiro.ui.audio.spectrum

/**
 * How a [SpectrumAnalyzer] connects the levels of the bands.
 *
 * @since 1.0.0
 */
public enum class SpectrumCurve {
    /**
     * Draws a smooth curve through the bands, as the analyzers of equalizers do.
     *
     * @since 1.0.0
     */
    Smooth,

    /**
     * Connects the bands by straight lines.
     *
     * @since 1.0.0
     */
    Linear,

    /**
     * Draws each band as a flat step across its width, as a third-octave analyzer does.
     *
     * @since 1.0.0
     */
    Steps,
}
