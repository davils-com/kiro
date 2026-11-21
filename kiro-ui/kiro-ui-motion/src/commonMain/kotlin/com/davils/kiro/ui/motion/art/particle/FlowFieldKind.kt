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
 * How the field of a [FlowFieldArt] derives its directions from the noise.
 *
 * @since 1.0.0
 */
public enum class FlowFieldKind {
    /**
     * The noise is the angle of the direction, which gives long, sweeping currents.
     *
     * @since 1.0.0
     */
    Angle,

    /**
     * The direction runs along the lines of equal noise, which gives swirls that never converge.
     *
     * @since 1.0.0
     */
    Curl,
}
