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

package com.davils.kiro.ui.components.messagescroller

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * The scroll position of a [MessageScroller], which tells whether its start or its end is reached and scrolls to
 * either. Create it with [rememberMessageScrollerState].
 *
 * @since 1.0.0
 */
@Stable
public class MessageScrollerState internal constructor(internal val scroll: ScrollState) {
    internal var isFollowing: Boolean by mutableStateOf(true)

    internal var isAutoScrolling: Boolean = false
        private set

    /**
     * Whether the end of the content is shown, or the scroller has not been laid out yet.
     *
     * @since 1.0.0
     */
    public val isAtEnd: Boolean
        get() = scroll.viewportSize == 0 || !scroll.canScrollForward

    /**
     * Whether the start of the content is shown.
     *
     * @since 1.0.0
     */
    public val isAtStart: Boolean
        get() = !scroll.canScrollBackward

    /**
     * Scrolls to the end of the content with an animation and suspends until it is there.
     *
     * @since 1.0.0
     */
    public suspend fun scrollToEnd() {
        scroll.animateScrollTo(scroll.maxValue)
    }

    /**
     * Scrolls to the start of the content with an animation and suspends until it is there.
     *
     * @since 1.0.0
     */
    public suspend fun scrollToStart() {
        scroll.animateScrollTo(0)
    }

    internal suspend fun follow(target: Int) {
        isAutoScrolling = true
        try {
            scroll.animateScrollTo(target)
        } finally {
            isAutoScrolling = false
        }
    }
}

/**
 * Creates and remembers a [MessageScrollerState] at the start of its content, which follows the end once content
 * arrives.
 *
 * @return the remembered state.
 * @since 1.0.0
 */
@Composable
public fun rememberMessageScrollerState(): MessageScrollerState {
    val scroll = rememberScrollState()
    return remember(scroll) { MessageScrollerState(scroll) }
}
