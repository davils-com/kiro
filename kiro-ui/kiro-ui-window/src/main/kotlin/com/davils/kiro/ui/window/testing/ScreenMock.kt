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

package com.davils.kiro.ui.window.testing

import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.window.screen.Screen
import com.davils.kiro.ui.window.screen.ScreenApi
import com.davils.kiro.ui.window.screen.ScreenId
import com.davils.kore.core.annotation.DavilsInternal

/**
 * A [ScreenApi] for tests whose screens a test can change.
 *
 * @property available the screens the mock reports.
 * @since 1.0.0
 */
@DavilsInternal
public class ScreenMock(public var available: List<Screen> = listOf(PRIMARY)) : ScreenApi {
    override fun screens(): List<Screen> = available

    /**
     * Holds the screens of a typical desktop with two screens.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * A primary screen of 1920 by 1080 with a bar at the top and a dock at the bottom.
         *
         * @since 1.0.0
         */
        public val PRIMARY: Screen = Screen(
            id = ScreenId("primary"),
            bounds = DpRect(left = 0.dp, top = 0.dp, right = 1920.dp, bottom = 1080.dp),
            workArea = DpRect(left = 0.dp, top = 32.dp, right = 1920.dp, bottom = 1032.dp),
            isPrimary = true,
        )

        /**
         * A secondary screen of 2560 by 1440 to the right of [PRIMARY].
         *
         * @since 1.0.0
         */
        public val SECONDARY: Screen = Screen(
            id = ScreenId("secondary"),
            bounds = DpRect(left = 1920.dp, top = 0.dp, right = 4480.dp, bottom = 1440.dp),
            workArea = DpRect(left = 1920.dp, top = 0.dp, right = 4480.dp, bottom = 1440.dp),
            isPrimary = false,
        )
    }
}
