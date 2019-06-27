object SortAlgorithm {

    fun sort(array: Array<Int>): Array<Int> {
        for (i in 0 until array.size)
            for (j in i + 1 until array.size) {
                if (array[i] > array[j]) {
                    permute(array, i, j)
                }
            }
        return array
    }

    fun isSorted(array: Array<Int>): Boolean {
        for (i in 0 until array.size) {
            if (i == array.size - 2 && array[i] > array[(i + 1)]) return false
        }
        return true
    }

    fun permute(array: Array<Int>, i: Int, j: Int) {
        val k = array[i]
        array[i] = array[j]
        array[j] = k
    }
}