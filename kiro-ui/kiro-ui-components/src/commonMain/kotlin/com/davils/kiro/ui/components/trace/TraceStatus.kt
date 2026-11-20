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

package com.davils.kiro.ui.components.trace

/**
 * The status of a step of a [Trace], which colors its [TraceLine].
 *
 * @since 1.0.0
 */
public enum class TraceStatus {
    /**
     * The step is done; the line has the color of the style.
     *
     * @since 1.0.0
     */
    Done,

    /**
     * The step is running; its title shimmers unless the theme reduces motion.
     *
     * @since 1.0.0
     */
    Running,

    /**
     * The step failed; the line has the error color of the style.
     *
     * @since 1.0.0
     */
    Error,

    /**
     * The step has not started; the line is drawn with the pending alpha of the style.
     *
     * @since 1.0.0
     */
    Pending,
}
