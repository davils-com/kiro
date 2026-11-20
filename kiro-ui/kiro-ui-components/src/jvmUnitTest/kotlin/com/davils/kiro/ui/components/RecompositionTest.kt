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

package com.davils.kiro.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.components.alert.Alert
import com.davils.kiro.ui.components.alert.AlertDescription
import com.davils.kiro.ui.components.alert.AlertTitle
import com.davils.kiro.ui.components.alert.AlertVariant
import com.davils.kiro.ui.components.alert.alertStyle
import com.davils.kiro.ui.components.aspectratio.AspectRatio
import com.davils.kiro.ui.components.avatar.Avatar
import com.davils.kiro.ui.components.avatar.AvatarGroup
import com.davils.kiro.ui.components.avatar.AvatarGroupCount
import com.davils.kiro.ui.components.avatar.AvatarSize
import com.davils.kiro.ui.components.avatar.avatarStyle
import com.davils.kiro.ui.components.badge.Badge
import com.davils.kiro.ui.components.badge.BadgeVariant
import com.davils.kiro.ui.components.badge.badgeStyle
import com.davils.kiro.ui.components.brand.BrandMark
import com.davils.kiro.ui.components.brand.brandMarkStyle
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.button.buttonStyle
import com.davils.kiro.ui.components.buttongroup.ButtonGroup
import com.davils.kiro.ui.components.buttongroup.ButtonGroupSeparator
import com.davils.kiro.ui.components.buttongroup.ButtonGroupText
import com.davils.kiro.ui.components.buttongroup.buttonGroupStyle
import com.davils.kiro.ui.components.card.Card
import com.davils.kiro.ui.components.card.CardContent
import com.davils.kiro.ui.components.card.CardDescription
import com.davils.kiro.ui.components.card.CardFooter
import com.davils.kiro.ui.components.card.CardHeader
import com.davils.kiro.ui.components.card.CardSize
import com.davils.kiro.ui.components.card.CardTitle
import com.davils.kiro.ui.components.card.cardStyle
import com.davils.kiro.ui.components.direction.Direction
import com.davils.kiro.ui.components.empty.Empty
import com.davils.kiro.ui.components.empty.EmptyContent
import com.davils.kiro.ui.components.empty.EmptyDescription
import com.davils.kiro.ui.components.empty.EmptyHeader
import com.davils.kiro.ui.components.empty.EmptyMedia
import com.davils.kiro.ui.components.empty.EmptyMediaVariant
import com.davils.kiro.ui.components.empty.EmptyTitle
import com.davils.kiro.ui.components.empty.emptyStyle
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.item.Item
import com.davils.kiro.ui.components.item.ItemActions
import com.davils.kiro.ui.components.item.ItemContent
import com.davils.kiro.ui.components.item.ItemDescription
import com.davils.kiro.ui.components.item.ItemMedia
import com.davils.kiro.ui.components.item.ItemMediaVariant
import com.davils.kiro.ui.components.item.ItemSize
import com.davils.kiro.ui.components.item.ItemTitle
import com.davils.kiro.ui.components.item.ItemVariant
import com.davils.kiro.ui.components.item.itemStyle
import com.davils.kiro.ui.components.kbd.Kbd
import com.davils.kiro.ui.components.kbd.KbdGroup
import com.davils.kiro.ui.components.kbd.kbdStyle
import com.davils.kiro.ui.components.label.Label
import com.davils.kiro.ui.components.label.labelStyle
import com.davils.kiro.ui.components.progress.Progress
import com.davils.kiro.ui.components.progress.progressStyle
import com.davils.kiro.ui.components.separator.Separator
import com.davils.kiro.ui.components.separator.separatorStyle
import com.davils.kiro.ui.components.skeleton.Skeleton
import com.davils.kiro.ui.components.skeleton.skeletonStyle
import com.davils.kiro.ui.components.spinner.Spinner
import com.davils.kiro.ui.components.spinner.spinnerStyle
import com.davils.kiro.ui.components.text.Text
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val ROUNDS: Int = 3
private const val FIXED: String = "fixed"

