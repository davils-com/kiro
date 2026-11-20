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

package com.davils.kiro.ui.components.buttongroup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

public fun interface ButtonGroupStyles {
    public fun resolve(theme: Theme): ButtonGroupStyle

    public companion object {
        public val Default: ButtonGroupStyles = ButtonGroupStyles(::defaultButtonGroupStyle)
    }
}

@Composable
@ReadOnlyComposable
public fun buttonGroupStyle(): ButtonGroupStyle = ButtonGroupStyleKey.current.resolve(theme)

public val ButtonGroupStyleKey: StyleKey<ButtonGroupStyles> = StyleKey(
    name = "button-group",
) { _ -> ButtonGroupStyles.Default }
