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

@file:OptIn(ExperimentalTestApi::class)

package com.davils.kiro.ui.motion.typewriter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.motion.fixture.TAG
import com.davils.kiro.ui.motion.fixture.Themed
import com.davils.kiro.ui.motion.fixture.reducedMotion
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import kotlin.time.Duration.Companion.milliseconds

private val style: TypewriterStyle = typedStyle
private val script: TypewriterScript = typedScript

private fun typed(setup: Typed = Typed(), check: ComposeUiTest.() -> Unit) = runComposeUiTest {
    mainClock.autoAdvance = false
    setContent {
        Themed(theme = setup.theme) {
            CompositionLocalProvider(LocalLayoutDirection provides setup.direction) {
                TypewriterText(
                    script = setup.script,
                    modifier = Modifier.testTag(TAG),
                    style = setup.style,
                    seed = 3,
                    onFinished = setup.onFinished,
                )
            }
        }
    }
    check()
}

private fun ComposeUiTest.announced(): String? {
    val node = onNodeWithTag(TAG).fetchSemanticsNode()
    val texts = node.config.getOrNull(SemanticsProperties.Text)
    return texts?.joinToString()
}

private fun ComposeUiTest.print(): Int {
    val image = onNodeWithTag(TAG).captureToImage()
    val buffer = IntArray(size = image.width * image.height)
    image.readPixels(buffer = buffer)
    return buffer.contentHashCode()
}

private fun ComposeUiTest.width(): Dp {
    val bounds = onNodeWithTag(TAG).getUnclippedBoundsInRoot()
    return bounds.right - bounds.left
}

private fun ComposeUiTest.advance(milliseconds: Long) {
    mainClock.advanceTimeBy(milliseconds)
    waitForIdle()
}

private fun printOf(setup: Typed, at: Long): Int {
    var print = 0
    typed(setup = setup) {
        advance(milliseconds = at)
        print = print()
    }
    return print
}

