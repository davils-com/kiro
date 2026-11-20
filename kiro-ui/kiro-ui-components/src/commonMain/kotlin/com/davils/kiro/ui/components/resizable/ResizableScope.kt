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

package com.davils.kiro.ui.components.resizable

import androidx.compose.runtime.Composable

/**
 * The scope in which the panels of a [ResizablePanelGroup] are declared.
 *
 * @since 1.0.0
 */
public interface ResizableScope {
    /**
     * Adds a panel to the end of the group.
     *
     * @param minSize the smallest size of the panel, in percent.
     * @param maxSize the largest size of the panel, in percent.
     * @param isCollapsible whether [ResizableState.collapse] can shrink the panel to [collapsedSize].
     * @param collapsedSize the size of the collapsed panel, in percent.
     * @param content the content of the panel, clipped to its bounds.
     * @since 1.0.0
     */
    public fun panel(
        minSize: Float = 0f,
        maxSize: Float = 100f,
        isCollapsible: Boolean = false,
        collapsedSize: Float = 0f,
        content: @Composable () -> Unit,
    )
}
