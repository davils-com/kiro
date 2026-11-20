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
import com.davils.kiro.ui.components.breadcrumb.Breadcrumb
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbEllipsis
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbItem
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbLink
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbPage
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbSeparator
import com.davils.kiro.ui.icons.lucide.Building
import com.davils.kiro.ui.icons.lucide.Folder
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Server

@Composable
internal fun BreadcrumbDemo() {
    Breadcrumb {
        BreadcrumbItem { BreadcrumbLink(text = "Home", onClick = {}) }
        BreadcrumbSeparator()
        BreadcrumbItem {
            BreadcrumbEllipsis(contentDescription = "More") {
                item(label = "Projects", onClick = {}, icon = Lucide.Folder)
                item(label = "Datacenter Frankfurt", onClick = {}, icon = Lucide.Building)
                item(label = "Rack A12", onClick = {}, icon = Lucide.Server)
            }
        }
        BreadcrumbSeparator()
        BreadcrumbItem { BreadcrumbLink(text = "Switch core-01", onClick = {}) }
        BreadcrumbSeparator()
        BreadcrumbItem { BreadcrumbPage(text = "Gi1/0/24") }
    }
}
