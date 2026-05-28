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
- `:app` coordinates app startup work such as default local data seeding.
- `:app` may depend on all core and feature modules.
- Feature modules may depend on core modules and `:feature:shared`.
- Feature modules must not depend on each other.
- `:core:navigation` must not depend on feature modules.
- `:core:data` implements `:core:domain` repository contracts.
- `:core:domain` must not depend on data, UI, navigation, or DI modules.
- `checkArchitecture` enforces these dependency and import boundaries.

## Build Logic

Shared Gradle defaults live in `build-logic` as included-build convention plugins:

```text
mffi.android.application   Android application defaults
mffi.android.library       Android library defaults
mffi.android.compose       Compose compiler and build feature defaults
```

These plugins centralize `compileSdk`, `minSdk`, Java 21, AGP built-in Kotlin support, and Compose enablement. Module build files should keep only their namespace, module-specific plugins, and dependencies.

## Feature Pattern

Each feature starts with:

```text
<Feature>UiState.kt
<Feature>ViewModel.kt
<Feature>Screen.kt
navigation/<Feature>Navigation.kt
```

Keep UI state immutable. The ViewModel exposes `StateFlow`, owns state mutation, and launches coroutine work in `viewModelScope`. The Screen renders state through `collectAsStateWithLifecycle` and sends user events back to the ViewModel.

ViewModels depend on use cases instead of repositories. Use cases define app actions such as seeding default data, observing module cards, and refreshing remote preview data. Repository interfaces remain in the domain layer, while repository implementations stay in the data layer.

## Navigation Boundaries

All type-safe route objects live in `:core:navigation`. Feature modules must not define app-level routes or import routes from another feature module. Each feature owns its `NavGraphBuilder` destination entry in a feature-local `navigation` package. When one feature needs to move to another screen, it emits a navigation event or uses a route contract from `:core:navigation`; the app navigation host composes feature graph entries without owning feature screen or ViewModel wiring.

This keeps per-feature modules isolated while still allowing Kotlinx Serialization route objects for type-safe Navigation Compose.

## Infrastructure

- Ktor Client handles network transport.
- Kotlinx Serialization handles DTO decoding.
- Room stores local entities and exposes Flow-backed queries.
- Room migrations are explicit when persisted entity columns change.
- Koin wires app dependencies without annotation processing.
- KSP is used for Room code generation.
- Coil renders remote image URLs in Compose.

## Home Dashboard Flow

- `HomeViewModel` observes cached Home cards through `ObserveFeatureCardsUseCase`.
- `RefreshHomeDashboardUseCase` refreshes remote dashboard content through the repository.
- `DefaultMffiRepository` fetches a remote photo with Ktor, writes the result into Room, and returns the stable thumbnail URL.
- Room emits the updated card list through Flow, and Compose renders stable `String` image URLs with Coil.
- Home exposes retry from the UI while keeping app-wide startup work outside the feature ViewModel.

## Dependency Injection

- `:app` is the Koin composition root.
- `appModule` wires application-level coordinators.
- Data infrastructure bindings live in `dataModule`.
- Domain use case bindings live in `domainModule`.
- Feature ViewModel bindings live in feature-owned modules such as `homeModule` and `profileModule`.
- Feature modules depend on Koin core ViewModel support, not Android Koin APIs.
- `MffiApplication` loads the full `appModules` list.

## Startup

- `MffiApplication` owns an application coroutine scope.
- `AppStartupCoordinator` runs process-level startup work once per app process.
- Feature ViewModels should not perform app-wide initialization.
- Feature ViewModels can still run feature-local refresh work that directly feeds their own UI state.

## Testing

- Core domain use cases are covered by JVM unit tests with `kotlin-test` and `kotlinx-coroutines-test`.
- Core data repository behavior is covered with fake DAO and remote data source implementations.
- Fast architecture checks should run `checkArchitecture`, `:core:domain:testDebugUnitTest`, and `:core:data:testDebugUnitTest`.
- Full app verification should run `verifyMffiApp`.

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
- Kotlin drawable references go through typed `MffiDrawableResource` wrappers.
- Kotlin string references go through typed `MffiStringResource` wrappers.
- Icons are exposed through `MffiIcons`.
- Images are exposed through `MffiImages`.
- Shared UI text is exposed through `MffiStrings`.
- Fonts are centralized through `MffiFontFamilies`; the project uses platform default fonts until a real brand font is chosen.
- Theme colors and spacing are centralized in `MffiColors`, `MffiTheme`, and `MffiSpacing`.
