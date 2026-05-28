# MffiApp Architecture

The app starts as a Kotlin Android skeleton using official Jetpack libraries.

## Main Stack

- Kotlin
- Jetpack Compose
- Material 3
- Type-safe Navigation Compose
- Coroutines and Flow
- Lifecycle KTX
- Ktor Client
- Kotlinx Serialization
- Room with KSP
- Koin
- Coil
- Lifecycle ViewModel
- Android vector resources

## Gradle Modules

```text
:app                 Application entry point, app navigation host, Koin composition root
:core:model          Shared app-level model enums
:core:domain         Domain models, repository contracts, typed results, use cases
:core:data           Room local source, Ktor remote source, repository implementation
:core:designsystem   Color definitions, drawable icon access, theme, typography, spacing
:core:navigation     Type-safe route contracts and bottom destination metadata
:feature:shared      Shared feature UI state and common Compose module screen
:feature:home        Home MVVM feature
:feature:explore     Explore MVVM feature
:feature:messages    Messages MVVM feature
:feature:profile     Profile MVVM feature
```

## Dependency Rules

- `:app` owns Android process entry, app-level navigation, and DI composition.
- `:app` may depend on all core and feature modules.
- Feature modules may depend on core modules and `:feature:shared`.
- Feature modules must not depend on each other.
- `:core:navigation` must not depend on feature modules.
- `:core:data` implements `:core:domain` repository contracts.
- `:core:domain` must not depend on data, UI, navigation, or DI modules.

## Build Logic

Shared Gradle defaults live in `build-logic` as included-build convention plugins:

```text
mffi.android.application   Android application defaults
mffi.android.library       Android library defaults
mffi.android.compose       Compose compiler and build feature defaults
```

These plugins centralize `compileSdk`, `minSdk`, Java 21, Kotlin JVM 21, vector drawable support, and Compose enablement. Module build files should keep only their namespace, module-specific plugins, and dependencies.

## Feature Pattern

Each feature starts with:

```text
<Feature>UiState.kt
<Feature>ViewModel.kt
<Feature>Screen.kt
```

Keep UI state immutable. The ViewModel exposes `StateFlow`, owns state mutation, and launches coroutine work in `viewModelScope`. The Screen renders state through `collectAsStateWithLifecycle` and sends user events back to the ViewModel.

ViewModels depend on use cases instead of repositories. Use cases define app actions such as seeding default data, observing module cards, and refreshing remote preview data. Repository interfaces remain in the domain layer, while repository implementations stay in the data layer.

## Navigation Boundaries

All type-safe route objects live in `:core:navigation`. Feature modules must not define app-level routes or import routes from another feature module. When one feature needs to move to another screen, it emits a navigation event or uses a route contract from `:core:navigation`; the app navigation host remains responsible for wiring the destination.

This keeps per-feature modules isolated while still allowing Kotlinx Serialization route objects for type-safe Navigation Compose.

## Infrastructure

- Ktor Client handles network transport.
- Kotlinx Serialization handles DTO decoding.
- Room stores local entities and exposes Flow-backed queries.
- Koin wires app dependencies without annotation processing.
- KSP is used for Room code generation.
- Coil renders remote image URLs in Compose.

## Testing

- Core domain use cases are covered by JVM unit tests with `kotlin-test` and `kotlinx-coroutines-test`.
- Core data repository behavior is covered with fake DAO and remote data source implementations.
- Fast architecture checks should run `:core:domain:testDebugUnitTest` and `:core:data:testDebugUnitTest`.
- Full app verification should run `:app:assembleDebug`.

## Compose Image Stability

- Pass stable `String` URLs directly to Coil `AsyncImage`.
- Avoid creating ad hoc image model objects in ViewModels or during every recomposition.
- Use stable keys for `LazyColumn` items that render images or remote-backed content.
- Domain list models should expose durable identifiers from the data layer instead of relying on list position.

## Current Tabs

- Home
- Explore
- Messages
- Profile

## Resource Policy

- App icons and tab icons live in the owning module `res/drawable` directory.
- Kotlin icon references go through `MffiIcons`.
- Fonts are centralized through `MffiFontFamilies`; the project uses platform default fonts until a real brand font is chosen.
- Theme colors and spacing are centralized in `MffiColors`, `MffiTheme`, and `MffiSpacing`.
