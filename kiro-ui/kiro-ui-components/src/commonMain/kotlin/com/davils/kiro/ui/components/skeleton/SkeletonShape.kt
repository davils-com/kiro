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
 * The shape of a [Skeleton].
 *
 * @since 1.0.0
 */
public sealed interface SkeletonShape {
    /**
     * A rectangle with square corners.
     *
     * @since 1.0.0
     */
    public data object Rectangle : SkeletonShape

    /**
     * A rectangle with the corner radius of the style.
     *
     * @since 1.0.0
     */
    public data object Rounded : SkeletonShape

    /**
     * A rectangle with fully rounded ends, as for a line of text.
     *
     * @since 1.0.0
     */
    public data object Pill : SkeletonShape

    /**
     * A circle, as for an avatar; on a block that is not square it has fully rounded ends like [SkeletonShape.Pill].
     *
     * @since 1.0.0
     */
    public data object Circle : SkeletonShape
}
