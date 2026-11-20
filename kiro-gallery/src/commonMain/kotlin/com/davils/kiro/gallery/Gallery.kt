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

package com.davils.kiro.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.demo.demos
import com.davils.kiro.gallery.navigation.GalleryNavigation
import com.davils.kiro.gallery.navigation.GalleryNode
import com.davils.kiro.gallery.page.DemoPage
import com.davils.kiro.gallery.page.GalleryCategory
import com.davils.kiro.gallery.page.GalleryDemo
import com.davils.kiro.ui.components.breadcrumb.Breadcrumb
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbItem
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbPage
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbSeparator
import com.davils.kiro.ui.components.scrollarea.ScrollArea
import com.davils.kiro.ui.components.separator.Separator
import com.davils.kiro.ui.components.sidebar.SidebarInset
import com.davils.kiro.ui.components.sidebar.SidebarProvider
import com.davils.kiro.ui.components.sidebar.SidebarTrigger
import com.davils.kiro.ui.components.sidebar.rememberSidebarState
import com.davils.kiro.ui.components.sidebar.tree.rememberSidebarTreeState
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider

@Composable
internal fun Gallery(isDark: Boolean, onDarkChange: (Boolean) -> Unit) {
    val tree = rememberSidebarTreeState<GalleryNode>(
        selected = GalleryNode.Demo(demos.first()),
        expanded = GalleryCategory.entries.map { category -> GalleryNode.Category(category) }.toSet(),
    )
    val selected = (tree.selected as? GalleryNode.Demo)?.demo ?: demos.first()
    ThemeProvider(theme = galleryThemeOf(isDark)) {
        SidebarProvider(state = rememberSidebarState(), modifier = Modifier.background(theme.colors.background)) {
            GalleryNavigation(
                tree = tree,
                isDark = isDark,
                onDarkChange = onDarkChange,
            )
            SidebarInset {
                GalleryHeader(demo = selected)
                Separator()
                ScrollArea(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) { DemoPage(selected) }
                }
            }
        }
    }
}

internal fun galleryThemeOf(isDark: Boolean): Theme {
    if (isDark) return Theme.dark()
    return Theme.light()
}

@Composable
private fun GalleryHeader(demo: GalleryDemo) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SidebarTrigger()
        Breadcrumb {
            BreadcrumbItem { Text(demo.category.label) }
            BreadcrumbSeparator()
            BreadcrumbItem { BreadcrumbPage(text = demo.name) }
        }
    }
}
