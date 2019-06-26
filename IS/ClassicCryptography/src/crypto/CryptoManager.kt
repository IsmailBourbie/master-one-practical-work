package crypto

object CryptoManager {

    fun encrypt(message: String, key: String = Keys.KEY): String {
        return VignereAlgorithm.encrypt(message, key)
    }

    fun decrypt(message: String, key: String = Keys.KEY): String {
        return VignereAlgorithm.decrypt(message, key)
    }
}