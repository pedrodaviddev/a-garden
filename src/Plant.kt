import Configuration.BY_HUMIDITY
import Configuration.BY_TIME_SUNRISE
import Configuration.BY_TIME_SUNSET
import RequiredHumidity.getValueForEachValue

data class Plant(val id: Int,
                 val name: String,
                 val requiredHumidity: Int,
                 val configuration: Int,
                 val temperature: Double,
                 val sunLight: Double,
                 val humidity: Double) {
    fun shouldIrrigate(): Boolean =  when (configuration) {
        BY_HUMIDITY -> humidity < getValueForEachValue(requiredHumidity)
        BY_TIME_SUNRISE -> SunPosition.isSunrise()
        BY_TIME_SUNSET -> SunPosition.isSunset()
        else -> SunPosition.isSunrise() || SunPosition.isSunset()
    }

}