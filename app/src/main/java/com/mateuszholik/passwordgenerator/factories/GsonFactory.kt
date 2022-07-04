package com.mateuszholik.passwordgenerator.factories

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mateuszholik.passwordgenerator.serializers.LocalDateTimeDeserializer
import com.mateuszholik.passwordgenerator.serializers.LocalDateTimeSerializer
import java.time.LocalDateTime

interface GsonFactory {

    fun create(): Gson
}

class GsonFactoryImpl(
    private val localDateTimeSerializer: LocalDateTimeSerializer,
    private val localDateTimeDeserializer: LocalDateTimeDeserializer
) : GsonFactory {

    override fun create(): Gson =
        GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, localDateTimeSerializer)
            .registerTypeAdapter(LocalDateTime::class.java, localDateTimeDeserializer)
            .create()
}