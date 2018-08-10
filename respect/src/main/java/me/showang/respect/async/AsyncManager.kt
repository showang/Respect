package me.showang.respect.async

interface AsyncManager {

    fun<Result> start(background:()->Result, uiThread:(Result)->Unit)
}