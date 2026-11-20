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
 * The size of a [SidebarMenuButton], which sets its height and the size of its text.
 *
 * @since 1.0.0
 */
public enum class SidebarMenuButtonSize {
    /**
     * A low button with the small text style of the sidebar.
     *
     * @since 1.0.0
     */
    Sm,

    /**
     * A button of the regular height.
     *
     * @since 1.0.0
     */
    Default,

    /**
     * A tall button, for content with two lines such as a title and a subtitle.
     *
     * @since 1.0.0
     */
    Lg,
}
