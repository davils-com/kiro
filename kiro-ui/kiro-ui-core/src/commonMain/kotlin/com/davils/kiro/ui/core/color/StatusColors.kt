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

package com.davils.kiro.ui.core.color

import androidx.compose.runtime.Immutable

/**
 * The colors of the four statuses a message can have.
 *
 * @property info the colors of an informational message.
 * @property success the colors of a success.
 * @property warning the colors of a warning.
 * @property error the colors of an error.
 * @since 1.0.0
 */
@Immutable
public class StatusColors(
    public val info: StatusColor,
    public val success: StatusColor,
    public val warning: StatusColor,
    public val error: StatusColor,
) {
    /**
     * Returns a copy of this [StatusColors] with the given values replaced and every other value kept.
     *
     * @param info the new value of [info].
     * @param success the new value of [success].
     * @param warning the new value of [warning].
     * @param error the new value of [error].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        info: StatusColor = this.info,
        success: StatusColor = this.success,
        warning: StatusColor = this.warning,
        error: StatusColor = this.error,
    ): StatusColors = StatusColors(
        info = info,
        success = success,
        warning = warning,
        error = error,
    )

    override fun equals(other: Any?): Boolean = other is StatusColors && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "StatusColors(" +
        "info=$info, " +
        "success=$success, " +
        "warning=$warning, " +
        "error=$error)"

    private fun values(): List<Any?> = listOf(
        info,
        success,
        warning,
        error,
    )
}
