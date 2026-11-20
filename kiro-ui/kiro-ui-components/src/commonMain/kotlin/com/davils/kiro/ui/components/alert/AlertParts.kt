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

package com.davils.kiro.ui.components.alert

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.text.Text

/**
 * Shows the title of an [Alert] in the title style of the alert.
 *
 * @param text the title.
 * @param modifier the modifier of the title.
 * @since 1.0.0
 */
@Composable
public fun AlertScope.AlertTitle(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = style.titleStyle)
}

/**
 * Shows the description of an [Alert] in the description style and color of the alert.
 *
 * @param text the description.
 * @param modifier the modifier of the description.
 * @since 1.0.0
 */
@Composable
public fun AlertScope.AlertDescription(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = style.descriptionStyle, color = style.descriptionColor)
}
