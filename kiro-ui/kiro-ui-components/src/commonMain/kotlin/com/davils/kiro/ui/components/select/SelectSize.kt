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

package com.davils.kiro.ui.components.select

/**
 * The size of a [Select], which chooses the height and shape of its default style.
 *
 * @since 1.0.0
 */
public interface SelectSize {
    /**
     * The default size.
     *
     * @since 1.0.0
     */
    public data object Default : SelectSize

    /**
     * A smaller size with a lower field and a smaller corner radius.
     *
     * @since 1.0.0
     */
    public data object Sm : SelectSize
}
