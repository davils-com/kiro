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

package com.davils.kiro.ui.components.aspectratio

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Sizes its content to a fixed ratio of width to height.
 *
 * The box takes the width its constraints allow and derives its height from [ratio], or the other way round when only
 * the height is bounded.
 *
 * @param ratio the width divided by the height, greater than zero.
 * @param modifier the modifier of the box.
 * @param content the content, laid out in a box of the computed size.
 * @since 1.0.0
 */
@Composable
public fun AspectRatio(ratio: Float, modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(modifier = modifier.aspectRatio(ratio), content = content)
}
