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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.window.fixture.RecordingActions
import com.davils.kiro.ui.window.fixture.Themed
import com.davils.kiro.ui.window.frame.CaptionInsets
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val TITLE: String = "title"
private const val LEADING: String = "leading"
private const val TRAILING: String = "trailing"
private val barWidth: Dp = 1000.dp
private val barHeight: Dp = 16.dp

private val DpRect.middle: Dp
    get() = (left + right) / 2

@OptIn(ExperimentalTestApi::class)
private fun boundsOf(
    platform: DesktopPlatform,
    startWidth: Dp,
    endWidth: Dp,
    titleWidth: Dp,
    direction: LayoutDirection = LayoutDirection.Ltr,
    chrome: TitleBarChrome = TitleBarChrome.Drawn,
    placement: TitlePlacement = TitlePlacement.WindowCenter,
): Map<String, DpRect> {
    val bounds = mutableMapOf<String, DpRect>()
    runComposeUiTest {
        setContent {
            Themed {
                CompositionLocalProvider(LocalLayoutDirection provides direction) {
                    val scope = TitleBarScopeImpl()
                    with(scope) {
                        if (startWidth > 0.dp) {
                            start {
                                Box(
                                    Modifier.width(startWidth).height(barHeight).testTag(LEADING),
                                )
                            }
                        }
                        center { Box(Modifier.width(titleWidth).height(barHeight).testTag(TITLE)) }
                        if (endWidth > 0.dp) {
                            end {
                                Box(
                                    Modifier.width(endWidth).height(barHeight).testTag(TRAILING),
                                )
                            }
                        }
                    }
                    TitleBarLayout(
                        slots = scope.slots,
                        actions = RecordingActions(),
                        style = titleBarStyle(platform).copy(titlePlacement = placement),
                        modifier = Modifier.width(barWidth),
                        chrome = chrome,
                    )
                }
            }
        }
        listOf(LEADING, TITLE, TRAILING).forEach { tag ->
            runCatching { onNodeWithTag(tag).getBoundsInRoot() }.onSuccess { rect -> bounds[tag] = rect }
        }
    }
    return bounds
}

class TitleBarCenteringTest : FunSpec({
    context("window center") {
        test("keeps the title in the middle of the window with a wide start slot") {
            val bounds = boundsOf(
                platform = DesktopPlatform.Linux,
                startWidth = 300.dp,
                endWidth = 40.dp,
                titleWidth = 120.dp,
            )

            bounds.getValue(TITLE).middle shouldBe barWidth / 2
        }

        test("keeps the title in the middle of the window next to the traffic lights of macOS") {
            val bounds = boundsOf(
                platform = DesktopPlatform.MacOs,
                startWidth = 0.dp,
                endWidth = 0.dp,
                titleWidth = 200.dp,
            )

            bounds.getValue(TITLE).middle shouldBe barWidth / 2
        }

        test("keeps the title in the middle of a window whose buttons the system draws") {
            val chrome = TitleBarChrome(insets = CaptionInsets(left = 0.dp, right = 138.dp), isSystemControlled = true)
            val bounds = boundsOf(
                platform = DesktopPlatform.Windows,
                startWidth = 80.dp,
                endWidth = 0.dp,
                titleWidth = 160.dp,
                chrome = chrome,
            )

            bounds.getValue(TITLE).middle shouldBe barWidth / 2
        }

        test("moves the title aside only as far as a slot would cover it") {
            val bounds = boundsOf(
                platform = DesktopPlatform.Linux,
                startWidth = 500.dp,
                endWidth = 0.dp,
                titleWidth = 200.dp,
            )

            bounds.getValue(TITLE).left shouldBe bounds.getValue(LEADING).right + 4.dp
        }

        test("shortens the title only once no room is left") {
            val bounds = boundsOf(
                platform = DesktopPlatform.Linux,
                startWidth = 700.dp,
                endWidth = 100.dp,
                titleWidth = 400.dp,
            )

            val room = bounds.getValue(TRAILING).left - bounds.getValue(LEADING).right - 8.dp
            val title = bounds.getValue(TITLE)
            title.right - title.left shouldBe room
        }

        test("stays in the middle in a language written from right to left") {
            val bounds = boundsOf(
                platform = DesktopPlatform.Linux,
                startWidth = 300.dp,
                endWidth = 40.dp,
                titleWidth = 120.dp,
                direction = LayoutDirection.Rtl,
            )

            bounds.getValue(TITLE).middle shouldBe barWidth / 2
            (bounds.getValue(LEADING).left > bounds.getValue(TITLE).right) shouldBe true
        }
    }

    context("other placements") {
        test("centers the title between the slots") {
            val bounds = boundsOf(
                platform = DesktopPlatform.Linux,
                startWidth = 300.dp,
                endWidth = 0.dp,
                titleWidth = 100.dp,
                placement = TitlePlacement.SlotCenter,
            )

            (bounds.getValue(TITLE).middle > barWidth / 2) shouldBe true
        }

        test("puts the title right after the start slot") {
            val bounds = boundsOf(
                platform = DesktopPlatform.Linux,
                startWidth = 100.dp,
                endWidth = 0.dp,
                titleWidth = 100.dp,
                placement = TitlePlacement.Start,
            )

            bounds.getValue(TITLE).left shouldBe bounds.getValue(LEADING).right + 4.dp
        }
    }
})
