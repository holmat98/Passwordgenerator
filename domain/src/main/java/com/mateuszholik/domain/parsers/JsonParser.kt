package com.mateuszholik.domain.parsers

import com.google.gson.Gson

internal interface JsonParser {

    fun <T> parseToJson(data: T): String

    fun <T> parseFromJson(data: String, kClass: Class<T>): T
}

internal class JsonParserImpl : JsonParser {

    override fun <T> parseToJson(data: T): String =
        Gson().toJson(data)

    override fun <T> parseFromJson(data: String, kClass: Class<T>): T =
        Gson().fromJson(data, kClass)
}

internal inline fun <reified T> JsonParser.parseFromJson(data: String): T =
    this.parseFromJson(data, T::class.java)