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

import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.color.ArtPalette

internal class ArtSpec<S : Any>(
    val name: String,
    private val defaults: (Theme) -> S,
    private val create: (S?) -> Art,
    val parameters: List<Parameter<S>>,
    val fixed: List<String>,
) {
    val art: String
        get() = "${name.filterNot(Char::isWhitespace)}Art"

    val styleFactory: String
        get() = "${art.replaceFirstChar(Char::lowercaseChar)}Style()"

    fun defaultsOf(theme: Theme): S = defaults(theme)

    fun artOf(theme: Theme, palette: ArtPalette, settings: Map<String, Setting>): Art {
        if (settings.isEmpty()) return create(null)
        return create(styleOf(theme = theme, palette = palette, settings = settings))
    }

    fun styleOf(theme: Theme, palette: ArtPalette, settings: Map<String, Setting>): S =
        parameters.fold(defaults(theme)) { style, parameter ->
            parameter.write(style = style, setting = settings[parameter.name], palette = palette)
        }

    fun settingsOf(theme: Theme, palette: ArtPalette, settings: Map<String, Setting>): Map<String, Setting> {
        val style = defaults(theme)
        return parameters.associate { parameter ->
            parameter.name to (settings[parameter.name] ?: parameter.read(style = style, palette = palette))
        }
    }

    fun changesOf(theme: Theme, palette: ArtPalette, settings: Map<String, Setting>): List<String> {
        val style = defaults(theme)
        return parameters.mapNotNull { parameter ->
            settings[parameter.name]
                ?.takeIf { setting -> setting != parameter.read(style = style, palette = palette) }
                ?.let(parameter::code)
        }
    }
}

internal fun <S : Any> artSpec(
    name: String,
    defaults: (Theme) -> S,
    create: (S?) -> Art,
    configure: ParameterScope<S>.() -> Unit,
): ArtSpec<S> {
    val scope = ParameterScope<S>()
    scope.configure()
    return ArtSpec(
        name = name,
        defaults = defaults,
        create = create,
        parameters = scope.parameters.toList(),
        fixed = scope.fixed.toList(),
    )
}
