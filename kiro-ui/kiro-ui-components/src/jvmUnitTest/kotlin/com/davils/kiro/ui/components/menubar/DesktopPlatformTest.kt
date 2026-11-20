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

package com.davils.kiro.ui.components.menubar

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DesktopPlatformTest : FunSpec({
    context("platformOf") {
        test("recognises macOS by its os name") {
            platformOf("Mac OS X") shouldBe DesktopPlatform.MacOs
            platformOf("Darwin") shouldBe DesktopPlatform.MacOs
        }

        test("recognises Windows by its os name") {
            platformOf("Windows 11") shouldBe DesktopPlatform.Windows
        }

        test("recognises Linux by its os name") {
            platformOf(" Linux ") shouldBe DesktopPlatform.Linux
        }

        test("falls back to other for every unknown os name") {
            platformOf("FreeBSD") shouldBe DesktopPlatform.Other
            platformOf("") shouldBe DesktopPlatform.Other
        }
    }

    context("current") {
        test("reads the os name of the running system") {
            DesktopPlatform.current() shouldBe platformOf(System.getProperty("os.name"))
        }
    }
})
