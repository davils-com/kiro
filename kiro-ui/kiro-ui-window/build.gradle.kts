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
    id("kiro.desktop-library")
}

kreate {
    project {
        description = "The desktop shell of Kiro: framed windows whose title bar looks native on every operating " +
            "system, the tray, the native application menu and the splash screens shown while an application starts."
    }
}

dependencies {
    api(project(":kiro-ui:kiro-ui-components"))
    api(project(":kiro-ui:kiro-ui-motion"))
    implementation(libs.jbr.api)
}
