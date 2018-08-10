package me.showang.respect.base

import me.showang.respect.ContentType
import me.showang.respect.RequestExecutor
import me.showang.respect.RespectApi

abstract class SimpleApi<ResultType, ChildType : SimpleApi<ResultType, ChildType>> : RespectApi {

    override val contentType: String
        get() = ContentType.NONE
    override val headers: Map<String, String>
        get() = emptyMap()
    override val urlQueries: Map<String, String>
        get() = emptyMap()
    override val body: ByteArray
        get() = ByteArray(0)

    fun start(executor: RequestExecutor,
              tag: Any = this,
              failHandler: (Error) -> Unit = {},
              successHandler: (ResultType) -> Unit): ChildType {
        executor.request(this, tag, failHandler) {
            try {
                successHandler(parse(it))
            } catch (e: Error) {
                failHandler(e)
            }
        }
        @Suppress("UNCHECKED_CAST")
        return this as? ChildType ?: throw Error("Child type error.")
    }

    @Throws(Exception::class)
    protected abstract fun parse(bytes: ByteArray): ResultType

}