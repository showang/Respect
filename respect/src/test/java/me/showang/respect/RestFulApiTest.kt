package me.showang.respect

import kotlinx.coroutines.runBlocking
import me.showang.respect.core.ContentType
import me.showang.respect.core.HttpMethod
import org.junit.Test

class RestFulApiTest {

    @Test
    fun testGet_urlQuery() {
        runBlocking {
            GetUrlQueryApi().start(this, failHandler = {
                assert(false)
            }) {
                println(it)
            }
        }
    }

    class GetUrlQueryApi : RespectApi<String>() {
        override fun parse(bytes: ByteArray): String {
            return String(bytes)
        }

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
            GetUrlPathApi("1").start(this, {
                assert(false)
            }) {
                println(it)
            }
        }

    }

    class GetUrlPathApi(private val postId: String) : RespectApi<String>() {
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
        val id = "66666"
        runBlocking {
            PostJsonApi(id).start(this, {
                assert(false)
            }) {
                println(it)
                assert(it.contains(id))
            }
        }
    }

    class PostJsonApi(private val id: String) : RespectApi<String>() {
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
            get() = "{\"id\":\"$id\"}".toByteArray()
    }

}