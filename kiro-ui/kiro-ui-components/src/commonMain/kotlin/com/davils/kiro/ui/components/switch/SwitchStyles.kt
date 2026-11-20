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

package com.davils.kiro.ui.components.switch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

public fun interface SwitchStyles {
    public fun resolve(theme: Theme, size: SwitchSize): SwitchStyle

    public companion object {
        public val Default: SwitchStyles = SwitchStyles(::defaultSwitchStyle)
    }
}

public val SwitchStyleKey: StyleKey<SwitchStyles> = StyleKey(name = "switch") { _ -> SwitchStyles.Default }

@Composable
@ReadOnlyComposable
public fun switchStyle(size: SwitchSize): SwitchStyle = SwitchStyleKey.current.resolve(theme, size)
