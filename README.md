# GithubUsers 👥

A **Clean Architecture** Android sample that demonstrates how to browse public GitHub users, cache them locally, and page through the list smoothly — built with **100 % Kotlin**.

---
## ✨ Features
- Fetches users from the public **GitHub REST API** and shows avatar, login, and profile link
- Infinite scrolling via **Paging 3** + `RemoteMediator`, including retry / refresh UI states
- **Offline‑first** – last successful page is stored in **Room** (encrypted with SQLCipher) and served when offline
- **Koin** for dependency‑injection across modules
- Image loading & caching with **Glide**
- Modern UI: ViewBinding, Navigation Component, Material 3 widgets
- Two flavours: `dev` (debuggable, `*.dev` idSuffix) & `prod`
- 90 %+ unit‑test coverage enforced by **Kover**

---
## 🏗 Tech stack
| Layer        | Libraries & Tools                                                                                              |
|--------------|-----------------------------------------------------------------------------------------------------------------|
| **UI**       | AndroidX, Material Components, SwipeRefreshLayout, Navigation‑Fragment                                          |
| **DI**       | Koin                                                                   |
| **Async**    | Kotlin Coroutines & Flow                                                                                        |
| **Images**   | Glide                                                                                                           |
| **Network**  | Retrofit 2, OkHttp 3, Gson converter                                                                            |
| **Database** | Room + Paging, SQLCipher                                                                                        |
| **Testing**  | JUnit 4, MockK, Coroutine Test, Room Test, MockWebServer, Robolectric                                 |
| **Coverage** | Kover plugin                                                                                                    |
| **Build**    | Gradle KTS (Java 11 toolchain, Compile/Target SDK 35)                                                           |

_Exact versions are centralised in **`libs.versions.toml`**._

---
## 🗂 Modules
| Module | Purpose |
|--------|---------|
| **:app**    | Presentation layer & UI glue |
| **:domain** | Business logic, use‑cases, models |
| **:data**   | Retrofit, Room, repository implementations |

---
## 🗺 Architecture
```text
presentation (:app)  <------->  domain (:domain)  <-------->  data (:data)
     | UI View                  | UseCases                   | RepositoryImpl
     | ViewModel                | Models                     | Retrofit, Room
     | Aoolication              | Repository                 | RemoteMediator
```
* **Unidirectional data‑flow**  
  `UI → ViewModel intents → UseCases → Repository → DB/Net → ViewModel state → UI`

---
## 🚀 Getting started
```bash
git clone https://github.com/hungho0206it/GithubUsers.git
cd GithubUsers
./gradlew :app:installDevDebug   # run dev flavour on a connected device
```
> **Requirements**: JDK 11 & Android Studio Iguana | Minimum SDK 24

## 🔐 Secure keys with Secret Manager (C++)
The project keeps sensitive strings – the SQLCipher pass‑phrase and an optional GitHub Personal‑Access‑Token – in a tiny native layer so they do **not** appear as plain‑text in the APK.

### File layout
```
data/
└── src/main
    ├── cpp/
    │   ├── secret_manager.cpp # This file is attached to the email. If you can’t find it, please let me know.
    │   └── CMakeLists.txt
    └── /com/hungho/data/helper/SecretHelper.kt
```

---
## 🧪 Tests & coverage
```bash
./gradlew koverHtmlReport                 # unit tests with coverage
```
HTML reports: `app/build/reports/kover/html/index.html`

<img width="1724" alt="Screenshot 2025-05-11 at 7 39 36 PM" src="https://github.com/user-attachments/assets/14a1051c-1c35-4673-a93d-7e93bf15a639" />


---
## 📸 Screenshots
| List | User Detail |
|------|-----------------|
| Light Mode | Light Mode | 
|![Screenshot_1746950865](https://github.com/user-attachments/assets/4af631ec-3eb6-49a2-9c19-c9e9edecfc9c) | ![Screenshot_1746950867](https://github.com/user-attachments/assets/862f2262-b336-4b56-a36a-84acf462aa8f) |
| Dark Mode | Dark Mode |
|![Screenshot_1746971660](https://github.com/user-attachments/assets/cf3aa7cb-41fe-4d82-98ea-a97032477fa4) | ![Screenshot_1746971662](https://github.com/user-attachments/assets/a4ceffc3-6f21-429e-9376-1a65cfd81219) |
