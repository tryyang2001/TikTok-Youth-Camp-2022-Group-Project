# WeatherReport

## Description
Are you tired of being caught outside during a thunderstorm? Really wish you had an umbrella for that sweltering sunny weather? Finding your current weather app too dull? This weather app is for you!
Project Set Up

## System Requirement
1. git
2. Android Studio

## Generate APK
Execute the following commands in your system’s command line program:
```
git clone https://github.com/tryyang2001/TikTok-Youth-Camp-2022-Group-Project.git
cd TikTok-Youth-Camp-2022-Group-Project
```

For Windows systems, run the following command to start up the release build:
```
gradlew.bat assembleRelease
```

For Linux / MacOS systems, run the following command to start up the release build:
```
./gradlew assembleRelease
```

The previous command will generate an .apk file that can be used to install on your Android device. The .apk file can be found within your project, specifically in the `TikTok-Youth-Camp-2022-Group-Project/app/build/outputs/apk/release/` directory.

## Usage of Application

![Mobile application’s main page](docs/figure1.jpg?raw=true "Mobile application’s main page")
    
This is the main display of our mobile application that will appear on your Android device as you open it up.

Towards the top of your display, you will be shown with a box of information. It contains the current temperature of your selected region at the top half of the box, along with the weather forecast at the bottom half.

Below that, there is a row of weather forecasts for the current date, separated into different periods of the day, i.e. `Morning`, `Afternoon`, `Evening` and `Night`. It shows the expected weather forecast of your region for the next 24 hours.

Following the row of weather forecasts for the current date, is it another row displaying an overview of the general expected weather forecast for the next 4 days.

At the bottom part of your display, there is a button indicating `SHOW REGIONS`. To change your weather forecast of another region, click on that button and a map of Singapore will pop up.

![Region marked Singapore map](docs/figure2.jpg?raw=true "Region marked Singapore map")

Looking carefully at the Singapore map, you can see that the map was marked with 5 purple circles, `W`, `N`, `S`, `C`, `E`. These markers help to break the country up into 5 different regions.
 
You can select a region by tapping onto one of the purple circles. The following table shows a mapping of the different purple circles and their corresponding regions.

| Purple Circle | Region |
| :---          | :---:  |
| W             | West   |
| N             | North  |
| S             | South  |
| C             | Central|
| E             | East   |
