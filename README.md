# Survey App

## Overview

This Android application is a simple survey tool consisting of two main screens: the Initial Screen and the Questions Screen. The user can start the survey, navigate through a set of questions, and submit their answers. This README provides instructions on how to set up, build, and run the application, as well as an overview of its functionality.

## Features

- **Initial Screen**: Contains a "Start survey" button.
- **Questions Screen**: A horizontal pager that displays survey questions.
    - Navigation buttons to move between questions.
    - A submit button to post answers to a server.
    - A dynamic counter that shows the number of questions submitted.

## Installation

### Prerequisites

- Android Studio
- Android SDK
- Gradle

### Steps

1. **Clone the repository**:
    ```bash
    git clone https://github.com/jakal23/XmQuestion
    ```
2. **Open the project** in Android Studio:
    - Start Android Studio.
    - Select "Open an existing Android Studio project".
    - Navigate to the cloned repository folder and open it.
3. **Build the project**:
    - Android Studio should automatically start building the project. If not, select `Build > Make Project`.
4. **Run the app**:
    - Connect an Android device via USB or start an emulator.
    - Click on the "Run" button in Android Studio or select `Run > Run 'app'`.

## Usage

1. **Launch the app** on your Android device or emulator.
2. **Initial Screen**:
    - Tap the "Start survey" button to begin the survey.
3. **Questions Screen**:
    - Navigate through questions using the "Previous" and "Next" buttons.
    - The "Previous" button is disabled on the first question, and the "Next" button is disabled on the last question.
    - Enter your answer in the text field.
    - Tap the "Submit" button to submit your answer. The button is disabled if the answer text is empty or if the question has already been submitted.
    - The counter at the top updates dynamically to show the number of questions submitted.

## Endpoints

- **Submit question endpoint**:
    - URL: `https://xm-assignment.web.app/question`
    - Method: POST
    - Success Response: 200 OK
    - Failure Response: 400 Bad Request

## Wireframes

### Initial Screen

```
![Screenshot_20240518_184355](https://github.com/jakal23/XmQuestion/assets/9334960/aea918bf-3cf6-4761-bcb9-7dd6d5526aac)
```

### Questions Screen

```
![Screenshot_20240518_184420](https://github.com/jakal23/XmQuestion/assets/9334960/08320d39-02d3-447c-a1fd-0f22fce3401d)
```
![Screenshot_20240518_184443](https://github.com/jakal23/XmQuestion/assets/9334960/6e625f37-3651-456d-9589-01168bb85dc5)

## Notes

- Navigating back to the Initial Screen and tapping "Start survey" again restarts the survey with 0 answered questions.
- Ensure you handle both 200 (success) and 400 (failure) responses appropriately when submitting answers.

## Contribution

Feel free to fork the repository and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

For any inquiries or issues, please contact [vahe_gharibyan@epam.com].
