package me.showang.respect

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.showang.respect.core.RequestExecutor
import me.showang.respect.okhttp.OkhttpRequestExecutor
import okhttp3.OkHttpClient
import kotlin.coroutines.CoroutineContext

private val defaultExecutor: RequestExecutor by lazy {
    OkhttpRequestExecutor(OkHttpClient())
}

class Respect {
    companion object {
        var specifiedExecutor: RequestExecutor? = null
    }
}

suspend fun <Result> RestfulApi<Result>.request(): Result =
        request(Respect.specifiedExecutor ?: defaultExecutor)

suspend fun <Result> RestfulApi<Result>.request(errorDelegate: (Throwable) -> Unit): Result? = try {
    request()
} catch (e: Throwable) {
    e.takeIf { it !is CancellationException }?.let(errorDelegate)
    null
}

fun <Result> RestfulApi<Result>.start(
        uiContext: CoroutineContext,
        failHandler: (Throwable) -> Unit = {},
        successHandler: (Result) -> Unit
) = CoroutineScope(IO).launch {
    try {
        val result = request()
        withContext(uiContext) { successHandler(result) }
    } catch (e: Throwable) {
        withContext(uiContext) { failHandler(e) }
    }
}

fun <Result> RestfulApi<Result>.start(
        failHandler: (Throwable) -> Unit = {},
        successHandler: (Result) -> Unit
) = start(Main, failHandler, successHandler)