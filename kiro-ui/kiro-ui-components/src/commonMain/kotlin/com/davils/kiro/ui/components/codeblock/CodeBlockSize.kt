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
 * The size of a [CodeBlock], which picks its text size, padding and the gap between its sections.
 *
 * The default style knows the sizes declared here; a size of your own needs [CodeBlockStyles] that resolve it.
 *
 * @since 1.0.0
 */
public interface CodeBlockSize {
    /**
     * The regular size, with small text.
     *
     * @since 1.0.0
     */
    public data object Default : CodeBlockSize

    /**
     * A smaller size, with extra small text and less padding.
     *
     * @since 1.0.0
     */
    public data object Sm : CodeBlockSize
}
