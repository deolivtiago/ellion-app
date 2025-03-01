package com.clarxlabs.ellion.auth.domain.validation

object ValidationFactory {
    fun create(type: ValidationStrategy.Type): ValidationComposite = create(listOf(type))

    fun create(types: List<ValidationStrategy.Type>): ValidationComposite =
        types
            .map { it.validators }
            .flatten()
            .let { ValidationComposite(it) }
}
