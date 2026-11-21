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

package com.davils.kiro.ui.motion.art

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull

private const val DASH: Float = 4f

class DashesTest : FunSpec({
    test("dashes a line only when its intervals have a length") {
        dashesOf(on = DASH, off = DASH).shouldNotBeNull()
        dashesOf(on = DASH, off = -DASH).shouldNotBeNull()
        dashesOf(on = 0f, off = 0f).shouldBeNull()
        dashesOf(on = -DASH, off = -DASH).shouldBeNull()
        dashesOf(on = Float.NaN, off = DASH).shouldBeNull()
        dashesOf(on = DASH, off = Float.NaN).shouldBeNull()
    }
})
