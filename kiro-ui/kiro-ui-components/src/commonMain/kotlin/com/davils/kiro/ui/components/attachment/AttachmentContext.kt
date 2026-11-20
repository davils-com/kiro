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

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.davils.kiro.ui.components.Orientation

internal val LocalAttachment = staticCompositionLocalOf<AttachmentContext?> { null }

@Stable
internal class AttachmentContext(
    val state: AttachmentState,
    val orientation: Orientation,
    val style: AttachmentStyle,
) {
    var hasMedia: Boolean by mutableStateOf(false)

    var hasContent: Boolean by mutableStateOf(false)
}
