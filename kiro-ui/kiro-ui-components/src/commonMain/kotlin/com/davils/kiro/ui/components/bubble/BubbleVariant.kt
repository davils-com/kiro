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

package com.davils.kiro.ui.components.bubble

/**
 * The variant of a [Bubble], which chooses the colors, shape and padding of its default style.
 *
 * @since 1.0.0
 */
public interface BubbleVariant {
    /**
     * The default variant: the primary color of the theme with its foreground.
     *
     * @since 1.0.0
     */
    public data object Default : BubbleVariant

    /**
     * The secondary color of the theme with its foreground.
     *
     * @since 1.0.0
     */
    public data object Secondary : BubbleVariant

    /**
     * The muted color of the theme with the foreground color.
     *
     * @since 1.0.0
     */
    public data object Muted : BubbleVariant

    /**
     * A light tint of the primary color in a light theme and a dark one in a dark theme.
     *
     * @since 1.0.0
     */
    public data object Tinted : BubbleVariant

    /**
     * The background color of the theme with a border.
     *
     * @since 1.0.0
     */
    public data object Outline : BubbleVariant

    /**
     * No container, no padding and the full available width, for text that reads as plain text.
     *
     * @since 1.0.0
     */
    public data object Ghost : BubbleVariant

    /**
     * A translucent destructive color with the destructive color as its content color.
     *
     * @since 1.0.0
     */
    public data object Destructive : BubbleVariant
}
