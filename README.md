# Wordy

Wordy is an Android word-guessing game that allows users to guess words and manage the word database.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
  - [Gameplay](#gameplay)
  - [Adding Words](#adding-words)
- [Contributing](#contributing)
- [Acknowledgments](#acknowledgments)

## Author

Zachary Patchen

## Introduction

Wordy is a simple Android application developed in Java. It provides users with a word-guessing game where they can play, add new words to a firebase database, and celebrate victories.

## Features

- **Gameplay:** Guess words in a grid-based interface and progress through rounds.
- **Database Management:** Add new words to the firebase database or clear the entire database.
- **Victory Screen:** Enjoy a congratulatory message when successfully guessing a word.

## Getting Started

### Prerequisites

- Android Studio installed on your development machine.
- An Android device or emulator for testing.

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/wordy.git
    ```

2. Open the project in Android Studio.

3. Build and run the application on an Android device or emulator.

## Usage

### Gameplay

1. **Start a New Game:**
   - Launch the app and click on "Play" from the main screen.
   - Guess the word in each round using the provided grid of EditText views.

2. **Victory:**
   - When you successfully guess the word, you'll be taken to the Victory screen.
   - Click "Back to Main" to return to the main screen.

### Adding Words

1. **Add New Words:**
   - Navigate to the "Add Words" section from the main screen.
   - Input a new word and click "Submit" to add it to the firebase database.

2. **Clear Database:**
   - From the "Add Words" section, click "Clear Database" to remove all words from the firebase database.

## Contributing

Contributions are welcome! If you'd like to contribute to Wordy, please follow the [contributing guidelines](CONTRIBUTING.md).

## Acknowledgments

- Thanks to [Firebase](https://firebase.google.com/) for providing the backend infrastructure.
- Special thanks to my professor, Esteban Parra Rodriguez
