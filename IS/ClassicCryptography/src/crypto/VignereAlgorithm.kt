package crypto

import java.util.*

object VignereAlgorithm : CryptoAlgorithm {

    private var vignere = ArrayList<ArrayList<Char>>()
    private const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"

    init {
        for (i in 0..25) {
            val rowVignere = ArrayList<Char>()
            for (j in 0..25)
                rowVignere.add(ALPHABET[(i + j) % 26])
            vignere.add(rowVignere)
        }
    }

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
                vignere[ALPHABET.indexOf(bigKey[l].toString())][ALPHABET.indexOf(plainText[l].toString())]
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
                ALPHABET[vignere[ALPHABET.indexOf(bigKey[l])].indexOf(cryptogram[l])]
            else
                cryptogram[l]
        }
        return plainText
    }
}