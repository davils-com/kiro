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

package com.davils.kiro.ui.components.primitive.overlay

/**
 * How an overlay lines up with its anchor along the side it opens on.
 *
 * On the top or the bottom side, the start and the end follow the layout direction.
 *
 * @since 1.0.0
 */
public enum class Align {
    /**
     * The start edges of the overlay and the anchor line up.
     *
     * @since 1.0.0
     */
    Start,

    /**
     * The overlay is centered on the anchor.
     *
     * @since 1.0.0
     */
    Center,

    /**
     * The end edges of the overlay and the anchor line up.
     *
     * @since 1.0.0
     */
    End,
}
