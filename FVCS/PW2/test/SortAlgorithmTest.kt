import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class SortAlgorithmTest {

    private lateinit var bigArray: Array<Int>

    @Before
    fun setup() {
        bigArray = Array(1000) { 0 }
        for (i in 0 until 1000) {
            bigArray[i] = (Random().nextInt())
        }
    }

    @Test
    fun sortSimple() {
        val arr = SortAlgorithm.sort(arrayOf(3, 2, 5, 1, 9))
        arr.forEachIndexed { index, i ->
            assertEquals(i, arrayOf(1, 2, 3, 5, 9)[index])
        }
    }

    @Test
    fun isSortTest() {
        val arr = SortAlgorithm.sort(bigArray)
        assertTrue(SortAlgorithm.isSorted(arr))
    }
}