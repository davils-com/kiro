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

package com.davils.kiro.gallery.demo

import androidx.compose.runtime.Composable
import com.davils.kiro.ui.components.attachment.Attachment
import com.davils.kiro.ui.components.attachment.AttachmentContent
import com.davils.kiro.ui.components.attachment.AttachmentDescription
import com.davils.kiro.ui.components.attachment.AttachmentGroup
import com.davils.kiro.ui.components.attachment.AttachmentMedia
import com.davils.kiro.ui.components.attachment.AttachmentState
import com.davils.kiro.ui.components.attachment.AttachmentTitle
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.lucide.FileText
import com.davils.kiro.ui.icons.lucide.Lucide

@Composable
internal fun AttachmentDemo() {
    AttachmentGroup {
        Attachment {
            AttachmentMedia { Icon(icon = Lucide.FileText, contentDescription = null) }
            AttachmentContent {
                AttachmentTitle(text = "inventory.pdf")
                AttachmentDescription(text = "2.4 MB")
            }
        }
        Attachment(state = AttachmentState.Uploading) {
            AttachmentMedia { Icon(icon = Lucide.FileText, contentDescription = null) }
            AttachmentContent {
                AttachmentTitle(text = "topology.svg")
                AttachmentDescription(text = "Uploading")
            }
        }
        Attachment(state = AttachmentState.Error) {
            AttachmentContent {
                AttachmentTitle(text = "backup.zip")
                AttachmentDescription(text = "Upload failed")
            }
        }
    }
}
