import org.jetbrains.exposed.dao.IntIdTable

object IrrigationTable : IntIdTable() {
    val plant = reference("plant", PlantTable)
    val date = long("date")
}