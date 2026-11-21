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

package com.davils.kiro.ui.window.titlebar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A title bar on its own, without a window, such as for a panel that looks like a window or for a preview. In a
 * [com.davils.kiro.ui.window.ChromeWindow] the window draws its title bar itself.
 *
 * @param actions what the window buttons do.
 * @param modifier the modifier of the bar.
 * @param style the style of the bar.
 * @param content fills the start, center and end slots of the bar.
 * @since 1.0.0
 */
@Composable
public fun TitleBar(
    actions: WindowActions,
    modifier: Modifier = Modifier,
    style: TitleBarStyle = titleBarStyle(),
    content: TitleBarScope.() -> Unit,
) {
    val slots = TitleBarScopeImpl().apply(content).slots
    TitleBarLayout(slots = slots, actions = actions, style = style, modifier = modifier)
}
