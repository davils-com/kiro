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

package com.davils.kiro.ui.window.tray

/**
 * How important a [TrayNotification] is, which decides the icon the operating system shows with it.
 *
 * @since 1.0.0
 */
public enum class TrayNotificationType {
    /**
     * A notification without an icon.
     *
     * @since 1.0.0
     */
    None,

    /**
     * A notification that informs.
     *
     * @since 1.0.0
     */
    Info,

    /**
     * A notification that warns.
     *
     * @since 1.0.0
     */
    Warning,

    /**
     * A notification that reports an error.
     *
     * @since 1.0.0
     */
    Error,
}
