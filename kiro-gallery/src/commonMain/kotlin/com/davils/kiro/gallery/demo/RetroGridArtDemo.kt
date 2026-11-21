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

package com.davils.kiro.gallery.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davils.kiro.gallery.theme
import com.davils.kiro.ui.motion.art.grid.RetroGridArt
import com.davils.kiro.ui.motion.host.MotionCanvas
import com.davils.kiro.ui.motion.scene.scene

@Composable
internal fun RetroGridArtDemo() {
    MotionCanvas(scene = scene { layer(RetroGridArt()) }, modifier = Modifier.artFrame(theme))
}
