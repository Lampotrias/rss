# Android RSS reader

It’s a free and open source feed reader for Android

# Architecture
  - A single-activity architecture, using the [Navigation component] to manage fragment operations.
  - [Android architecture components], part of Android Jetpack for give to project a robust design, testable and maintainable.
  - Pattern Model-View-Presenter (MPV) facilitating a separation of development of the graphical user interface.
  - [S.O.L.I.D] design principles intended to make software designs more understandable, flexible and maintainable.
  - [Android-CleanArchitecture](https://fernandocejas.com/2018/05/07/architecting-android-reloaded/) 

# Tech-stack
This project takes advantage of many popular libraries, plugins and tools of the Android ecosystem. Most of the libraries are in the stable version, unless there is a good reason to use non-stable dependency.
-   Minimum SDK level 21
-   Java based + RXJava for asynchronous.
   
#### Dependencies

-   [Jetpack](https://developer.android.com/jetpack):
    -   [AndroidX](https://developer.android.com/jetpack/androidx) - major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    -   [Navigation](https://developer.android.com/guide/navigation/) - helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.
    -   [Room](https://developer.android.com/topic/libraries/architecture/room) - persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
    -   [Work Manager](https://developer.android.com/topic/libraries/architecture/workmanager/basics) - WorkManager is an API that makes it easy to schedule deferrable, asynchronous tasks that are expected to run even if the app exits or the device restarts.
-   [RxJava2](https://github.com/ReactiveX/RxJava) - RxJava is a Java VM implementation of Reactive Extensions: a library for composing asynchronous and event-based programs by using observable sequences.
-   [Dagger2](https://developer.android.com/training/dependency-injection/dagger-android) - dependency injector for replacement all FactoryFactory classes.

#### Plugins
- [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args) - generates simple object and builder classes for type-safe navigation and access to any associated arguments.

# Design
In terms of design has been followed recommendations android [material design](https://developer.android.com/guide/topics/ui/look-and-feel) comprehensive guide for visual design across platforms and devices

   [Navigation component]: <https://developer.android.com/guide/navigation/navigation-getting-started>
   [Android architecture components]: <https://developer.android.com/topic/libraries/architecture/>
   [S.O.L.I.D]: <https://en.wikipedia.org/wiki/SOLID>
 
