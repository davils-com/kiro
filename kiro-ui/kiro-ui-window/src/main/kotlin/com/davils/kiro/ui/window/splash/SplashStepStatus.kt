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

package com.davils.kiro.ui.window.splash

/**
 * How far a [SplashStep] is.
 *
 * @since 1.0.0
 */
public enum class SplashStepStatus {
    /**
     * The step has not run yet.
     *
     * @since 1.0.0
     */
    Pending,

    /**
     * The step runs now.
     *
     * @since 1.0.0
     */
    Running,

    /**
     * The step is done.
     *
     * @since 1.0.0
     */
    Done,

    /**
     * The step failed.
     *
     * @since 1.0.0
     */
    Failed,

    /**
     * The step was skipped.
     *
     * @since 1.0.0
     */
    Skipped,
    ;

    internal val isFinished: Boolean
        get() = this == Done || this == Skipped
}
