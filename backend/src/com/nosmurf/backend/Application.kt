package com.nosmurf.backend

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import org.jetbrains.exposed.sql.Database
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.app() {
    // Database
    Database.connect(
            url = "jdbc:mysql://localhost:3306/multicinescc",
            driver = "com.mysql.jdbc.Driver",
            user = "multicines",
            password = "RFjDPm8bHBQjfdG9"
    )

    // Serialize json
    install(ContentNegotiation) {
        gson {}
    }

    // CORS
    install(CORS) {
        anyHost()
    }

    // Return custom errors (if needed)
    install(StatusPages)

    install(CallLogging) {
        level = Level.TRACE
    }

    // Modules
    main()
}