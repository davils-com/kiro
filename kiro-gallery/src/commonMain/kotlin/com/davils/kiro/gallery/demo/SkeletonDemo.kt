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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.skeleton.Skeleton
import com.davils.kiro.ui.components.skeleton.SkeletonAnimation
import com.davils.kiro.ui.components.skeleton.SkeletonAvatar
import com.davils.kiro.ui.components.skeleton.SkeletonButton
import com.davils.kiro.ui.components.skeleton.SkeletonCard
import com.davils.kiro.ui.components.skeleton.SkeletonListItem
import com.davils.kiro.ui.components.skeleton.SkeletonShape
import com.davils.kiro.ui.components.skeleton.SkeletonTable
import com.davils.kiro.ui.components.skeleton.SkeletonText
import com.davils.kiro.ui.components.skeleton.skeletonStyle
import com.davils.kiro.ui.components.text.Text

private val shapes: List<SkeletonShape> = listOf(
    SkeletonShape.Rectangle,
    SkeletonShape.Rounded,
    SkeletonShape.Pill,
    SkeletonShape.Circle,
)

@Composable
internal fun SkeletonDemo() {
    var animation by remember { mutableStateOf(SkeletonAnimation.Pulse) }
    val style = skeletonStyle().copy(animation = animation)
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SkeletonAnimation.entries.forEach { option ->
                val variant = ButtonVariant.Secondary.takeIf { option == animation } ?: ButtonVariant.Outline
                Button(
                    onClick = { animation = option },
                    variant = variant,
                    size = ButtonSize.Sm,
                ) { Text(option.name) }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            shapes.forEach { shape ->
                Skeleton(
                    modifier = Modifier.size(width = 64.dp, height = 40.dp),
                    shape = shape,
                    style = style,
                )
            }
            SkeletonAvatar(style = style)
            SkeletonButton(style = style)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Column(modifier = Modifier.width(280.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                SkeletonText(style = style)
                SkeletonListItem(style = style)
                SkeletonListItem(style = style)
                SkeletonTable(rows = 3, columns = 3, style = style)
            }
            SkeletonCard(modifier = Modifier.width(280.dp), style = style)
        }
    }
}
