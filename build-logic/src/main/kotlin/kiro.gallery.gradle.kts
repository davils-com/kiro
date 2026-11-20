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

@file:OptIn(ExperimentalWasmDsl::class)

import com.davils.kreate.platform.WebEnvironment
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.wasm.binaryen.BinaryenEnvSpec
import org.jetbrains.kotlin.gradle.targets.wasm.binaryen.BinaryenPlugin

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("kiro.base")
    id("kiro.dependency-locking")
    id("kiro.trivy")
    id("kiro.assurance")
    id("kiro.platform")
    id("kiro.web-tools")
    id("kiro.testing")
    id("kiro.detekt")
    id("kiro.archives")
    id("kiro.tool-classpaths")
    id("kiro.compose")
}

val galleryPackage: String = "com.davils.kiro.${name.removePrefix("kiro-").replace('-', '.')}"
val demoDirectory: String = "src/commonMain/kotlin/${galleryPackage.replace('.', '/')}/demo"

kreate {
    platform {
        multiplatform {
            targets {
                wasmJs = setOf(WebEnvironment.BROWSER)
            }
        }
    }

    assurance {
        advisories {
            configurations = setOf(
                "jvmCompileClasspath",
                "jvmRuntimeClasspath",
                "jvmUnitTestCompileClasspath",
                "jvmUnitTestRuntimeClasspath",
                "wasmJsCompileClasspath",
                "wasmJsRuntimeClasspath",
            )
        }
    }
}

kotlin {
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "${project.name}.js"
            }
        }
        binaries.executable()
    }
}

plugins.withType<BinaryenPlugin>().configureEach {
    the<BinaryenEnvSpec>().downloadBaseUrl.set(null as String?)
}

val gallerySources = tasks.register<Sync>("galleryDemoSources") {
    description = "Copies the sources of the demos into the resources of the gallery, which shows them as code."
    from(layout.projectDirectory.dir(demoDirectory)) {
        include("*Demo.kt")
    }
    into(layout.buildDirectory.dir("generated/gallery/composeResources/files/demos"))
}

compose {
    resources {
        customDirectory(
            sourceSetName = "commonMain",
            directoryProvider = layout.dir(gallerySources.map { sync -> sync.destinationDir.parentFile.parentFile }),
        )
    }

    desktop {
        application {
            mainClass = "$galleryPackage.MainKt"
            jvmArgs += "--enable-native-access=ALL-UNNAMED"
        }
    }
}
