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

package com.davils.kiro.ui.audio.timeline

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import com.davils.kiro.ui.audio.fixture.clipOf
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.seconds

private const val PER_SECOND: Float = 100f
private const val HEIGHT: Float = 120f

class ClipGeometryTest : FunSpec({
    val style = AudioClipStyles.Default.resolve(Theme.light())
    val clip = clipOf()
    val geometry = ClipGeometry(
        clip = clip,
        width = 400f,
        height = HEIGHT,
        perSecond = PER_SECOND,
        style = style,
        density = Density(density = 1f),
    )

    test("finds the part of a clip under the pointer") {
        geometry.zoneAt(Offset(x = 100f, y = geometry.header)) shouldBe ClipZone.FadeIn
        geometry.zoneAt(Offset(x = 300f, y = geometry.header)) shouldBe ClipZone.FadeOut
        geometry.zoneAt(Offset(x = 2f, y = 80f)) shouldBe ClipZone.Start
        geometry.zoneAt(Offset(x = 398f, y = 80f)) shouldBe ClipZone.End
        geometry.zoneAt(Offset(x = 200f, y = geometry.gain)) shouldBe ClipZone.Gain
        geometry.zoneAt(Offset(x = 200f, y = HEIGHT - 2f)) shouldBe ClipZone.Body
    }

    test("turns a drag into an action") {
        val right = Offset(x = PER_SECOND, y = 0f)
        geometry.actionOf(zone = ClipZone.Body, drag = right) shouldBe TimelineAction.Move(clip.id, 3.seconds)
        geometry.actionOf(
            zone = ClipZone.Start,
            drag = right,
        ) shouldBe TimelineAction.Trim(clip.id, ClipEdge.Start, 3.seconds)
        geometry.actionOf(
            zone = ClipZone.End,
            drag = right,
        ) shouldBe TimelineAction.Trim(clip.id, ClipEdge.End, 7.seconds)
        geometry.actionOf(
            zone = ClipZone.FadeIn,
            drag = right,
        ) shouldBe TimelineAction.Fade(clip.id, ClipEdge.Start, 2.seconds)
        geometry.actionOf(
            zone = ClipZone.FadeOut,
            drag = right,
        ) shouldBe TimelineAction.Fade(clip.id, ClipEdge.End, 0.seconds)
        val gain = geometry.actionOf(zone = ClipZone.Gain, drag = Offset(x = 0f, y = -geometry.body))
        gain shouldBe TimelineAction.Gain(
            clip.id,
            Decibels(style.gainRange.ceiling.value - style.gainRange.floor.value),
        )
    }

    test("asks nothing of a locked clip") {
        val fixed = ClipGeometry(
            clip = clipOf(isLocked = true),
            width = 400f,
            height = HEIGHT,
            perSecond = PER_SECOND,
            style = style,
            density = Density(density = 1f),
        )
        fixed.actionOf(zone = ClipZone.Body, drag = Offset.Zero) shouldBe null
    }
})
