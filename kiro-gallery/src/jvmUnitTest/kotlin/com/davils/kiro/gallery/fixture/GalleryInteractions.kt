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

package com.davils.kiro.gallery.fixture

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider

@OptIn(ExperimentalTestApi::class)
internal fun ComposeUiTest.first(text: String): SemanticsNodeInteraction = onAllNodesWithText(text).onFirst()

@OptIn(ExperimentalTestApi::class)
internal fun ComposeUiTest.click(node: SemanticsNodeInteraction) {
    node.performMouseInput { moveTo(center) }
    node.performClick()
    waitForIdle()
}

@OptIn(ExperimentalTestApi::class)
internal fun ComposeUiTest.click(text: String) {
    click(first(text))
}

@OptIn(ExperimentalTestApi::class)
internal fun ComposeUiTest.escape(text: String) {
    first(text).performKeyInput { pressKey(Key.Escape) }
    waitForIdle()
}

@OptIn(ExperimentalTestApi::class)
internal fun ComposeUiTest.type(text: String, index: Int = 0) {
    val fields = onAllNodes(hasSetTextAction())
    fields[index].performTextInput(text)
    waitForIdle()
}

@OptIn(ExperimentalTestApi::class)
internal fun ComposeUiTest.role(role: Role, index: Int = 0): SemanticsNodeInteraction =
    onAllNodes(SemanticsMatcher.expectValue(SemanticsProperties.Role, role))[index]

@OptIn(ExperimentalTestApi::class)
internal fun shown(
    theme: Theme = Theme.light(),
    autoAdvance: Boolean = true,
    content: @Composable () -> Unit,
    steps: ComposeUiTest.() -> Unit,
) {
    runComposeUiTest {
        mainClock.autoAdvance = autoAdvance
        setContent { ThemeProvider(theme = theme, content = content) }
        steps()
    }
}
