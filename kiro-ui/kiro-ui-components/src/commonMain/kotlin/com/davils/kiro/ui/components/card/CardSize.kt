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

package com.davils.kiro.ui.components.card

/**
 * The size of a [Card], which sets the spacing and the title size of its default style.
 *
 * A size of your own needs a [CardStyles] that knows it in [com.davils.kiro.ui.core.Theme.components].
 *
 * @since 1.0.0
 */
public interface CardSize {
    /**
     * The default size, with a wide spacing and a base title.
     *
     * @since 1.0.0
     */
    public data object Default : CardSize

    /**
     * The small size, with a narrower spacing and a small title.
     *
     * @since 1.0.0
     */
    public data object Sm : CardSize
}
