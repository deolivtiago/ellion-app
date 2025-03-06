package com.clarxlabs.ellion.auth.domain.services

import com.clarxlabs.ellion.auth.domain.DomainService
import com.clarxlabs.ellion.auth.domain.services.ValidationService.Error
import com.clarxlabs.ellion.auth.domain.services.ValidationService.Input
import com.clarxlabs.ellion.auth.domain.services.ValidationService.Output

interface ValidationService : DomainService<Input, Output, Error> {
    data class Input(
        val email: String,
        val password: String,
    ) : DomainService.Input

    data class Output(
        val email: String,
        val password: String,
    ) : DomainService.Output

    data class Error(
        val email: List<String> = emptyList(),
        val password: List<String> = emptyList(),
    ) : DomainService.Error
}
