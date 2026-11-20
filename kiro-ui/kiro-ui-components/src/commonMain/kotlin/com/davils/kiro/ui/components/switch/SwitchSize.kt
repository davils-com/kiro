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

package com.davils.kiro.ui.components.switch

/**
 * The size of a [Switch]: the size of its track and of its thumb.
 *
 * A custom size can implement this interface when its [SwitchStyles] resolves it.
 *
 * @since 1.0.0
 */
public interface SwitchSize {
    /**
     * The regular size of a switch.
     *
     * @since 1.0.0
     */
    public data object Default : SwitchSize

    /**
     * The small size of a switch.
     *
     * @since 1.0.0
     */
    public data object Sm : SwitchSize
}
