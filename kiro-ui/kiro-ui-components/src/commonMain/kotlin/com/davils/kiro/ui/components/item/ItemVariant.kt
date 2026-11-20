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

package com.davils.kiro.ui.components.item

/**
 * The look of an [Item], which picks its default style.
 *
 * The interface is open, so that a theme can add variants of its own through its [ItemStyles].
 *
 * @since 1.0.0
 */
public interface ItemVariant {
    /**
     * An item without background or border, which shows a background only while hovered.
     *
     * @since 1.0.0
     */
    public data object Default : ItemVariant

    /**
     * An item with a border.
     *
     * @since 1.0.0
     */
    public data object Outline : ItemVariant

    /**
     * An item on a muted background.
     *
     * @since 1.0.0
     */
    public data object Muted : ItemVariant
}
