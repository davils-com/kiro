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

package com.davils.kiro.gradle.lucide.writer

import com.squareup.kotlinpoet.FileSpec
import java.io.File

private val LICENSE_HEADER: String = """
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
""".trimIndent()

internal object SourceFiles {
    fun render(file: FileSpec): String {
        val body = file.toString()
        return "$LICENSE_HEADER\n\n$body"
    }

    fun write(file: FileSpec, directory: File) {
        val packageDirectory = file.packageName.replace('.', File.separatorChar)
        val target = directory.resolve(packageDirectory).resolve("${file.name}.kt")
        target.parentFile.mkdirs()
        target.writeText(render(file))
    }
}
