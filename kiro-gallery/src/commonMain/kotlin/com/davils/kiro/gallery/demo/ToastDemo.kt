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

package com.davils.kiro.gallery.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.label.Label
import com.davils.kiro.ui.components.switch.Switch
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.toast.ToastAction
import com.davils.kiro.ui.components.toast.ToastType
import com.davils.kiro.ui.components.toast.Toaster
import com.davils.kiro.ui.components.toast.ToasterState
import com.davils.kiro.ui.components.toast.rememberToaster
import com.davils.kiro.ui.components.toast.task
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private const val CHUNKS: Int = 10
private const val CHUNK_MILLIS: Int = 250

private val variants: List<Pair<String, ToastType>> = listOf(
    "Default" to ToastType.Default,
    "Info" to ToastType.Info,
    "Success" to ToastType.Success,
    "Warning" to ToastType.Warning,
    "Error" to ToastType.Error,
)

@Composable
internal fun ToastDemo() {
    val toaster = rememberToaster()
    val scope = rememberCoroutineScope()
    var isRich by remember { mutableStateOf(false) }
    var isTimerShown by remember { mutableStateOf(false) }
    Column(modifier = Modifier.width(560.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Switch(checked = isRich, onCheckedChange = { checked -> isRich = checked })
            Label(text = "Rich colors")
            Switch(checked = isTimerShown, onCheckedChange = { checked -> isTimerShown = checked })
            Label(text = "Timer")
        }
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            variants.forEach { (label, type) ->
                Button(
                    onClick = { showVariant(toaster = toaster, label = label, type = type) },
                    variant = ButtonVariant.Outline,
                ) {
                    Text(label)
                }
            }
            Button(
                onClick = { scope.launch { upload(toaster) } },
                variant = ButtonVariant.Outline,
            ) { Text("Upload") }
            Button(
                onClick = {
                    toaster.show(
                        title = "Event has been created",
                        description = "Sunday, December 3 at 9:00 AM",
                        action = ToastAction(label = "Undo") {},
                    )
                },
                variant = ButtonVariant.Outline,
            ) { Text("With action") }
            Button(onClick = { toaster.dismissAll() }, variant = ButtonVariant.Ghost) { Text("Dismiss all") }
        }
    }
    Toaster(toaster = toaster, isRich = isRich, isTimerShown = isTimerShown)
}

private fun showVariant(toaster: ToasterState, label: String, type: ToastType) {
    toaster.show(
        title = "$label notification",
        description = "Swipe it away, hover to pause or close it with the cross.",
        type = type,
    )
}

private suspend fun upload(toaster: ToasterState) {
    toaster.task(
        loading = "Uploading core-01.cfg",
        success = { size: Int -> "Uploaded $size KB" },
        error = { "Upload failed" },
    ) { report ->
        repeat(CHUNKS) { chunk ->
            delay(CHUNK_MILLIS.milliseconds)
            report((chunk + 1f) / CHUNKS)
        }
        CHUNKS * CHUNKS
    }
}
