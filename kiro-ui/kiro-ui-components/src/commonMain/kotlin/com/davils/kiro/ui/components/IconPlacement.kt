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

package com.davils.kiro.ui.components

/**
 * Where an icon sits in a row of content, which decides whether it reaches into the padding of the row.
 *
 * @since 1.0.0
 */
public enum class IconPlacement {
    /**
     * Between other content, without reaching into the padding.
     *
     * @since 1.0.0
     */
    Inline,

    /**
     * At the start of the row, reaching into the padding on the start side.
     *
     * @since 1.0.0
     */
    Leading,

    /**
     * At the end of the row, reaching into the padding on the end side.
     *
     * @since 1.0.0
     */
    Trailing,
}
