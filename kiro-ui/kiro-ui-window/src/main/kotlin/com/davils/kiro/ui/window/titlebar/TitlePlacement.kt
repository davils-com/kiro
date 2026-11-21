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

package com.davils.kiro.ui.window.titlebar

/**
 * Where the content of the center slot of a title bar sits.
 *
 * @since 1.0.0
 */
public enum class TitlePlacement {
    /**
     * In the middle of the whole window, whatever the start and end slots hold. The title moves aside only as far as a
     * slot would otherwise cover it, and shortens with an ellipsis only once there is no room left.
     *
     * @since 1.0.0
     */
    WindowCenter,

    /**
     * In the middle of the room between the start and the end slot.
     *
     * @since 1.0.0
     */
    SlotCenter,

    /**
     * Right after the start slot.
     *
     * @since 1.0.0
     */
    Start,
}
