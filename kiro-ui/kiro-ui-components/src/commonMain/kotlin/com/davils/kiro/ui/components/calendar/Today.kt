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

package com.davils.kiro.ui.components.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

/**
 * The clock from which [today] reads the current instant; the system clock by default, and a fixed clock in tests.
 *
 * @since 1.0.0
 */
public val LocalClock: ProvidableCompositionLocal<Clock> = staticCompositionLocalOf { Clock.System }

/**
 * The time zone in which [today] reads the current date; the default time zone of the system by default.
 *
 * @since 1.0.0
 */
public val LocalTimeZone: ProvidableCompositionLocal<TimeZone> =
    staticCompositionLocalOf { TimeZone.currentSystemDefault() }

/**
 * Returns the current date of [LocalClock] in [LocalTimeZone].
 *
 * @return the current date.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun today(): LocalDate = LocalClock.current.todayIn(LocalTimeZone.current)
