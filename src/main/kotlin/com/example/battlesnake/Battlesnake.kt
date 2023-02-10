package com.example.battlesnake

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

const val name = "YourSnakeHere" // put the name of your new snake here!
const val author = "YourNameHere" // put your name here!

// This is the json serializer used
// It ignores unknown keys by default, but invalid values will not be accepted
// Be careful with errors!
val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

// This is the main entry point of your snake.
fun main() {
    // Print out the author
    println("\"$name\" by $author")
    println()

    // Read our customizations
    val (color, head, tail) = readCustomizations()

    // Print out a small debug about the customizations
    println("""
        Customizations:
        Color:  $color
        Head:   $head
        Tail:   $tail
    """.trimIndent())
    println()

    // Convert it to bot info
    val info = BotInfo(
        author = author,
        color = color,
        head = head,
        tail = tail,
        version = "v1.0" // You can update your version here, or make it dynamic
    )

    // Specify your preferred port here
    val port = System.getenv("PORT")?.toIntOrNull() ?: 3000

    // You can remove this debug message
    println("Starting to listen on port $port")
    println()

    // Fire up the API server
    // `wait` means it blocks the thread while the server is up, which is what we want
    embeddedServer(Netty, port = port) {
        // This is so the server will automatically handle json for us
        install(ContentNegotiation) { json(json) }

        // This exists for statistic purposes
        install(DefaultHeaders) {
            header("Server", "770grappenmaker/starter-snake-kotlin")
        }

        // Define routing here
        routing {
            // The "start game request"
            // Here, we just accept it, but you can handle this in the future
            post("/start") {
                call.respond(HttpStatusCode.OK)
            }

            // We will do the same for the end request
            post("/end") {
                call.respond(HttpStatusCode.OK)
            }

            // The "info" request
            // We can respond with the info we retrieved earlier
            get("/") {
                call.respond(info)
            }

            // The most important part: the move request
            post("/move") {
                // This receives the post body as json text
                val request = call.receive<MoveRequest>()

                // We will respond with whatever Logic.kt tells us!
                call.respond(MoveResponse(
                    shout = "Hello, world!",
                    move = decideMove(request)
                ))
            }
        }
    }.start(wait = true)
}

// Reads customizations.json and returns the customizations object
fun readCustomizations(): SnakeCustomization =
    json.decodeFromString(File("customizations.json").readText())