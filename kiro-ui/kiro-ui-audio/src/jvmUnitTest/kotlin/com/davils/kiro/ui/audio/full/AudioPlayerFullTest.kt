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

package com.davils.kiro.ui.audio.full

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.audio.AudioAction
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.audio.TrackId
import com.davils.kiro.ui.audio.fixture.THIRD
import com.davils.kiro.ui.audio.fixture.Themed
import com.davils.kiro.ui.audio.fixture.tracks
import com.davils.kiro.ui.components.text.Text
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

private const val QUEUE: String = "Queue"
private const val COVER: String = "cover"
private val viewport: Modifier = Modifier.size(width = 900.dp, height = 600.dp)

@OptIn(ExperimentalTestApi::class)
class AudioPlayerFullTest : FunSpec({
    test("opens the queue and picks a track from it") {
        val actions = mutableListOf<AudioAction>()
        val state = AudioPlayerState(queue = tracks)

        runComposeUiTest {
            setContent {
                Themed {
                    AudioPlayerFull(
                        state = state,
                        onAction = { action ->
                            actions += action
                            state.apply(action)
                        },
                        modifier = viewport,
                    )
                }
            }
            onAllNodesWithContentDescription(QUEUE)[0].performClick()
            waitForIdle()
            onNodeWithText(THIRD).performClick()
            waitForIdle()
            onAllNodesWithContentDescription(QUEUE)[0].performClick()
            waitForIdle()
        }

        actions shouldContain AudioAction.Select(TrackId(THIRD))
        state.track?.title shouldBe THIRD
    }

    test("starts with the queue shown when asked") {
        val state = AudioPlayerState(queue = emptyList())

        runComposeUiTest {
            setContent {
                Themed { AudioPlayerFull(state = state, onAction = state::apply, isQueueInitiallyShown = true) }
            }
            onNodeWithText("Nothing playing").assertExists()
        }
    }

    test("shows a changed style and replaces the cover through its slot") {
        val state = AudioPlayerState(queue = tracks)

        runComposeUiTest {
            setContent {
                Themed {
                    AudioPlayerFull(
                        state = state,
                        onAction = {},
                        modifier = viewport,
                        style = audioPlayerFullStyle().copy(coverSize = 100.dp),
                        cover = { modifier -> Box(modifier.testTag(COVER)) },
                    )
                }
            }
            onNodeWithTag(COVER).assertWidthIsEqualTo(100.dp)
        }
    }

    test("replaces the queue through its slot") {
        val state = AudioPlayerState(queue = tracks)

        runComposeUiTest {
            setContent {
                Themed {
                    AudioPlayerFull(
                        state = state,
                        onAction = {},
                        modifier = viewport,
                        isQueueInitiallyShown = true,
                        queue = { Text("Up next") },
                    )
                }
            }
            onNodeWithText("Up next").assertExists()
        }
    }
})
