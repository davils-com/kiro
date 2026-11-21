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

package com.davils.kiro.ui.motion.render

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.scene.LayerTransform

internal fun translationOf(transform: LayerTransform, size: Size, inputs: ArtInputs): Offset {
    val shift = Offset(x = transform.offset.x * size.width, y = transform.offset.y * size.height)
    val follow = followOf(pointer = inputs.pointer, size = size) * transform.parallax
    val scroll = Offset(x = 0f, y = -inputs.scroll * transform.parallax)
    return shift + follow + scroll
}

internal fun anchorOf(transform: LayerTransform, size: Size): Offset =
    Offset(x = transform.anchor.x * size.width, y = transform.anchor.y * size.height)

internal fun isMoved(transform: LayerTransform, translation: Offset): Boolean =
    translation != Offset.Zero || transform.scale != 1f || transform.rotation != 0f

private fun followOf(pointer: Offset?, size: Size): Offset {
    val position = pointer ?: return Offset.Zero
    return position - Offset(x = size.width / 2f, y = size.height / 2f)
}
