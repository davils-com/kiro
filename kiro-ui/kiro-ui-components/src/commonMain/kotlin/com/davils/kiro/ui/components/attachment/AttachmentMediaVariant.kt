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
 * What an [AttachmentMedia] shows, which decides how it reacts to the state of its attachment.
 *
 * @since 1.0.0
 */
public enum class AttachmentMediaVariant {
    /**
     * An icon, drawn in the content color of the attachment.
     *
     * @since 1.0.0
     */
    Icon,

    /**
     * An image, such as a preview of the file, dimmed while the attachment is uploading, processing or failed.
     *
     * @since 1.0.0
     */
    Image,
}
