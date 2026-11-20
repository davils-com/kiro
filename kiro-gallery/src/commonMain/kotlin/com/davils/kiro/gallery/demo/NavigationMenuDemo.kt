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

import androidx.compose.runtime.Composable
import com.davils.kiro.ui.components.navigationmenu.NavigationMenu
import com.davils.kiro.ui.components.navigationmenu.NavigationMenuLink
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun NavigationMenuDemo() {
    NavigationMenu {
        item(label = "Getting started") {
            NavigationMenuLink(onClick = {}) { Text("Introduction") }
            NavigationMenuLink(onClick = {}) { Text("Installation") }
        }
        item(label = "Components") {
            NavigationMenuLink(onClick = {}) { Text("Button") }
            NavigationMenuLink(onClick = {}) { Text("Dialog") }
        }
        menu(label = "Products") {
            item(label = "Overview", onClick = {})
            sub(label = "Switching") {
                item(label = "Data center", onClick = {})
                sub(label = "Campus") {
                    item(label = "Access layer", onClick = {})
                    item(label = "Core layer", onClick = {})
                }
            }
        }
    }
}
