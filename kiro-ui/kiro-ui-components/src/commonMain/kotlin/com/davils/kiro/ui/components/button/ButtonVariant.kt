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

package com.davils.kiro.ui.components.button

/**
 * The look of a [Button]: the colors of its container, its content and its border in each interaction state.
 *
 * A custom variant can implement this interface when its [ButtonStyles] resolves it.
 *
 * @since 1.0.0
 */
public interface ButtonVariant {
    /**
     * A filled button in the primary color, for the main action of a view.
     *
     * @since 1.0.0
     */
    public data object Default : ButtonVariant

    /**
     * A button with a border on the background color.
     *
     * @since 1.0.0
     */
    public data object Outline : ButtonVariant

    /**
     * A filled button in the secondary color, for actions next to the main one.
     *
     * @since 1.0.0
     */
    public data object Secondary : ButtonVariant

    /**
     * A button without a container that shows one only while it is hovered or expanded.
     *
     * @since 1.0.0
     */
    public data object Ghost : ButtonVariant

    /**
     * A button in a light tint of the destructive color, for actions that delete or cannot be undone.
     *
     * @since 1.0.0
     */
    public data object Destructive : ButtonVariant

    /**
     * A button that looks like a link: primary text without a container, underlined while it is hovered.
     *
     * @since 1.0.0
     */
    public data object Link : ButtonVariant
}
