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

package com.davils.kiro.ui.components.alertdialog

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalAlertDialogSize = staticCompositionLocalOf<AlertDialogSize> { AlertDialogSize.Default }

private val widths: Map<AlertDialogSize, Dp> = mapOf(
    AlertDialogSize.Default to 384.dp,
    AlertDialogSize.Sm to 320.dp,
)

internal fun maxWidthOf(size: AlertDialogSize): Dp =
    requireNotNull(widths[size]) { "No default width for the alert dialog size $size." }
