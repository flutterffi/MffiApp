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

## Package Rules

```text
core/designsystem/colors    Color definitions
core/designsystem/icons     Drawable icon access
core/designsystem/theme     Material theme, typography, spacing
core/data/local             Room database, DAO, entities
core/data/remote            Ktor Client data source and DTOs
core/data/repository        Repository implementations
core/domain/model           Domain models
core/domain/repository      Repository contracts
core/domain/result          Typed result wrappers
core/domain/usecase         Application use cases
core/model                  Shared app-level model enums
core/di                     Koin dependency modules
core/navigation             App routes and bottom navigation
core/resources              String/resource access wrappers
feature/<module>            MVVM screen module
```

## Feature Pattern

Each feature starts with:

```text
<Feature>UiState.kt
<Feature>ViewModel.kt
<Feature>Screen.kt
```

Keep UI state immutable. The ViewModel exposes `StateFlow`, owns state mutation, and launches coroutine work in `viewModelScope`. The Screen renders state through `collectAsStateWithLifecycle` and sends user events back to the ViewModel.

ViewModels depend on use cases instead of repositories. Use cases define app actions such as seeding default data, observing module cards, and refreshing remote preview data. Repository interfaces remain in the domain layer, while repository implementations stay in the data layer.

## Infrastructure

- Ktor Client handles network transport.
- Kotlinx Serialization handles DTO decoding.
- Room stores local entities and exposes Flow-backed queries.
- Koin wires app dependencies without annotation processing.
- KSP is used for Room code generation.
- Coil renders remote image URLs in Compose.

## Current Tabs

- Home
- Explore
- Messages
- Profile

## Resource Policy

- App icons and tab icons live in `res/drawable`.
- Kotlin icon references go through `MffiIcons`.
- App strings go through Android string resources, with shared references in `MffiStrings`.
- Fonts are centralized through `MffiFontFamilies`; the project uses platform default fonts until a real brand font is chosen.
- Theme colors and spacing are centralized in `MffiColors`, `MffiTheme`, and `MffiSpacing`.
