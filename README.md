# High Score Tracker

## Overview

High Score Tracker is an Android application designed to keep track of high scores for various games. The app allows users to view, add, and manage high scores, ensuring that only the top scores are retained.

## Features

- **View High Scores**: Displays a list of the top 10 high scores.
- **Add High Scores**: Automatically adds new high scores, replacing the lowest score if the new score is higher.
- **Persistent Storage**: Uses `SharedPreferences` to store high scores, ensuring data is retained between app sessions.
- **Custom List Adapter**: Utilizes a custom adapter to display high scores in a ListView.

## Technical Details

- **Language**: Kotlin
- **Framework**: Android SDK
- **Storage**: SharedPreferences with JSON serialization
- **Architecture**: Singleton pattern for data management

## Project Structure

- `App.kt`: Initializes the application and its components.
- `HighScoreListFragment.kt`: Fragment to display the list of high scores.
- `DataManger.kt`: Manages the high scores, including adding and retrieving scores.
- `SharedPreferences.kt`: Handles the storage and retrieval of high scores using SharedPreferences.
- `HighScoreAdapter.kt`: Custom adapter for displaying high scores in a ListView.

## How to Use

1. **Initialization**: The app initializes the `SharedPreferences`, `DataManger`, and other utilities in the `App` class.
2. **Viewing Scores**: The `HighScoreListFragment` displays the high scores using a ListView.
3. **Adding Scores**: Scores are added through the `DataManger` class, which ensures only the top 10 scores are kept.

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/high-score-tracker.git
    ```
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.
