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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun SplashFailure(
    message: String,
    onClose: () -> Unit,
    onRetry: (() -> Unit)?,
    labels: SplashLabels,
    style: SplashFailureStyle,
    alignment: Alignment.Horizontal = Alignment.Start,
) {
    Column(verticalArrangement = Arrangement.spacedBy(style.gap), horizontalAlignment = alignment) {
        Text(
            text = message,
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            style = style.textStyle,
            color = style.color,
            maxLines = style.maxLines,
            overflow = TextOverflow.Ellipsis,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(style.actionGap)) {
            onRetry?.let { retry ->
                Button(onClick = retry, variant = style.retryVariant, size = style.buttonSize) {
                    Text(text = labels.retry)
                }
            }
            Button(onClick = onClose, variant = style.closeVariant, size = style.buttonSize) {
                Text(text = labels.close)
            }
        }
    }
}
