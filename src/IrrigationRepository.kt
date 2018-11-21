import DatabaseFactory.dbQuery
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.joda.time.DateTime
import java.util.*
import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis

class IrrigationRepository {
    suspend fun registerIrrigation(idPlant: Plant) {
        dbQuery {
            IrrigationTable.insert {
                it[plant] = EntityID(idPlant.id, PlantTable)
                it[date] = System.currentTimeMillis()
            }
        }
    }

    suspend fun getLastIrrigationList() = dbQuery {
            IrrigationTable
                    .selectAll()
                    .orderBy(IrrigationTable.date)
                    .mapNotNull(::toIrrigation)
                    .takeLast(10)
        }


    private fun toIrrigation(row: ResultRow): Irrigation =
            Irrigation(row[IrrigationTable.plant].value,
                    row[IrrigationTable.date].absoluteValue)
}