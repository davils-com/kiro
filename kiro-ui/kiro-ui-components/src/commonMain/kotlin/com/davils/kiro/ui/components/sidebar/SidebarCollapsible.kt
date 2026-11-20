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

package com.davils.kiro.ui.components.sidebar

/**
 * How a [Sidebar] collapses when its [SidebarState] is closed.
 *
 * @since 1.0.0
 */
public enum class SidebarCollapsible {
    /**
     * The sidebar shrinks to no width and leaves the window.
     *
     * @since 1.0.0
     */
    Offcanvas,

    /**
     * The sidebar shrinks to a rail that shows only the icons of its menu buttons.
     *
     * @since 1.0.0
     */
    Icon,

    /**
     * The sidebar never collapses and stays open whatever its state.
     *
     * @since 1.0.0
     */
    None,
}