@Composable
private fun Everything(label: String, isOdd: Boolean, tick: Int) {
    check(tick >= 0)
    val source = remember { MutableInteractionSource() }
    Text(
        text = label,
        modifier = Modifier,
        style = TextStyle(),
        color = Color.Red,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
    Label(text = label, modifier = Modifier, isDisabled = isOdd, style = labelStyle())
    KbdGroup(modifier = Modifier, style = kbdStyle()) {
        Kbd(modifier = Modifier, style = kbdStyle()) { Text(label) }
    }
    Separator(modifier = Modifier, orientation = Orientation.Vertical, style = separatorStyle())
    Spinner(modifier = Modifier, contentDescription = label, style = spinnerStyle())
    Skeleton(modifier = Modifier, style = skeletonStyle())
    BrandMark(modifier = Modifier, style = brandMarkStyle())
    Badge(
        modifier = Modifier,
        variant = BadgeVariant.Outline,
        onClick = {},
        style = badgeStyle(BadgeVariant.Outline),
        interactionSource = source,
    ) {
        Text(label)
    }
    Button(
        onClick = {},
        modifier = Modifier,
        variant = ButtonVariant.Outline,
        size = ButtonSize.Sm,
        enabled = !isOdd,
        style = buttonStyle(ButtonVariant.Outline, ButtonSize.Sm),
        interactionSource = source,
    ) {
        Text(label)
    }
}

@Composable
private fun Panels(label: String, tick: Int) {
    check(tick >= 0)
    Card(modifier = Modifier, size = CardSize.Sm, style = cardStyle(CardSize.Sm)) {
        CardHeader(modifier = Modifier, action = { Text(label) }) {
            CardTitle(text = label, modifier = Modifier)
            CardDescription(text = label, modifier = Modifier)
        }
        CardContent(modifier = Modifier) { Text(label) }
        CardFooter(modifier = Modifier) { Text(label) }
    }
    Alert(
        modifier = Modifier,
        variant = AlertVariant.Destructive,
        icon = null,
        action = { Text(label) },
        style = alertStyle(AlertVariant.Destructive),
    ) {
        AlertTitle(text = label, modifier = Modifier)
        AlertDescription(text = label, modifier = Modifier)
    }
}

@Composable
private fun People(label: String, tick: Int) {
    check(tick >= 0)
    Empty(modifier = Modifier, style = emptyStyle()) {
        EmptyHeader(modifier = Modifier) {
            EmptyMedia(modifier = Modifier, variant = EmptyMediaVariant.Icon) { Text(label) }
            EmptyTitle(text = label, modifier = Modifier)
            EmptyDescription(text = label, modifier = Modifier)
        }
        EmptyContent(modifier = Modifier) { Text(label) }
    }
    AvatarGroup(modifier = Modifier, size = AvatarSize.Sm, style = avatarStyle(AvatarSize.Sm)) {
        Avatar(
            modifier = Modifier,
            size = AvatarSize.Sm,
            image = ColorPainter(Color.Red),
            contentDescription = label,
            style = avatarStyle(AvatarSize.Sm),
            badge = { Text(label) },
        ) {
            Text(label)
        }
        AvatarGroupCount(modifier = Modifier) { Text(label) }
    }
}

@Composable
private fun Groups(label: String, isOdd: Boolean, value: Float, tick: Int) {
    check(tick >= 0)
    AspectRatio(ratio = 2f, modifier = Modifier) { Box(modifier = Modifier) }
    Direction(direction = LayoutDirection.Rtl) { Text(label) }
    Progress(
        value = value,
        modifier = Modifier,
        label = label,
        isValueShown = isOdd,
        style = progressStyle(),
    )
    ButtonGroup(modifier = Modifier, orientation = Orientation.Vertical, style = buttonGroupStyle()) {
        item { ButtonGroupText(modifier = Modifier, style = buttonGroupStyle()) { Text(label) } }
        item { ButtonGroupSeparator(modifier = Modifier, style = buttonGroupStyle()) }
    }
    Item(
        modifier = Modifier,
        variant = ItemVariant.Muted,
        size = ItemSize.Xs,
        onClick = {},
        style = itemStyle(ItemVariant.Muted, ItemSize.Xs),
        header = { Text(label) },
        footer = { Text(label) },
    ) {
        ItemMedia(modifier = Modifier, variant = ItemMediaVariant.Image) { Text(label) }
        ItemContent(modifier = Modifier) {
            ItemTitle(text = label, modifier = Modifier)
            ItemDescription(text = label, modifier = Modifier)
        }
        ItemActions(modifier = Modifier) { Text(label) }
    }
}

@OptIn(ExperimentalTestApi::class)
class RecompositionTest : FunSpec({
    context("components") {
        test("take every argument explicitly and recompose with changed and unchanged arguments") {
            var round by mutableIntStateOf(0)
            var rendered = -1

            runComposeUiTest {
                setContent {
                    Themed {
                        val isOdd = round % 2 == 1
                        val label = "Round $round"
                        Everything(label = label, isOdd = isOdd, tick = round)
                        Everything(label = FIXED, isOdd = false, tick = round)
                        Panels(label = label, tick = round)
                        Panels(label = FIXED, tick = round)
                        People(label = label, tick = round)
                        People(label = FIXED, tick = round)
                        Groups(label = label, isOdd = isOdd, value = round.toFloat() / ROUNDS, tick = round)
                        Groups(label = FIXED, isOdd = false, value = 0f, tick = round)
                        val stable = remember { FIXED.uppercase() }
                        val isNever = round < 0
                        Everything(label = stable, isOdd = isNever, tick = round)
                        Panels(label = stable, tick = round)
                        People(label = stable, tick = round)
                        Groups(label = stable, isOdd = isNever, value = stable.length.toFloat(), tick = round)
                        rendered = round
                    }
                }
                repeat(ROUNDS) {
                    round++
                    waitForIdle()
                }
            }

            rendered shouldBe ROUNDS
        }
    }
})
