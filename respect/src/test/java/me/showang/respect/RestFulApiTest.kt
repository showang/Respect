package me.showang.respect

import me.showang.respect.base.BasicApi
import me.showang.respect.core.ContentType
import me.showang.respect.core.HttpMethod
import me.showang.respect.core.RequestExecutor
import me.showang.respect.okhttp.OkhttpRequestExecutor
import org.junit.Test

class RestFulApiTest {

    private val executor: RequestExecutor = OkhttpRequestExecutor(syncMode = true)

    @Test
    fun testGet_urlQuery() {
        GetUrlQueryApi().start(executor, {}) {
            println(it)
        }
    }

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
        override val contentType: String
            get() = ContentType.JSON                    // Default is ContentType.NONE
    }

    @Test
    fun testGet_urlPath() {
        GetUrlPathApi("1").start(executor) {
            println(it)
        }
    }

    class GetUrlPathApi(private val postId: String) : BasicApi<String, GetUrlQueryApi>() {
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
        PostJsonApi("66666").start(executor, {}) {
            println(it)
        }
    }

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

}