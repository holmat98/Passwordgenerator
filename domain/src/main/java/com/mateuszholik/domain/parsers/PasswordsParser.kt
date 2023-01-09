package com.mateuszholik.domain.parsers

import com.mateuszholik.domain.models.ExportedPassword

internal interface PasswordsParser : DataParser<List<ExportedPassword>>

internal class PasswordsParserImpl : PasswordsParser {

    override fun parseToString(data: List<ExportedPassword>): String {
        var result = ""

        data.forEach {
            result += "${it.platformName}:${it.password}\n"
        }

        return result.trim()
    }

    override fun parseFromString(data: String): List<ExportedPassword> {
        val lines = data.split("\n").filter { it.isNotEmpty() }

        return lines.map {
            val (platformName, password) = it.split(':')

            ExportedPassword(
                platformName = platformName,
                password = password
            )
        }
    }
}