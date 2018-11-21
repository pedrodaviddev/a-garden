import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import java.time.LocalDateTime
import kotlin.math.absoluteValue

const val API = "http://api.sunrise-sunset.org/json?lat=39.46975,&lng=-0.37739"

object SunPosition {

    fun isSunrise(): Boolean = (API.httpGet()
            .responseObject<Results>()
            .third.component1()!!.results.sunrise.substring(0, 1)
            .toInt() + 1 - LocalDateTime.now().hour).absoluteValue < 2

    fun isSunset(): Boolean = (API.httpGet()
            .responseObject<Results>()
            .third.component1()!!.results.sunset.substring(0, 1)
            .toInt() + 1 - LocalDateTime.now().hour).absoluteValue < 2
}

data class Results(val results: SunHours)
data class SunHours(val sunrise: String, val sunset: String)