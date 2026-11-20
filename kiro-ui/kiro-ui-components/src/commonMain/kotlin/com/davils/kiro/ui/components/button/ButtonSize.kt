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
 * The size of a [Button]: its height, its padding, its gap and the size of its text and icons.
 *
 * The sizes without `Icon` fit a label with optional icons; the `Icon` sizes give a square button for a single icon.
 * A custom size can implement this interface when its [ButtonStyles] resolves it.
 *
 * @since 1.0.0
 */
public interface ButtonSize {
    /**
     * The regular size of a button with a label.
     *
     * @since 1.0.0
     */
    public data object Default : ButtonSize

    /**
     * The extra small size of a button with a label, with smaller text and icons.
     *
     * @since 1.0.0
     */
    public data object Xs : ButtonSize

    /**
     * The small size of a button with a label, with smaller text and icons.
     *
     * @since 1.0.0
     */
    public data object Sm : ButtonSize

    /**
     * The large size of a button with a label.
     *
     * @since 1.0.0
     */
    public data object Lg : ButtonSize

    /**
     * The regular size of a square button that shows only an icon.
     *
     * @since 1.0.0
     */
    public data object Icon : ButtonSize

    /**
     * The extra small size of a square button that shows only an icon.
     *
     * @since 1.0.0
     */
    public data object IconXs : ButtonSize

    /**
     * The small size of a square button that shows only an icon.
     *
     * @since 1.0.0
     */
    public data object IconSm : ButtonSize

    /**
     * The large size of a square button that shows only an icon.
     *
     * @since 1.0.0
     */
    public data object IconLg : ButtonSize
}
