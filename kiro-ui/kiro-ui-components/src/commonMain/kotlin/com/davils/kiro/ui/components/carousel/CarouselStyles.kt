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

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.ChevronLeft
import com.davils.kiro.ui.icons.lucide.ChevronRight
import com.davils.kiro.ui.icons.lucide.Lucide

private const val SPACING_STEPS: Int = 4
private const val OFFSET_STEPS: Int = 12

public fun interface CarouselStyles {
    public fun resolve(theme: Theme): CarouselStyle

    public companion object {
        public val Default: CarouselStyles = CarouselStyles(::defaultCarouselStyle)
    }
}

@Composable
@ReadOnlyComposable
public fun carouselStyle(): CarouselStyle = CarouselStyleKey.current.resolve(theme)

internal fun defaultCarouselStyle(theme: Theme): CarouselStyle = CarouselStyle(
    spacing = theme.spacing.unit * SPACING_STEPS,
    buttonOffset = theme.spacing.unit * OFFSET_STEPS,
    buttonShape = CircleShape,
    previousIcon = Lucide.ChevronLeft,
    nextIcon = Lucide.ChevronRight,
    previousLabel = Messages.Carousel.previous,
    nextLabel = Messages.Carousel.next,
)

public val CarouselStyleKey: StyleKey<CarouselStyles> = StyleKey(name = "carousel") { _ -> CarouselStyles.Default }
