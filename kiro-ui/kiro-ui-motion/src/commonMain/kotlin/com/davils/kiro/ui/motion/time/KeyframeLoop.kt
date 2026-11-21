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

package com.davils.kiro.ui.motion.time

/**
 * What [Keyframes] do before their first and after their last keyframe.
 *
 * @since 1.0.0
 */
public enum class KeyframeLoop {
    /**
     * Holds the value of the first keyframe before it and the value of the last one after it.
     *
     * @since 1.0.0
     */
    Clamp,

    /**
     * Starts over at the first keyframe after the last one.
     *
     * @since 1.0.0
     */
    Loop,

    /**
     * Runs backwards to the first keyframe after the last one, and forwards again.
     *
     * @since 1.0.0
     */
    PingPong,
}
