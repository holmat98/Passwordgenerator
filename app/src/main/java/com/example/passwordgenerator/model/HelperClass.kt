package com.example.passwordgenerator.model

import com.example.passwordgenerator.model.Entities.Password
import java.lang.StringBuilder
import java.security.SecureRandom
import java.util.*

object HelperClass {
        var password: String = ""
        var editPassword: Password? = null
        private const val letters: String = "abcdefghijklmnopqrstuvwxyz"
        private const val uppercase: String = "ABCDEFGHIJKLMNOPQESTUVWXYZ"
        private const val numbers: String = "0123456789"
        private const val specialCharacters: String = "!@#$%^&*-_+=?"
        private const val maxPasswordLength: Double = 20.0
        private const val maxPasswordFactor: Double = 20.0

        private fun patternSearcher(password: String): Double{
            var numOfPatterns = 0
            for( i in 0..password.length-2){
                if (password.toLowerCase(Locale.ROOT)[i].toInt() == password.toLowerCase(Locale.ROOT)[i+1].toInt() || password.toLowerCase(Locale.ROOT)[i].toInt() - password.toLowerCase(Locale.ROOT)[i+1].toInt() in 0..2)
                {
                    numOfPatterns++
                }
            }

            when(numOfPatterns){
                0 -> {
                    return if(password.length >= 8) 10.0 else 5.0
                }
                1 -> {
                    return if(password.length >= 8) 9.0 else 0.0
                }
                2 -> {
                    return if (password.length >= 8) 5.0 else 0.0
                }
                else -> return 0.0
            }
        }

        fun generatePassword(isWithLetters: Boolean, isWithUpperCase: Boolean, isWithNumbers: Boolean, isWithSpecial: Boolean, length: Int): String{
            var result = ""

            if(isWithLetters) result += letters
            if(isWithUpperCase) result += uppercase
            if(isWithNumbers) result += numbers
            if(isWithSpecial) result += specialCharacters

            val rnd = SecureRandom.getInstance("SHA1PRNG")
            val sb = StringBuilder(length)

            var i = 0
            while (i < length){
                val randomInt: Int = rnd.nextInt(result.length)
                sb.append(result[randomInt])
                i++
            }

            return sb.toString()
        }

        fun testPassword(password: String): Double{
            var factor = 0.0
            val length = password.length

            if(password.matches(Regex(".*[$letters].*")) && password.length >= 8) factor += 2.0
            if(password.matches(Regex(".*[$letters].*")) && password.length < 8) factor += 1.0
            if(password.matches(Regex(".*[$uppercase].*")) && password.length >= 8) factor += 2.0
            if(password.matches(Regex(".*[$uppercase].*")) && password.length < 8) factor += 1.0
            if(password.matches(Regex(".*[$numbers].*")) && password.length >= 8) factor += 1.0
            if(password.matches(Regex(".*[$numbers].*")) && password.length < 8) factor += 0.5
            if(password.matches(Regex(".*[$specialCharacters].*")) && password.length >= 8) factor += 5.0
            if(password.matches(Regex(".*[$specialCharacters].*")) && password.length < 8) factor += 2.5
            /*when(length){
                in 1..7 -> factor += 0.0
                in 8..10 -> factor += 2.0
                in 11..14 -> factor += 3.0
                in 15..17 -> factor += 4.0
                in 18..20 -> factor += 5.0
            }*/
            factor += patternSearcher(password)

            return (factor*length)/(maxPasswordFactor * maxPasswordLength)
        }
}