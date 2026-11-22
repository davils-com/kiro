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

package com.davils.kiro.ui.motion.scene

import androidx.compose.ui.geometry.Offset
import com.davils.kiro.ui.motion.art.field.ContourArt
import com.davils.kiro.ui.motion.art.field.HalftoneArt
import com.davils.kiro.ui.motion.art.field.RidgeArt
import com.davils.kiro.ui.motion.art.geometry.IsometricCityArt
import com.davils.kiro.ui.motion.art.geometry.OrbitArt
import com.davils.kiro.ui.motion.art.particle.FlowFieldArt
import com.davils.kiro.ui.motion.art.tiling.BauhausArt
import com.davils.kiro.ui.motion.art.tiling.TruchetArt
import com.davils.kiro.ui.motion.mask.Mask
import com.davils.kiro.ui.motion.mask.MaskStop

private const val SHOWN: Float = 1f
private const val HIDDEN: Float = 0f
private const val DOWN: Float = 90f
private const val UP: Float = 270f
private const val FADE_START: Float = 0.45f
private const val EDGE_FADE: Float = 0.7f
private const val VIGNETTE_STRENGTH: Float = 0.55f
private const val VIGNETTE_EXTENT: Float = 0.55f
private const val HALF: Float = 0.5f
private const val CENTER_RADIUS: Float = 0.75f

private val fadeTowardsTop: Mask = Mask.Linear(
    angle = UP,
    stops = listOf(MaskStop(position = 0f, alpha = SHOWN), MaskStop(position = 1f, alpha = HIDDEN)),
)

private val fadeTowardsBottom: Mask = Mask.Linear(
    angle = DOWN,
    stops = listOf(MaskStop(position = FADE_START, alpha = SHOWN), MaskStop(position = 1f, alpha = HIDDEN)),
)

private val softEdges: Mask = Mask.Radial(
    center = Offset(x = HALF, y = HALF),
    radius = CENTER_RADIUS,
    stops = listOf(MaskStop(position = EDGE_FADE, alpha = SHOWN), MaskStop(position = 1f, alpha = HIDDEN)),
)

/**
 * Scenes Kiro brings along, calm enough to sit behind an interface: a splash screen, a sign-in form or an empty
 * state. Every art takes its style from the theme, so a theme changes the presets too.
 *
 * @since 1.0.0
 */
public object ScenePresets {
    /**
     * An isometric city whose blocks rise and fall in a slow wave, fading towards the top.
     *
     * @since 1.0.0
     */
    public val Skyline: Scene = scene { layer(IsometricCityArt()) { mask = fadeTowardsTop } }

    /**
     * Dashed orbits around a calm center, made for a logo in the middle.
     *
     * @since 1.0.0
     */
    public val Orbits: Scene = scene { layer(OrbitArt()) }

    /**
     * Fine lines that rise into ridges like the plot of a signal.
     *
     * @since 1.0.0
     */
    public val Signal: Scene = scene { layer(RidgeArt()) }

    /**
     * The quiet contour lines of a slowly drifting landscape.
     *
     * @since 1.0.0
     */
    public val Topography: Scene = scene { layer(ContourArt()) { mask = softEdges } }

    /**
     * A field of tiles whose arcs join into winding paths that keep rearranging.
     *
     * @since 1.0.0
     */
    public val Maze: Scene = scene {
        mask = Mask.Vignette(strength = VIGNETTE_STRENGTH, extent = VIGNETTE_EXTENT)
        layer(TruchetArt())
    }

    /**
     * A grid of tiles with bold geometric shapes that turn now and then.
     *
     * @since 1.0.0
     */
    public val Composition: Scene = scene { layer(BauhausArt()) }

    /**
     * Halftone dots that swell and shrink with slow interfering waves, fading towards the bottom.
     *
     * @since 1.0.0
     */
    public val Halftone: Scene = scene { layer(HalftoneArt()) { mask = fadeTowardsBottom } }

    /**
     * Thin streamlines drifting through a gentle current.
     *
     * @since 1.0.0
     */
    public val Flow: Scene = scene { layer(FlowFieldArt()) { mask = softEdges } }

    /**
     * Every preset, for a picker.
     *
     * @since 1.0.0
     */
    public val all: List<Scene>
        get() = listOf(Skyline, Orbits, Signal, Topography, Maze, Composition, Halftone, Flow)
}
