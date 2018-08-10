package me.showang.respect

interface RespectApi {

    companion object {
        const val TIMEOUT_DEFAULT = 3000L
    }

    val url: String
    val httpMethod: HttpMethod
    val contentType: String

    val headers: Map<String, String>
    val urlQueries: Map<String, String>

    val body: ByteArray

    val priority: Priority get() = Priority.NORMAL
    val timeout: Long get() = TIMEOUT_DEFAULT

}