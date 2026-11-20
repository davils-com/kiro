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

package com.davils.kiro.ui.components.attachment

/**
 * The size of an [Attachment], which picks its paddings, gaps, media size, radii and text size.
 *
 * The default style knows the sizes declared here; a size of your own needs [AttachmentStyles] that resolve it.
 *
 * @since 1.0.0
 */
public interface AttachmentSize {
    /**
     * The regular size, with small text and 40 dp media.
     *
     * @since 1.0.0
     */
    public data object Default : AttachmentSize

    /**
     * A smaller size, with extra small text and 32 dp media.
     *
     * @since 1.0.0
     */
    public data object Sm : AttachmentSize

    /**
     * The smallest size, with extra small text, 28 dp media and smaller radii.
     *
     * @since 1.0.0
     */
    public data object Xs : AttachmentSize
}
