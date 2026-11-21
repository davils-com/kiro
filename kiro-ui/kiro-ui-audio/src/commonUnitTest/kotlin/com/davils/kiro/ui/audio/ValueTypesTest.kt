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

package com.davils.kiro.ui.audio

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.audio.bar.AudioPlayerBarStyles
import com.davils.kiro.ui.audio.card.AudioPlayerCardStyles
import com.davils.kiro.ui.audio.fixture.ValueSample
import com.davils.kiro.ui.audio.fixture.tracks
import com.davils.kiro.ui.audio.full.AudioPlayerFullStyles
import com.davils.kiro.ui.audio.mini.AudioPlayerMiniStyles
import com.davils.kiro.ui.audio.palette.CoverPalette
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private fun samples(): List<ValueSample> {
    val theme = Theme.light()
    val controls = AudioControlsStyles.Default.resolve(theme)
    val shared = listOf(
        ValueSample(
            name = "AudioControlsStyle",
            value = controls,
            copy = controls.copy(),
            changed = controls.copy(gap = 1.dp),
        ),
        ValueSample(
            name = "AudioIcons",
            value = controls.icons,
            copy = controls.icons.copy(),
            changed = controls.icons.copy(next = controls.icons.previous),
        ),
        ValueSample(
            name = "AudioLabels",
            value = controls.labels,
            copy = controls.labels.copy(),
            changed = controls.labels.copy(play = "Start"),
        ),
    )
    return shared + playerSamples(theme)
}

private fun playerSamples(theme: Theme): List<ValueSample> {
    val bar = AudioPlayerBarStyles.Default.resolve(theme)
    val card = AudioPlayerCardStyles.Default.resolve(theme)
    val full = AudioPlayerFullStyles.Default.resolve(theme)
    val mini = AudioPlayerMiniStyles.Default.resolve(theme)
    val palette = CoverPalette(
        dominant = Color.Red,
        vibrant = Color.Green,
        muted = Color.Blue,
        onDominant = Color.White,
    )
    val track = tracks.first()
    return listOf(
        ValueSample(
            name = "AudioPlayerBarStyle",
            value = bar,
            copy = bar.copy(),
            changed = bar.copy(height = 1.dp),
        ),
        ValueSample(
            name = "AudioPlayerCardStyle",
            value = card,
            copy = card.copy(),
            changed = card.copy(width = 1.dp),
        ),
        ValueSample(
            name = "AudioPlayerFullStyle",
            value = full,
            copy = full.copy(),
            changed = full.copy(padding = 1.dp),
        ),
        ValueSample(
            name = "AudioPlayerMiniStyle",
            value = mini,
            copy = mini.copy(),
            changed = mini.copy(height = 1.dp),
        ),
        ValueSample(
            name = "CoverPalette",
            value = palette,
            copy = palette.copy(),
            changed = palette.copy(muted = Color.Black),
        ),
        ValueSample(
            name = "Track",
            value = track,
            copy = track.copy(),
            changed = track.copy(title = "Other"),
        ),
    )
}

class ValueTypesTest : FunSpec({
    context("every value type") {
        samples().forEach { sample ->
            test("${sample.name} equals its copy and differs from a copy with a changed value") {
                sample.copy shouldBe sample.value
                sample.copy.hashCode() shouldBe sample.value.hashCode()
                sample.changed shouldNotBe sample.value
                sample.value shouldNotBe Any()
            }

            test("${sample.name} describes itself by its name and values") {
                sample.value.toString() shouldStartWith sample.name
            }
        }
    }
})
