# AnimeInfo - Android Developer Assignment

A modern Android application that fetches and displays anime information using the [Jikan API](https://jikan.moe/), built with Jetpack Compose and following Clean Architecture principles with MVVM pattern.

##  Download APK

[Download APK from Google Drive](https://drive.google.com/file/d/1Pqsvx7UENj6jongbA3tb8FF7gaN0xQvD/view?usp=sharing)

## Repository

[![GitHub](https://img.shields.io/badge/GitHub-2Anonymous%2FAnimeinfo-181717?style=for-the-badge&logo=github)](https://github.com/2Anonymous/Animeinfo)

```bash
git clone https://github.com/2Anonymous/Animeinfo.git
```

## Features

### Splash Screen
- Animated app logo with scale effect
- Smooth transition to home screen
- Theme-aware background (Dark/Light)

### Anime List Page
- Beautiful **2-column grid layout** displaying top-rated anime
- Each card shows:
  - High-quality poster image
  - Anime title
  - Rating with star icon
  - Episode count
- **Infinite scroll pagination** - automatically loads more content
- **Pull-to-refresh** functionality
- Offline mode indicator with toast notification

### Anime Detail Page
- Full-width poster image with back navigation
- **In-app YouTube trailer playback** (tap play button on poster)
- Comprehensive information display:
  - Title (Japanese & English)
  - Stats row: Rating, Episodes, Year, Members
  - Genre chips
  - Full synopsis
  - Info card: Status, Type, Rating, Source, Studios, Aired dates

### Technical Features
- **Offline Support**: Room database caching for offline viewing
- **Dark/Light Theme**: Automatic system theme adaptation
- **Network Resilience**: Shows cached data when offline, auto-syncs when online
- **Error Handling**: Graceful handling of API errors with retry option
- **Real-time Connectivity**: Toast notification when going offline

## Architecture

The app follows **Clean Architecture** with **MVVM** pattern:

```
app/
├── data/
│   ├── local/
│   │   ├── dao/              # Room DAOs
│   │   ├── entity/           # Room Entities
│   │   └── AnimeDatabase.kt
│   ├── mapper/               # DTO ↔ Entity ↔ Domain mappers
│   ├── remote/
│   │   ├── dto/              # API Response DTOs
│   │   └── ApiService.kt
│   └── repository/           # Repository implementations
├── di/
│   ├── NetworkModule.kt      # Retrofit, OkHttp
│   ├── DatabaseModule.kt     # Room Database
│   ├── RepositoryModule.kt   # Repository bindings
│   └── UseCaseModule.kt      # UseCase providers
├── domain/
│   ├── model/                # Domain models
│   ├── repository/           # Repository interfaces
│   └── usecase/              # Business logic use cases
├── presentation/
│   ├── detail/
│   │   ├── DetailScreen.kt
│   │   ├── DetailViewModel.kt
│   │   ├── DetailState.kt
│   │   └── DetailEvent.kt
│   ├── home/
│   │   ├── HomeScreen.kt
│   │   ├── HomeViewModel.kt
│   │   ├── HomeState.kt
│   │   └── HomeEvent.kt
│   ├── splash/
│   │   └── SplashScreen.kt   # Animated splash screen
│   └── navigation/           # NavGraph
├── ui/theme/                 # Material 3 theming
└── util/
    ├── Resource.kt           # Loading/Success/Error wrapper
    └── NetworkConnectivityObserver.kt
```

## Tech Stack

| Library | Purpose |
|---------|---------|
| **Jetpack Compose** | Modern declarative UI |
| **Dagger Hilt** | Dependency Injection |
| **Retrofit** | REST API calls |
| **OkHttp** | HTTP client with logging interceptor |
| **Room** | Local SQLite database |
| **Glide** | Image loading & caching |
| **android-youtube-player** | In-app YouTube video playback |
| **Navigation Compose** | Type-safe screen navigation |
| **StateFlow** | Reactive state management |
| **Kotlin Coroutines** | Asynchronous programming |
| **Material 3** | Modern Material Design components |

## Design Patterns Used

- **MVVM** - ViewModel manages UI state
- **Clean Architecture** - Separation of concerns across layers
- **Repository Pattern** - Single source of truth for data
- **Use Cases** - Business logic encapsulation
- **State & Event Pattern** - Unidirectional data flow in ViewModels
- **Dependency Injection** - Loose coupling with Hilt

## Features Checklist

- [x] Animated Splash Screen with app logo
- [x] Anime List Page with grid layout
- [x] Anime Detail Page with comprehensive info
- [x] In-app YouTube trailer playback
- [x] Room Database for offline storage
- [x] Offline mode with cached data
- [x] Data sync when online
- [x] Infinite scroll pagination
- [x] Pull-to-refresh functionality
- [x] Error handling with retry
- [x] Network connectivity monitoring
- [x] Dark/Light theme support
- [x] MVVM + Clean Architecture
- [x] Dependency Injection (Hilt)
- [x] Use Cases layer
- [x] State & Event pattern

## Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17
- Android SDK 35 (compileSdk)
- Minimum SDK 24 (Android 7.0)

### Build & Run

1. Clone the repository
```bash
git clone https://github.com/2Anonymous/Animeinfo.git
cd Animeinfo
```

2. Open in Android Studio

3. Sync Gradle files

4. Run on emulator or physical device

### Build APK

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

## Configuration

### Gradle Dependencies (libs.versions.toml)
- Kotlin: 2.0.0
- Compose BOM: 2024.11.00
- Hilt: 2.51.1
- Retrofit: 2.11.0
- Room: 2.6.1
- Glide Compose: 1.0.0-beta01
- android-youtube-player: 12.1.0