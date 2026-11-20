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

package com.davils.kiro.ui.components.primitive.interaction

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.core.state.InteractionState
import com.davils.kiro.ui.core.state.resolve
import com.davils.kiro.ui.core.style.StateColors

@Immutable
public class ResolvedColors(
    public val container: Color,
    public val content: Color,
    public val border: Color,
    public val ring: Color,
) {
    /**
     * Returns a copy of this [ResolvedColors] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        container: Color = this.container,
        content: Color = this.content,
        border: Color = this.border,
        ring: Color = this.ring,
    ): ResolvedColors = ResolvedColors(
        container = container,
        content = content,
        border = border,
        ring = ring,
    )

    override fun equals(other: Any?): Boolean = other is ResolvedColors && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ResolvedColors(" +
        "container=$container, " +
        "content=$content, " +
        "border=$border, " +
        "ring=$ring)"

    private fun values(): List<Any?> = listOf(
        container,
        content,
        border,
        ring,
    )
}

@Composable
public fun StateColors.animate(state: InteractionState, animation: AnimationSpec<Color>): ResolvedColors {
    val container = animateColor(targetValue = container.resolve(state), animationSpec = animation)
    val content = animateColor(targetValue = content.resolve(state), animationSpec = animation)
    val border = animateColor(targetValue = border.resolve(state), animationSpec = animation)
    val ring = animateColor(targetValue = ring.resolve(state), animationSpec = animation)
    return ResolvedColors(
        container = container.value,
        content = content.value,
        border = border.value,
        ring = ring.value,
    )
}
