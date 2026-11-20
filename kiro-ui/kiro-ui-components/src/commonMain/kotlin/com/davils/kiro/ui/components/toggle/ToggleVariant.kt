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

package com.davils.kiro.ui.components.toggle

/**
 * The look of a toggle, which selects the colors and the border of its default style.
 *
 * A variant of your own needs [ToggleStyles] that know it.
 *
 * @since 1.0.0
 */
public interface ToggleVariant {
    /**
     * A toggle without a border, with a transparent container while it is not pressed.
     *
     * @since 1.0.0
     */
    public data object Default : ToggleVariant

    /**
     * A toggle with a border, with a transparent container while it is not pressed.
     *
     * @since 1.0.0
     */
    public data object Outline : ToggleVariant
}
