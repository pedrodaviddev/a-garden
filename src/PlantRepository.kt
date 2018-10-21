import DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class PlantRepository {
    suspend fun createPlant(plant: Plant) {
        dbQuery {
            PlantTable.insert {
                it[name] = plant.name
                it[requiredHumidity] = plant.requiredHumidity
                it[configuration] = plant.configuration
                it[temperature] = plant.temperature.toLong()
                it[sunLight] = plant.sunLight.toLong()
                it[humidity] = plant.humidity.toLong()
                it[changes] = false
            }
        }
    }

    suspend fun getPlants(): List<Plant> = dbQuery {
        PlantTable.selectAll().map(::toPlant)
    }


    private fun toPlant(row: ResultRow): Plant =
            Plant(row[PlantTable.id].value,
                    row[PlantTable.name],
                    row[PlantTable.requiredHumidity],
                    row[PlantTable.configuration],
                    row[PlantTable.temperature].toDouble(),
                    row[PlantTable.sunLight].toDouble(),
                    row[PlantTable.humidity].toDouble())

    suspend fun updatePlant(plant: Plant) = dbQuery {
        PlantTable.update({ PlantTable.id eq plant.id }) {
            it[configuration] = plant.configuration
            it[requiredHumidity] = plant.requiredHumidity
            it[changes] = true
        }
    }

    suspend fun getPlant(idPlant: Int): Plant = dbQuery {
        PlantTable.select { PlantTable.id eq idPlant }.mapNotNull(::toPlant).first()
    }
}