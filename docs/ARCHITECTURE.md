# MffiApp Architecture

The app starts as a Kotlin Android skeleton using official Jetpack libraries.

## Main Stack

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Lifecycle ViewModel
- Android vector resources

## Package Rules

```text
core/designsystem/colors    Color definitions
core/designsystem/icons     Drawable icon access
core/designsystem/theme     Material theme, typography, spacing
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

Keep UI state immutable. The ViewModel owns state mutation. The Screen renders state and sends user events back to the ViewModel.

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
