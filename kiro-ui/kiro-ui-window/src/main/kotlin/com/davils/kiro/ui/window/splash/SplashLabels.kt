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

package com.davils.kiro.ui.window.splash

import androidx.compose.runtime.Immutable

/**
 * The texts a splash screen shows and announces, in English by default.
 *
 * @property starting the text shown before any step ran.
 * @property close the text of the button that closes a failed start.
 * @property retry the text of the button that starts again after a failure.
 * @property version the text that shows the version of the application.
 * @property status the spoken description of the status of a step.
 * @since 1.0.0
 */
@Immutable
public class SplashLabels(
    public val starting: String,
    public val close: String,
    public val retry: String,
    public val version: (version: String) -> String,
    public val status: (status: SplashStepStatus) -> String,
) {
    /**
     * Returns a copy of this [SplashLabels] with the given values replaced and every other value kept.
     *
     * @param starting the new value of [starting].
     * @param close the new value of [close].
     * @param retry the new value of [retry].
     * @param version the new value of [version].
     * @param status the new value of [status].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        starting: String = this.starting,
        close: String = this.close,
        retry: String = this.retry,
        version: (version: String) -> String = this.version,
        status: (status: SplashStepStatus) -> String = this.status,
    ): SplashLabels = SplashLabels(
        starting = starting,
        close = close,
        retry = retry,
        version = version,
        status = status,
    )

    override fun equals(other: Any?): Boolean = other is SplashLabels && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SplashLabels(" +
        "starting=$starting, " +
        "close=$close, " +
        "retry=$retry, " +
        "version=$version, " +
        "status=$status)"

    private fun values(): List<Any?> = listOf(
        starting,
        close,
        retry,
        version,
        status,
    )
}
