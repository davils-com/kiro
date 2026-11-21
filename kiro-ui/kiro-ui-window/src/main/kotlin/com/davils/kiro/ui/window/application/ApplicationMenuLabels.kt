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

package com.davils.kiro.ui.window.application

import androidx.compose.runtime.Immutable

/**
 * The texts of the application menu that [applicationMenu] adds to the menubar of a window on Windows and Linux,
 * in English by default.
 *
 * @property about the text of the item that shows information about the application.
 * @property settings the text of the item that opens the settings.
 * @property quit the text of the item that quits the application.
 * @since 1.0.0
 */
@Immutable
public class ApplicationMenuLabels(
    public val about: String = ApplicationMessages.ABOUT,
    public val settings: String = ApplicationMessages.SETTINGS,
    public val quit: String = ApplicationMessages.QUIT,
) {
    /**
     * Returns a copy of this [ApplicationMenuLabels] with the given values replaced and every other value kept.
     *
     * @param about the new value of [about].
     * @param settings the new value of [settings].
     * @param quit the new value of [quit].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        about: String = this.about,
        settings: String = this.settings,
        quit: String = this.quit,
    ): ApplicationMenuLabels = ApplicationMenuLabels(
        about = about,
        settings = settings,
        quit = quit,
    )

    override fun equals(other: Any?): Boolean = other is ApplicationMenuLabels && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ApplicationMenuLabels(" +
        "about=$about, " +
        "settings=$settings, " +
        "quit=$quit)"

    private fun values(): List<Any?> = listOf(
        about,
        settings,
        quit,
    )
}
