import DatabaseFactory.dbQuery
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import java.util.*
import java.util.Calendar.HOUR
import kotlin.math.absoluteValue

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

    suspend fun getNumberOfIrrigationToday(): Int = dbQuery {
        IrrigationTable
                .select {
                    IrrigationTable.date greater
                            Calendar.getInstance().also {
                                it.set(HOUR, 0)
                            }.timeInMillis
                }.count()

    }

}
