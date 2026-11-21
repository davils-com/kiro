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

internal val demos: List<GalleryDemo> = listOf(
    GalleryDemo(name = "Accordion", category = GalleryCategory.Layout) { AccordionDemo() },
    GalleryDemo(name = "Alert", category = GalleryCategory.Feedback) { AlertDemo() },
    GalleryDemo(name = "Alert Dialog", category = GalleryCategory.Overlays) { AlertDialogDemo() },
    GalleryDemo(name = "Aspect Ratio", category = GalleryCategory.Layout) { AspectRatioDemo() },
    GalleryDemo(name = "Attachment", category = GalleryCategory.Conversation) { AttachmentDemo() },
    GalleryDemo(
        name = "Audio Player",
        category = GalleryCategory.Audio,
        sections = listOf(
            DemoSection(name = "Audio Player Bar") { AudioPlayerBarDemo() },
            DemoSection(name = "Audio Player Card") { AudioPlayerCardDemo() },
            DemoSection(name = "Audio Player Full") { AudioPlayerFullDemo() },
            DemoSection(name = "Audio Player Mini") { AudioPlayerMiniDemo() },
        ),
    ),
    GalleryDemo(
        name = "Audio Timeline",
        category = GalleryCategory.Audio,
        sections = listOf(
            DemoSection(name = "Waveform") { WaveformDemo() },
            DemoSection(name = "Audio Clip") { AudioClipDemo() },
            DemoSection(name = "Clip Timeline") { ClipTimelineDemo() },
        ),
    ),
    GalleryDemo(
        name = "Audio Visualizer",
        category = GalleryCategory.Audio,
        sections = listOf(
            DemoSection(name = "Level Meter") { LevelMeterDemo() },
            DemoSection(name = "Goniometer") { GoniometerDemo() },
            DemoSection(name = "Oscilloscope") { OscilloscopeDemo() },
            DemoSection(name = "Spectrum Analyzer") { SpectrumAnalyzerDemo() },
            DemoSection(name = "Spectrogram") { SpectrogramDemo() },
            DemoSection(name = "Spectrum Bars") { SpectrumBarsDemo() },
            DemoSection(name = "Radial Spectrum") { RadialSpectrumDemo() },
            DemoSection(name = "Spectrum Wave") { SpectrumWaveDemo() },
        ),
    ),
    GalleryDemo(name = "Avatar", category = GalleryCategory.Data) { AvatarDemo() },
    GalleryDemo(name = "Badge", category = GalleryCategory.Feedback) { BadgeDemo() },
    GalleryDemo(name = "Brand Mark", category = GalleryCategory.Utilities) { BrandMarkDemo() },
    GalleryDemo(name = "Breadcrumb", category = GalleryCategory.Navigation) { BreadcrumbDemo() },
    GalleryDemo(name = "Bubble", category = GalleryCategory.Conversation) { BubbleDemo() },
    GalleryDemo(name = "Button", category = GalleryCategory.Forms) { ButtonDemo() },
    GalleryDemo(name = "Button Group", category = GalleryCategory.Forms) { ButtonGroupDemo() },
    GalleryDemo(name = "Calendar", category = GalleryCategory.Forms) { CalendarDemo() },
    GalleryDemo(name = "Card", category = GalleryCategory.Layout) { CardDemo() },
    GalleryDemo(name = "Carousel", category = GalleryCategory.Data) { CarouselDemo() },
    GalleryDemo(name = "Chart", category = GalleryCategory.Data) { ChartDemo() },
    GalleryDemo(name = "Checkbox", category = GalleryCategory.Forms) { CheckboxDemo() },
    GalleryDemo(name = "Code Block", category = GalleryCategory.Data) { CodeBlockDemo() },
    GalleryDemo(name = "Collapsible", category = GalleryCategory.Layout) { CollapsibleDemo() },
    GalleryDemo(name = "Combobox", category = GalleryCategory.Forms) { ComboboxDemo() },
    GalleryDemo(name = "Command", category = GalleryCategory.Navigation) { CommandDemo() },
    GalleryDemo(name = "Context Menu", category = GalleryCategory.Navigation) { ContextMenuDemo() },
    GalleryDemo(name = "Data Table", category = GalleryCategory.Data) { DataTableDemo() },
    GalleryDemo(name = "Date Picker", category = GalleryCategory.Forms) { DatePickerDemo() },
    GalleryDemo(name = "Dialog", category = GalleryCategory.Overlays) { DialogDemo() },
    GalleryDemo(name = "Direction", category = GalleryCategory.Utilities) { DirectionDemo() },
    GalleryDemo(name = "Drawer", category = GalleryCategory.Overlays) { DrawerDemo() },
    GalleryDemo(name = "Dropdown Menu", category = GalleryCategory.Navigation) { DropdownMenuDemo() },
    GalleryDemo(name = "Empty", category = GalleryCategory.Feedback) { EmptyDemo() },
    GalleryDemo(name = "Field", category = GalleryCategory.Forms) { FieldDemo() },
    GalleryDemo(
        name = "Fields",
        category = GalleryCategory.Motion,
        sections = listOf(
            DemoSection(name = "Contour Art") { ContourArtDemo() },
            DemoSection(name = "Halftone Art") { HalftoneArtDemo() },
            DemoSection(name = "Low Poly Art") { LowPolyArtDemo() },
            DemoSection(name = "Ridge Art") { RidgeArtDemo() },
            DemoSection(name = "Voronoi Art") { VoronoiArtDemo() },
        ),
    ),
    GalleryDemo(
        name = "Geometry",
        category = GalleryCategory.Motion,
        sections = listOf(
            DemoSection(name = "Blueprint Art") { BlueprintArtDemo() },
            DemoSection(name = "Isometric City Art") { IsometricCityArtDemo() },
            DemoSection(name = "Lissajous Art") { LissajousArtDemo() },
            DemoSection(name = "Orbit Art") { OrbitArtDemo() },
            DemoSection(name = "Polygons Art") { PolygonsArtDemo() },
            DemoSection(name = "Radar Art") { RadarArtDemo() },
            DemoSection(name = "Spirograph Art") { SpirographArtDemo() },
            DemoSection(name = "Wireframe Art") { WireframeArtDemo() },
        ),
    ),
    GalleryDemo(
        name = "Gradients",
        category = GalleryCategory.Motion,
        sections = listOf(
            DemoSection(name = "Aurora Art") { AuroraArtDemo() },
            DemoSection(name = "Gradient Art") { GradientArtDemo() },
        ),
    ),
    GalleryDemo(
        name = "Grids",
        category = GalleryCategory.Motion,
        sections = listOf(
            DemoSection(name = "Flickering Grid Art") { FlickeringGridArtDemo() },
            DemoSection(name = "Grid Pattern Art") { GridPatternArtDemo() },
            DemoSection(name = "Matrix Art") { MatrixArtDemo() },
            DemoSection(name = "Retro Grid Art") { RetroGridArtDemo() },
        ),
    ),
    GalleryDemo(name = "Hover Card", category = GalleryCategory.Overlays) { HoverCardDemo() },
    GalleryDemo(name = "Input", category = GalleryCategory.Forms) { InputDemo() },
    GalleryDemo(name = "Input Group", category = GalleryCategory.Forms) { InputGroupDemo() },
    GalleryDemo(name = "Input OTP", category = GalleryCategory.Forms) { InputOTPDemo() },
    GalleryDemo(name = "Item", category = GalleryCategory.Data) { ItemDemo() },
    GalleryDemo(name = "Kbd", category = GalleryCategory.Data) { KbdDemo() },
    GalleryDemo(name = "Label", category = GalleryCategory.Forms) { LabelDemo() },
    GalleryDemo(name = "Layers", category = GalleryCategory.Motion) { LayersDemo() },
    GalleryDemo(
        name = "Lines",
        category = GalleryCategory.Motion,
        sections = listOf(
            DemoSection(name = "Beams Art") { BeamsArtDemo() },
            DemoSection(name = "Ripple Art") { RippleArtDemo() },
        ),
    ),
    GalleryDemo(name = "Marker", category = GalleryCategory.Data) { MarkerDemo() },
    GalleryDemo(name = "Menubar", category = GalleryCategory.Navigation) { MenubarDemo() },
    GalleryDemo(name = "Message", category = GalleryCategory.Conversation) { MessageDemo() },
    GalleryDemo(name = "Message Scroller", category = GalleryCategory.Conversation) { MessageScrollerDemo() },
    GalleryDemo(name = "Motion Playground", category = GalleryCategory.Motion) { MotionPlaygroundDemo() },
    GalleryDemo(name = "Native Select", category = GalleryCategory.Forms) { NativeSelectDemo() },
    GalleryDemo(name = "Navigation Menu", category = GalleryCategory.Navigation) { NavigationMenuDemo() },
    GalleryDemo(name = "Pagination", category = GalleryCategory.Navigation) { PaginationDemo() },
    GalleryDemo(
        name = "Particles",
        category = GalleryCategory.Motion,
        sections = listOf(
            DemoSection(name = "Bokeh Art") { BokehArtDemo() },
            DemoSection(name = "Flow Field Art") { FlowFieldArtDemo() },
            DemoSection(name = "Meteors Art") { MeteorsArtDemo() },
            DemoSection(name = "Particles Art") { ParticlesArtDemo() },
            DemoSection(name = "Starfield Art") { StarfieldArtDemo() },
        ),
    ),
    GalleryDemo(name = "Popover", category = GalleryCategory.Overlays) { PopoverDemo() },
    GalleryDemo(name = "Progress", category = GalleryCategory.Feedback) { ProgressDemo() },
    GalleryDemo(name = "Questionnaire", category = GalleryCategory.Conversation) { QuestionnaireDemo() },
    GalleryDemo(name = "Radio Group", category = GalleryCategory.Forms) { RadioGroupDemo() },
    GalleryDemo(name = "Resizable", category = GalleryCategory.Layout) { ResizableDemo() },
    GalleryDemo(
        name = "Scene Presets",
        category = GalleryCategory.Motion,
        sections = listOf(
            DemoSection(name = "Skyline") { SkylineDemo() },
            DemoSection(name = "Orbits") { OrbitsDemo() },
            DemoSection(name = "Signal") { SignalDemo() },
            DemoSection(name = "Topography") { TopographyDemo() },
            DemoSection(name = "Maze") { MazeDemo() },
            DemoSection(name = "Composition") { CompositionDemo() },
            DemoSection(name = "Halftone") { HalftoneDemo() },
            DemoSection(name = "Flow") { FlowDemo() },
            DemoSection(name = "Spirograph") { SpirographDemo() },
        ),
    ),
    GalleryDemo(name = "Scroll Area", category = GalleryCategory.Layout) { ScrollAreaDemo() },
    GalleryDemo(name = "Select", category = GalleryCategory.Forms) { SelectDemo() },
    GalleryDemo(name = "Separator", category = GalleryCategory.Layout) { SeparatorDemo() },
    GalleryDemo(
        name = "Shaders",
        category = GalleryCategory.Motion,
        sections = listOf(
            DemoSection(name = "Grain Art") { GrainArtDemo() },
            DemoSection(name = "Mesh Gradient Art") { MeshGradientArtDemo() },
            DemoSection(name = "Silk Art") { SilkArtDemo() },
        ),
    ),
    GalleryDemo(name = "Sheet", category = GalleryCategory.Overlays) { SheetDemo() },
    GalleryDemo(name = "Sidebar", category = GalleryCategory.Layout) { SidebarDemo() },
    GalleryDemo(name = "Skeleton", category = GalleryCategory.Feedback) { SkeletonDemo() },
    GalleryDemo(name = "Slider", category = GalleryCategory.Forms) { SliderDemo() },
    GalleryDemo(name = "Spinner", category = GalleryCategory.Feedback) { SpinnerDemo() },
    GalleryDemo(name = "Switch", category = GalleryCategory.Forms) { SwitchDemo() },
    GalleryDemo(name = "Table", category = GalleryCategory.Data) { TableDemo() },
    GalleryDemo(name = "Tabs", category = GalleryCategory.Layout) { TabsDemo() },
    GalleryDemo(name = "Textarea", category = GalleryCategory.Forms) { TextareaDemo() },
    GalleryDemo(
        name = "Tiling",
        category = GalleryCategory.Motion,
        sections = listOf(
            DemoSection(name = "Bauhaus Art") { BauhausArtDemo() },
            DemoSection(name = "Checker Morph Art") { CheckerMorphArtDemo() },
            DemoSection(name = "Hex Flip Art") { HexFlipArtDemo() },
            DemoSection(name = "Truchet Art") { TruchetArtDemo() },
            DemoSection(name = "Weave Art") { WeaveArtDemo() },
        ),
    ),
    GalleryDemo(name = "Toast", category = GalleryCategory.Overlays) { ToastDemo() },
    GalleryDemo(name = "Toggle", category = GalleryCategory.Forms) { ToggleDemo() },
    GalleryDemo(name = "Toggle Group", category = GalleryCategory.Forms) { ToggleGroupDemo() },
    GalleryDemo(name = "Tooltip", category = GalleryCategory.Overlays) { TooltipDemo() },
    GalleryDemo(name = "Trace", category = GalleryCategory.Conversation) { TraceDemo() },
    GalleryDemo(name = "Typewriter", category = GalleryCategory.Motion) { TypewriterDemo() },
) +
    platformDemos()
