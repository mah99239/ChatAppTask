# Overview
A chat application showcasing two screens: "type user" and "chat screen."  implements a real-time chat experience using WebSockets and the MVVM architecture for separation of concerns. Users can exchange messages with each other and store chat history locally in a database.
## Project Structure
The project is organized into four packages:
* di: Contains the dependency injection setup using Hilt.
* data: Handles data-related operations, including network requests, local storage, and data models.
* domain: Defines the business logic of the chat application.
* Repository: Provides an abstraction layer for interacting with the local database and WebSocket communication.
* presentation: Houses the UI components, ViewModels, and navigation setup.


## Libraries Used
* Navigation Component: Used for seamless navigation between the "Products" and "Product Details" screens.
* Hilt: Employs dependency injection to enhance code organization and testability.
* Coroutines: Facilitates asynchronous operations for efficient background tasks.
* WebSockets with OkHttp:
* Room: Facilitates offline caching, providing a local database for data storage and retrieval.
* ViewModel and LiveData: Manages UI-related data in a lifecycle-aware manner.
* ViewBinding: Reduces boilerplate code related to UI interactions.

### Screenshot
[![](screen_shoot/screen_chat_task.gif)]