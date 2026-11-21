# Kiro

Kiro is the user interface framework of Davils: themed components for Compose Multiplatform on the JVM and on
WebAssembly (`wasmJs`), built on Compose Foundation, without Material. It is published under the
[Apache License 2.0](LICENSE).

| Module | Artifact | What it holds |
|---|---|---|
| `kiro-ui-core` | `com.davils:kiro-ui-core` | The theme: colors, typography and the bundled fonts, radii, spacing, borders, elevation, motion, focus and the registry of component styles |
| `kiro-ui-icons` | `com.davils:kiro-ui-icons` | The `Icon` composable and every icon of Lucide, generated from its SVGs |
| `kiro-ui-components` | `com.davils:kiro-ui-components` | Buttons, inputs, menus, dialogs, sheets, tables, calendars, charts and more |
| `kiro-ui-audio` | `com.davils:kiro-ui-audio` | Audio players as a bar, a card, a full view and a mini player, tinted by their cover, and audio visualizers: level meters, goniometer, oscilloscope, spectrum analyzer, spectrogram, desktop visualizers, waveform and timeline clips |

## Every detail is yours

Every value a component draws comes from the theme, so a theme can change any detail of any component:

```kotlin
val theme = Theme.light().copy(
    radii = Radii.from(base = 6.dp),
    components = ComponentStyles.Default.with(ButtonStyleKey) { _, styles ->
        ButtonStyles { theme, variant, size -> styles.resolve(theme, variant, size).copy(/* ... */) }
    },
)

ThemeProvider(theme = theme) {
    Button(onClick = { }) { Text("Save") }
}
```

A single component takes a `style` parameter on top, and its parts, such as icons, are slots.

## Installation

Kiro is published to the Davils repository, not to Maven Central:

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        exclusiveContent {
            forRepository { maven("https://repo.davils.com/releases") }
            filter { includeGroupAndSubgroups("com.davils") }
        }
        mavenCentral()
        google()
    }
}
```

```kotlin
// build.gradle.kts
dependencies {
    implementation("com.davils:kiro-ui-components:1.0.0")
}
```

## Gallery

`kiro-gallery` shows every component in the light and the dark theme, next to the source of its demo:

```bash
./gradlew :kiro-gallery:run
```

```bash
./gradlew :kiro-gallery:wasmJsBrowserDistribution
```

The second command writes the browser version to `kiro-gallery/build/dist/wasmJs/productionExecutable`.

## Building

`./gradlew check` builds, tests and verifies everything; it needs JDK 25 and network access. The rules for
contributions are in [CONTRIBUTING.md](CONTRIBUTING.md) and [AGENTS.md](AGENTS.md).
