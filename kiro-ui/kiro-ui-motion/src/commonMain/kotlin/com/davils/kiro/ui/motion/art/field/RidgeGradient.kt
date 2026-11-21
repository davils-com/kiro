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

package com.davils.kiro.ui.motion.art.field

/**
 * The direction in which the colors of a [RidgeArt] run over its lines.
 *
 * @since 1.0.0
 */
public enum class RidgeGradient {
    /**
     * The first color is at the back, the last at the front.
     *
     * @since 1.0.0
     */
    BackToFront,

    /**
     * The first color is at the front, the last at the back.
     *
     * @since 1.0.0
     */
    FrontToBack,

    /**
     * The first color is at the back and at the front, the last in the middle.
     *
     * @since 1.0.0
     */
    Mirrored,
}
