package me.showang.respect

interface RequestExecutor {

    fun request(api: RespectApi,
                tag: Any = api,
                failCallback: (error: Error) -> Unit = {},
                completeCallback: (response: ByteArray) -> Unit)

    fun cancel(tag: Any)
    fun cancelAll()
}