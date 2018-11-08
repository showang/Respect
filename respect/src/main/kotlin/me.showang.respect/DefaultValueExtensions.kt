package me.showang.respect

import kotlinx.coroutines.CoroutineScope
import me.showang.respect.core.RequestExecutor
import me.showang.respect.okhttp.OkhttpRequestExecutor

private val defaultExecutor: RequestExecutor by lazy { OkhttpRequestExecutor() }

class Respect {
    companion object {
        var requestExecutor: RequestExecutor? = null
    }
}

suspend fun RespectApi<*>.suspend() {
    suspend(Respect.requestExecutor ?: defaultExecutor)
}

fun <Result> RespectApi<Result>.start(scope: CoroutineScope,
                                      failHandler: (Error) -> Unit = {},
                                      successHandler: (Result) -> Unit) {
    start(Respect.requestExecutor ?: defaultExecutor, scope, failHandler, successHandler)
}