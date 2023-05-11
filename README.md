# NBA-Dashboard

NBA Dashboard is an Android application that showcases NBA teams, players, and games. 

## Overview

Built with MVVM architecture and clean architecture principles, the application consists of two main tabs:

### 1. Home Page Tab:
- Lists NBA teams with details including full name, city, and conference.
- Allows users to sort teams by name, city, or conference.
- Provides a detailed view of a team's recent games on selection.

### 2. Search Players Tab:
- Lists NBA players along with their associated teams.
- Provides a search functionality to find players.
- On selecting a player, displays the associated team's detailed information.

## API Used

The application uses the [balldontlie](https://www.balldontlie.io/) API for fetching NBA data. This includes information about players, teams, games, and more.

## Tech Stack
- Kotlin: The project is fully written in Kotlin.
- MVVM: The application follows the Model-View-ViewModel design pattern.
- Hilt: Hilt is used for dependency injection.
- Retrofit2: Retrofit is used for network tasks.
- Room: Room is used for data persistence.
- Paging 3: The Paging library is used to load and display pages of data from the API and cache.
- Compose: Jetpack Compose is used for building the app's UI.
- Datastore: Datastore is used for data storage.

## Architecture
The application uses the MVVM (Model-View-ViewModel) design pattern, supplemented with clean architecture principles. This ensures separation of concerns, testability, and maintainability.

# License
```xml
Designed and developed by 2023 evaldasso (Evaldas Sodonis)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```