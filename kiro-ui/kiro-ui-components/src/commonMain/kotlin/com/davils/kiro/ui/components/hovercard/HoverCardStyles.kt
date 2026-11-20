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

package com.davils.kiro.ui.components.hovercard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.overlay.popoverPanel
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import kotlin.time.Duration.Companion.milliseconds

private const val OPEN_DELAY_MILLIS: Int = 600
private const val CLOSE_DELAY_MILLIS: Int = 300

public fun interface HoverCardStyles {
    public fun resolve(theme: Theme): HoverCardStyle

    public companion object {
        public val Default: HoverCardStyles = HoverCardStyles(::defaultHoverCardStyle)
    }
}

@Composable
@ReadOnlyComposable
public fun hoverCardStyle(): HoverCardStyle = HoverCardStyleKey.current.resolve(theme)

internal fun defaultHoverCardStyle(theme: Theme): HoverCardStyle = HoverCardStyle(
    panel = theme.popoverPanel(),
    width = 256.dp,
    textStyle = theme.textStyleOf(size = theme.typography.scale.sm),
    openDelay = OPEN_DELAY_MILLIS.milliseconds,
    closeDelay = CLOSE_DELAY_MILLIS.milliseconds,
)

public val HoverCardStyleKey: StyleKey<HoverCardStyles> = StyleKey(name = "hover-card") { _ -> HoverCardStyles.Default }
