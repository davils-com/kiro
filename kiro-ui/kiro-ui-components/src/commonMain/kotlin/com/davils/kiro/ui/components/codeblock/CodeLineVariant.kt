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

package com.davils.kiro.ui.components.codeblock

/**
 * How a line of a [CodeBlockContent] is marked, such as in a diff.
 *
 * @since 1.0.0
 */
public enum class CodeLineVariant {
    /**
     * A plain line.
     *
     * @since 1.0.0
     */
    Default,

    /**
     * An added line, with the added background and a plus sign at its start.
     *
     * @since 1.0.0
     */
    Added,

    /**
     * A removed line, with the removed background and a minus sign in the removed marker color at its start.
     *
     * @since 1.0.0
     */
    Removed,

    /**
     * A line that stands out with the highlighted background.
     *
     * @since 1.0.0
     */
    Highlighted,
}
