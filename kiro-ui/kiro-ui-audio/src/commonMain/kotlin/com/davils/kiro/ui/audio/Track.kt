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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap
import kotlin.time.Duration

private const val NONE: String = "none"

/**
 * One track in the queue of an [AudioPlayerState].
 *
 * @property id identifies the track within its queue.
 * @property title the title of the track.
 * @property artist the artist of the track.
 * @property duration the length of the track.
 * @property album the album of the track, or `null` when it has none.
 * @property cover the cover of the track, which tints the players, or `null` for a placeholder.
 * @since 1.0.0
 */
@Immutable
public class Track(
    public val id: TrackId,
    public val title: String,
    public val artist: String,
    public val duration: Duration,
    public val album: String? = null,
    public val cover: ImageBitmap? = null,
) {
    /**
     * Returns a copy of this [Track] with the given values replaced and every other value kept.
     *
     * @param id the new value of [id].
     * @param title the new value of [title].
     * @param artist the new value of [artist].
     * @param duration the new value of [duration].
     * @param album the new value of [album].
     * @param cover the new value of [cover].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        id: TrackId = this.id,
        title: String = this.title,
        artist: String = this.artist,
        duration: Duration = this.duration,
        album: String? = this.album,
        cover: ImageBitmap? = this.cover,
    ): Track = Track(id = id, title = title, artist = artist, duration = duration, album = album, cover = cover)

    override fun equals(other: Any?): Boolean = other is Track && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Track(" +
        "id=$id, " +
        "title=$title, " +
        "artist=$artist, " +
        "duration=$duration, " +
        "album=${album ?: NONE}, " +
        "cover=${cover ?: NONE})"

    private fun values(): List<Any?> = listOf(id, title, artist, duration, album, cover)
}
