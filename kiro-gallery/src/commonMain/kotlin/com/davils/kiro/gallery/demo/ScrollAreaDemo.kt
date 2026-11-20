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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.theme
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.label.Label
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.scrollarea.LazyScrollArea
import com.davils.kiro.ui.components.scrollarea.ScrollArea
import com.davils.kiro.ui.components.scrollarea.ScrollDirection
import com.davils.kiro.ui.components.separator.Separator
import com.davils.kiro.ui.components.text.Text

private const val TAGS: Int = 30
private const val ARTWORKS: Int = 12
private const val PORTS: Int = 48
private const val COLUMNS: Int = 8
private const val ROWS: Int = 1000

@Composable
internal fun ScrollAreaDemo() {
    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Label(text = "Vertical")
            ScrollArea(modifier = Modifier.size(width = 200.dp, height = 240.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    repeat(TAGS) { index ->
                        Text("v1.2.0-beta.${TAGS - index}")
                        Separator(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
            Label(text = "Lazy, $ROWS rows")
            LazyScrollArea(modifier = Modifier.size(width = 200.dp, height = 160.dp)) {
                items(count = ROWS) { row -> Text("Interface Gi1/0/${row + 1}", modifier = Modifier.padding(8.dp)) }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Label(text = "Horizontal")
            ScrollArea(modifier = Modifier.width(360.dp), direction = ScrollDirection.Horizontal) {
                Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    repeat(ARTWORKS) { index -> Artwork(index) }
                }
            }
            Label(text = "Both")
            ScrollArea(
                modifier = Modifier.size(width = 360.dp, height = 200.dp),
                direction = ScrollDirection.Both,
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    repeat(PORTS) { port ->
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            repeat(COLUMNS) { column -> Text("port ${port + 1} · lane ${column + 1}") }
                        }
                    }
                }
            }
            Label(text = "Lazy row")
            LazyScrollArea(modifier = Modifier.width(360.dp), orientation = Orientation.Horizontal) {
                items(count = ROWS) { index -> Text("VLAN ${index + 1}", modifier = Modifier.padding(12.dp)) }
            }
        }
    }
}

@Composable
private fun Artwork(index: Int) {
    val colors = theme.colors
    Box(
        modifier = Modifier
            .size(width = 120.dp, height = 90.dp)
            .surface(shape = RoundedCornerShape(theme.radii.md), color = colors.muted),
        contentAlignment = Alignment.Center,
    ) {
        Text("Rack ${index + 1}", color = colors.mutedForeground)
    }
}
