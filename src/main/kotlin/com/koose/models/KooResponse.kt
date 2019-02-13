package com.koose.models

import com.koose.services.GooResponse

data class KooResponse(val service: String, val version: String, val res: List<GooResponse>)