class TypewriterTextTest : FunSpec({
    test("types character by character while it announces the whole phrase once") {
        typed {
            advance(milliseconds = 16)
            announced() shouldBe PHRASE
            val early = print()
            advance(milliseconds = 450)
            announced() shouldBe PHRASE
            val later = print()
            advance(milliseconds = 1200)
            announced() shouldBe PHRASE

            later shouldNotBe early
            print() shouldNotBe later
        }
    }

    test("announces the next phrase of a cycle when it starts typing it") {
        val cycle = typewriterCycle(phrases = listOf("One", "Two"), hold = 100.milliseconds)
        typed(setup = Typed(script = cycle)) {
            advance(milliseconds = 16)
            announced() shouldBe "One"
            advance(milliseconds = 700)
            announced() shouldBe "Two"
        }
    }

    test("calls back once when a script that ends has played") {
        var calls = 0
        typed(setup = Typed(onFinished = { calls += 1 })) {
            advance(milliseconds = 500)
            calls shouldBe 0
            advance(milliseconds = 1000)
            advance(milliseconds = 1000)
            calls shouldBe 1
        }
        var cycled = 0
        typed(setup = Typed(script = typewriterCycle(phrases = listOf("A")), onFinished = { cycled += 1 })) {
            advance(milliseconds = 5000)
            cycled shouldBe 0
        }
    }

    test("shows the finished text at once under reduced motion") {
        var calls = 0
        typed(setup = Typed(theme = reducedMotion, onFinished = { calls += 1 })) {
            advance(milliseconds = 16)
            val first = print()
            announced() shouldBe PHRASE
            advance(milliseconds = 700)
            print() shouldBe first
            calls shouldBe 1
        }
        typed(setup = Typed(style = style.copy(isAnimated = false), script = typewriterCycle(listOf("A", "B")))) {
            advance(milliseconds = 16)
            announced() shouldBe "A"
        }
    }

    test("types faster at a higher speed") {
        val slow = printOf(setup = Typed(style = style.copy(hasCaret = false)), at = 400)
        val fast = printOf(setup = Typed(style = style.copy(hasCaret = false, speed = 2f)), at = 400)

        fast shouldNotBe slow
    }

    test("shows a changed style") {
        val base = printOf(setup = Typed(), at = 300)

        printOf(setup = Typed(style = style.copy(caretColor = Color.Red)), at = 300) shouldNotBe base
        val square = style.copy(caretShape = RectangleShape, caretWidth = style.caretGap * 3)
        printOf(setup = Typed(style = square), at = 300) shouldNotBe base
        printOf(setup = Typed(style = style.copy(hasCaret = false)), at = 300) shouldNotBe base
        printOf(setup = Typed(style = style.copy(color = Color.Red)), at = 300) shouldNotBe base
        printOf(setup = Typed(direction = LayoutDirection.Rtl), at = 300) shouldNotBe base
    }

    test("blinks its caret while the text rests") {
        typed {
            advance(milliseconds = 1200)
            val on = print()
            advance(milliseconds = 600)
            print() shouldNotBe on
        }
    }

    test("reserves the space of its phrase unless its style says otherwise") {
        var reserved = Dp.Unspecified
        var growing = Dp.Unspecified
        typed {
            advance(milliseconds = 16)
            reserved = width()
        }
        typed(setup = Typed(style = style.copy(isSpaceReserved = false))) {
            advance(milliseconds = 16)
            growing = width()
        }

        reserved shouldBeGreaterThan growing
    }

    test("takes its style from the components of the theme") {
        val theme = Theme.light()
        var resolved: Pair<TypewriterStyle, Theme>? = null
        var custom: Pair<TypewriterStyle, Theme>? = null
        val changed = theme.copy(
            components = theme.components.with(TypewriterStyleKey) { _, _ ->
                TypewriterStyles { current -> defaultTypewriterStyle(current).copy(jitter = 0f) }
            },
        )
        runComposeUiTest {
            setContent {
                Resolve(theme = theme) { resolved = it }
                Resolve(theme = changed) { custom = it }
            }
        }

        resolved?.let { (style, current) -> style shouldBe defaultTypewriterStyle(current) }
        custom?.let { (style, current) -> style shouldBe defaultTypewriterStyle(current).copy(jitter = 0f) }
        resolved shouldNotBe null
        custom shouldNotBe null
        defaultTypewriterStyle(reducedMotion).isAnimated shouldBe false
    }

    test("keeps its script, steps and style as values") {
        val type = TypeStep(text = "a")
        val pause = PauseStep(duration = 1.milliseconds)
        val delete = DeleteStep(count = 1)

        type.copy() shouldBe type
        type.copy().hashCode() shouldBe type.hashCode()
        type.copy(text = "b") shouldNotBe type
        type.toString() shouldBe "TypeStep(text=a)"
        pause.copy() shouldBe pause
        pause.copy(duration = null) shouldNotBe pause
        pause.copy().hashCode() shouldBe pause.hashCode()
        PauseStep().toString() shouldBe "PauseStep(duration=null)"
        delete.copy() shouldBe delete
        delete.copy().hashCode() shouldBe delete.hashCode()
        delete.copy(count = null).toString() shouldBe "DeleteStep(count=null)"
        script.copy() shouldBe script
        script.copy().hashCode() shouldBe script.hashCode()
        script.copy(isRepeating = true) shouldNotBe script
        script.toString() shouldStartWith "TypewriterScript(steps=[TypeStep(text=Hello world)]"
        style.copy() shouldBe style
        style.copy().hashCode() shouldBe style.hashCode()
        style.copy(hold = 1.milliseconds) shouldNotBe style
        style.toString() shouldStartWith "TypewriterStyle(textStyle="
    }
})

@Composable
private fun Resolve(theme: Theme, onStyle: (Pair<TypewriterStyle, Theme>) -> Unit) {
    ThemeProvider(theme = theme) { onStyle(typewriterStyle() to Theme.current) }
}
