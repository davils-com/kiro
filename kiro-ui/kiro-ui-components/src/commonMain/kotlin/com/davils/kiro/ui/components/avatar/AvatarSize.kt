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

package com.davils.kiro.ui.components.avatar

/**
 * The size of an avatar, which picks its default style.
 *
 * The interface is open, so that a theme can add sizes of its own through its [AvatarStyles].
 *
 * @since 1.0.0
 */
public interface AvatarSize {
    /**
     * The default size of an avatar.
     *
     * @since 1.0.0
     */
    public data object Default : AvatarSize

    /**
     * A small avatar, without an icon in its badge.
     *
     * @since 1.0.0
     */
    public data object Sm : AvatarSize

    /**
     * A large avatar.
     *
     * @since 1.0.0
     */
    public data object Lg : AvatarSize
}
