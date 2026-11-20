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

package com.davils.kiro.ui.core.state

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val DEFAULT: String = "default"
private const val HOVERED: String = "hovered"
private const val PRESSED: String = "pressed"
private const val FOCUS: String = "focus"
private const val DISABLED: String = "disabled"
private const val SELECTED: String = "selected"
private const val EXPANDED: String = "expanded"
private const val INVALID: String = "invalid"

class StateResolutionTest : FunSpec({
    val value = StateValue(
        default = DEFAULT,
        hovered = HOVERED,
        pressed = PRESSED,
        focusVisible = FOCUS,
        disabled = DISABLED,
        selected = SELECTED,
        expanded = EXPANDED,
        invalid = INVALID,
    )

    context("resolve") {
        test("returns the default without any state") {
            value.resolve(InteractionState()) shouldBe DEFAULT
        }

        test("returns the value of a single active state") {
            value.resolve(InteractionState(isSelected = true)) shouldBe SELECTED
            value.resolve(InteractionState(isExpanded = true)) shouldBe EXPANDED
            value.resolve(InteractionState(isHovered = true)) shouldBe HOVERED
            value.resolve(InteractionState(isFocusVisible = true)) shouldBe FOCUS
            value.resolve(InteractionState(isPressed = true)) shouldBe PRESSED
            value.resolve(InteractionState(isInvalid = true)) shouldBe INVALID
        }

        test("lets disabled win over every other state") {
            val everything = InteractionState(
                isHovered = true,
                isPressed = true,
                isFocusVisible = true,
                isDisabled = true,
                isSelected = true,
                isExpanded = true,
                isInvalid = true,
            )

            value.resolve(everything) shouldBe DISABLED
        }

        test("lets pressed win over hovered like the order of tailwind variants") {
            value.resolve(InteractionState(isHovered = true, isPressed = true)) shouldBe PRESSED
        }

        test("falls back to the default when an active state has no value") {
            val plain = StateValue(default = DEFAULT)

            plain.resolve(InteractionState(isHovered = true)) shouldBe DEFAULT
        }
    }
})
