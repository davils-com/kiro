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

package com.davils.kiro.ui.components.separator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

public fun interface SeparatorStyles {
    public fun resolve(theme: Theme): SeparatorStyle

    public companion object {
        public val Default: SeparatorStyles = SeparatorStyles(::defaultSeparatorStyle)
    }
}

public val SeparatorStyleKey: StyleKey<SeparatorStyles> = StyleKey(name = "separator") { _ -> SeparatorStyles.Default }

@Composable
@ReadOnlyComposable
public fun separatorStyle(): SeparatorStyle = SeparatorStyleKey.current.resolve(theme)
