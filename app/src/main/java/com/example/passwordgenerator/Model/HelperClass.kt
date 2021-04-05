package com.example.passwordgenerator.Model

import android.util.Log
import com.example.passwordgenerator.Model.Entities.Password
import java.lang.StringBuilder
import java.security.SecureRandom

class HelperClass {
    companion object {
        var password: String = ""
        var editPassword: Password? = null
        private val letters: String = "abcdefghijklmnopqrstuvwxyz"
        private val uppercase: String = "ABCDEFGHIJKLMNOPQESTUVWXYZ"
        private val numbers: String = "0123456789"
        private val specialCharacters: String = "!@#$%^&*-_+=?"
        private val maxPasswordLength: Double = 20.0
        private val maxPasswordFactor: Double = 20.0

        private fun patternSearcher(password: String): Double{
            var numOfPatterns: Int = 0
            for( i in 0..password.length-2){
                if(password.toLowerCase()[i].toInt() == password.toLowerCase()[i+1].toInt() || password.toLowerCase()[i].toInt() - password.toLowerCase()[i+1].toInt() in 0..2)
                {
                    numOfPatterns++
                    Log.d("TEST", password.toLowerCase()[i] + " " + password.toLowerCase()[i].toInt().toString() + " " + password.toLowerCase()[i+1] + password.toLowerCase()[i+1].toInt().toString())
                }
            }

            when(numOfPatterns){
                0 -> return 10.0
                1 -> return 9.0
                2 -> {
                    return if (password.length >= 8) 5.0 else 0.0
                }
                else -> return 0.0
            }
        }

        fun generatePassword(isWithLetters: Boolean, isWithUpperCase: Boolean, isWithNumbers: Boolean, isWithSpecial: Boolean, length: Int): String{
            var result: String = ""

            if(isWithLetters) result += letters
            if(isWithUpperCase) result += uppercase
            if(isWithNumbers) result += numbers
            if(isWithSpecial) result += specialCharacters

            val rnd = SecureRandom.getInstance("SHA1PRNG")
            val sb = StringBuilder(length)

            var i: Int = 0
            while (i < length){
                val randromInt: Int = rnd.nextInt(result.length)
                sb.append(result[randromInt])
                i++
            }

            return sb.toString()
        }

        fun testPassword(password: String): Double{
            var factor: Double = 0.0
            val length = password.length

            if(password.matches(Regex(".*["+ letters+"].*"))) factor += 2.0
            if(password.matches(Regex(".*["+ uppercase+"].*"))) factor += 2.0
            if(password.matches(Regex(".*["+ numbers+"].*"))) factor += 1.0
            if(password.matches(Regex(".*["+ specialCharacters+"].*"))) factor += 5.0
            /*when(length){
                in 1..7 -> factor += 0.0
                in 8..10 -> factor += 2.0
                in 11..14 -> factor += 3.0
                in 15..17 -> factor += 4.0
                in 18..20 -> factor += 5.0
            }*/
            factor += patternSearcher(password)

            Log.d("TEST", ((factor*length)/(maxPasswordFactor * maxPasswordLength)).toString())
            return (factor*length)/(maxPasswordFactor * maxPasswordLength)
        }
    }
}