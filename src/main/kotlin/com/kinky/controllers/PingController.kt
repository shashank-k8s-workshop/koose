package com.kinky.controllers

import com.kinky.models.PingResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController {

    @GetMapping("/ping")
    fun ping() = PingResponse("kinky", "0.1.0", "pong")
}