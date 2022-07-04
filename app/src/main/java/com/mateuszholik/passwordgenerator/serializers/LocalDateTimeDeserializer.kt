package com.mateuszholik.passwordgenerator.serializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime = json?.let {
        LocalDateTime.ofEpochSecond(it.asLong, 0, ZoneOffset.UTC)
    } ?: LocalDateTime.now()
}