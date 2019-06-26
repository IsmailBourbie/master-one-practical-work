package crypto

object CryptoManager {

    fun encrypt(message: String, key: String = Keys.KEY): String {
        return PolyAlphabeticalAlgorithm.encrypt(message, key)
    }

    fun decrypt(message: String, key: String = Keys.KEY): String {
        return PolyAlphabeticalAlgorithm.decrypt(message, key)
    }
}