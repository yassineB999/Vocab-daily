# VocabDaily

A clean, modern Android app to build and maintain your vocabulary. Add words with descriptions, color‑code them, sort in multiple ways, and manage them with an intuitive UI built on Jetpack Compose.

## Features
- Add, edit, and delete words with descriptions.
- Color‑coded word cards with high‑contrast text for readability.
- Sorting by `Word`, `Date`, or `Color` with `Ascending`/`Descending` options.
- Undo delete via Snackbar action.
- Material 3 design, rounded cards, and friendly UX.

## Tech Stack
- Kotlin, Coroutines, Flow
- Jetpack Compose (Material 3)
- Room (SQLite) for local persistence
- Hilt for dependency injection
- Navigation‑Compose
- Gradle Kotlin DSL

## Architecture
Follows a layered, clean architecture with clear separation of concerns.

- Domain
  - `model/Word.kt`: Entity representing a vocabulary word.
  - `repository/WordRepository.kt`: Abstraction for data operations.
  - `use_case/`: Business logic (Add, Delete, Get, GetAll, etc.).
  - `utils/`: Ordering and sorting models.
- Data
  - `data_source/WordDao.kt`: Room DAO for CRUD operations.
  - `data_source/WordDatabase.kt`: Room database configuration.
  - `repository/WordRepositoryImpl.kt`: Implementation of domain repository.
- Presentation
  - `words/WordsViewModel.kt`: MVVM state holder using `WordsState` and `WordsEvent`.
  - `words/component/WordsScreen.kt`: Main screen with sorting and list rendering.
  - `words/component/WordItem.kt`: Word card composable.
  - `add_edit_words/*`: Add/Edit word flow.
- DI
  - `di/AppModule.kt`: Hilt bindings and providers.
- App
  - `WordApp.kt`: Application class for Hilt.

## Project Structure
```
app/src/main/java/com/example/vocabdaily/
├── WordApp.kt
├── data/
│   ├── data_source/
│   │   ├── WordDao.kt
│   │   └── WordDatabase.kt
│   └── repository/
│       └── WordRepositoryImpl.kt
├── di/
│   └── AppModule.kt
├── domain/
│   ├── model/Word.kt
│   ├── repository/WordRepository.kt
│   ├── use_case/
│   │   ├── AddWordUseCase.kt
│   │   ├── DeleteWordUseCase.kt
│   │   ├── GetWordUseCase.kt
│   │   ├── GetWordsUseCase.kt
│   │   └── WordUseCases.kt
│   └── utils/
│       ├── OrderType.kt
│       └── WordOrder.kt
└── presentation/
    ├── MainActivity.kt
    ├── routes/Screen.kt
    ├── words/
    │   ├── WordsEvent.kt
    │   ├── WordsState.kt
    │   ├── WordsViewModel.kt
    │   └── component/
    │       ├── DefaultRadioButton.kt
    │       ├── WordItem.kt
    │       └── WordsScreen.kt
    └── add_edit_words/
        ├── AddEditWordEvent.kt
        ├── AddEditWordViewModel.kt
        ├── WordsTextFieldState.kt
        └── component/
```

## Getting Started
- Requirements
  - Android Studio (Giraffe or newer)
  - JDK 17
  - Android SDK (minSdk and targetSdk set in `build.gradle.kts`)
- Setup
  - Clone the repo and open in Android Studio.
  - Let Gradle sync dependencies.
  - Run the app on an emulator or a device.

## Usage Notes
- Tap `+` to add a new word.
- Long press or use the delete action on a card to remove; Snackbar offers `Undo`.
- Use the radio buttons at the top to change sorting.
- Card color is selectable and used throughout for visual grouping; text color adjusts automatically for readability.

## Testing & Quality
- ViewModels and use cases use Kotlin Flow for reactive streams.
- Consider adding unit tests for use cases and instrumented tests for DAO operations.

## Contributing
- Fork the repository and create a feature branch.
- Keep changes focused and consistent with the existing style.
- Open a pull request describing the motivation, changes, and testing.

## Screenshots
You can add screenshots in `docs/screenshots/` and reference them here:
```
![Words Screen](docs/screenshots/words_screen.png)
![Add/Edit Word](docs/screenshots/add_edit_word.png)
```

## Notes
- This project uses Material 3 and a simple clean architecture to remain easy to extend.
- If you plan to publish, consider adding a license and CI workflow (e.g., GitHub Actions) for builds.