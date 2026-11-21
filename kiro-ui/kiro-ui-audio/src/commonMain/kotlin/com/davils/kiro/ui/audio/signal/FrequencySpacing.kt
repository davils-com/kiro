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

package com.davils.kiro.ui.audio.signal

/**
 * How the bands of an [AudioSpectrum] are spaced between the lowest and the highest frequency of its [FrequencyRange].
 *
 * @since 1.0.0
 */
public enum class FrequencySpacing {
    /**
     * Spaces the bands by octaves, as the ear hears them and as analyzers of studios show them.
     *
     * @since 1.0.0
     */
    Logarithmic,

    /**
     * Spaces the bands by the same distance in hertz.
     *
     * @since 1.0.0
     */
    Linear,
}
