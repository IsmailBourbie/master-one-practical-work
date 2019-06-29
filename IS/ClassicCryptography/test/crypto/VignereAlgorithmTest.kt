package crypto

import org.junit.Assert.assertEquals
import org.junit.Test

class VignereAlgorithmTest {

    @Test
    fun encrypt() {
        assertEquals(VignereAlgorithm.encrypt("DCODE", "cle")
                , "FNSFP".toLowerCase())

        assertEquals(VignereAlgorithm.encrypt("meet me after the toga party", "sololearn")
                , "esph xi awgwf evp xoxn hochj".toLowerCase())
    }

    @Test
    fun decrypt() {
        assertEquals(VignereAlgorithm.decrypt("FNSFP", "cle")
                , "DCODE".toLowerCase())

        assertEquals(VignereAlgorithm.decrypt("esph xi awgwf evp xoxn hochj", "sololearn")
                , "meet me after the toga party".toLowerCase())
    }
}