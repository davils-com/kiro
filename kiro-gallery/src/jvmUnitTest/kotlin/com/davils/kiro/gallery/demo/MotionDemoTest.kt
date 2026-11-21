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

package com.davils.kiro.gallery.demo

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import com.davils.kiro.gallery.fixture.shown
import com.davils.kiro.gallery.page.GalleryCategory
import com.davils.kiro.gallery.page.file
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.scene.ScenePresets
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

private const val SETTLE: Long = 2_000L

private val motion = demos.filter { demo -> demo.category == GalleryCategory.Motion }

@OptIn(ExperimentalTestApi::class)
class MotionDemoTest : FunSpec({
    context("motion demos") {
        test("are listed under motion") {
            motion.map { demo -> demo.name } shouldContainAll listOf(
                "Scene Presets",
                "Layers",
                "Typewriter",
                "Motion Playground",
                "Tiling",
                "Geometry",
                "Fields",
                "Particles",
                "Gradients",
                "Grids",
                "Lines",
                "Shaders",
            )
            motion.first { demo -> demo.name == "Scene Presets" }.sections.size shouldBe
                ScenePresets.all.size
        }

        test("render every section in the light and the dark theme") {
            listOf(Theme.light(), Theme.dark()).forEach { theme ->
                motion.flatMap { demo -> demo.sections }.forEach { section ->
                    shown(theme = theme, content = section.content) { waitForIdle() }
                }
            }
        }

        test("type their scripts") {
            shown(content = { TypewriterDemo() }) {
                mainClock.advanceTimeBy(SETTLE)
                onNodeWithText("Calm interfaces.").assertExists()
            }
        }

        test("name a file for every section") {
            motion.flatMap { demo -> demo.sections }.map { section -> section.file } shouldContainAll
                listOf("SkylineDemo.kt", "TruchetArtDemo.kt", "MotionPlaygroundDemo.kt")
        }
    }
})
