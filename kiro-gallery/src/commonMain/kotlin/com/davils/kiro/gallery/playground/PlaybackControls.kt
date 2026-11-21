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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.label.Label
import com.davils.kiro.ui.components.select.Select
import com.davils.kiro.ui.motion.quality.QualityLevel
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

private const val ADAPTIVE: String = "Adaptive"
private const val SEEDS: Float = 99f
private const val MAX_SPEED: Float = 3f
private const val SPEED_STEP: Float = 0.25f
private const val TIMELINE: Float = 120f
private const val TIME_STEP: Float = 0.1f

@Composable
internal fun PlaybackControls(state: PlaygroundState) {
    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
        TimeControl(state = state, modifier = Modifier.weight(1f))
        LabeledSlider(
            name = "Speed",
            value = state.playback.speed,
            valueText = floatCode(state.playback.speed),
            control = Control.Slider(range = 0f..MAX_SPEED, step = SPEED_STEP),
            modifier = Modifier.weight(1f),
        ) { speed -> state.playback.speed = speed }
        LabeledSlider(
            name = "Seed",
            value = state.seed?.toFloat() ?: 0f,
            valueText = state.seed?.toString() ?: "Default",
            control = Control.Slider(range = 0f..SEEDS, step = 1f),
            modifier = Modifier.weight(1f),
        ) { seed -> state.seed = seed.roundToInt() }
        QualityControl(state = state, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun TimeControl(state: PlaygroundState, modifier: Modifier) {
    val elapsed = state.playback.elapsed.toDouble(DurationUnit.SECONDS)
    val seconds = elapsed.toFloat()
    LabeledSlider(
        name = "Time",
        value = seconds.coerceIn(minimumValue = 0f, maximumValue = TIMELINE),
        valueText = "${numberOf(seconds)} s",
        control = Control.Slider(range = 0f..TIMELINE, step = TIME_STEP),
        modifier = modifier,
    ) { time -> state.playback.seek(to = time.toDouble().seconds) }
}

@Composable
private fun QualityControl(state: PlaygroundState, modifier: Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Label(text = "Quality")
        Select(
            selected = state.quality?.name ?: ADAPTIVE,
            onSelectedChange = { name -> state.quality = qualityOf(name) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            option(value = ADAPTIVE, label = ADAPTIVE)
            QualityLevel.entries.forEach { level -> option(value = level.name, label = level.name) }
        }
    }
}

private fun qualityOf(name: String): QualityLevel? = QualityLevel.entries.firstOrNull { level -> level.name == name }
