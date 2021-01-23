package com.bd.blacksky.utils

object Keys {
    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
}