import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.experimental.CoroutineContext

object DatabaseFactory {

    init {
        Database.connect(hikari())
        transaction {
            create(PlantTable)
            create(SampleTable)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql://ec2-54-217-205-90.eu-west-1.compute.amazonaws.com:5432/d41c8gkcpl44pe"
        config.username = "ltznucrbbzypnf"
        config.password = "3d371533c7286962146147d61bf2b715f03ed85de0c61dbd922acef45ad599cc"
        config.maximumPoolSize = 3
        config.validate()
        return HikariDataSource(config)
    }

    private fun hikariTest(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql://localhost/testdb"
        config.username = "postgres"
        config.password = ""
        config.maximumPoolSize = 3
        config.validate()
        return HikariDataSource(config)
    }

    private val dispatcher: CoroutineContext

    init {
        dispatcher = newFixedThreadPoolContext(5, "database-pool")
    }

    suspend fun <T> dbQuery(
            block: () -> T): T =
            withContext(dispatcher) {
                transaction { block() }
            }

}