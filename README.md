# VISIONEER - Android App

## Overview
**VISIONEER** is an Android application designed to streamline the process of creating, editing, and visualizing floor plans. This app is particularly useful for architects, engineers, and stakeholders who need to communicate design ideas effectively.

## Features
1. **2D Floor Plan Generation**:
   - Generate 2D-floor plans with or without furniture using two diffusion models.

2. **Integration with Onshape**:
   - Download compatible formats for AutoCAD and other platforms or edit generated floor plans directly on the Onshape platform within the app.
   - Generate 3D models from the floor plans for a more comprehensive view using Onshape integrated features.

3. **Augmented Reality (AR) Visualization**:
   - View 3D models in AR to facilitate better communication among engineers and stakeholders.
   - Enhance collaboration by providing a tangible view of the design.

## Installation
1. Clone the repository:
   ```bash
   https://github.com/Ali-Mohamed109/VISIONEER_APP.git
   ```
2. Open the project in Android Studio.
3. Build and run the app on your Android device.

## Usage
1. **Generate Floor Plans**:
   - Open the app and select the option to generate a new floor plan.
   - Choose whether to include furniture or not.
   - Customize the layout as needed.

2. **Edit and Download**:
   - Save the generated floor plan and open it in the Onshape platform.
   - Make any necessary edits and download the updated version in a compatible format.

3. **View in AR**:
   - Select the 3D model view option.
   - Use your device's camera to view the model in AR.

## Models used
   - we used Stable Diffusion v1.5 as a base model and fine-tuned it twice using PEFT technique for two tasks:
      1. Schematic Design(without furniture):
           - model: <a href="https://huggingface.co/OmarAmir2001/visioneer5-0">First Fine-tuned model</a> 
           - Dataset: <a href="https://huggingface.co/datasets/OmarAmir2001/visioneer-dataset-with-no-text">First models dataset</a>
      2. Layout Design(with furniture):
         - Model: <a href="https://huggingface.co/Ahmed167/visioneer-v2">Second Fine-tuned model</a> 
         - Dataset:<a href="https://huggingface.co/datasets/OmarAmir2001/pseudo-floor-plan12k-modified2">pseudo-floor-plan12k-modified2</a>
            <p>this is a modified version of the original pseudo-floor-plan12k which contains some of the columns mainly plans and plans_captions</p>
         - original pseudo-floor-plan12k:<a href="https://huggingface.co/datasets/zimhe/pseudo-floor-plan-12k">pseudo-floor-plan-12k</a>
   - HuggingFace Tutorial: <a href="https://huggingface.co/docs/diffusers/index"> Diffusers Documentation</a>

   ## Android and AR
  Here is the function of each tool and library used in our application:

   - Android SDK: Provides the API libraries and developer tools necessary to build, test, and debug apps for Android.
   - Android Studio
   - Java
   - Gradle: A build automation tool used to compile, build, and manage dependencies in your Android project.
   - Retrofit: A type-safe HTTP client for Android and Java, used for making network requests to your backend API. <implementation ("com.squareup.retrofit2:converter-gson:2.9.0")>
   - OkHttp: An HTTP client used by Retrofit for handling network requests, providing efficient HTTP operations.
   - HttpLoggingInterceptor: A logging interceptor for OkHttp used to log HTTP request and response data. <implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")>
   - Gson: A library used to convert Java objects to JSON and vice versa, integrated with Retrofit for parsing JSON responses. < implementation ("com.squareup.retrofit2:converter-gson:2.9.0")>
   - ARCore: Google's platform for building augmented reality experiences on Android, providing tools for motion tracking, environmental understanding, and light estimation.       
     <implementation("com.google.ar:core:1.43.0")>
   - Material Components for Android: A library that allows you to build modern, beautiful, and responsive user interfaces using Material Design guidelines.
   - Fragment: A component of Android's UI that represents a portion of the user interface in an Activity, allowing for more modular and flexible UI designs.
   - FragmentManager: A class that provides methods to interact with fragments within an activity.
   - FragmentTransaction: A class that provides methods to perform a set of fragment operations atomically.
   - BottomNavigationView: A Material Design component used for bottom navigation in your app, providing a consistent way to navigate between top-level views.
   - Intents: Messaging objects used to request an action from another app component, facilitating communication between different parts of your app or between different apps.
   - TimeUnit: A utility class that provides time duration constants and conversion methods.
   - Bundle: A mapping from String keys to various Parcelable values, used for passing data between activities and fragments.
   - Serializable: A marker interface that indicates that a class can be serialized and deserialized.

   
