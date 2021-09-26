package com.euvic.euvic_staz_marvel.utils

import java.lang.System.currentTimeMillis
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Constants {
    companion object {
        const val MAIN_LAYOUT_ID = 1
        const val ID_1 = 2
        const val ID_2 = 3
        const val ID_3 = 3
        const val MARVEL_API_URL: String = "https://gateway.marvel.com/v1/public/"
        val TIMESTAMP: String = Timestamp(currentTimeMillis()).time.toString()
        const val API_PUBLIC_KEY: String = "023175bf0afe106de630351265bd770c"
        private const val API_PRIVATE_KEY: String = "805cb8a65909ea7cb5e7d73659191b198f3ad9ae"
        const val RESULTS_LIMIT = 20
        const val DEFAULT_OFFSET = 0

        fun hash(): String {
            val input = "$TIMESTAMP$API_PRIVATE_KEY$API_PUBLIC_KEY"
            val messageDigest = MessageDigest.getInstance("MD5")
            return BigInteger(1, messageDigest.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}