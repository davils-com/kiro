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

package com.davils.kiro.ui.components.toast

/**
 * The type of a [Toast], which picks its icon and, in a rich toaster, its colors.
 *
 * The interface is open, so that a style can give icons and colors to types of its own.
 *
 * @since 1.0.0
 */
public interface ToastType {
    /**
     * A plain toast, without an icon or status colors in the default style.
     *
     * @since 1.0.0
     */
    public data object Default : ToastType

    /**
     * A toast that reports a success.
     *
     * @since 1.0.0
     */
    public data object Success : ToastType

    /**
     * A toast that informs.
     *
     * @since 1.0.0
     */
    public data object Info : ToastType

    /**
     * A toast that warns.
     *
     * @since 1.0.0
     */
    public data object Warning : ToastType

    /**
     * A toast that reports an error.
     *
     * @since 1.0.0
     */
    public data object Error : ToastType

    /**
     * A toast for work in progress, with a spinning icon; by default it neither dismisses itself nor can be closed.
     *
     * @since 1.0.0
     */
    public data object Loading : ToastType
}
