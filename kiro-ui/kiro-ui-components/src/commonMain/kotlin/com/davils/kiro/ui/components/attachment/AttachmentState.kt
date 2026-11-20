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
 * The state of the file behind an [Attachment], which decides its outline, colors and the animation of its title.
 *
 * @since 1.0.0
 */
public enum class AttachmentState {
    /**
     * No file is chosen yet; the attachment draws a dashed outline.
     *
     * @since 1.0.0
     */
    Idle,

    /**
     * The file is uploading; the title shimmers unless motion is reduced and an image media is dimmed.
     *
     * @since 1.0.0
     */
    Uploading,

    /**
     * The file is uploaded and being processed; the title shimmers unless motion is reduced and an image media is
     * dimmed.
     *
     * @since 1.0.0
     */
    Processing,

    /**
     * The upload or the processing failed; the outline, the media and the description use the destructive colors.
     *
     * @since 1.0.0
     */
    Error,

    /**
     * The file is ready; the attachment shows its regular colors.
     *
     * @since 1.0.0
     */
    Done,
}
