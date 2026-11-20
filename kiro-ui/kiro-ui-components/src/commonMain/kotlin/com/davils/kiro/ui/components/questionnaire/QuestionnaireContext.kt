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

package com.davils.kiro.ui.components.questionnaire

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalQuestionnaire = staticCompositionLocalOf<QuestionnaireContext?> { null }

@Stable
internal class QuestionnaireContext(
    val state: QuestionnaireState,
    val style: QuestionnaireStyle,
    val onSubmit: (Map<QuestionKey, Answer>) -> Unit,
) {
    val shortcuts: SnapshotStateMap<Char, String> = mutableStateMapOf()

    fun advance(step: QuestionnaireStep) {
        if (step is QuestionnaireStep.Submitted) onSubmit(step.answers)
    }
}
