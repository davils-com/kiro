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

package com.davils.kiro.ui.window.screen

/**
 * Lists the screens attached to the computer.
 *
 * @since 1.0.0
 */
public fun interface ScreenApi {
    /**
     * Returns the screens attached to the computer, or an empty list on a machine without a display.
     *
     * @return the attached screens.
     * @since 1.0.0
     */
    public fun screens(): List<Screen>

    /**
     * Holds the [ScreenApi] of the operating system.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The screens the Java runtime reports.
         *
         * @since 1.0.0
         */
        public val System: ScreenApi = JdkScreenApi
    }
}
