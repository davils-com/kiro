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

/**
 * How an [AudioPlayerState] continues at the end of a track.
 *
 * @since 1.0.0
 */
public enum class RepeatMode {
    /**
     * Plays the queue once and stops after its last track.
     *
     * @since 1.0.0
     */
    Off,

    /**
     * Starts the queue again after its last track.
     *
     * @since 1.0.0
     */
    All,

    /**
     * Plays the current track again and again.
     *
     * @since 1.0.0
     */
    One,
}
