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

### Fixed

- **The scopes of the components carry their public names.** The public interfaces of the scopes, such as
  `MenubarScope` and `CardScope`, had been named after their internal implementations.
