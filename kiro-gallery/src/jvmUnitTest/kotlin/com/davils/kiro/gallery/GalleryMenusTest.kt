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
import androidx.compose.ui.test.performMouseInput
import com.davils.kiro.gallery.fixture.click
import com.davils.kiro.gallery.fixture.first
import com.davils.kiro.gallery.fixture.shown
import com.davils.kiro.ui.components.menubar.Menubar
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val ZOOM: Int = 125

@OptIn(ExperimentalTestApi::class)
class GalleryMenusTest : FunSpec({
    context("gallery menus") {
        test("describe file, edit and view menus that keep their state and quit") {
            var quits = 0
            val menus = GalleryMenus(onQuit = { quits++ })

            shown(content = { Menubar(content = menus::describe) }) {
                click("Edit")
                click("Undo")
                click("View")
                click("Status Bar")
                click("$ZOOM %")
                onNodeWithText("Zoom").assertExists()
                first("File").performMouseInput { moveTo(center) }
                waitForIdle()
                click("Quit")
            }

            menus.isStatusBarVisible shouldBe false
            menus.zoom shouldBe ZOOM
            quits shouldBe 1
        }
    }
})
