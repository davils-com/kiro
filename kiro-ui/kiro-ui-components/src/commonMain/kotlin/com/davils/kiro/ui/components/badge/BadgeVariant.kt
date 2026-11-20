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

package com.davils.kiro.ui.components.badge

/**
 * The variant of a [Badge], which selects its default colors.
 *
 * The variants of Kiro are the objects nested in this interface; implement it to add a variant of your own and give
 * it a style through [BadgeStyles].
 *
 * @since 1.0.0
 */
public interface BadgeVariant {
    /**
     * The primary badge: the primary color with its foreground.
     *
     * @since 1.0.0
     */
    public data object Default : BadgeVariant

    /**
     * The secondary badge: the secondary color with its foreground.
     *
     * @since 1.0.0
     */
    public data object Secondary : BadgeVariant

    /**
     * The badge of a destructive or failed state: a faint tint of the destructive color with destructive text.
     *
     * @since 1.0.0
     */
    public data object Destructive : BadgeVariant

    /**
     * The outlined badge: a transparent container with a border.
     *
     * @since 1.0.0
     */
    public data object Outline : BadgeVariant

    /**
     * The ghost badge: a transparent container that shows the muted color while hovered.
     *
     * @since 1.0.0
     */
    public data object Ghost : BadgeVariant

    /**
     * The link badge: primary text on a transparent container, underlined while hovered.
     *
     * @since 1.0.0
     */
    public data object Link : BadgeVariant
}
