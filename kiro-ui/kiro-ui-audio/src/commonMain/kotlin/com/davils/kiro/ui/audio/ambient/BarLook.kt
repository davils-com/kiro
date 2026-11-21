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

package com.davils.kiro.ui.audio.ambient

/**
 * How the bars of a [SpectrumBars] are filled.
 *
 * @since 1.0.0
 */
public enum class BarLook {
    /**
     * Fills each bar in one piece.
     *
     * @since 1.0.0
     */
    Solid,

    /**
     * Builds each bar from blocks, as the LED displays of a stereo do.
     *
     * @since 1.0.0
     */
    Segmented,
}
