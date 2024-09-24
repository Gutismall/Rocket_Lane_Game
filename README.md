
# Project Name

## Overview
This Android app is built using Kotlin and implements various features like a scoreboard, user interaction through different activities, and functionality such as map integration. The app uses `SharedPreferences` for storing data, and components like fragments and custom adapters to display scores and handle user interactions.

### Key Features:
- **Scoreboard Functionality**: Users can view and interact with a scoreboard to track high scores.
- **Map Integration**: Includes a `MapFragment2` which likely integrates Google Maps functionality.
- **SharedPreferences**: Saves persistent user data like scores.
- **Custom Adapters**: Uses a custom `HighScoreAdapter` to display high scores.
- **Game Mechanics**: Managed through a `GameManeger` class, providing the core logic for the game.
- **Fragments for UI**: The app uses fragments to separate UI components, like `HighScoreListFragment`.

## File Structure
The app's main structure is as follows:

- **Activities**:
  - `MainActivity.kt`: The main entry point for the app.
  - `MenuActivity.kt`: Likely handles navigation and user interactions from the main menu.
  - `ScoreboardActivity.kt`: Displays the scoreboard with user scores.
  
- **Fragments**:
  - `HighScoreListFragment.kt`: Displays a list of high scores in a fragment.
  - `MapFragment2.kt`: Integrates Google Maps functionality, allowing users to interact with the map.
  
- **Models**:
  - `HighScore.kt`: Model class representing a high score.
  - `boardModel.kt`: Represents the game board and logic related to it.
  
- **Logic**:
  - `GameManeger.kt`: Manages game-related logic such as scoring, state transitions, and user interactions.
  
- **Utilities**:
  - `DataManger.kt`: Handles data management and probably deals with saving/loading data from `SharedPreferences`.
  - `SharedPreferences.kt`: Manages reading and writing persistent data to `SharedPreferences`.
  - `ImageLoader.kt`: Likely responsible for loading images dynamically.
  - `MoveDetector.kt`: Handles movement detection, likely for in-game interactions.
  
- **Interfaces**:
  - `OnScoreSelectedListener.kt`: Interface for handling score selection.
  - `Callback_MoveCallback.kt`: Interface for handling movement-related callbacks.
  - `CallBackLocation.kt`: Handles location-related callbacks, likely used for map integration.

## Technologies Used
- **Kotlin**: Primary programming language for the app.
- **Google Maps**: Integrated into the app via `MapFragment2.kt`.
- **Glide**: Used for image loading via `ImageLoader.kt`.
- **SharedPreferences**: Used to store persistent data such as user scores.
- **Fragments and Adapters**: For modular UI development.

## Setup Instructions

### Prerequisites:
1. Android Studio 4.0 or higher.
2. Kotlin support enabled.
3. A physical or virtual Android device with API level 23 or higher.
4. Google Maps API key (if using Google Maps in `MapFragment2`).

### Steps:
1. Clone this repository:
   ```bash
   git clone https://github.com/your-repository.git
   ```
   
2. Open the project in Android Studio.

3. Ensure the following dependencies are added to your `build.gradle` (app-level):
   ```gradle
   dependencies {
       implementation 'com.github.bumptech.glide:glide:4.12.0'
       annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
       implementation 'com.google.android.gms:play-services-maps:17.0.0'
   }
   ```

4. Obtain a Google Maps API key and add it to your `AndroidManifest.xml` under `<application>`:
   ```xml
   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="YOUR_API_KEY" />
   ```

5. Build and run the app on a physical or virtual device.

## Usage

### Main Features:
- **Scoreboard**: Add, view, and delete high scores.
- **Map**: View the location using the integrated Google Maps in `MapFragment2`.
- **Game Management**: The app provides game logic management and movement detection.

## Screenshots
(Include relevant screenshots here)

## Known Issues
- None reported at the moment.

## Future Enhancements
- Improved UI/UX design for the scoreboard and map.
- Additional game levels and features.

