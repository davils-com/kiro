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

package com.davils.kiro.ui.components.message

/**
 * The side of a [Message] that its avatar and its content are aligned to.
 *
 * @since 1.0.0
 */
public enum class MessageAlign {
    /**
     * The avatar at the start and the content aligned to the start, such as for messages of others.
     *
     * @since 1.0.0
     */
    Start,

    /**
     * The avatar at the end and the content aligned to the end, such as for messages of the user.
     *
     * @since 1.0.0
     */
    End,
}
