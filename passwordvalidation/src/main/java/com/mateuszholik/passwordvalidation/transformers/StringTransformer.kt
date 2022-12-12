package com.mateuszholik.passwordvalidation.transformers

internal interface StringTransformer {

    fun removeNumbers(text: String): String

    fun removeLeetSpeak(text: String): String
}

internal class StringTransformerImpl : StringTransformer {

    override fun removeNumbers(text: String): String =
        text.filterNot { it.code in 48..57 }

    override fun removeLeetSpeak(text: String): String =
        text.replaceSpecialCharactersWithLetters()
            .replaceNumbersWithLetters()

    private fun String.replaceSpecialCharactersWithLetters(): String =
        this.replace('@', 'a')
            .replace("/\\", "a")
            .replace("|3", "b")
            .replace('(', 'c')
            .replace('[', 'c')
            .replace("|)", "d")
            .replace("|]", "d")
            .replace("|=", "f")
            .replace("|\"", "f")
            .replace("&", "g")
            .replace("|-|", "h")
            .replace("#", "h")
            .replace("}{", "h")
            .replace("!", "i")
            .replace("_|", "j")
            .replace("|<", "k")
            .replace("|_", "l")
            .replace("/\\/\\", "m")
            .replace("/v\\", "m")
            .replace("|\\|", "n")
            .replace("/\\/", "n")
            .replace("()", "o")
            .replace("[]", "o")
            .replace("|>", "p")
            .replace("0_", "q")
            .replace("|2", "r")
            .replace('$', 's')
            .replace('+', 't')
            .replace("|_|", "u")
            .replace("\\/", "v")
            .replace("\\/\\/", "w")
            .replace("vv", "w")
            .replace("VV", "w")
            .replace("><", "x")
            .replace("`/", "y")
            .replace("\"/_", "z")

    private fun String.replaceNumbersWithLetters(): String =
        this.replace('4', 'a')
            .replace('8', 'b')
            .replace('3', 'e')
            .replace('6', 'g')
            .replace('9', 'g')
            .replace('1', 'i')
            .replace("11", "m")
            .replace('0', 'o')
            .replace('7', 't')
            .replace('2', 'z')
}