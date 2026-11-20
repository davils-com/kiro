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
 * The size of a toggle, which selects the height, the icon size and the text size of its default style.
 *
 * A size of your own needs [ToggleStyles] that know it.
 *
 * @since 1.0.0
 */
public interface ToggleSize {
    /**
     * The regular size of a toggle.
     *
     * @since 1.0.0
     */
    public data object Default : ToggleSize

    /**
     * A small toggle, lower than [Default] and with smaller text and icons.
     *
     * @since 1.0.0
     */
    public data object Sm : ToggleSize

    /**
     * A large toggle, higher than [Default].
     *
     * @since 1.0.0
     */
    public data object Lg : ToggleSize
}
