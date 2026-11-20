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

package com.davils.kiro.ui.icons

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.PathParser

internal class IconGeometry(val viewportSize: Float, val shapes: List<IconShape>)

internal fun IconGraphic.toGeometry(): IconGeometry {
    val shapes = paths.map { path -> IconShape(path = pathOf(path.data), isFilled = path.isFilled) }
    return IconGeometry(viewportSize = viewportSize, shapes = shapes)
}

private fun pathOf(data: String): Path {
    val parser = PathParser().parsePathString(data)
    return parser.toPath()
}
