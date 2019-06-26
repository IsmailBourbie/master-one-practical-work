package crypto

interface CryptoAlgorithm {

    fun encrypt(message: String, key: String) : String
    fun decrypt(message: String, key: String) : String
}