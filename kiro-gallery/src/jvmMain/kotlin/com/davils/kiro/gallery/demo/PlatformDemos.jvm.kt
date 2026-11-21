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

import com.davils.kiro.gallery.page.DemoSection
import com.davils.kiro.gallery.page.GalleryCategory
import com.davils.kiro.gallery.page.GalleryDemo

internal actual fun platformDemos(): List<GalleryDemo> = listOf(
    GalleryDemo(
        name = "Splash Screen",
        category = GalleryCategory.Window,
        sections = listOf(
            DemoSection(name = "Split Splash") { SplitSplashDemo() },
            DemoSection(name = "Centered Splash") { CenteredSplashDemo() },
            DemoSection(name = "Checklist Splash") { ChecklistSplashDemo() },
            DemoSection(name = "Minimal Splash") { MinimalSplashDemo() },
            DemoSection(name = "Hero Splash") { HeroSplashDemo() },
        ),
    ),
    GalleryDemo(name = "Title Bar", category = GalleryCategory.Window) { TitleBarDemo() },
)
