import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {

    val server = embeddedServer(Netty, System.getenv("PORT")?.toInt() ?: 8080) {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }
        routing {
            get("/plants") {
                call.respond(PlantRepository().getPlants())
            }
            get("/plant/{id}"){
                call.respond(PlantRepository().getPlant(Integer.parseInt(call.parameters["id"])))
            }
            post("/plant") {
                val received = call.receive<Plant>()
                PlantRepository().createPlant(received)
            }
            get("/demo") {
                call.respondText("HELLO WORLD!")
            }
            post("/sample") {
                SampleRepository().addSample(call.receive())
            }
            get("/sample/{id}") {
                call.respond(SampleRepository().getSamples(Integer.parseInt(call.parameters["id"])))
            }
            put("/plant/{id}") {
                PlantRepository().updatePlant(
                        call.receive<Plant>().copy(id = Integer.parseInt(call.parameters["id"]))
                )
            }
            get(""){

            }
        }
    }
    server.start(wait = true)
}

