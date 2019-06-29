package crypto

import org.junit.Test

import org.junit.Assert.*

class PolyAlphabeticalAlgorithmTest {

    @Test
    fun encrypt() {
        assertEquals(PolyAlphabeticalAlgorithm.encrypt("DCODE", "cle")
                , "fnsfp")
    }

    @Test
    fun decrypt() {
        assertEquals(PolyAlphabeticalAlgorithm.decrypt("fnsfp", "cle")
                , "DCODE".toLowerCase())
    }
}