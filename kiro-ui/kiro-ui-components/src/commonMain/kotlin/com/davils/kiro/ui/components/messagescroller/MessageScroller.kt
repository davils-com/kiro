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

package com.davils.kiro.ui.components.messagescroller

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.scrollarea.ScrollAreaLayout
import com.davils.kiro.ui.components.scrollarea.ScrollDirection
import com.davils.kiro.ui.components.scrollarea.scrollAreaStyle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

/**
 * Shows a vertically scrolling column of messages, such as a chat, that follows new content at its end.
 *
 * While the user is at the end, the scroller scrolls along when the content grows; once the user scrolls away it
 * stops following until they return to the end. While the end is not reached, the bottom edge fades out and a button
 * that scrolls to the end is shown.
 *
 * @param modifier the modifier of the scroller.
 * @param state the scroll position of the scroller.
 * @param isButtonShown whether the button that scrolls to the end is shown while the end is not reached.
 * @param style the style of the message scroller.
 * @param content the messages, laid out in a column.
 * @since 1.0.0
 */
@Composable
public fun MessageScroller(
    modifier: Modifier = Modifier,
    state: MessageScrollerState = rememberMessageScrollerState(),
    isButtonShown: Boolean = true,
    style: MessageScrollerStyle = messageScrollerStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val scroll = state.scroll
    LaunchedEffect(state) {
        val positions = snapshotFlow { scroll.value }
        positions.drop(1).collect { followIfUser(state) }
    }
    LaunchedEffect(state) {
        snapshotFlow { scroll.maxValue }.collectLatest { max -> if (state.isFollowing) state.follow(max) }
    }
    Box(modifier = modifier) {
        ScrollAreaLayout(
            modifier = Modifier.fillMaxSize(),
            direction = ScrollDirection.Vertical,
            verticalState = scroll,
            horizontalState = rememberScrollState(),
            style = scrollAreaStyle(),
            viewport = Modifier.fadeEnd(length = style.fadeLength) { !state.isAtEnd },
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(style.gap),
                content = content,
            )
        }
        ScrollButton(
            state = state,
            isActive = isButtonShown && !state.isAtEnd,
            style = style,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = style.buttonInset),
        )
    }
}

@Composable
private fun ScrollButton(
    state: MessageScrollerState,
    isActive: Boolean,
    style: MessageScrollerStyle,
    modifier: Modifier,
) {
    val progress = animateFloatAsState(
        targetValue = 1f.takeIf { isActive } ?: 0f,
        animationSpec = style.showAnimation.takeIf { isActive } ?: style.hideAnimation,
    )
    val isVisible by remember { derivedStateOf { progress.value > 0f } }
    if (!isVisible) return
    val scope = rememberCoroutineScope()
    val entrance = Modifier.graphicsLayer {
        val shown = progress.value
        val scale = style.hiddenScale + (1f - style.hiddenScale) * shown
        alpha = shown
        scaleX = scale
        scaleY = scale
        translationY = (1f - shown) * size.height
    }
    val label = style.label
    Button(
        onClick = { scope.launch { state.scrollToEnd() } },
        modifier = modifier
            .then(entrance)
            .semantics { contentDescription = label },
        variant = style.buttonVariant,
        size = style.buttonSize,
    ) {
        Icon(icon = style.icon, placement = IconPlacement.Inline)
    }
}

private fun followIfUser(state: MessageScrollerState) {
    if (state.isAutoScrolling) return
    state.isFollowing = state.isAtEnd
}

private fun Modifier.fadeEnd(length: Dp, isFaded: () -> Boolean): Modifier = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        if (!isFaded()) return@drawWithContent
        val brush = Brush.verticalGradient(
            colors = listOf(Color.Black, Color.Transparent),
            startY = size.height - length.toPx(),
            endY = size.height,
        )
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }
