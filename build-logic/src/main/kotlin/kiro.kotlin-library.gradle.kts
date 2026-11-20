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
    id("org.jetbrains.kotlin.multiplatform")
    id("kiro.base")
    id("kiro.dependency-locking")
    id("kiro.git-hooks")
    id("kiro.trivy")
    id("kiro.assurance")
    id("kiro.platform")
    id("kiro.web-tools")
    id("kiro.testing")
    id("kiro.detekt")
    id("kiro.coverage")
    id("kiro.api-validation")
    id("kiro.docs")
    id("kiro.publish")
    id("kiro.archives")
    id("kiro.tool-classpaths")
}

kreate {
    assurance {
        advisories {
            configurations = setOf(
                "jvmCompileClasspath",
                "jvmRuntimeClasspath",
                "jvmUnitTestCompileClasspath",
                "jvmUnitTestRuntimeClasspath",
                "wasmJsCompileClasspath",
                "wasmJsRuntimeClasspath",
                "wasmJsTestCompileClasspath",
                "wasmJsTestRuntimeClasspath",
            )
        }
    }
}
