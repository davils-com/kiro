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

package com.davils.kiro.ui.motion.host

import com.davils.kiro.ui.motion.power.PowerPolicy
import com.davils.kiro.ui.motion.power.PowerState
import com.davils.kiro.ui.motion.power.isAwake
import com.davils.kiro.ui.motion.power.isRunning

internal enum class ClockMode {
    Stopped,
    Pointer,
    Running,
}

internal fun clockModeOf(policy: PowerPolicy, state: PowerState, isSettling: Boolean): ClockMode {
    if (policy.isRunning(state)) return ClockMode.Running
    return idleModeOf(isTracking = policy.isAwake(state) && isSettling)
}

private fun idleModeOf(isTracking: Boolean): ClockMode {
    if (isTracking) return ClockMode.Pointer
    return ClockMode.Stopped
}
