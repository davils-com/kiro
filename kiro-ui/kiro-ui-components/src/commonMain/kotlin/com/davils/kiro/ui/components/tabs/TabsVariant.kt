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

package com.davils.kiro.ui.components.tabs

/**
 * The variant of [Tabs], which sets their default style.
 *
 * A variant of your own needs a [TabsStyles] that knows it in [com.davils.kiro.ui.core.Theme.components].
 *
 * @since 1.0.0
 */
public interface TabsVariant {
    /**
     * Tabs on a muted list, where the selected tab is raised on its own container with a shadow.
     *
     * @since 1.0.0
     */
    public data object Default : TabsVariant

    /**
     * Tabs on a transparent list, where the selected tab is marked by a line.
     *
     * @since 1.0.0
     */
    public data object Line : TabsVariant
}
