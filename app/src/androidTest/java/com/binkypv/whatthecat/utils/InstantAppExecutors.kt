package com.binkypv.whatthecat.utils

import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor {
            it.run()
        }
    }
}