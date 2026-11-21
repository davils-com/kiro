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

package com.davils.kiro.gallery.page

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.theme
import com.davils.kiro.ui.components.codeblock.CodeBlock
import com.davils.kiro.ui.components.codeblock.CodeBlockContent
import com.davils.kiro.ui.components.codeblock.CodeBlockCopyButton
import com.davils.kiro.ui.components.codeblock.CodeBlockHeader
import com.davils.kiro.ui.components.codeblock.CodeBlockTitle
import com.davils.kiro.ui.components.collapsible.Collapsible
import com.davils.kiro.ui.components.collapsible.CollapsibleContent
import com.davils.kiro.ui.components.collapsible.CollapsibleTrigger
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.lucide.ChevronRight
import com.davils.kiro.ui.icons.lucide.Lucide

private const val QUARTER_TURN: Float = 90f
private const val SHOW: String = "Show code"
private const val HIDE: String = "Hide code"

@Composable
internal fun DemoPage(demo: GalleryDemo) {
    val typography = theme.typography
    val title = TextStyle(
        fontFamily = typography.families.heading,
        fontSize = typography.scale.xl3.fontSize,
        fontWeight = FontWeight.SemiBold,
    )
    val isTitled = demo.sections.size > 1
    Column(modifier = Modifier.widthIn(max = 960.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Text(text = demo.name, style = title)
        demo.sections.forEach { section ->
            key(section) { DemoSectionView(section = section, isTitled = isTitled) }
        }
    }
}

@Composable
private fun DemoSectionView(section: DemoSection, isTitled: Boolean) {
    val code by produceState(initialValue = "", section) { value = section.source() }
    var isCodeShown by remember(section) { mutableStateOf(false) }
    val colors = theme.colors
    val typography = theme.typography
    val heading = TextStyle(
        fontFamily = typography.families.heading,
        fontSize = typography.scale.xl.fontSize,
        fontWeight = FontWeight.SemiBold,
    )
    val frame = Modifier.surface(
        shape = RoundedCornerShape(theme.radii.xl),
        color = colors.background,
        border = Border(width = 1.dp, color = colors.border),
    )
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (isTitled) {
            Text(text = section.name, style = heading)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 320.dp)
                .then(frame)
                .padding(40.dp),
            contentAlignment = Alignment.Center,
        ) {
            section.content()
        }
        DemoSource(file = section.file, code = code, isShown = isCodeShown) { shown -> isCodeShown = shown }
    }
}

@Composable
private fun DemoSource(file: String, code: String, isShown: Boolean, onShownChange: (Boolean) -> Unit) {
    val turn = animateFloatAsState(targetValue = QUARTER_TURN.takeIf { isShown } ?: 0f)
    Collapsible(expanded = isShown, onExpandedChange = onShownChange, modifier = Modifier.fillMaxWidth()) {
        CodeBlock(modifier = Modifier.fillMaxWidth()) {
            CodeBlockHeader {
                CollapsibleTrigger(modifier = Modifier.weight(1f)) {
                    Icon(
                        icon = Lucide.ChevronRight,
                        contentDescription = null,
                        modifier = Modifier.graphicsLayer { rotationZ = turn.value },
                    )
                    CodeBlockTitle(text = file)
                    Text(text = HIDE.takeIf { isShown } ?: SHOW, modifier = Modifier.padding(start = 8.dp))
                }
                CodeBlockCopyButton(value = code)
            }
            CollapsibleContent { CodeBlockContent(code = code) }
        }
    }
}
