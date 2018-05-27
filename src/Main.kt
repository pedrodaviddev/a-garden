import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.decodeURLQueryComponent
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {

    val server = embeddedServer(Netty, System.getenv("PORT")?.toInt() ?: 8080) {
        install(ContentNegotiation){
            gson {
                setPrettyPrinting()
            }
        }
        routing {
            get("/plants") {
                call.respond(listOf(
                        Plant("Orquideas", 32.4, 78.4),
                        Plant("Arbol", 31.4, 20.4),
                        Plant("Rosas", 29.4, 71.4),
                        Plant("Tulipanes", 40.4, 30.4),
                        Plant("Margaritas", 32.4, 10.4)
                ))
            }
            get("/demo") {
                call.respondText("HELLO WORLD!")
            }
        }
    }
    server.start(wait = true)
}