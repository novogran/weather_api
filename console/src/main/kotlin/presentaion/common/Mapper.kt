package presentaion.common

interface Mapper<F, R> {
    fun map(from: F): R
}