# MiniPokedex 🧩

A modern Android application to explore Pokémon, built with **Clean Architecture**, **Jetpack Paging 3**, and an **offline-first approach**. This project demonstrates up-to-date practices in architecture, testing, and modularization.

---

## Features

- Browse Pokémon list with infinite scrolling
- Paging 3 + RemoteMediator for network + database sync
- Offline-first: data cached in local DB
- Modular Clean Architecture
- Unit tests for each layer
- Built with Kotlin, Coroutines, Jetpack libraries
- (cont...)

---

## Tech Stack

| Layer       | Tech Used                                               |
|------------|----------------------------------------------------------|
| UI         | Jetpack Compose, ViewModel, StateFlow                   |
| Domain     | Kotlin, UseCases, Interfaces                            |
| Data       | Room DB, Retrofit, RemoteMediator, Repository pattern   |
| Tools      | Dagger/Hilt, Coroutine, Paging 3, Kotlin DSL            |
| Testing    | JUnit4, Mockito/MockK, Coroutine test, Flow test        |

---

## 🧭 Architecture Overview

This project follows the principles of **Clean Architecture** with clearly separated modules:

```plaintext
    +---------------------+
    |      UI (app)       |
    |  Jetpack Compose    |
    +----------+----------+
               |
               ▼
    +---------------------+
    |     Domain Layer     |
    | UseCases + Entities  |
    +----------+----------+
               |
               ▼
    +---------------------+
    |      Data Layer      |
    | Repository + Paging  |
    | Retrofit + Room DB   |
    +---------------------+
