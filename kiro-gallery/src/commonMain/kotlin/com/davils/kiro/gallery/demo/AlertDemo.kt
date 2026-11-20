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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.alert.Alert
import com.davils.kiro.ui.components.alert.AlertDescription
import com.davils.kiro.ui.components.alert.AlertTitle
import com.davils.kiro.ui.components.alert.AlertVariant
import com.davils.kiro.ui.icons.lucide.CircleCheck
import com.davils.kiro.ui.icons.lucide.Info
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.OctagonX
import com.davils.kiro.ui.icons.lucide.TriangleExclamationPoint

@Composable
internal fun AlertDemo() {
    Column(modifier = Modifier.width(420.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Alert(icon = Lucide.Info) {
            AlertTitle(text = "Heads up!")
            AlertDescription(text = "You can add components to your app using the kit.")
        }
        Alert(variant = AlertVariant.Destructive, icon = Lucide.OctagonX) {
            AlertTitle(text = "Unable to process your payment.")
            AlertDescription(text = "Please verify your billing information and try again.")
        }
        Alert(variant = AlertVariant.Info, icon = Lucide.Info) {
            AlertTitle(text = "A new version is available.")
            AlertDescription(text = "kide 1.4 brings nested menus and a faster sidebar.")
        }
        Alert(variant = AlertVariant.Success, icon = Lucide.CircleCheck) {
            AlertTitle(text = "Configuration deployed.")
            AlertDescription(text = "All 24 switches accepted the new VLAN layout.")
        }
        Alert(variant = AlertVariant.Warning, icon = Lucide.TriangleExclamationPoint) {
            AlertTitle(text = "Certificate expires soon.")
            AlertDescription(text = "The TLS certificate of core-01 expires in 12 days.")
        }
    }
}
