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

package com.davils.kiro.gallery.page

import com.davils.kiro.gallery.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

private const val DEMO_DIRECTORY: String = "files/demos"
private const val PACKAGE: String = "package "

internal val GalleryDemo.file: String
    get() = "${name.filterNot { character -> character.isWhitespace() }}Demo.kt"

@OptIn(ExperimentalResourceApi::class)
internal suspend fun GalleryDemo.source(): String {
    val bytes = Res.readBytes("$DEMO_DIRECTORY/$file")
    val text = bytes.decodeToString()
    val body = text.substringAfter(PACKAGE).substringAfter('\n')
    return body.trim()
}
