# Android template project

This project aims to group best practices and best libraries for Android development. The sample app is a shopping list creator.

It demonstrate how to instantiate fragments with parameters and automatically save their parameters as well as their DTO state using **IcePick** and **FragmentArgs**.

To make it savable/restorable, DTOs are made parcelable using **ParcelablePlease** library.

To associates UI components to field variable and listener methods, we use **Butterknife**.

In order to easily change logging system, we use **Timber** as a logging entry point, which is integrated with **Android-Logger** for pretty printing.

For API calls, we use **Retrofit 2** integrated with **RxJava**.

Services are injected using **Dagger**. Dagger is configured to instanciate two different services configuration according the selection of one of the predefined flavours : *mock* and *full*.
The mock configuration provide a mocked *Retrofit* mock proxy that simulate behaviour of the network (async delay, failures, etc) to test error handling.

A default implementation for error handling is implemented. More precisely, when retrofit generates network errors, we need to parse API errors. A ** CallAdapterFactory** encapsulating **RxJavaCallAdapterFactory** (RxJava Retrofit integration) allows to parse errors when http response code are not 2XX.

To be able to use **Java8 lambdas** and **method references**, the project build is configured to use **Jack compiler** and code is processed with the **Retrolambda** processor. We simulate Java8 stream using **Annimon Stream** library.

### Libraries
- Dagger
- Butterknife
- FragmentArgs
- IcePick
- Timber (logging hub + automatic tagging)
- Logger (pretty logger integrated with Timber)
- ParcelablePlease
- Retrofit 2
- RxJava
- Jackson
- OkHttp3
- Annimon Stream

### Gradle config
- flavours with Dagger modules
- java 8 + retrolambda processor + Annimon streams
- MultiDex application

