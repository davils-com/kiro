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

plugins {
    id("kiro.base")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
}

val catalog: VersionCatalog = the<VersionCatalogsExtension>().named("libs")
val composeTesting = catalog.findBundle("compose-testing").get()
val forbiddenGroups: List<String> = listOf(
    "androidx.compose.material",
    "androidx.compose.material3",
    "org.jetbrains.compose.material",
    "org.jetbrains.compose.material3",
)
val resourcePackage: String = "com.davils.kiro.${name.removePrefix("kiro-").replace('-', '.')}.resources"

configurations.configureEach {
    forbiddenGroups.forEach { group -> exclude(group = group) }
}

configurations.matching { configuration -> configuration.name == "jvmUnitTestImplementation" }.configureEach {
    dependencies.addAllLater(composeTesting.map { bundle -> bundle.map { library -> library } })
}

kreate {
    project {
        tests {
            suites {
                named("unitTest") {
                    nonJvmTargets = false
                }
            }
        }
    }
}

compose {
    resources {
        packageOfResClass = resourcePackage
        publicResClass = false
        generateResClass = auto
    }
}
