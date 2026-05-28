# MffiApp

Kotlin Android app architecture skeleton.

## Stack

- Kotlin 2.3.20
- Jetpack Compose
- Material 3
- Type-safe Navigation Compose
- Coroutines, Flow, and Lifecycle KTX
- Ktor Client with Kotlinx Serialization
- Room with KSP
- Koin
- Coil
- MVVM per feature module

## Structure

```text
app/src/main/java/com/flutterffi/mffiapp/
  core/designsystem/   Theme, colors, typography, icons, resources
  core/data/           Ktor remote source, Room database, repository implementation
  core/domain/         App models, result types, use cases, repository contracts
  core/model/          Shared app-level model enums
  core/di/             Koin modules
  core/navigation/     Bottom tabs and app routes
  feature/             Home, Explore, Messages, Profile modules
```

## Architecture Rules

- One feature owns its `UiState`, `ViewModel`, and `Screen`.
- ViewModels depend on use cases, not repositories.
- Use cases own application actions and query boundaries.
- Shared UI rules live under `core/designsystem`.
- Route definitions and bottom tabs live under `core/navigation`.
- Routes are type-safe Kotlin serialization objects.
- Async state flows through Coroutines, Flow, and `collectAsStateWithLifecycle`.
- Network transport uses Ktor Client and Kotlinx Serialization.
- Local persistence uses Room with KSP.
- Dependency injection uses Koin.
- Remote images render through Coil.
- Android resources are wrapped from Kotlin through `core/resources` and `core/designsystem/icons`.
- App-wide color, typography, spacing, and theme decisions stay in `core/designsystem/theme`.

Run:

```bash
./gradlew :app:assembleDebug
```

More detail: [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)
