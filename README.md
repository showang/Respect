[![](https://jitpack.io/v/showang/Respect.svg)](https://jitpack.io/#showang/Respect)

# Respect
A simple RESTful API framework depends on language spec, without invasive.  
  
Request is based on OkHttp3, it's available to customize your own request executor.

# Example
   
```val executor: RequestExecutor = OkHttpRequestExecutor(httpClient)```

## Get
```
class GetUrlQueryApi : BasicApi<String, GetUrlQueryApi>() {
    override fun parse(bytes: ByteArray): String {
        return String(bytes)
    }

    override val url: String
        get() = "https://jsonplaceholder.typicode.com/comments"
    override val httpMethod: HttpMethod
        get() = HttpMethod.GET
    override val urlQueries: Map<String, String>
        get() = mapOf("postId" to "1")              // ?postId=1              
}

val getApi = GetUrlQuerApi().start(executor, { error ->
	// Error handling.
}) { result ->
	// Process your result
}
```
## Post
```
class PostJsonApi(private val id: String) : BasicApi<String, PostJsonApi>() {
    override fun parse(bytes: ByteArray): String {
        return String(bytes)
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

val postApi = PostJsonApi("post id").start(executor, { error ->
	// Error handling.
}) { result ->
	// Process your result
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

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
## Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.showang:Respect:0.0.2'
	}
