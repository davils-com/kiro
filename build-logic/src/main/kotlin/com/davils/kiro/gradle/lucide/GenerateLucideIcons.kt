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

package com.davils.kiro.gradle.lucide

import com.davils.kiro.gradle.lucide.archive.LucideArchive
import com.davils.kiro.gradle.lucide.writer.Provenance
import com.davils.kiro.gradle.lucide.writer.SourceFiles
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

@CacheableTask
public abstract class GenerateLucideIcons : DefaultTask() {
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val archive: ConfigurableFileCollection

    @get:Input
    public abstract val packageName: Property<String>

    @get:Input
    public abstract val release: Property<String>

    @get:Input
    public abstract val since: Property<String>

    @get:OutputDirectory
    public abstract val outputDirectory: DirectoryProperty

    init {
        group = "kiro"
        description = "Generates the Lucide icons from the SVGs of the pinned Lucide release."
    }

    @TaskAction
    public fun generate() {
        val output = outputDirectory.get().asFile
        output.deleteRecursively()
        val sources = LucideArchive.read(archive.singleFile.readBytes())
        val provenance = Provenance(release = release.get(), since = since.get())
        val files = IconGeneration.generate(sources = sources, packageName = packageName.get(), provenance = provenance)
        files.forEach { file -> SourceFiles.write(file = file, directory = output) }
    }
}
