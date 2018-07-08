import DatabaseFactory.dbQuery
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class SampleRepository {
    suspend fun addSample(sample: Sample) {
        dbQuery {
            SampleTable.insert {
                it[plant] = EntityID(sample.plant, PlantTable)
                it[temperature] = sample.temperature.toLong()
                it[humidity] = sample.humidity.toLong()
            }
        }
    }

    suspend fun getSamples(plant: Int): List<Sample> = dbQuery {
        SampleTable.select { SampleTable.plant eq plant }.mapNotNull(::toSample)
    }

    private fun toSample(row: ResultRow): Sample =
            Sample(row[SampleTable.plant].value,
                    row[SampleTable.humidity].toDouble(),
                    row[SampleTable.temperature].toDouble())
}