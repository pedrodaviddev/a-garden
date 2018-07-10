import org.jetbrains.exposed.dao.IntIdTable

object PlantTable : IntIdTable() {
    val name = varchar("name", 70).uniqueIndex()
    val requiredHumidity = integer("reqHumidity")
    val temperature = long("temperature")
    val sunLight = long("sunLight")
}