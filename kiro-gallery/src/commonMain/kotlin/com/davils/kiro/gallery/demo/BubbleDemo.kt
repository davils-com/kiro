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
import com.davils.kiro.ui.components.bubble.Bubble
import com.davils.kiro.ui.components.bubble.BubbleContent
import com.davils.kiro.ui.components.bubble.BubbleGroup
import com.davils.kiro.ui.components.bubble.BubbleReactions
import com.davils.kiro.ui.components.bubble.BubbleVariant
import com.davils.kiro.ui.components.message.MessageAlign
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun BubbleDemo() {
    BubbleGroup(modifier = Modifier.width(420.dp)) {
        Bubble(variant = BubbleVariant.Muted) { BubbleContent { Text("Which ports are open on core-01?") } }
        Bubble(align = MessageAlign.End) {
            BubbleContent { Text("Ports 22, 443 and 8443 are open.") }
            BubbleReactions { Text("+1") }
        }
        Bubble(variant = BubbleVariant.Tinted) { BubbleContent { Text("Thanks, that matches the audit.") } }
    }
}
