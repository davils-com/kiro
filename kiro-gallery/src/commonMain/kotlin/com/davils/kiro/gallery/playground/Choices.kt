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

package com.davils.kiro.gallery.playground

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import com.davils.kiro.ui.motion.time.Easings

private const val ACCENTS: Int = 4
private const val FROM_PALETTE: String = "From palette"

internal val colorChoices: List<Choice<Color?>> = buildList {
    add(Choice(label = FROM_PALETTE, code = "null") { _ -> null })
    add(Choice(label = "Ink", code = "palette.ink") { palette -> palette.ink })
    add(Choice(label = "Highlight", code = "palette.highlight") { palette -> palette.highlight })
    add(Choice(label = "Shade", code = "palette.shade") { palette -> palette.shade })
    repeat(ACCENTS) { index ->
        add(Choice(label = "Accent ${index + 1}", code = "palette.accent($index)") { palette -> palette.accent(index) })
    }
}

internal val colorListChoices: List<Choice<List<Color>?>> = buildList {
    add(Choice(label = FROM_PALETTE, code = "null") { _ -> null })
    add(Choice(label = "All accents", code = "palette.accents") { palette -> palette.accents })
    add(
        Choice(label = "Ink and accent 1", code = "listOf(palette.ink, palette.accent(0))") { palette ->
            listOf(palette.ink, palette.accent(0))
        },
    )
    add(
        Choice(label = "Accents 1 and 2", code = "listOf(palette.accent(0), palette.accent(1))") { palette ->
            listOf(palette.accent(0), palette.accent(1))
        },
    )
    repeat(ACCENTS) { index ->
        add(
            Choice(label = "Accent ${index + 1} only", code = "listOf(palette.accent($index))") { palette ->
                listOf(palette.accent(index))
            },
        )
    }
}

internal val easingChoices: List<Choice<Easing>> = listOf(
    Choice(label = "Ease", code = "Ease") { _ -> Ease },
    Choice(label = "Ease in out", code = "EaseInOut") { _ -> EaseInOut },
    Choice(label = "Fast out slow in", code = "FastOutSlowInEasing") { _ -> FastOutSlowInEasing },
    Choice(label = "Linear out slow in", code = "LinearOutSlowInEasing") { _ -> LinearOutSlowInEasing },
    Choice(label = "Linear", code = "LinearEasing") { _ -> LinearEasing },
    Choice(label = "Back out", code = "Easings.backOut") { _ -> Easings.backOut },
    Choice(label = "Elastic out", code = "Easings.elasticOut") { _ -> Easings.elasticOut },
    Choice(label = "Bounce out", code = "Easings.bounceOut") { _ -> Easings.bounceOut },
)

internal val blendChoices: List<Choice<BlendMode>> = listOf(
    BlendMode.SrcOver to "SrcOver",
    BlendMode.Plus to "Plus",
    BlendMode.Screen to "Screen",
    BlendMode.Multiply to "Multiply",
    BlendMode.Overlay to "Overlay",
    BlendMode.Softlight to "Softlight",
).map { (mode, name) -> Choice(label = name, code = "BlendMode.$name") { _ -> mode } }

internal val capChoices: List<Choice<StrokeCap>> = listOf(
    StrokeCap.Butt to "Butt",
    StrokeCap.Round to "Round",
    StrokeCap.Square to "Square",
).map { (cap, name) -> Choice(label = name, code = "StrokeCap.$name") { _ -> cap } }
