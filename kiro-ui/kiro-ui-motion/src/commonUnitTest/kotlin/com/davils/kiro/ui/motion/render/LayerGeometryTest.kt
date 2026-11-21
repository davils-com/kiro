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
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val size: Size = Size(width = 200f, height = 100f)

class LayerGeometryTest : FunSpec({
    test("shifts by fractions of the size") {
        val transform = LayerTransform.Identity.copy(offset = Offset(x = 0.1f, y = -0.5f))

        translationOf(transform = transform, size = size, inputs = ArtInputs.None) shouldBe Offset(x = 20f, y = -50f)
    }

    test("follows the pointer and the scroll with its parallax") {
        val transform = LayerTransform.Identity.copy(parallax = 0.5f)
        val inputs = ArtInputs.None.copy(pointer = Offset(x = 140f, y = 30f), scroll = 40f)

        translationOf(transform = transform, size = size, inputs = inputs) shouldBe Offset(x = 20f, y = -30f)
    }

    test("anchors at fractions of the size") {
        anchorOf(transform = LayerTransform.Identity, size = size) shouldBe Offset(x = 100f, y = 50f)
    }

    test("knows whether a layer moves at all") {
        isMoved(transform = LayerTransform.Identity, translation = Offset.Zero) shouldBe false
        isMoved(transform = LayerTransform.Identity, translation = Offset(x = 1f, y = 0f)) shouldBe true
        isMoved(transform = LayerTransform.Identity.copy(scale = 2f), translation = Offset.Zero) shouldBe true
        isMoved(transform = LayerTransform.Identity.copy(rotation = 9f), translation = Offset.Zero) shouldBe true
    }
})
