import org.jetbrains.exposed.dao.IntIdTable

object PlantTable : IntIdTable() {
    val name = varchar("name", 70).uniqueIndex()
}