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

package com.davils.kiro.ui.motion.input

/**
 * Names a number an application feeds into a scene, such as the level of an audio signal or the load of a server, so
 * that an art can react to it. Two keys are the same only if they are the same object.
 *
 * @property name the name of the signal, for diagnostics.
 * @property default the value while the application has not fed one.
 * @since 1.0.0
 */
public class SignalKey(public val name: String, public val default: Float) {
    override fun toString(): String = "SignalKey(name=$name)"
}
