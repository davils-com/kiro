# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

### Added

- **The theme of Kiro in `kiro-ui-core`.** Color roles with light and dark schemes, typography with the bundled
  variable fonts Inter, Archivo and Geist Mono, radii, spacing, border widths, elevation, motion, the focus ring, icon
  tokens and the `ComponentStyles` registry, through which a theme replaces or changes the style of every component.
- **The Lucide icons in `kiro-ui-icons`.** Every icon of Lucide 1.71.0 that is not deprecated, generated from its SVG
  by the build, with the `Icon` composable and a catalog for search.
- **The components of Kiro in `kiro-ui-components`**, migrated from the user interface of Kide to Compose
  Multiplatform for the JVM and WebAssembly, without Material and without localization of their own.
- **The audio players of Kiro in `kiro-ui-audio`**, migrated from the media components of Kide: `AudioPlayerBar`,
  `AudioPlayerCard`, `AudioPlayerFull` and `AudioPlayerMini` as separate components with a style each, sharing the
  `AudioPlayerState`, the controls and their `AudioControlsStyle`, tinted by the cover of the track.
- **The audio visualizers of Kiro in `kiro-ui-audio`**, modeled on the panels of editing programs and on the
  visualizers of desktops. They only show values: the caller measures, smooths and holds them, and Kiro decodes, plays
  and analyzes nothing. The values arrive as `AudioLevels`, `AudioSpectrum`, `AudioScope` and `WaveformPeaks` over
  `Samples`, `Decibels`, `DecibelRange` and `FrequencyRange`.
  - Meters: `LevelMeter` with zones, peak hold, clip indicators, a scale and readouts, upright or lying, continuous or
    segmented, and `CorrelationMeter`.
  - Scopes: `Goniometer` with dots or lines on a round or square grid, and `Oscilloscope`, overlaid or split.
  - Analyzers: `SpectrumAnalyzer` with a logarithmic grid, smooth, linear or stepped curves, held peaks and a
    crosshair, and `Spectrogram`, a scrolling heat map over a `SpectrogramState`.
  - Desktop visualizers: `SpectrumBars` with peak caps, blocks, a reflection and a centered baseline,
    `RadialSpectrum` around a content of its own that pulses with the lowest band, and `SpectrumWave` in layers.
  - Timeline: `Waveform` that seeks, and the clips of an editing program, `AudioClip`, `ClipTrack`, `TimeRuler` and
    `ClipTimeline`, which ask through `TimelineAction` to select, move, trim, fade, change the gain of a clip, seek,
    scroll, zoom and switch mute, solo and record of a track, carried out by `TimelineState`.
- **The gallery of Kiro in `kiro-gallery`**, which shows every component in the light and the dark theme with the
  source of its demo, as a desktop application and in the browser. A demo can show several sections, each with its own
  box and its own code, as the Audio Player page does for the four players. The Audio Visualizer and Audio Timeline
  pages feed the visualizers with a song the gallery synthesizes and analyzes itself.
- **The motion engine of Kiro in `kiro-ui-motion`**, generative art for the JVM and WebAssembly:
  - Scenes of layers with opacity, blend mode, transform, parallax, mask and their own time, drawn by `MotionCanvas`,
    which records the still parts of every art once, pauses while the window is unfocused or hidden, caps its frame
    rate, steps its quality down when frames run late and shows a still frame under reduced motion.
  - Thirty arts, every value of them themeable: Truchet, Bauhaus, hex flip, Kumiko weave and checker morph; isometric
    city, orbits, spirograph, polygons, wireframe solids, radar, blueprint and Lissajous figures; ridge lines, contours,
    halftone, Voronoi and low poly; flow field, particles, starfield, meteors and bokeh; gradients and aurora; grid
    pattern, flickering grid, retro grid and digital rain; beams and ripples.
  - Shader arts for a mesh gradient, silk and film grain with typed uniforms and a canvas fallback.
  - Palettes derived from the theme (`Refined` by default, `Expressive`, `Monochrome`, `Vivid`, `Pastel`, `Duotone`,
    `Night`), scene presets, playback with seeking and keyframes, pointer, progress and signal inputs, masks, export of
    frames and diagnostics.
  - The typewriter, which announces the whole phrase once to screen readers.
- **The desktop shell of Kiro in `kiro-ui-window`**, a plain JVM module migrated from the window chrome of Kide:
  - `ChromeWindow` keeps the frame of Windows and macOS through the window decorations of the JetBrains Runtime, with
    the native shadow, resizing, window buttons and snap layouts, reaches under the transparent title bar of macOS
    without them, and draws the whole frame on Linux. Its title stays in the middle of the window whatever the slots of
    the title bar hold, and `WindowLayout` remembers where a window was, moved back onto a screen that is still there.
  - `TitleBar`, `TitleBarButton` and `NotificationBell` with the window buttons of each system as `WindowsControls`,
    `LinuxControls` and `MacOsControls`.
  - `Tray` on every system that has one, with a template icon on macOS, notifications and a callback where the desktop
    offers no tray.
  - `ApplicationMenu` and `Dock` for the native application menu of macOS, with a `QuitRequest` an application can
    cancel, and `applicationMenu` for the same entries in the menubar of a window on Windows and Linux.
    `DesktopEnvironment.prepare` names the application before AWT starts.
  - Five splash screens on scenes of `kiro-ui-motion`, `SplitSplash` (an isometric city), `CenteredSplash` (orbits
    around the logo with a progress ring), `ChecklistSplash` (ridge lines beside the steps), `MinimalSplash` (a card
    over contour lines) and `HeroSplash` (Bauhaus tiles), over a shared `Splash` with steps, progress and failure,
    shown in a `SplashWindow` that closes the native splash of the JVM once it draws.
- **A convention for plain JVM modules.** `kiro.desktop-library` applies the conventions of `kiro.ui-library` without
  Kotlin Multiplatform; `kiro.platform`, `kiro.api-validation` and `kiro.compose` configure their multiplatform parts
  only where the multiplatform plugin is applied.
- **Native menus from the menu DSL in `kiro-ui-components`.** The internal opt-in `nativeEntriesOf`, the
  `NativeMenuEntry` entries and `NativeEntries` translate a `MenuScope` into a menu that the operating system draws,
  so that the tray and the dock of `kiro-ui-window` reuse the entries of a Kiro menu.

### Fixed

- **The assurance gate aggregates every published module.** `kiro-ui-audio` was missing from it.
- **The `Spectrogram` draws its frequency marks when it is lower than a label.** Placing a label threw an
  `IllegalArgumentException`, which broke the gallery.
- **The scopes of the components carry their public names.** The public interfaces of the scopes, such as
  `MenubarScope` and `CardScope`, had been named after their internal implementations.
