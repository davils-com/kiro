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
 * One step of the start of an application, such as reading the configuration.
 *
 * @property id the identifier of the step, unique within a [Splash].
 * @property label the text a splash screen shows for the step.
 * @property status how far the step is.
 * @since 1.0.0
 */
@Immutable
public class SplashStep(
    public val id: String,
    public val label: String,
    public val status: SplashStepStatus,
) {
    /**
     * Returns a copy of this [SplashStep] with the given values replaced and every other value kept.
     *
     * @param id the new value of [id].
     * @param label the new value of [label].
     * @param status the new value of [status].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        id: String = this.id,
        label: String = this.label,
        status: SplashStepStatus = this.status,
    ): SplashStep = SplashStep(
        id = id,
        label = label,
        status = status,
    )

    override fun equals(other: Any?): Boolean = other is SplashStep && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SplashStep(" +
        "id=$id, " +
        "label=$label, " +
        "status=$status)"

    private fun values(): List<Any?> = listOf(
        id,
        label,
        status,
    )
}
