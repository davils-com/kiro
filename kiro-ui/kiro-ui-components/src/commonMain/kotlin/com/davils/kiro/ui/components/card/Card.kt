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

package com.davils.kiro.ui.components.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle

@Composable
public fun Card(
    modifier: Modifier = Modifier,
    size: CardSize = CardSize.Default,
    style: CardStyle = cardStyle(size),
    content: @Composable CardScope.() -> Unit,
) {
    val surface = Modifier.surface(shape = style.shape, color = style.container, ring = style.ring)
    val policy = remember(style.spacing) { CardMeasurePolicy(spacing = style.spacing) }
    CompositionLocalProvider(LocalContentColor provides style.content, LocalTextStyle provides style.textStyle) {
        Layout(
            content = { CardScopeImpl(style = style).content() },
            modifier = modifier
                .fillMaxWidth()
                .then(surface)
                .clip(style.shape),
            measurePolicy = policy,
        )
    }
}
