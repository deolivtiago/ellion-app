package com.clarxlabs.ellion.auth.domain

import com.clarxlabs.ellion.application.utilities.Either

interface DomainService<I : DomainService.Input, O : DomainService.Output, E : DomainService.Error> {
    suspend fun execute(input: I): Either<O, E>

    interface Input
    interface Output
    interface Error
}
