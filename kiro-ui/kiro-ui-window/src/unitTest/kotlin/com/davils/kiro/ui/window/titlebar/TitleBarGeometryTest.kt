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

package com.davils.kiro.ui.window.titlebar

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TitleBarGeometryTest : FunSpec({
    context("window center") {
        test("centers the title on the whole width while the room allows it") {
            titleOffset(
                placement = TitlePlacement.WindowCenter,
                width = 1000,
                title = 200,
                room = 300..900,
                isRtl = false,
            ).shouldBe(expected = 400)
        }

        test("moves the title only as far as a slot would cover it") {
            titleOffset(
                placement = TitlePlacement.WindowCenter,
                width = 1000,
                title = 200,
                room = 500..950,
                isRtl = false,
            ).shouldBe(expected = 500)
            titleOffset(
                placement = TitlePlacement.WindowCenter,
                width = 1000,
                title = 200,
                room = 50..550,
                isRtl = false,
            ).shouldBe(expected = 350)
        }

        test("keeps a title that fills the room at its start") {
            titleOffset(
                placement = TitlePlacement.WindowCenter,
                width = 1000,
                title = 300,
                room = 600..900,
                isRtl = false,
            ).shouldBe(expected = 600)
        }
    }

    context("other placements") {
        test("centers the title in the room between the slots") {
            titleOffset(
                placement = TitlePlacement.SlotCenter,
                width = 1000,
                title = 100,
                room = 500..900,
                isRtl = false,
            ).shouldBe(expected = 650)
        }

        test("puts the title at the start of the room in both directions") {
            titleOffset(
                placement = TitlePlacement.Start,
                width = 1000,
                title = 100,
                room = 200..800,
                isRtl = false,
            ).shouldBe(expected = 200)
            titleOffset(
                placement = TitlePlacement.Start,
                width = 1000,
                title = 100,
                room = 200..800,
                isRtl = true,
            ).shouldBe(expected = 700)
        }

        test("survives a room without width") {
            titleOffset(
                placement = TitlePlacement.WindowCenter,
                width = 100,
                title = 0,
                room = IntRange(start = 80, endInclusive = 20),
                isRtl = false,
            ).shouldBe(expected = 80)
        }
    }

    context("gaps") {
        test("adds a gap only after something with a width") {
            gapAfter(width = 0, gap = 8).shouldBe(expected = 0)
            gapAfter(width = 10, gap = 8).shouldBe(expected = 8)
        }
    }
})
