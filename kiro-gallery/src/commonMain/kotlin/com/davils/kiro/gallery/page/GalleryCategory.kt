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

package com.davils.kiro.gallery.page

import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.icons.lucide.AppWindow
import com.davils.kiro.ui.icons.lucide.Bell
import com.davils.kiro.ui.icons.lucide.Compass
import com.davils.kiro.ui.icons.lucide.Disc3
import com.davils.kiro.ui.icons.lucide.LayoutDashboard
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.MessagesSquare
import com.davils.kiro.ui.icons.lucide.PanelsTopLeft
import com.davils.kiro.ui.icons.lucide.Sparkles
import com.davils.kiro.ui.icons.lucide.Table
import com.davils.kiro.ui.icons.lucide.TextCursorInput
import com.davils.kiro.ui.icons.lucide.Wrench

internal enum class GalleryCategory(val label: String, val icon: IconGraphic) {
    Layout(label = "Layout", icon = Lucide.LayoutDashboard),
    Forms(label = "Forms", icon = Lucide.TextCursorInput),
    Navigation(label = "Navigation", icon = Lucide.Compass),
    Overlays(label = "Overlays", icon = Lucide.PanelsTopLeft),
    Feedback(label = "Feedback", icon = Lucide.Bell),
    Data(label = "Data display", icon = Lucide.Table),
    Conversation(label = "Conversation", icon = Lucide.MessagesSquare),
    Audio(label = "Audio", icon = Lucide.Disc3),
    Motion(label = "Motion", icon = Lucide.Sparkles),
    Window(label = "Window", icon = Lucide.AppWindow),
    Utilities(label = "Utilities", icon = Lucide.Wrench),
}
