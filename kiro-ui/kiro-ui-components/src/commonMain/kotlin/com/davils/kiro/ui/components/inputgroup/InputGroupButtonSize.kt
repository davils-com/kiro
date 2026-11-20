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

package com.davils.kiro.ui.components.inputgroup

/**
 * The size of an [InputGroupButton], which selects its default height, padding and shape.
 *
 * The sizes of Kiro are the objects nested in this interface; implement it to add a size of your own and give it a
 * style through [InputGroupButtonStyles].
 *
 * @since 1.0.0
 */
public interface InputGroupButtonSize {
    /**
     * The extra small size for a button with text: 24 dp high with a narrow padding.
     *
     * @since 1.0.0
     */
    public data object Xs : InputGroupButtonSize

    /**
     * The small size for a button with text: the height of a default button.
     *
     * @since 1.0.0
     */
    public data object Sm : InputGroupButtonSize

    /**
     * The extra small size for a button with only an icon: a square of 24 dp.
     *
     * @since 1.0.0
     */
    public data object IconXs : InputGroupButtonSize

    /**
     * The small size for a button with only an icon: a square as high as a default button.
     *
     * @since 1.0.0
     */
    public data object IconSm : InputGroupButtonSize
}
