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
- **The gallery of Kiro in `kiro-gallery`**, which shows every component in the light and the dark theme with the
  source of its demo, as a desktop application and in the browser.

### Fixed

- **The scopes of the components carry their public names.** The public interfaces of the scopes, such as
  `MenubarScope` and `CardScope`, had been named after their internal implementations.
