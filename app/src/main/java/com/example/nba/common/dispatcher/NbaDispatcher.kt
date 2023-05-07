package com.example.nba.common.dispatcher

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: NbaDispatchers)

enum class NbaDispatchers {
    DEFAULT,
    MAIN,
    UNCONFINED,
    IO
}