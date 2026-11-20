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

package com.davils.kiro.ui.components.carousel

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.button.buttonStyle
import com.davils.kiro.ui.components.primitive.layout.mirroredInRtl
import com.davils.kiro.ui.icons.IconGraphic
import kotlinx.coroutines.launch

private const val QUARTER_TURN: Float = 90f

@Composable
public fun Carousel(
    count: Int,
    modifier: Modifier = Modifier,
    state: PagerState = rememberPagerState(pageCount = { count }),
    orientation: Orientation = Orientation.Horizontal,
    itemsPerView: Int = 1,
    isNavigationShown: Boolean = true,
    style: CarouselStyle = carouselStyle(),
    item: @Composable (Int) -> Unit,
) {
    require(itemsPerView >= 1) { "A carousel shows at least one item at a time, not $itemsPerView." }
    val scope = rememberCoroutineScope()
    val sign = signOf(
        isReversed = LocalLayoutDirection.current == LayoutDirection.Rtl && orientation == Orientation.Horizontal,
    )
    val step = { delta: Int -> scope.launch { state.animateScrollToPage(state.currentPage + delta) } }
    val keys = Modifier.onPreviewKeyEvent { event ->
        val delta = keyDeltas[event.key]?.takeIf { event.type == KeyEventType.KeyDown }
        delta?.let { amount -> step(amount * sign) }
        delta != null
    }
    val pageSize = PerViewPageSize(itemsPerView)
    Box(modifier = modifier.semantics { isTraversalGroup = true }.then(keys).focusable()) {
        when (orientation) {
            Orientation.Horizontal -> HorizontalPager(
                state = state,
                pageSize = pageSize,
                pageSpacing = style.spacing,
            ) { page -> item(page) }

            Orientation.Vertical -> VerticalPager(
                state = state,
                pageSize = pageSize,
                pageSpacing = style.spacing,
            ) { page -> item(page) }
        }
        if (isNavigationShown) {
            NavigationButton(
                icon = style.previousIcon,
                label = style.previousLabel,
                isEnabled = state.canScrollBackward,
                position = positionOf(orientation = orientation, isStart = true, offset = style.buttonOffset),
                style = style,
                onClick = { step(-1) },
            )
            NavigationButton(
                icon = style.nextIcon,
                label = style.nextLabel,
                isEnabled = state.canScrollForward,
                position = positionOf(orientation = orientation, isStart = false, offset = style.buttonOffset),
                style = style,
                onClick = { step(1) },
            )
        }
    }
}

private val keyDeltas: Map<Key, Int> = mapOf(Key.DirectionLeft to -1, Key.DirectionRight to 1)

@Composable
private fun BoxScope.NavigationButton(
    icon: IconGraphic,
    label: String,
    isEnabled: Boolean,
    position: ButtonPosition,
    style: CarouselStyle,
    onClick: () -> Unit,
) {
    val base = buttonStyle(ButtonVariant.Outline, ButtonSize.IconSm)
    Button(
        onClick = onClick,
        modifier = Modifier
            .align(position.alignment)
            .offset(position.offset.x, position.offset.y)
            .rotate(position.rotation)
            .mirroredInRtl()
            .semantics { contentDescription = label },
        variant = ButtonVariant.Outline,
        size = ButtonSize.IconSm,
        enabled = isEnabled,
        style = base.copy(shape = style.buttonShape),
    ) {
        Icon(icon = icon, placement = IconPlacement.Inline)
    }
}

private fun positionOf(orientation: Orientation, isStart: Boolean, offset: Dp): ButtonPosition = when (orientation) {
    Orientation.Horizontal -> ButtonPosition(
        alignment = Alignment.CenterStart.takeIf { isStart } ?: Alignment.CenterEnd,
        offset = DpOffset(x = -offset, y = 0.dp).takeIf { isStart } ?: DpOffset(x = offset, y = 0.dp),
        rotation = 0f,
    )

    Orientation.Vertical -> ButtonPosition(
        alignment = Alignment.TopCenter.takeIf { isStart } ?: Alignment.BottomCenter,
        offset = DpOffset(x = 0.dp, y = -offset).takeIf { isStart } ?: DpOffset(x = 0.dp, y = offset),
        rotation = QUARTER_TURN,
    )
}

private fun signOf(isReversed: Boolean): Int {
    if (isReversed) return -1
    return 1
}
