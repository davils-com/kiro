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

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.avatar.Avatar
import com.davils.kiro.ui.components.bubble.Bubble
import com.davils.kiro.ui.components.bubble.BubbleContent
import com.davils.kiro.ui.components.message.Message
import com.davils.kiro.ui.components.message.MessageAlign
import com.davils.kiro.ui.components.message.MessageFooter
import com.davils.kiro.ui.components.message.MessageGroup
import com.davils.kiro.ui.components.message.MessageHeader
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun MessageDemo() {
    MessageGroup(modifier = Modifier.width(480.dp)) {
        Message(avatar = { Avatar { Text("AI") } }) {
            MessageHeader { Text("Assistant") }
            Bubble { BubbleContent { Text("The audit found three open ports on core-01.") } }
        }
        Message(align = MessageAlign.End, avatar = { Avatar { Text("NJ") } }) {
            Bubble { BubbleContent { Text("Close everything except 22.") } }
            MessageFooter { Text("Read") }
        }
    }
}
