package com.mateuszholik.data.managers.io

interface IOManager {

    fun <T> write(key: String, data: T)

    fun <T> read(key: String, kClass: Class<T>): T

    fun clear(key: String)
}