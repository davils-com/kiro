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

package com.davils.kiro.ui.motion.art

import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Draws a prepared [Art].
 *
 * The parts that do not move, such as a grid behind moving shapes, belong in [drawStatic]: the host records them once
 * for every size and palette and replays the recording, so that only [drawFrame] runs in every frame.
 *
 * @since 1.0.0
 */
public interface ArtRenderer {
    /**
     * Whether [drawStatic] draws anything, which lets the host skip recording it.
     *
     * @since 1.0.0
     */
    public val hasStatic: Boolean
        get() = false

    /**
     * Draws the parts of the art that never move.
     *
     * @param frame the frame at the time of the recording.
     * @since 1.0.0
     */
    public fun DrawScope.drawStatic(frame: ArtFrame): Unit = Unit

    /**
     * Draws the parts of the art that move, for one frame.
     *
     * @param frame the time, the inputs, the palette and the quality of the frame.
     * @since 1.0.0
     */
    public fun DrawScope.drawFrame(frame: ArtFrame)
}
