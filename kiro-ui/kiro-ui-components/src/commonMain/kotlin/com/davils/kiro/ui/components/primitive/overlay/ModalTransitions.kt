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

package com.davils.kiro.ui.components.primitive.overlay

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Immutable

@Immutable
public class ModalTransitions(
    public val scrimEnter: EnterTransition,
    public val scrimExit: ExitTransition,
    public val panelEnter: EnterTransition,
    public val panelExit: ExitTransition,
) {
    public companion object {
        public val None: ModalTransitions = ModalTransitions(
            scrimEnter = EnterTransition.None,
            scrimExit = ExitTransition.None,
            panelEnter = EnterTransition.None,
            panelExit = ExitTransition.None,
        )
    }

    /**
     * Returns a copy of this [ModalTransitions] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        scrimEnter: EnterTransition = this.scrimEnter,
        scrimExit: ExitTransition = this.scrimExit,
        panelEnter: EnterTransition = this.panelEnter,
        panelExit: ExitTransition = this.panelExit,
    ): ModalTransitions = ModalTransitions(
        scrimEnter = scrimEnter,
        scrimExit = scrimExit,
        panelEnter = panelEnter,
        panelExit = panelExit,
    )

    override fun equals(other: Any?): Boolean = other is ModalTransitions && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ModalTransitions(" +
        "scrimEnter=$scrimEnter, " +
        "scrimExit=$scrimExit, " +
        "panelEnter=$panelEnter, " +
        "panelExit=$panelExit)"

    private fun values(): List<Any?> = listOf(
        scrimEnter,
        scrimExit,
        panelEnter,
        panelExit,
    )
}
