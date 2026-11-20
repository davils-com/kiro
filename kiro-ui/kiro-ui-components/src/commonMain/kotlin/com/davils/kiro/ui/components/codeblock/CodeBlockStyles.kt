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

package com.davils.kiro.ui.components.codeblock

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.primitive.darkAware
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.Check
import com.davils.kiro.ui.icons.lucide.Copy
import com.davils.kiro.ui.icons.lucide.Lucide
import kotlin.time.Duration.Companion.seconds

private const val DARK_FILL: Float = 0.3f
private const val HALF: Float = 0.5f
private const val SOFT: Float = 0.1f
private const val BORDER_LIGHT: Float = 0.3f
private const val BORDER_DARK: Float = 0.4f
private const val COPIED_SECONDS: Int = 2

private val known: Set<CodeBlockVariant> = setOf(CodeBlockVariant.Default, CodeBlockVariant.Destructive)
private val sizes: Set<CodeBlockSize> = setOf(CodeBlockSize.Default, CodeBlockSize.Sm)

public fun interface CodeBlockStyles {
    public fun resolve(theme: Theme, variant: CodeBlockVariant, size: CodeBlockSize): CodeBlockStyle

    public companion object {
        public val Default: CodeBlockStyles = CodeBlockStyles(::defaultCodeBlockStyle)
    }
}

@Composable
@ReadOnlyComposable
public fun codeBlockStyle(variant: CodeBlockVariant, size: CodeBlockSize): CodeBlockStyle =
    CodeBlockStyleKey.current.resolve(theme, variant, size)

internal fun defaultCodeBlockStyle(theme: Theme, variant: CodeBlockVariant, size: CodeBlockSize): CodeBlockStyle {
    require(variant in known) { "No default style for the code block variant $variant." }
    require(size in sizes) { "No default style for the code block size $size." }
    val colors = theme.colors
    val isDestructive = variant == CodeBlockVariant.Destructive
    val isSmall = size == CodeBlockSize.Sm
    val scale = theme.typography.scale
    val text = scale.xs.takeIf { isSmall } ?: scale.sm
    val destructiveBorder = colors.destructive.copy(alpha = theme.darkAware(light = BORDER_LIGHT, dark = BORDER_DARK))
    return CodeBlockStyle(
        shape = RoundedCornerShape(theme.radii.lg),
        border = Border(width = 1.dp, color = destructiveBorder.takeIf { isDestructive } ?: colors.input),
        focusBorder = colors.ring,
        ring = Border(width = theme.focusRing.width, color = colors.ring.copy(alpha = HALF)),
        container = Color.Transparent.takeIf { isDestructive } ?: colors.input.copy(
            alpha = theme.darkAware(0f, DARK_FILL),
        ),
        content = colors.destructive.takeIf { isDestructive } ?: colors.foreground,
        muted = colors.destructive.takeIf { isDestructive } ?: colors.mutedForeground,
        titleStyle = theme.textStyleOf(size = text, weight = FontWeight.Medium),
        codeStyle = theme.textStyleOf(
            size = text,
            lineHeight = theme.typography.leading.relaxed,
            family = theme.typography.families.mono,
        ),
        padding = 10.dp.takeIf { isSmall } ?: 12.dp,
        sectionGap = 6.dp.takeIf { isSmall } ?: 8.dp,
        gap = 8.dp,
        gutterMinWidth = 24.dp,
        gutterGap = 12.dp,
        added = colors.muted,
        removed = colors.destructive.copy(alpha = SOFT),
        removedMarker = colors.destructive,
        highlighted = colors.muted,
        actionSurface = colors.background,
        actionRing = Border(width = 1.dp, color = colors.foreground.copy(alpha = SOFT)),
        iconSize = 14.dp,
        copyIcon = Lucide.Copy,
        copiedIcon = Lucide.Check,
        copyLabel = Messages.CodeBlock.copy,
        copiedLabel = Messages.CodeBlock.copied,
        copiedDuration = COPIED_SECONDS.seconds,
    )
}

public val CodeBlockStyleKey: StyleKey<CodeBlockStyles> = StyleKey(name = "code-block") { _ -> CodeBlockStyles.Default }
