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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.badge.Badge
import com.davils.kiro.ui.components.badge.BadgeVariant
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun BadgeDemo() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Badge { Text("Badge") }
        Badge(variant = BadgeVariant.Secondary) { Text("Secondary") }
        Badge(variant = BadgeVariant.Destructive) { Text("Destructive") }
        Badge(variant = BadgeVariant.Outline) { Text("Outline") }
    }
}
