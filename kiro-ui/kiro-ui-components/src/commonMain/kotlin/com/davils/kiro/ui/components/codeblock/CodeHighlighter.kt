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

import androidx.compose.ui.text.AnnotatedString

/**
 * Colors the lines of a [CodeBlockContent], such as for syntax highlighting.
 *
 * @since 1.0.0
 */
public fun interface CodeHighlighter {
    /**
     * Returns one line of code with its styles.
     *
     * @param line the line, without its line break.
     * @return the text of [line] with the spans that color it.
     * @since 1.0.0
     */
    public fun highlight(line: String): AnnotatedString

    /**
     * Holds the highlighters that come with Kiro.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * Leaves every line as it is, without any styles.
         *
         * @since 1.0.0
         */
        public val Plain: CodeHighlighter = CodeHighlighter { line -> AnnotatedString(line) }
    }
}
