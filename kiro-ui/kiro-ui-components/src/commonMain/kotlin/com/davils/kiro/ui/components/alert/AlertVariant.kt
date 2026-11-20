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

package com.davils.kiro.ui.components.alert

/**
 * The variant of an [Alert], which picks its colors. The interface is open, so a theme can add variants of its own
 * and give them a style in its [AlertStyles].
 *
 * @since 1.0.0
 */
public interface AlertVariant {
    /**
     * A neutral alert in the colors of a card.
     *
     * @since 1.0.0
     */
    public data object Default : AlertVariant

    /**
     * An alert about an error or a destructive action, with its content in the destructive color.
     *
     * @since 1.0.0
     */
    public data object Destructive : AlertVariant

    /**
     * An alert with information, in the info status colors.
     *
     * @since 1.0.0
     */
    public data object Info : AlertVariant

    /**
     * An alert about a success, in the success status colors.
     *
     * @since 1.0.0
     */
    public data object Success : AlertVariant

    /**
     * An alert with a warning, in the warning status colors.
     *
     * @since 1.0.0
     */
    public data object Warning : AlertVariant
}
