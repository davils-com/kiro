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
| `kiro-ui-motion` | `com.davils:kiro-ui-motion` | Generative, animated art in layered scenes: thirty themeable arts, shader layers, presets, playback, power and quality policies, export and the typewriter |
| `kiro-ui-window` | `com.davils:kiro-ui-window` | The desktop shell (JVM only): windows with a themed title bar that still behave natively, the tray, the native application menu and splash screens |

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

## Motion

`kiro-ui-motion` draws generative art behind an interface, calm by default and themeable down to every value:

```kotlin
MotionCanvas(scene = ScenePresets.Orbits, modifier = Modifier.fillMaxSize()) {
    SignInForm(modifier = Modifier.align(Alignment.Center))
}

val custom = scene {
    layer(RidgeArt(style = ridgeArtStyle().copy(lines = 24)))
    layer(GrainArt()) { opacity = 0.4f }
}
```

Scenes stop their clock while the window is unfocused or hidden, step their quality down when frames run late and show
a still frame under reduced motion.

## Desktop shell

`kiro-ui-window` runs on the JVM only and turns a Compose application into a desktop application that looks like
Kiro and still behaves like the operating system:

```kotlin
fun main() {
    DesktopEnvironment.prepare(name = "Studio")
    application {
        ApplicationMenu(onQuitRequest = { request -> request.confirm() })
        Tray(icon = TrayIcon(light = logo), tooltip = "Studio", menu = { item("Quit", onClick = ::exitApplication) })
        ChromeWindow(onCloseRequest = ::exitApplication, title = "Studio", titleBar = {
            start { WindowMenubar { menu("File") { item("Open…", onClick = {}) } } }
            center { WindowTitle("Studio") }
            end { NotificationBell(count = 3, onClick = {}) }
        }) {
            Workspace()
        }
    }
}
```

The window keeps the native frame where the JetBrains Runtime offers it and draws its own frame elsewhere; its title
stays in the middle of the window. `SplitSplash`, `CenteredSplash`, `ChecklistSplash`, `MinimalSplash` and
`HeroSplash` show the start of an application in a `SplashWindow`.

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
