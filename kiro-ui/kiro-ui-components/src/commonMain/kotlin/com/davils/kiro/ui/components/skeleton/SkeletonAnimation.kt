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

package com.davils.kiro.ui.components.skeleton

/**
 * The animation of a [Skeleton] while it is shown.
 *
 * @since 1.0.0
 */
public enum class SkeletonAnimation {
    /**
     * The skeleton fades to the dimmed opacity of its style and back.
     *
     * @since 1.0.0
     */
    Pulse,

    /**
     * A light band runs across the skeleton from the start to the end.
     *
     * @since 1.0.0
     */
    Shimmer,

    /**
     * The skeleton does not move.
     *
     * @since 1.0.0
     */
    None,
}
