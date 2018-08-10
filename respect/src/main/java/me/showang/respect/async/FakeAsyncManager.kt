package me.showang.respect.async

class FakeAsyncManager: AsyncManager {

    override fun <Result> start(background: () -> Result, uiThread: (Result) -> Unit) {
        uiThread(background())
    }

}