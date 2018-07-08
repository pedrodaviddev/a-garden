import org.jetbrains.exposed.dao.IntIdTable

object SampleTable : IntIdTable() {
    val plant = reference("plant", PlantTable)
    val temperature = long("temperature")
    val humidity = long("humidity")
}