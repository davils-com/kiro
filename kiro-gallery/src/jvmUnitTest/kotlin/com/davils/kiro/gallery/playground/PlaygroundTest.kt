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

package com.davils.kiro.gallery.playground

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performSemanticsAction
import com.davils.kiro.gallery.demo.MotionPlaygroundDemo
import com.davils.kiro.gallery.fixture.click
import com.davils.kiro.gallery.fixture.role
import com.davils.kiro.gallery.fixture.shown
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec

private const val CELL: Float = 100f
private const val SPEED: Float = 2f
private const val SEED: Float = 7f
private const val TIME: Float = 10f

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.slide(name: String, to: Float) {
    val slider = SemanticsMatcher.keyIsDefined(SemanticsActions.SetProgress) and
        hasAnyAncestor(hasContentDescription(name))
    val node = onAllNodes(slider).onFirst()
    node.performSemanticsAction(SemanticsActions.SetProgress) { set -> set(to) }
    waitForIdle()
}

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.press(name: String) {
    val described = hasContentDescription(name)
    val control = hasClickAction() and (described or hasAnyAncestor(described))
    val node = onAllNodes(control).onFirst()
    node.performSemanticsAction(SemanticsActions.OnClick)
    waitForIdle()
}

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.code(text: String) {
    onNodeWithText(text, substring = true).assertExists()
}

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.noCode(text: String) {
    onNodeWithText(text, substring = true).assertDoesNotExist()
}

@OptIn(ExperimentalTestApi::class)
class PlaygroundTest : FunSpec({
    context("playground") {
        test("updates the code when a parameter changes") {
            shown(content = { MotionPlaygroundDemo() }) {
                code("layer(BauhausArt())")
                slide(name = "cell", to = CELL)
                code("style = bauhausArtStyle().copy(")
                code("cell = 100.dp,")
            }
        }

        test("switches arts, palettes, menus and toggles") {
            shown(content = { MotionPlaygroundDemo() }) {
                click("Bauhaus")
                click("Ridge")
                code("layer(RidgeArt())")
                press("hasFill")
                code("hasFill = false,")
                press("gradient")
                click("Mirrored")
                code("gradient = RidgeGradient.Mirrored,")
                click("Refined")
                click("Vivid")
                code("ArtPalettes.Vivid.resolve(Theme.current)")
            }
        }

        test("drives the playback, the seed, the quality and the diagnostics") {
            shown(theme = Theme.dark(), content = { MotionPlaygroundDemo() }) {
                slide(name = "Speed", to = SPEED)
                code("rememberArtPlayback(speed = 2f)")
                slide(name = "Seed", to = SEED)
                code("seed = ArtSeed(value = 7)")
                slide(name = "Time", to = TIME)
                click("Adaptive")
                click("Low")
                code("QualityPolicy.Fixed(level = QualityLevel.Low)")
                click(role(Role.Switch))
                click("Pause")
                click("Play")
                click("Reset")
                noCode("copy(")
            }
        }
    }
})
