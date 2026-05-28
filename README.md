# MffiApp

Kotlin Android app architecture skeleton.

## Stack

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Lifecycle ViewModel
- MVVM per feature module

## Structure

```text
app/src/main/java/com/flutterffi/mffiapp/
  core/designsystem/   Theme, colors, typography, icons, resources
  core/navigation/     Bottom tabs and app routes
  feature/             Home, Explore, Messages, Profile modules
```

## Architecture Rules

- One feature owns its `UiState`, `ViewModel`, and `Screen`.
- Shared UI rules live under `core/designsystem`.
- Route definitions and bottom tabs live under `core/navigation`.
- Android resources are wrapped from Kotlin through `core/resources` and `core/designsystem/icons`.
- App-wide color, typography, spacing, and theme decisions stay in `core/designsystem/theme`.

Run:

```bash
./gradlew :app:assembleDebug
```

More detail: [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)
