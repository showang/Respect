package me.showang.respect

import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.runBlocking
import me.showang.respect.core.ContentType
import me.showang.respect.core.HttpMethod
import me.showang.respect.core.error.ParseError
import me.showang.respect.core.error.RequestError
import org.junit.Test

class RestFulApiTest {

    @Test
    fun testGet_urlQuery() {
        runBlocking {
            GetUrlQueryApi().start(Default, failHandler = {
                assert(false)
            }) {
                println(it)
            }.join()
        }
    }

    class GetUrlQueryApi : RestfulApi<String>() {
        override fun parse(bytes: ByteArray): String = String(bytes)

        override val url: String
            get() = "https://jsonplaceholder.typicode.com/comments"
        override val httpMethod: HttpMethod
            get() = HttpMethod.GET
        override val urlQueries: Map<String, String>
            get() = mapOf("postId" to "1")              // ?postId=1
        override val contentType: String
            get() = ContentType.JSON                    // Default is ContentType.NONE
    }

    @Test
    fun testGet_urlPath() {
        runBlocking {
            GetUrlPathApi("1").start(Default, {
                assert(false)
            }) {
                println(it)
            }.join()
        }

    }

    class GetUrlPathApi(private val postId: String) : RestfulApi<String>() {
        override fun parse(bytes: ByteArray): String {
            return String(bytes)
        }

        override val url: String
            get() = "https://jsonplaceholder.typicode.com/posts/$postId/comments"
        override val httpMethod: HttpMethod
            get() = HttpMethod.GET
        override val contentType: String
            get() = ContentType.JSON
    }

    @Test
    fun testPost_success() {
        val id = "101"
        runBlocking {
            PostJsonApi(id).start(Default, {
                assert(false)
            }) {
                println(it)
                assert(it.contains(id)) { "didn't contains id: $id" }
            }.join()
            println("wtf")
        }
    }

    class PostJsonApi(private val id: String) : RestfulApi<String>() {
        override fun parse(bytes: ByteArray): String = String(bytes)

        override val url: String
            get() = "https://jsonplaceholder.typicode.com/posts"
        override val httpMethod: HttpMethod
            get() = HttpMethod.POST
        override val contentType: String
            get() = ContentType.JSON
        override val body: ByteArray
            get() = "{\"id\":\"$id\"}".toByteArray()
    }


    class ParseErrorApi : RestfulApi<String>() {
        override val httpMethod: HttpMethod
            get() = HttpMethod.GET
        override val url: String
            get() = "https://jsonplaceholder.typicode.com/posts/1/comments"
        override val contentType: String
            get() = ContentType.JSON

        override fun parse(bytes: ByteArray): String {
            throw IllegalArgumentException("Parse Error")
        }
    }

    @Test
    fun testError_parseException() {
        runBlocking {
            ParseErrorApi().start(Default, {
                println("on parse error: $it")
                assert(it is ParseError)
            }) {
                assert(false)
            }.join()
        }
    }

    class NotFoundErrorApi : RestfulApi<String>() {
        override val httpMethod: HttpMethod
            get() = HttpMethod.GET
        override val url: String
            get() = "https://jsonplaceholder.typicode.com/po"
        override val contentType: String
            get() = ContentType.JSON

        override fun parse(bytes: ByteArray): String {
            throw IllegalArgumentException("Parse Error")
        }
    }

    @Test
    fun testError_404NotFound() {
        runBlocking {
            NotFoundErrorApi().start(Default, {
                assert(it is RequestError) { "testError_404NotFound must throw RequestError" }
            }) {
                assert(false) { "testError_404NotFound should be failed" }
            }.join()
        }
    }

}