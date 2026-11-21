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

@file:OptIn(DavilsInternal::class)

package com.davils.kiro.ui.window.tray

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.davils.kore.core.annotation.DavilsInternal

/**
 * The [TrayHost] that shows the tray icons of Kiro, the tray of the operating system by default.
 *
 * @since 1.0.0
 */
@DavilsInternal
public val LocalTrayHost: ProvidableCompositionLocal<TrayHost> = staticCompositionLocalOf { NativeTrayHost }
