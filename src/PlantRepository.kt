import DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class PlantRepository {
    suspend fun createPlant(plant: Plant) {
        dbQuery {
            PlantTable.insert { it[name] = plant.name }
        }
    }

    suspend fun getPlants(): List<Plant> = dbQuery {
        PlantTable.selectAll()
                .mapNotNull(::toPlant)
    }


    private fun toPlant(row: ResultRow): Plant = Plant(row[PlantTable.name], 0)
}