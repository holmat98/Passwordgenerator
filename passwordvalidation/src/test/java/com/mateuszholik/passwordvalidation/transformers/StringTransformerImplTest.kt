package com.mateuszholik.passwordvalidation.transformers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class StringTransformerImplTest {

    private val stringTransformer = StringTransformerImpl()

    @TestFactory
    fun removeNumbersTest() =
        listOf(
            "ab12cd" to "abcd",
            "abcd" to "abcd",
            "1234" to "",
            "abba12" to "abba"
        ).map { (text, expected) ->
            dynamicTest("Given text $text after removing numbers is equal to $expected") {
                val result = stringTransformer.removeNumbers(text)
                assertThat(result).isEqualTo(expected)
            }
        }

    @TestFactory
    fun removeLeetSpeakTest() =
        listOf(
            "l33tsp34k" to "leetspeak",
            "w1r3l3\$\$" to "wireless"
        ).map { (text, expected) ->
            dynamicTest("Given text $text after removing leet speak is equal to $expected") {
                val result = stringTransformer.removeLeetSpeak(text)
                assertThat(result).isEqualTo(expected)
            }
        }
}