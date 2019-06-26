package crypto

object PolyAlphabeticalAlgorithm : CryptoAlgorithm {

    private const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"

    override fun encrypt(message: String, key: String): String {
        val plainText = message.toLowerCase()
        var bigKey = ""
        var cryptogram = ""
        var i = 0
        var j = 0
        while (i < plainText.length) {
            bigKey += if (plainText[i] in 'a'..'z')
                key[j++ % key.length]
            else
                plainText[i]
            i++
        }

        for (l in 0 until plainText.length) {
            cryptogram += if (plainText[l] in 'a'..'z')
                ALPHABET[(((ALPHABET.indexOf(plainText[l]) + ALPHABET.indexOf(bigKey[l])) % ALPHABET.length))]
            else
                plainText[l]
        }
        return cryptogram
    }

    override fun decrypt(message: String, key: String): String {
        val cryptogram = message.toLowerCase()
        var bigKey = ""
        var plainText = ""
        var i = 0
        var j = 0
        while (i < cryptogram.length) {
            bigKey += if (cryptogram[i] in 'a'..'z')
                key[j++ % key.length]
            else
                cryptogram[i]
            i++
        }

        for (l in 0 until cryptogram.length) {
            plainText += if (cryptogram[l] in 'a'..'z')
                ALPHABET[(((ALPHABET.indexOf(cryptogram[l]) - ALPHABET.indexOf(bigKey[l])) % ALPHABET.length))]
            else
                cryptogram[l]
        }
        return plainText
    }
}