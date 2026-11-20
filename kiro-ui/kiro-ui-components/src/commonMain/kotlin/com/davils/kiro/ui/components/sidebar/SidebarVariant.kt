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
 * The look of a [Sidebar] and of the [SidebarInset] next to it, set on the [SidebarProvider].
 *
 * @since 1.0.0
 */
public enum class SidebarVariant {
    /**
     * A sidebar flush with the edge of the window, with a line between it and the content.
     *
     * @since 1.0.0
     */
    Sidebar,

    /**
     * A sidebar that floats inside the window as a panel with rounded corners, a ring and a shadow.
     *
     * @since 1.0.0
     */
    Floating,

    /**
     * A sidebar on the sidebar background, next to content in a raised panel with rounded corners.
     *
     * @since 1.0.0
     */
    Inset,
}
