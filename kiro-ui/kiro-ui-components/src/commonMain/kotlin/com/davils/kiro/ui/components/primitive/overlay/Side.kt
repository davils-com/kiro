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
 * The side of its anchor on which an overlay opens.
 *
 * @since 1.0.0
 */
public enum class Side {
    /**
     * Above the anchor.
     *
     * @since 1.0.0
     */
    Top,

    /**
     * Below the anchor.
     *
     * @since 1.0.0
     */
    Bottom,

    /**
     * Before the anchor in the layout direction: on its left, or on its right in a right-to-left layout.
     *
     * @since 1.0.0
     */
    Start,

    /**
     * After the anchor in the layout direction: on its right, or on its left in a right-to-left layout.
     *
     * @since 1.0.0
     */
    End,
}
