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
 * What a splash screen shows while an application starts: its name and version, the steps of the start and,
 * once the start failed, why.
 *
 * @property name the name of the application.
 * @property version the version of the application, or `null` to show none.
 * @property steps the steps of the start in the order they run.
 * @property subtitle a line that describes the application, or `null` for none.
 * @property edition the edition of the application, such as `Community`, or `null` for none.
 * @property vendor the name of the vendor of the application, or `null` for none.
 * @property copyright the copyright notice, or `null` for none.
 * @property failure why the start failed, or `null` while it has not failed.
 * @property progress how far the start is as a fraction from `0` to `1`, or `null` to derive it from [steps].
 * @since 1.0.0
 */
@Immutable
public class Splash(
    public val name: String,
    public val version: String? = null,
    public val steps: List<SplashStep> = emptyList(),
    public val subtitle: String? = null,
    public val edition: String? = null,
    public val vendor: String? = null,
    public val copyright: String? = null,
    public val failure: String? = null,
    public val progress: Float? = null,
) {
    /**
     * The step a splash screen names: the first failed step, else the first running step, else the last finished one,
     * or `null` before any step ran.
     *
     * @since 1.0.0
     */
    public val current: SplashStep?
        get() = steps.firstOrNull { step -> step.status == SplashStepStatus.Failed }
            ?: steps.firstOrNull { step -> step.status == SplashStepStatus.Running }
            ?: steps.lastOrNull { step -> step.status == SplashStepStatus.Done }

    /**
     * Whether the start failed, which is when [failure] is set.
     *
     * @since 1.0.0
     */
    public val isFailed: Boolean
        get() = failure != null

    /**
     * How far the start is as a fraction from `0` to `1`: [progress] when it is set, else the share of the [steps]
     * that are done or skipped, or `null` when there are neither.
     *
     * @since 1.0.0
     */
    public val fraction: Float?
        get() {
            val explicit = progress
            if (explicit != null) return explicit.coerceIn(minimumValue = 0f, maximumValue = 1f)
            if (steps.isEmpty()) return null
            val finished = steps.count { step -> step.status.isFinished }
            return finished.toFloat() / steps.size
        }

    /**
     * Returns a copy of this [Splash] with the given values replaced and every other value kept.
     *
     * @param name the new value of [name].
     * @param version the new value of [version].
     * @param steps the new value of [steps].
     * @param subtitle the new value of [subtitle].
     * @param edition the new value of [edition].
     * @param vendor the new value of [vendor].
     * @param copyright the new value of [copyright].
     * @param failure the new value of [failure].
     * @param progress the new value of [progress].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        name: String = this.name,
        version: String? = this.version,
        steps: List<SplashStep> = this.steps,
        subtitle: String? = this.subtitle,
        edition: String? = this.edition,
        vendor: String? = this.vendor,
        copyright: String? = this.copyright,
        failure: String? = this.failure,
        progress: Float? = this.progress,
    ): Splash = Splash(
        name = name,
        version = version,
        steps = steps,
        subtitle = subtitle,
        edition = edition,
        vendor = vendor,
        copyright = copyright,
        failure = failure,
        progress = progress,
    )

    override fun equals(other: Any?): Boolean = other is Splash && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Splash(" +
        "name=$name, " +
        "version=${version ?: "null"}, " +
        "steps=$steps, " +
        "subtitle=${subtitle ?: "null"}, " +
        "edition=${edition ?: "null"}, " +
        "vendor=${vendor ?: "null"}, " +
        "copyright=${copyright ?: "null"}, " +
        "failure=${failure ?: "null"}, " +
        "progress=${progress ?: "null"})"

    private fun values(): List<Any?> = listOf(
        name,
        version,
        steps,
        subtitle,
        edition,
        vendor,
        copyright,
        failure,
        progress,
    )
}
