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

package com.davils.kiro.ui.audio.signal

import kotlin.jvm.JvmInline

/**
 * A level in decibels relative to full scale (dBFS), where `0` is the loudest level a signal can reach without clipping
 * and negative infinity is silence.
 *
 * @property value the level in decibels.
 * @since 1.0.0
 */
@JvmInline
public value class Decibels(public val value: Float)
