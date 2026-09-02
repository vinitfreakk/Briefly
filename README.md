# Briefly

A news app built with Kotlin Multiplatform, targeting Android and iOS. Uses NewsAPI under the hood.

---

## [Watch Preview](https://drive.google.com/file/d/1wUUc3rit1hjxtq13qsCTvxNjQJ2qMnRn/view?usp=sharing)

[![Briefly Preview](https://drive.google.com/thumbnail?id=1wUUc3rit1hjxtq13qsCTvxNjQJ2qMnRn&sz=w800)](https://drive.google.com/file/d/1wUUc3rit1hjxtq13qsCTvxNjQJ2qMnRn/view?usp=sharing)

---

## What's inside

- Top headlines feed
- Search
- Bookmarks
- In-app article reader
- Share articles

---

## Tech Stack

- Kotlin Multiplatform
- Compose Multiplatform
- MVVM
- Koin
- Ktor
- Room
- DataStore
- Coil
- Navigation3

---

## Setup

Get a free API key from [newsapi.org](https://newsapi.org/), then enter it directly inside the app on first launch. It gets saved locally via DataStore.

**Android** — `./gradlew :androidApp:assembleDebug`  
**iOS** — open `/iosApp` in Xcode and run