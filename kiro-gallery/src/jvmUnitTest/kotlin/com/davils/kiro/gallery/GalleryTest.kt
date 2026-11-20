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

package com.davils.kiro.gallery

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import com.davils.kiro.gallery.fixture.ControlledGallery
import com.davils.kiro.gallery.fixture.click
import com.davils.kiro.gallery.fixture.first
import com.davils.kiro.gallery.fixture.shown
import com.davils.kiro.gallery.fixture.type
import io.kotest.core.spec.style.FunSpec

private const val SETTLE: Long = 1_000L

@OptIn(ExperimentalTestApi::class)
class GalleryTest : FunSpec({
    context("gallery") {
        test("switches between components and themes") {
            shown(content = { ControlledGallery() }) {
                onNodeWithText("AccordionDemo.kt").assertExists()
                click("Dark")
                val trace = first("Trace")
                trace.performScrollTo()
                click(trace)
                onNodeWithText("TraceDemo.kt").assertExists()
            }
        }

        test("searches components and lists them by category") {
            shown(content = { ControlledGallery() }) {
                onNodeWithText("Overlays").assertExists()
                type("draw")
                onNodeWithText("Tabs").assertDoesNotExist()
                click("Drawer")
                onNodeWithText("DrawerDemo.kt").assertExists()
                type("zzz")
                onNodeWithText("No component matches \"drawzzz\".").assertExists()
            }
        }

        test("collapses a category") {
            shown(content = { ControlledGallery() }) {
                val feedback = first("Feedback")
                feedback.performScrollTo()
                click(feedback)
                mainClock.advanceTimeBy(SETTLE)
                waitForIdle()
                onNodeWithText("Skeleton").assertDoesNotExist()
            }
        }
    }
})
