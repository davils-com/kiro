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

import com.davils.kiro.ui.motion.color.ArtPalette

internal class Parameter<S>(
    val name: String,
    val control: Control,
    private val reader: (S, ArtPalette) -> Setting,
    private val writer: (S, Setting, ArtPalette) -> S,
    private val coder: (Setting) -> String,
) {
    fun read(style: S, palette: ArtPalette): Setting = reader(style, palette)

    fun write(style: S, setting: Setting?, palette: ArtPalette): S {
        if (setting == null) return style
        return writer(style, setting, palette)
    }

    fun valueCode(setting: Setting): String = coder(setting)

    fun code(setting: Setting): String = "$name = ${coder(setting)}"
}
