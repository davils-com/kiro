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

import androidx.compose.ui.layout.Measurable

internal class BarParts(measurables: List<List<Measurable>>) {
    val startControls: Measurable? = measurables[0].firstOrNull()
    val start: Measurable? = measurables[1].firstOrNull()
    val center: Measurable? = measurables[2].firstOrNull()
    val end: Measurable? = measurables[3].firstOrNull()
    val endControls: Measurable? = measurables[4].firstOrNull()
}
