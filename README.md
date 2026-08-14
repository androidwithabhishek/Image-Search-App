# Image Search App

An Android image search application powered by the Unsplash API. The app allows users to search for images, browse results with pagination, open images in detail, zoom in for a closer view, and download images in different available qualities.

## Screenshots

|                                                                                                               |                                                                                                               |                                                                                                               |                                                                                                               |
| ------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- |
| <img src="https://raw.githubusercontent.com/androidwithabhishek/my-res/main/image_search/1.jpeg" width="180"> | <img src="https://raw.githubusercontent.com/androidwithabhishek/my-res/main/image_search/2.jpeg" width="180"> | <img src="https://raw.githubusercontent.com/androidwithabhishek/my-res/main/image_search/3.jpeg" width="180"> | <img src="https://raw.githubusercontent.com/androidwithabhishek/my-res/main/image_search/4.jpeg" width="180"> |
| <img src="https://raw.githubusercontent.com/androidwithabhishek/my-res/main/image_search/5.jpeg" width="180"> | <img src="https://raw.githubusercontent.com/androidwithabhishek/my-res/main/image_search/6.jpeg" width="180"> | <img src="https://raw.githubusercontent.com/androidwithabhishek/my-res/main/image_search/7.jpeg" width="180"> | <img src="https://raw.githubusercontent.com/androidwithabhishek/my-res/main/image_search/8.png" width="180">  |

## Features

* Search images using the Unsplash API
* Paginated image loading
* Image detail and preview screen
* Zoomable image viewing
* Download images in different available qualities
* Local image caching
* Offline access to previously loaded content
* Material 3 interface
* Loading and error states
* Efficient image loading and memory handling

## Tech Stack

* **Kotlin** — Primary programming language
* **Jetpack Compose** — Declarative UI toolkit
* **Material 3** — Modern Android design system
* **MVVM** — Application architecture
* **Clean Architecture** — Separation of presentation, domain, and data layers
* **Repository Pattern** — Centralized data access
* **Retrofit** — REST API communication
* **Paging 3** — Efficient paginated image loading
* **Room** — Local database and caching
* **Hilt** — Dependency injection
* **Coil** — Image loading and rendering
* **Coroutines & Flow** — Asynchronous and reactive data handling
* **Unsplash API** — Image search and image data

## Architecture

The project follows a clean and maintainable architecture with clear separation between the presentation, domain, and data layers.

```text
UI / Presentation
       |
   ViewModel
       |
   Use Cases
       |
   Repository
      / \
     /   \
Remote    Local
 API       Room
```

This structure keeps business logic independent from UI components and makes the application easier to maintain, test, and extend.

## API

The application uses the **Unsplash API** for image search and image metadata.

You will need an Unsplash API access key to run the project locally.

Create an application through the Unsplash Developer Portal and configure the required API key according to the project's configuration.

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/androidwithabhishek/Image-Search-App.git
```

### 2. Open the Project

Open the cloned project in Android Studio.

### 3. Configure the API Key

Add your Unsplash API access key using the configuration expected by the project.

### 4. Build and Run

Sync the Gradle files, build the project, and run the application on an Android device or emulator.

## Project Highlights

This project demonstrates practical implementation of:

* REST API integration
* Paging with remote data
* Local database caching
* Repository-based data management
* Dependency injection
* Reactive UI state handling
* Image loading and caching
* Remote image downloading
* Offline data access
* Modern Android UI development

## Repository

[View the source code on GitHub](https://github.com/androidwithabhishek/Image-Search-App)

## Author

**Abhishek Gupta**

Android Developer focused on building modern, maintainable Android applications with Kotlin and Jetpack Compose.
