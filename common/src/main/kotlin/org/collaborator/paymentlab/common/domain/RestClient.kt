package org.collaborator.paymentlab.common.domain

import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity

interface RestClient<REQUEST, RESPONSE> {
    fun postForEntity(url: String, request: HttpEntity<REQUEST>): ResponseEntity<RESPONSE>
}