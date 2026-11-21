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

package com.davils.kiro.ui.motion.art.field

import androidx.compose.ui.graphics.Path

internal class ContourPathSink(val path: Path) : ContourSink {
    var scale: Float = 1f

    override fun moveTo(x: Float, y: Float) {
        path.moveTo(x = x * scale, y = y * scale)
    }

    override fun lineTo(x: Float, y: Float) {
        path.lineTo(x = x * scale, y = y * scale)
    }

    override fun close() {
        path.close()
    }
}
