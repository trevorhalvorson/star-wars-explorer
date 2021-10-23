Star Wars Explorer
===

A sample application demoing some of the latest and greatest Android patterns, practices, and tools such as [Android Jetpack](https://developer.android.com/jetpack).

Star Wars data provided by [SWAPI](https://www.swapi.tech/).

### Architecture

This app follows [Android's guide to app architecture](https://developer.android.com/jetpack/guide) by implementing strong separation of concerns and a model-driven UI.
More specifically, the UI (view) is separated from the data (model) layer with an Android [`ViewModel`](https://developer.android.com/topic/libraries/architecture/viewmodel).

As an example, the following diagram outlines how the people list screen functions:

```
                    PeopleListFragment
                            |
                            v
                    PeopleListViewModel
                            |
                            v
                -----PeopleRepository-----
                |                        |
                v                        v
            Room/SQLite                SWAPI
                            
```

`PeopleListFragment` listens for UI state updates emitted by `PeopleListViewModel` via a [`StateFlow`](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow).
`PeopleListFragment` listens for these updates in a coroutine tied to it's lifecycle to ensure updates are only processed when the UI is visible ([LifecycleScope](https://developer.android.com/topic/libraries/architecture/coroutines#lifecyclescope)).

`PeopleListViewModel` listens for data updates emitted by `PeopleRepository` via a `Flow`.
It does this in a coroutine tied the ViewModel ensuring the coroutine will be canceled when the ViewModel is no longer in use ([ViewModelScope](https://developer.android.com/topic/libraries/architecture/coroutines#viewmodelscope)).

As the interface to our data sources, `PeopleRepository` is responsible for fetching the underlying *people* data.
Our data sources are a SQLite DB and an HTTP web service. As described previously, `PeopleRepository` will return a `Flow` to the ViewModel.
This Flow will come directly from our database.
This pattern allows us to treat our database as the [single source of truth](https://developer.android.com/jetpack/guide#truth) where the ViewModel will listen for changes to our database which may be updated asynchronously with data fetched from the network. 

Additionally, this application uses a sinlge-activity/multiple fragment architecture with navigation managed by the [Navigation component](https://developer.android.com/guide/navigation).

### Dependencies (selected)

- [Gson](https://github.com/google/gson) (JSON serial/deserialization)
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) (dependency injection)
- [Lifecycle-aware components](https://developer.android.com/topic/libraries/architecture/lifecycle) (component lifecycle changes)
- [Navigation component](https://developer.android.com/guide/navigation) (in-app navigation)
- [Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) (load and display paginated data)
- [Retrofit](https://square.github.io/retrofit/)/[OkHttp](https://square.github.io/okhttp/) (REST/HTTP client)
- [Room](https://developer.android.com/training/data-storage/room) (SQLite abstraction)

> This project uses a Gradle [`buildSrc`](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources) directory to simplify our dependency management and versioning.
(see `buildSrc` in the root of the project)

### Tools/other

- [Kotlin](https://kotlinlang.org/)
- [Flows](https://developer.android.com/kotlin/flow)
- [View Binding](https://developer.android.com/topic/libraries/view-binding)
