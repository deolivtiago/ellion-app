package com.clarxlabs.ellion.auth.domain.validation.validators

import com.clarxlabs.ellion.application.utilities.Result
import com.clarxlabs.ellion.application.utilities.ResultData
import com.clarxlabs.ellion.application.utilities.ResultError
import com.clarxlabs.ellion.auth.domain.validation.TextValidator

class EmailValidator : TextValidator {
    private val emailRegex = "^[.!?@#$%^&*_+a-z\\-0-9]+@[._+\\-a-z0-9]+$".toRegex()

    override fun validate(value: String): Result<EmailValidatorResult, EmailValidatorResult> {

        if (!value.matches(emailRegex)) return Result.Error(EmailValidatorResult.INVALID_FORMAT)

        return Result.Data(EmailValidatorResult.VALID)
    }
}

enum class EmailValidatorResult : ResultData, ResultError { VALID, INVALID_FORMAT }
