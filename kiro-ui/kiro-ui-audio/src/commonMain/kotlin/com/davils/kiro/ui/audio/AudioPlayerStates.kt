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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlin.time.Duration
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Remembers an [AudioPlayerState] for a queue across recompositions.
 *
 * @param queue the tracks to play, in order.
 * @return the remembered state.
 * @since 1.0.0
 */
@Composable
public fun rememberAudioPlayerState(queue: List<Track>): AudioPlayerState = remember { AudioPlayerState(queue = queue) }

internal fun progressOf(position: Duration, duration: Duration?): Float {
    val total = duration?.inWholeMilliseconds ?: 0L
    if (total <= 0L) return 0f
    return (position.inWholeMilliseconds.toFloat() / total).coerceIn(minimumValue = 0f, maximumValue = 1f)
}

@OptIn(ExperimentalUuidApi::class)
internal fun secureShuffled(ids: List<TrackId>): List<TrackId> {
    val keyed = ids.map { id -> Uuid.random() to id }
    val sorted = keyed.sortedBy { (key, _) -> key }
    return sorted.map { (_, id) -> id }
}
