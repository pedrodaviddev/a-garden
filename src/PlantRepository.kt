import DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class PlantRepository {
    suspend fun createPlant(plant: Plant) {
        dbQuery {
            PlantTable.insert {
                it[name] = plant.name
                it[requiredHumidity] = plant.requiredHumidity
                it[temperature] = plant.temperature.toLong()
                it[sunLight] = plant.sunLight.toLong()
            }
        }
    }

    suspend fun getPlants(): List<Plant> = dbQuery {
        PlantTable.selectAll().map(::toPlant)
    }


    private fun toPlant(row: ResultRow): Plant =
            Plant(row[PlantTable.name],
                    row[PlantTable.requiredHumidity],
                    row[PlantTable.temperature].toDouble(),
                    row[PlantTable.sunLight].toDouble())
}