# AGENTS.md

Binding rules for every agent and every person working in this repository. Kiro is the user interface framework of
Davils: themed components for Compose Multiplatform on the JVM and on WebAssembly (`wasmJs`), built on Compose
Foundation and on [Kore](https://github.com/davils-com/kore). It is published under the Apache License 2.0 and built
to the same standard as Kore. Where this file is silent, the rules of Kore apply.

## Repository

| Module | Artifact | Purpose |
|---|---|---|
| `kiro-ui/kiro-ui-core` | `com.davils:kiro-ui-core` | The theme: color roles, typography and the bundled fonts, radii, spacing, border widths, elevation, motion, the focus ring, icon tokens, interaction states and the `ComponentStyles` registry |
| `kiro-ui/kiro-ui-icons` | `com.davils:kiro-ui-icons` | The `Icon` composable and the complete Lucide set, generated from the SVGs of the pinned Lucide release |
| `kiro-ui/kiro-ui-components` | `com.davils:kiro-ui-components` | The components, from buttons and inputs to menus, dialogs, tables, calendars and charts |
| `kiro-ui/kiro-ui-audio` | `com.davils:kiro-ui-audio` | The audio players: bar, card, full view and mini player, their shared state and controls, tinted by the cover of the track |
| `build-logic` | - | One convention plugin per Kreate concern, composed by `kiro.kotlin-library`, `kiro.ui-library` and `kiro.root`, and the Lucide generator |
| `kiro-gallery` | - | The gallery: every component in a light and a dark theme with its source, as a desktop application and in the browser; never published |
| `kiro-application` | - | Not part of the build yet; nothing in it is touched until it is planned |

The root package of a module is `com.davils.kiro.<module without kiro- and with dots>`, such as `com.davils.kiro.ui.core`,
and its `Automatic-Module-Name` has the same name.

## Targets

Every module is a Kotlin Multiplatform library with two targets: `jvm` and `wasmJs`.

- Code lives in `src/commonMain/kotlin`. What only the desktop can offer, such as AWT, window menus and native menu
  bars, lives in `src/jvmMain/kotlin`.
- A platform difference hides behind an `internal expect fun` in `<Role>.kt` with its `actual` in `<Role>.jvm.kt` and
  `<Role>.wasmJs.kt`, such as `ResizeCursors` and `ClipboardText`. No `expect` or `actual` class.
- Resources, such as the bundled fonts and the license notices, are Compose resources in
  `src/commonMain/composeResources`, never Java resources, so that they load on both targets.

## Verify

```bash
./gradlew check
```

Nothing is done until it is green. `check` runs what the `check` of Kore runs: warnings as errors, Detekt with type
resolution, the Kreate rule set and `allRules`, the tests, Kover, the API and semver check, the license header check,
the changelog check, every Trivy scan and the Kreate assurance gate. It needs the network and JDK 25.

## Theme and styles

These rules are what makes Kiro Kiro. A change that breaks one of them is not merged.

- **Never Material.** No `androidx.compose.material*`, no `org.jetbrains.compose.material*`, no Material icons, not
  even in tests. `kiro.compose` excludes the groups from every configuration and Detekt forbids the imports. Build on
  Compose Foundation.
- **Every detail can be changed.** Every value a component shows or animates, from a color to a radius, a padding, a
  size, a text style, a shadow, a duration, an easing, a fraction or a threshold, is a field of a token of the theme or
  of the style of the component. Nothing visible is a private constant of a composable.
- **Four layers, none of them fixed.** 1. The tokens of `Theme` in `kiro-ui-core`. 2. The default style of a
  component, derived only from the tokens through its `StyleKey`, and the overrides of `Theme.components`. 3. The
  `style` parameter of every component, whose default is the style of the theme. 4. Slots for the parts of a component,
  such as icons, indicators and close buttons, with defaults.
- **Literals belong to the tokens.** Lengths, text sizes, colors and durations are written as literals only in the
  token defaults of `kiro-ui-core`. A default style derives them from the theme, lengths as steps of
  `theme.spacing`, borders from `theme.borders`, colors from `theme.colors`. Unitless values that belong to one
  component only, such as a fraction or an alpha, may appear in its default style factory and nowhere else.
- **Every color comes from a role.** A color that no role covers gets a new role in `Colors`, such as `scrim`,
  `shadow` and `knob`, never a literal in a component.
- **Values that components hand down come from their style.** `LocalContentColor`, `LocalTextStyle` and
  `LocalIconStyle` are set from the style of the component that provides them.
- **Texts are parameters.** Kiro has no localization of its own. Every text a component shows or announces is a
  parameter, or a field of its style, with an English default; a text with values is a function, such as
  `progress: (current: Int, total: Int) -> String`. Formats such as dates come from a formatter parameter.

## Code rules

The code rules of Kore apply unchanged: the license header, no comments, KDoc on public API only with `@param`,
`@return`, `@throws` and `@since`, no `else`, at most two chained calls, one top-level type per file, no package for a
single file, no `!!` and no `lateinit`, explicit API, immutable values, no public `data class`, explicit return types,
Kotlin first and shared building blocks of Kore first. In addition:

- **A chain of `Modifier` calls** counts as one call: a `Modifier` is the builder of Compose and its chain reads as a
  list. Every other chain follows the limit.
- **Composable functions** are nouns in PascalCase, as Compose requires.
- **Names** carry no prefix of the library, so `Theme` and `ButtonStyle`, never `KiroTheme`. When an internal
  implementation needs the name of its public interface, it is `<Interface>Impl`, such as `CardScopeImpl`.
- **Value types** of the theme and of styles are classes with a `copy` function, `equals`, `hashCode` and `toString`
  over all their values and `@Immutable`, never a `data class`.

## Tests

The test rules of Kore apply. In addition:

- Tests of composables run on the JVM only, in `src/jvmUnitTest/kotlin`, with `runComposeUiTest`: the Wasm runtime of
  Skiko cannot load under Node.js. Tests of plain logic, such as colors, state resolution and the style registry, stay
  in `src/commonUnitTest/kotlin`; `kiro.compose` runs them on the JVM only for the same reason, while `wasmJs` is
  compiled and published.
- Every component has a test that renders it with a changed style and checks that the change shows, and a test that
  each of its slots can be replaced.

## Build

- Every module applies exactly one plugin and declares nothing but its description and its dependencies: `kiro.lucide`
  for `kiro-ui-icons`, `kiro.gallery` for `kiro-gallery`, `kiro.ui-library` for the other modules.
- The convention plugins follow Kore, with `kiro.` instead of `kore.`. Kiro adds:

  | Plugin | Configures |
  |---|---|
  | `kiro.compose` | The Compose compiler and Compose Multiplatform, the exclusion of Material, the Compose test libraries of the JVM tests and the package of the generated resource class |
  | `kiro.ui-library` | `kiro.kotlin-library` and `kiro.compose` for a module of the user interface |
  | `kiro.lucide` | The `lucide` configuration and the `generateLucideIcons` task, whose output is part of `commonMain` |
  | `kiro.gallery` | The gallery as an application: the desktop main class, the executable of the browser, Binaryen from the repositories of the settings and the sources of the demos as Compose resources; it applies neither publishing nor API validation, documentation or coverage |

- **The Lucide generator is the one exception** to the rule of Kore that `build-logic` holds no classes and no tasks
  of its own. It lives in `build-logic/src/main/kotlin/com/davils/kiro/gradle/lucide`, reads the SVGs and metadata of
  the pinned release archive of Lucide, never draws an icon by hand, writes one file per icon with the license header,
  KDoc and `@DavilsGenerated`, and is tested by Kotest in `build-logic/src/test/kotlin`. A new icon set or a new
  generator is agreed on with a maintainer first.
- Lucide and Binaryen come from their GitHub releases through `exclusiveContent` Ivy repositories in the settings,
  the artifacts of AndroidX that Compose needs from Google through another one. `gradle/verification-metadata.xml` names
  the Binaryen and Node.js archives of every platform the pipeline runs on. Everything else follows the repository rules of Kore.
- **Coverage** is verified on the JVM at 97 % of lines and instructions and 70 % of branches; the branches of a
  composable are many small layout decisions that the JVM tests reach only in part.
- The assurance gate follows Kore, except that, as in Kide, only CRITICAL findings can never be ignored: Compose
  Desktop loads Skiko and opens links, and kotlinx-coroutines declares its debug agent, which are known properties of
  the libraries Kiro is built on. Each of them has an `ignore` rule in `kiro.assurance` with a reason, the exact module
  and an expiry date no more than six months ahead. A new rule for a HIGH finding needs the approval of a maintainer.
- Google Maven serves no signatures for the POMs of AndroidX; their SHA-256 sums are recorded in
  `gradle/verification-metadata.xml`, checked against the repository, and the marker POM of the serialization plugin of
  `build-logic` trusts the different sums of the Gradle Plugin Portal and Maven Central.

## Gallery

- Every component has a demo in `kiro-gallery/src/commonMain/kotlin/com/davils/kiro/gallery/demo`, named `<Name>Demo.kt`
  and listed in `Demos.kt`. The gallery shows the source of that file, so a demo is written to be read: short, with
  the values a user would write.
- The gallery runs with `./gradlew :kiro-gallery:run` on the desktop and is built for the browser with
  `./gradlew :kiro-gallery:wasmJsBrowserDistribution`.

## Public API

As in Kore: the `api/*.api` dumps are the contract, recorded with `./gradlew kreateApiDump`, checked by
`kreateApiCheck` and `kreateApiSemverCheck`, with a `CHANGELOG.md` entry for every deliberate change.

## Releases and Git

As in Kore: the version lives in `gradle.properties` only, a release is a tag without a `v` on `main`, snapshots and
releases go to `repo.davils.com`. Work on `develop`, Conventional Commits in English with the module as scope, such as
`feat(components): ...`, small commits, commit only when asked, no `Co-Authored-By` trailer and no other attribution
to tools or AI assistants.
