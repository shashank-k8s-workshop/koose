package com.koose.controllers

import com.koose.models.KooResponse
import com.koose.services.GooService
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class KooController {
    private var logger = LogManager.getLogger("ControllerLogger")!!

    @Autowired
    private lateinit var gooService: GooService

    @Value("\${name}")
    private val serviceName = "koose"

    @Value("\${version}")
    private val version = "0.1.0"

    @GetMapping("/koo")
    fun ping(): ResponseEntity<KooResponse> {
        logger.info("koo api invoked")
        val gooResponse = gooService.goo()
        if (gooResponse == null) {
            logger.error("goo service request failed")
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity.ok(KooResponse(serviceName, version, listOf(gooResponse)))
    }
}