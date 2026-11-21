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

package com.davils.kiro.ui.audio.card

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.audio.fixture.TAG
import com.davils.kiro.ui.audio.fixture.Themed
import com.davils.kiro.ui.audio.fixture.reducedMotion
import com.davils.kiro.ui.audio.fixture.tracks
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val COVER: String = "cover"

@OptIn(ExperimentalTestApi::class)
class AudioPlayerCardTest : FunSpec({
    test("plays from its controls in both themes") {
        listOf(reducedMotion, Theme.dark()).forEach { theme ->
            val state = AudioPlayerState(queue = tracks)

            runComposeUiTest {
                setContent { Themed(theme = theme) { AudioPlayerCard(state = state, onAction = state::apply) } }
                onNodeWithContentDescription("Play").performClick()
                waitForIdle()
            }

            state.isPlaying shouldBe true
        }
    }

    test("shows a changed style") {
        val state = AudioPlayerState(queue = tracks)

        runComposeUiTest {
            setContent {
                Themed {
                    val style = audioPlayerCardStyle().copy(width = 240.dp)
                    AudioPlayerCard(state = state, onAction = {}, modifier = Modifier.testTag(TAG), style = style)
                }
            }
            onNodeWithTag(TAG).assertWidthIsEqualTo(240.dp)
        }
    }

    test("replaces the cover through its slot") {
        val state = AudioPlayerState(queue = tracks)

        runComposeUiTest {
            setContent {
                Themed {
                    AudioPlayerCard(state = state, onAction = {}, cover = { modifier -> Box(modifier.testTag(COVER)) })
                }
            }
            onNodeWithTag(COVER).assertExists()
        }
    }
})
