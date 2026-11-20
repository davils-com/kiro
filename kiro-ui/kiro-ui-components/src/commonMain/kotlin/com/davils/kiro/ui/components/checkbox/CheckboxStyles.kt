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

package com.davils.kiro.ui.components.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

public fun interface CheckboxStyles {
    public fun resolve(theme: Theme): CheckboxStyle

    public companion object {
        public val Default: CheckboxStyles = CheckboxStyles(::defaultCheckboxStyle)
    }
}

@Composable
@ReadOnlyComposable
public fun checkboxStyle(): CheckboxStyle = CheckboxStyleKey.current.resolve(theme)

public val CheckboxStyleKey: StyleKey<CheckboxStyles> = StyleKey(name = "checkbox") { _ -> CheckboxStyles.Default }
