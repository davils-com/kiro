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

package com.davils.kiro.ui.core.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme

/**
 * The style of the component of this key in the theme of the composition: its default for the theme, changed by the
 * overrides of [Theme.components].
 *
 * The style is resolved on every read, so keep the default of a key and its overrides cheap: they should only
 * assemble values of the theme.
 *
 * @since 0.1.0
 */
public val <T : Any> StyleKey<T>.current: T
    @Composable
    @ReadOnlyComposable
    get() {
        val theme = Theme.current
        return theme.components.resolve(key = this, theme = theme)
    }
