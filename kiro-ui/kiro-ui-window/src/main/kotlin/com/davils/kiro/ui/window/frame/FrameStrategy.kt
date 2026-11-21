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

package com.davils.kiro.ui.window.frame

import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.window.FramePreference

internal enum class FrameStrategy(val hasSystemFrame: Boolean) {
    Decorated(hasSystemFrame = true),
    FullContent(hasSystemFrame = true),
    Emulated(hasSystemFrame = false),
}

internal fun frameStrategyOf(
    platform: DesktopPlatform,
    preference: FramePreference,
    isDecorationSupported: Boolean,
): FrameStrategy {
    if (preference == FramePreference.Emulated) return FrameStrategy.Emulated
    return when (platform) {
        DesktopPlatform.Windows -> decoratedOr(
            isDecorationSupported = isDecorationSupported,
            fallback = FrameStrategy.Emulated,
        )

        DesktopPlatform.MacOs -> decoratedOr(
            isDecorationSupported = isDecorationSupported,
            fallback = FrameStrategy.FullContent,
        )

        DesktopPlatform.Linux -> FrameStrategy.Emulated

        DesktopPlatform.Other -> FrameStrategy.Emulated
    }
}

private fun decoratedOr(isDecorationSupported: Boolean, fallback: FrameStrategy): FrameStrategy {
    if (isDecorationSupported) return FrameStrategy.Decorated
    return fallback
}
