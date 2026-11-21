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

package com.davils.kiro.ui.motion.art.particle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware

private const val LINES: Int = 260
private const val STEPS: Int = 140
private const val TRAIL: Int = 60
private const val ALPHA_LIGHT: Float = 0.7f
private const val ALPHA_DARK: Float = 0.9f
private const val FADE: Float = 1f
private const val OCTAVES: Int = 2
private const val TURNS: Float = 1.25f
private const val EVOLUTION: Int = 240
private const val LIFE: Int = 48
private const val POINTER_STRENGTH: Float = 0.6f

/**
 * Derives the style of a [FlowFieldArt] from the tokens of a theme.
 *
 * Register an implementation under [FlowFieldArtStyleKey] in the components of a theme to change how every flow field
 * art
 * looks.
 *
 * @since 1.0.0
 */
public fun interface FlowFieldArtStyles {
    /**
     * Returns the style of a flow field art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the flow field art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): FlowFieldArtStyle

    /**
     * Holds the default [FlowFieldArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a flow field art.
         *
         * @since 1.0.0
         */
        public val Default: FlowFieldArtStyles = FlowFieldArtStyles(::defaultFlowFieldArtStyle)
    }
}

/**
 * The key of the styles of a [FlowFieldArt] in the components of a theme, with [FlowFieldArtStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val FlowFieldArtStyleKey: StyleKey<FlowFieldArtStyles> =
    StyleKey(name = "flow-field-art") { _ -> FlowFieldArtStyles.Default }

/**
 * Returns the style of a [FlowFieldArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the flow field art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun flowFieldArtStyle(): FlowFieldArtStyle = FlowFieldArtStyleKey.current.resolve(Theme.current)

internal fun defaultFlowFieldArtStyle(theme: Theme): FlowFieldArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return FlowFieldArtStyle(
        colors = null,
        lines = LINES,
        segment = spacing(steps = 0.75f),
        steps = STEPS,
        trail = TRAIL,
        lineWidth = spacing(steps = 0.25f),
        alpha = theme.darkAware(light = ALPHA_LIGHT, dark = ALPHA_DARK),
        fade = FADE,
        kind = FlowFieldKind.Curl,
        scale = spacing(steps = 90f),
        octaves = OCTAVES,
        turns = TURNS,
        evolution = long * EVOLUTION,
        life = long * LIFE,
        colorMode = FlowColorMode.Position,
        pointerStrength = POINTER_STRENGTH,
        pointerRadius = spacing(steps = 30f),
    )
}
