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

## Gradle Modules

```text
:app                 Application entry point, navigation host, DI composition
:core:model          Shared app-level model enums
:core:domain         Domain models, result types, repository contracts, use cases
:core:data           Ktor remote source, Room database, repository implementation
:core:designsystem   Theme, colors, typography, icons, drawable resources
:core:navigation     Type-safe route contracts and bottom destination metadata
:feature:shared      Shared feature UI state and module screen components
:feature:home        Home MVVM feature
:feature:explore     Explore MVVM feature
:feature:messages    Messages MVVM feature
:feature:profile     Profile MVVM feature
```

## Architecture Rules

- One feature owns its `UiState`, `ViewModel`, and `Screen`.
- Feature Gradle modules do not depend on each other.
- `:app` is the only module that depends on all feature modules.
- Shared Android, Kotlin, Java 21, and Compose Gradle defaults live in `build-logic`.
- Core data and domain bindings live in core-owned Koin modules.
- Feature ViewModel bindings live in feature-owned Koin modules.
- Feature navigation entries live in feature-owned `navigation` packages.
- ViewModels depend on use cases, not repositories.
- Use cases own application actions and query boundaries.
- Shared UI rules live under `core/designsystem`.
- Route definitions and bottom tabs live under `core/navigation`.
- Routes are type-safe Kotlin serialization objects.
- Feature modules depend on shared route contracts, not on each other.
- Async state flows through Coroutines, Flow, and `collectAsStateWithLifecycle`.
- Network transport uses Ktor Client and Kotlinx Serialization.
- Local persistence uses Room with KSP.
- Dependency injection uses Koin.
- Remote images render through Coil with stable `String` URL models and stable list keys.
- Android resources are wrapped from Kotlin through `core/designsystem/icons`.
- App-wide color, typography, spacing, and theme decisions stay in `core/designsystem/theme`.

Run:

```bash
./gradlew verifyMffiApp
```

More detail: [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)
