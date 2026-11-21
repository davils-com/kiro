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

package com.davils.kiro.ui.motion.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.art.ArtSeed
import com.davils.kiro.ui.motion.power.PowerPolicy
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.quality.QualityPolicy
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val FRAME_RATE: Int = 60
private const val HEADROOM: Float = 0.6f
private const val SMOOTHING: Float = 0.1f
private const val PATIENCE: Int = 45
private const val POINTER_SMOOTHING: Float = 0.18f
private const val SEED: Int = 0x6b69726f
private const val DIAGNOSTICS_INTERVAL: Int = 60

/**
 * Derives the style of a [MotionCanvas] from the tokens of a theme.
 *
 * Register an implementation under [MotionStyleKey] in the components of a theme to change how every motion canvas
 * plays.
 *
 * @since 1.0.0
 */
public fun interface MotionStyles {
    /**
     * Returns the style of a motion canvas for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the motion canvas.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): MotionStyle

    /**
     * Holds the default [MotionStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a motion canvas: animated unless the theme asks for reduced motion, at the ambient speed
         * of the theme, with an adaptive quality and paused while the window is unfocused or the canvas hidden.
         *
         * @since 1.0.0
         */
        public val Default: MotionStyles = MotionStyles(::defaultMotionStyle)
    }
}

/**
 * The key of the styles of a [MotionCanvas] in the components of a theme, with [MotionStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val MotionStyleKey: StyleKey<MotionStyles> = StyleKey(name = "motion") { _ -> MotionStyles.Default }

/**
 * Returns the style of a [MotionCanvas] in the current theme, with the overrides of its components.
 *
 * @return the style of the motion canvas.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun motionStyle(): MotionStyle = MotionStyleKey.current.resolve(Theme.current)

internal fun motionStyleOf(theme: Theme): MotionStyle = theme.components.resolve(MotionStyleKey, theme).resolve(theme)

internal fun defaultMotionStyle(theme: Theme): MotionStyle {
    val motion = theme.motion
    return MotionStyle(
        isAnimated = !motion.isReduced,
        speed = motion.ambient.speed,
        quality = QualityPolicy.Adaptive(
            budget = 1.seconds / FRAME_RATE,
            initial = QualityLevel.High,
            minimum = QualityLevel.Low,
            maximum = QualityLevel.High,
            headroom = HEADROOM,
            smoothing = SMOOTHING,
            patience = PATIENCE,
        ),
        power = PowerPolicy(),
        pointerSmoothing = POINTER_SMOOTHING,
        stillTime = Duration.ZERO,
        seed = ArtSeed(value = SEED),
        isInteractive = true,
        diagnosticsInterval = DIAGNOSTICS_INTERVAL,
    )
}
