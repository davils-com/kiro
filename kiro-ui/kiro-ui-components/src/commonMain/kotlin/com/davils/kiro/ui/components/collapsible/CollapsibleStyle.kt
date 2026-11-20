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

package com.davils.kiro.ui.components.collapsible

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Immutable

/**
 * The style of a [Collapsible]: how its content enters and exits.
 *
 * @property enter the transition that shows the content.
 * @property exit the transition that hides the content.
 * @since 1.0.0
 */
@Immutable
public class CollapsibleStyle(
    public val enter: EnterTransition,
    public val exit: ExitTransition,
) {
    /**
     * Returns a copy of this [CollapsibleStyle] with the given values replaced and every other value kept.
     *
     * @param enter the new value of [enter].
     * @param exit the new value of [exit].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(enter: EnterTransition = this.enter, exit: ExitTransition = this.exit): CollapsibleStyle =
        CollapsibleStyle(
            enter = enter,
            exit = exit,
        )

    override fun equals(other: Any?): Boolean = other is CollapsibleStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CollapsibleStyle(" +
        "enter=$enter, " +
        "exit=$exit)"

    private fun values(): List<Any?> = listOf(
        enter,
        exit,
    )
}
