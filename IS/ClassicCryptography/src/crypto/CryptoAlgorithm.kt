package crypto

import crypto.Keys.KEY

interface CryptoAlgorithm {

    fun encrypt(message: String, key: String = KEY) : String
    fun decrypt(message: String, key: String = KEY) : String
}