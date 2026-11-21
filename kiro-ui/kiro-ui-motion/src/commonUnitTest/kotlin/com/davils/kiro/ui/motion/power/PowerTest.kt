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

package com.davils.kiro.ui.motion.power

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain

private val awake: PowerState = PowerState(
    isAnimated = true,
    isFocused = true,
    isVisible = true,
    isReducedMotion = false,
    isStatic = false,
    isPlaying = true,
)

class PowerTest : FunSpec({
    test("runs while animated, focused, visible, playing and moving") {
        PowerPolicy().isRunning(awake) shouldBe true
    }

    test("stops while unfocused, hidden or under reduced motion") {
        val policy = PowerPolicy()

        policy.isRunning(awake.copy(isFocused = false)) shouldBe false
        policy.isRunning(awake.copy(isVisible = false)) shouldBe false
        policy.isRunning(awake.copy(isReducedMotion = true)) shouldBe false
        policy.isRunning(awake.copy(isAnimated = false)) shouldBe false
    }

    test("keeps running when the policy does not pause") {
        val policy = PowerPolicy(
            isPausedWhenUnfocused = false,
            isPausedWhenHidden = false,
            isPausedWhenReducedMotion = false,
        )
        val asleep = awake.copy(isFocused = false, isVisible = false, isReducedMotion = true)

        policy.isRunning(asleep) shouldBe true
        policy.isStill(isAnimated = true, isReducedMotion = true) shouldBe false
        policy.isStill(isAnimated = false, isReducedMotion = false) shouldBe true
    }

    test("stays awake but does not run while paused or static") {
        val policy = PowerPolicy()

        policy.isAwake(awake.copy(isPlaying = false)) shouldBe true
        policy.isRunning(awake.copy(isPlaying = false)) shouldBe false
        policy.isRunning(awake.copy(isStatic = true)) shouldBe false
    }

    test("is a value") {
        val policy = PowerPolicy(maxFrameRate = 30)

        policy.copy() shouldBe policy
        policy.copy().hashCode() shouldBe policy.hashCode()
        policy.copy(maxFrameRate = null) shouldNotBe policy
        policy.toString() shouldContain "maxFrameRate=30"
        PowerPolicy().toString() shouldContain "maxFrameRate=null"
        policy.equals(awake) shouldBe false
    }
})
