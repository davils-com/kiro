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

package com.davils.kiro.ui.components.inputotp

/**
 * The scope in which the layout of an [InputOtp] declares its groups of slots and the separators between them, from
 * start to end.
 *
 * @since 1.0.0
 */
public interface InputOtpScope {
    /**
     * Declares a group of slots joined into one box.
     *
     * @param slots the indices of the slots in the group, counted from zero.
     * @since 1.0.0
     */
    public fun group(slots: IntRange)

    /**
     * Declares a separator, the separator icon of the style of the field.
     *
     * @since 1.0.0
     */
    public fun separator()
}
