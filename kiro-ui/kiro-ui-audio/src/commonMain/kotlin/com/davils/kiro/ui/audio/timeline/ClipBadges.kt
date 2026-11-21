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

package com.davils.kiro.ui.audio.timeline

import androidx.compose.runtime.Composable
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle

@Composable
internal fun ClipBadges(clip: Clip, style: AudioClipStyle) {
    val look = IconStyle(size = style.iconSize, color = style.nameOf(clip))
    if (clip.isMuted) Icon(icon = style.muted, contentDescription = style.mutedLabel, style = look)
    if (clip.isLocked) Icon(icon = style.locked, contentDescription = style.lockedLabel, style = look)
}
