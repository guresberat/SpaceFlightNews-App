# SpaceFlightNewsApp Documentation

## Introduction
SpaceFlightNewsApp is a mobile application developed to display news headlines related to spaceflight using the SpaceFlightNewsApi. The application allows users to view a list of news headlines, read detailed information about each news article, and add interesting articles to their reading list for later reference.

## Key Features

### News Headlines
The home screen displays a list of news headlines related to spaceflight.
Each headline includes essential information such as title, image, news site, published date, and a summary.

### Article Detail
Users can view detailed information about a selected news article on the article detail screen.
The article detail screen features a prominent placement for the news image, with a collapsible toolbar for scrolling through the summary text.

### Reading List
Users can add news articles to their reading list for later reference.
The reading list persists across app sessions, ensuring that saved articles are retained even if the app is closed and reopened.

### Search Functionality
The app includes a search feature that allows users to search through the list of articles based on the text they set.
This feature enhances user convenience, enabling them to find specific content quickly.

## Architecture

### MVVM Architecture

The project follows the MVVM (Model-View-ViewModel) architecture pattern, separating concerns between the data model, UI components, and business logic.

### Clean Code Principles

The SpaceFlightNewsApp adheres to clean code principles to ensure readability, maintainability, and efficiency in the codebase. Here are some key clean code practices followed in the project:
- Descriptive Naming
- Modularization and Package Structure
- Consistent Formatting
- Comments and Documentation
- Use of Constants
- Avoidance of Magic Numbers
- Error Handling
- SOLID Principles

### Use Case Pattern

The Use Case pattern is employed to manage business logic, providing a clear separation between the presentation layer and the data layer.

### Repository Pattern

The Repository pattern is implemented to handle data access, incorporating both local and remote data sources.

## Modules and Packages

### Module Structure

The project is structured into modular components, including features like home and article detail. The project has 3 modules that are data, domain, and presentation.

## Dependencies

### Retrofit
The Retrofit library is used for handling HTTP requests, providing a clean and concise way to interact with the SpaceFlightNewsApi.

### Room
Room is employed for local data storage, allowing the application to cache articles and manage the reading list efficiently.

## Database

### Room Database

The Room database is utilized to store articles locally, facilitating offline access and preserving the reading list across app sessions.

## Networking

### SpaceFlightAPI

Integration with the SpaceFlightAPI allows the app to fetch news headlines asynchronously. Special attention is given to handling network connectivity for optimal user experience.

### Network Connectivity

The application actively monitors network connectivity and refrains from initiating API calls when the network is unavailable. This ensures that the app remains responsive and avoids unnecessary attempts to fetch data in the absence of a network connection.

## User Interface

## Screens and Fragments

The application consists of two main screens: the home screen for listing news headlines and the article detail screen for reading detailed information about each article.

## Key Components

### ViewModel

View models manage UI-related data and communicate with the domain layer, ensuring separation of concerns and testability.

### Use Cases

Use cases encapsulate business logic and are responsible for orchestrating data flow between the UI and repositories.

### Repository

Repositories abstract data access, providing a clean API for the rest of the application to fetch and update data.

## Testing

### Test Strategy

The project includes comprehensive testing covering unit tests for view models with %100 coverage.