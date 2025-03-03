package com.clarxlabs.ellion.auth.domain.validation

object ValidationComposite {
    fun validate(fieldMap: Map<ValidationStrategy.Type, String>): Map<ValidationStrategy.Type, ValidationStrategy.Result> {
        return fieldMap.mapValues { (type, value) ->
            type.validators
                .map { it.validate(value) }
                .firstOrNull { it != ValidationStrategy.Result.VALID } // Return the first invalid result
                ?: ValidationStrategy.Result.VALID // Default to VALID if all pass
        }
    }
}
