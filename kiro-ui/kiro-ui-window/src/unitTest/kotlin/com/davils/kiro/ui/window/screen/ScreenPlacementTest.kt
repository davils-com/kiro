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

@file:OptIn(DavilsInternal::class)

package com.davils.kiro.ui.window.screen

import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.window.testing.ScreenMock
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val fallback: DpSize = DpSize(width = 1200.dp, height = 800.dp)

private val visible: DpSize = DpSize(width = 100.dp, height = 40.dp)

private val screens: List<Screen> = listOf(ScreenMock.PRIMARY, ScreenMock.SECONDARY)

private fun placed(saved: DpRect, on: List<Screen> = screens, size: DpSize = fallback): DpRect =
    visibleBoundsOf(saved = saved, screens = on, fallback = size, minimumVisible = visible)

class ScreenPlacementTest : FunSpec({
    context("saved bounds") {
        test("stay where they are on any screen") {
            val primary = DpRect(left = 100.dp, top = 100.dp, right = 900.dp, bottom = 700.dp)
            val secondary = DpRect(left = 2000.dp, top = 100.dp, right = 2800.dp, bottom = 700.dp)

            placed(saved = primary) shouldBe primary
            placed(saved = secondary) shouldBe secondary
        }

        test("stay when enough of them reaches into a screen") {
            val overhanging = DpRect(left = -700.dp, top = 900.dp, right = 100.dp, bottom = 1500.dp)

            placed(saved = overhanging) shouldBe overhanging
        }

        test("stay as long as no primary screen is known") {
            val lost = DpRect(left = 9000.dp, top = 9000.dp, right = 9800.dp, bottom = 9600.dp)

            placed(saved = lost, on = emptyList()) shouldBe lost
            placed(saved = lost, on = listOf(ScreenMock.SECONDARY)) shouldBe lost
        }
    }

    context("bounds on a lost screen") {
        test("move to the centre of the primary screen") {
            val lost = DpRect(left = 9000.dp, top = 9000.dp, right = 9800.dp, bottom = 9600.dp)

            placed(saved = lost) shouldBe
                DpRect(left = 360.dp, top = 132.dp, right = 1560.dp, bottom = 932.dp)
        }

        test("move when only a sliver remains visible") {
            val sliver = DpRect(left = 1880.dp, top = 1010.dp, right = 1890.dp + 1920.dp, bottom = 1600.dp)
            val ledge = DpRect(left = 100.dp, top = 1020.dp, right = 900.dp, bottom = 1600.dp)
            val lone = listOf(ScreenMock.PRIMARY)
            val centre = DpRect(left = 360.dp, top = 132.dp, right = 1560.dp, bottom = 932.dp)

            placed(saved = sliver, on = lone) shouldBe centre
            placed(saved = ledge, on = lone) shouldBe centre
        }

        test("shrink to the work area of the primary screen") {
            val lost = DpRect(left = -5000.dp, top = 0.dp, right = -4000.dp, bottom = 600.dp)

            placed(saved = lost, size = DpSize(width = 4000.dp, height = 3000.dp)) shouldBe
                ScreenMock.PRIMARY.workArea
        }
    }

    context("screens") {
        test("come from the platform unless a mock replaces them") {
            val mock = ScreenMock()

            ScreenApi.System shouldBe JdkScreenApi
            mock.screens() shouldBe listOf(ScreenMock.PRIMARY)
            mock.available = screens
            mock.screens() shouldBe screens
            ScreenMock.SECONDARY.id.value shouldBe "secondary"
        }
    }
})
