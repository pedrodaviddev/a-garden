object RequiredHumidity {
    val VERY_HIGH = 3
    val HIGH = 2
    val MEDIUM = 1
    val LOW = 0

    fun getValueForEachValue(required: Int) = when(required){
        LOW -> 60
        MEDIUM -> 70
        HIGH -> 80
        else -> 90
    }
}