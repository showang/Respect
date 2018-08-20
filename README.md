[![Build Status](https://travis-ci.org/showang/Respect.svg?branch=master)](https://travis-ci.org/showang/Respect)
[![](https://jitpack.io/v/showang/Respect.svg)](https://jitpack.io/#showang/Respect)

# Respect
A simple RESTful API framework depends on language spec, without invasive.  
  
HTTP request based on OkHttp3, it's available to customize your own request executor.

#### Modules
* [Respect-Core](https://github.com/showang/Respect-Core)
* [Respect-OkHttp](https://github.com/showang/Respect-OkHttp)

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

    override val url: String
        get() = "https://jsonplaceholder.typicode.com/comments"
    override val httpMethod: HttpMethod
        get() = HttpMethod.GET
    override val urlQueries: Map<String, String>
        get() = mapOf("postId" to "1")              // ?postId=1              
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

    override val url: String
        get() = "https://jsonplaceholder.typicode.com/posts"
    override val httpMethod: HttpMethod
        get() = HttpMethod.POST
    override val contentType: String
        get() = ContentType.JSON
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
        implementation 'com.github.showang:Respect:0.1.1'
}
```
