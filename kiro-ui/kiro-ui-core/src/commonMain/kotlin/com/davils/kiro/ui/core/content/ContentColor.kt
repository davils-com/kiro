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

package com.davils.kiro.ui.core.content

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * The color of text and icons in the current content. A [com.davils.kiro.ui.core.ThemeProvider] sets it to the
 * [com.davils.kiro.ui.core.color.Colors.foreground] of its theme, and every surface sets it to the foreground of its
 * own style. Outside of a theme it is [Color.Unspecified], so that text falls back to the default of Compose.
 *
 * @since 1.0.0
 */
public val LocalContentColor: ProvidableCompositionLocal<Color> = compositionLocalOf { Color.Unspecified }
