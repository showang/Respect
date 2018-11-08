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

suspend fun <Result> RespectApi<Result>.suspend(): Result =
        suspend(Respect.requestExecutor ?: defaultExecutor)

fun <Result> RespectApi<Result>.start(scope: CoroutineScope? = null,
                                      failHandler: (Error) -> Unit = {},
                                      successHandler: (Result) -> Unit) {
    val executor = Respect.requestExecutor ?: defaultExecutor
    scope?.let {
        start(executor, it, failHandler, successHandler)
    } ?: run {
        start(executor = executor, failHandler = failHandler, successHandler = successHandler)
    }
}