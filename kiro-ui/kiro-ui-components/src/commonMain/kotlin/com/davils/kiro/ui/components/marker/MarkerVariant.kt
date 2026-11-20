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

package com.davils.kiro.ui.components.marker

/**
 * The variant of a [Marker], which decides the lines drawn with its content.
 *
 * @since 1.0.0
 */
public enum class MarkerVariant {
    /**
     * The content alone, without lines.
     *
     * @since 1.0.0
     */
    Default,

    /**
     * A line on both sides of the content, which fills the width of the row.
     *
     * @since 1.0.0
     */
    Separator,

    /**
     * A line below the content, at the bottom of the row.
     *
     * @since 1.0.0
     */
    Border,
}
