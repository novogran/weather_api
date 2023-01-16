package presentaion

class InputValidation {

    fun enterCoordinate(text: String): Double? {
        println(text)
        val coordinate = readLine()
        if (coordinateValidation(coordinate)) {
            return coordinate?.toDouble()
        } else {
            println("Неверный ввод")
            enterCoordinate(text)
        }
        return null
    }

    fun checkInput(input: String?): Int? {
        return try {
            input?.toInt()
        } catch (e: java.lang.NumberFormatException) {
            0
        }
    }

    private fun coordinateValidation(coordinate: String?): Boolean {
        return try {
            coordinate?.toDouble()
            true
        } catch (e: java.lang.NumberFormatException) {
            false
        }
    }

}