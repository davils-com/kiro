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

/**
 * The scope of the content of a [Collapsible], which holds its [CollapsibleTrigger] and its [CollapsibleContent].
 *
 * @since 1.0.0
 */
public interface CollapsibleScope {
    /**
     * Whether the content of the collapsible is shown.
     *
     * @since 1.0.0
     */
    public val isExpanded: Boolean

    /**
     * Asks the collapsible to show its content if it is hidden and to hide it if it is shown, unless the collapsible
     * is disabled.
     *
     * @since 1.0.0
     */
    public fun toggle()
}
