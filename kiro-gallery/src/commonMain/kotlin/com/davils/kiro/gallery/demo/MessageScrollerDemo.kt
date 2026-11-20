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

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.bubble.Bubble
import com.davils.kiro.ui.components.bubble.BubbleContent
import com.davils.kiro.ui.components.message.Message
import com.davils.kiro.ui.components.message.MessageAlign
import com.davils.kiro.ui.components.messagescroller.MessageScroller
import com.davils.kiro.ui.components.text.Text

private const val MESSAGES: Int = 12

@Composable
internal fun MessageScrollerDemo() {
    MessageScroller(modifier = Modifier.size(width = 420.dp, height = 260.dp)) {
        repeat(MESSAGES) { index ->
            val align = MessageAlign.End.takeIf { index % 2 == 1 } ?: MessageAlign.Start
            Message(align = align) { Bubble { BubbleContent { Text("Message ${index + 1}") } } }
        }
    }
}
