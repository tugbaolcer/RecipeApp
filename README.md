# 🍲 RecipeApp

RecipeApp is a modern Android application that allows users to explore and discover delicious recipes.  
This project is built with **Clean Architecture** and follows best practices in Android development, ensuring scalability, maintainability, and testability.  

---

## 🚀 Features
- Browse and explore recipes
- Modern **MVVM + Clean Architecture** structure
- **Dependency Injection** with Hilt
- **Firebase Authentication** support
- **Kotlin Coroutines & Flow** for asynchronous programming
- **Retrofit + Moshi** for network layer and JSON parsing
- **DataBinding** for efficient UI binding
  
---

## 🛠️ Tech Stack
- **Language:** Kotlin  
- **Architecture:** Clean Architecture (Data, Domain, Presentation layers)  
- **Concurrency:** Coroutines & Flow  
- **Network:** Retrofit + Moshi  
- **Dependency Injection:** Dagger Hilt  
- **Authentication:** Firebase Authentication  
- **UI:** Android View System + XML  

---

## 📂 Project Structure
RecipeApp/

├── data/ # Handles API, DTOs, repository implementations

├── domain/ # Business logic: models, repository interfaces, use cases

├── presentation/ # UI layer: ViewModels, Activities, Fragments

└── di/ # Hilt modules for dependency injection


---

## 🔑 Clean Architecture
- **Presentation Layer** → Contains ViewModels and UI logic.  
- **Domain Layer** → Contains use cases and business models.  
- **Data Layer** → Contains repositories, API services, and DTOs.  

This separation of concerns provides better testability and maintainability.

---

## 📦 Dependencies
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)  
- [Kotlin Flow](https://developer.android.com/kotlin/flow)  
- [Retrofit](https://square.github.io/retrofit/)  
- [Moshi](https://github.com/square/moshi)  
- [Firebase Authentication](https://firebase.google.com/docs/auth)  
- [Dagger Hilt](https://dagger.dev/hilt/)  

---

## 🚀 Getting Started
1. Clone the repository  
   ```bash
   git clone https://github.com/tugbaolcer/RecipeApp.git
   ```

2. Open the project in Android Studio

3. Add your Firebase configuration (google-services.json)

4. Build & Run 🚀
   

