[![Build Status](https://travis-ci.org/showang/Respect.svg?branch=master)](https://travis-ci.org/showang/Respect)
[![](https://jitpack.io/v/showang/Respect.svg)](https://jitpack.io/#showang/Respect)

# Respect
A simple RESTful API framework depends on language spec, without invasive.  
  
HTTP request based on OkHttp3 by default, but you can build your own request executor if any requiring implementation changes.

#### Modules
* [Respect-Core](https://github.com/showang/Respect-Core)
* [Respect-OkHttp](https://github.com/showang/Respect-OkHttp)

# New Feature
### \[ Ver 1.0.0 \] Officially Release. ( Refactoring and upgrading libraries version)
### \[ Ver 0.2.4 \] Fix error handling issue.
### \[ Ver 0.2.1 \] Support default request executor.
```kotlin
uiScope.launch {
    val result = GetUrlQuerApi().suspend() //Worker thread.
    update(result)
}
```

### \[ Ver 0.2.0 \] Support Kotlin Coroutine API. (Require Kotlin 1.3)
```kotlin
uiScope.launch {
    val result = GetUrlQuerApi().suspend(requestExecutor) //Worker thread.
    update(result)
}
```

# Example
   
```kotlin
val executor: RequestExecutor = OkHttpRequestExecutor(okHttpClient)
```

## Get
```kotlin
val getApi = GetUrlQuerApi().start(executor, { error ->
	// Error handling.
}) { result ->
	// Process your result
}

class GetUrlQueryApi : RespectApi<ApiResult, GetUrlQueryApi>() {
    override fun parse(bytes: ByteArray): ApiResult {
        return ApiResult.parseToModels(bytes)	// Parse result to your model classes
    }

    override val url = "https://jsonplaceholder.typicode.com/comments"
    override val httpMethod = HttpMethod.GET
    override val urlQueries = mapOf("postId" to "1")              // ?postId=1
}
```
## Post
```kotlin
val postApi = PostJsonApi("post id").start(executor, { error ->
	// Error handling.
}) { result ->
	// Process your result
}

class PostJsonApi(private val id: String) : RespectApi<ApiResult, PostJsonApi>() {
    override fun parse(bytes: ByteArray): ApiResult {
        return ApiResult.parseToModels(bytes)	// Parse result to your model classes
    }

    override val url = "https://jsonplaceholder.typicode.com/posts"
    override val httpMethod = HttpMethod.POST
    override val contentType = ContentType.JSON
    override val body: ByteArray
        get() = "{\"id\"=\"$id\"}".toByteArray()
}
```
...etc

# How to
To get a Git project into your build:

## Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
## Step 2. Add the dependency
```gradle
dependencies {
        implementation 'com.github.showang:Respect:{last_version}'
}
```
