package com.clarxlabs.ellion.auth.domain.validation.validators

import com.clarxlabs.ellion.application.utilities.Result
import com.clarxlabs.ellion.application.utilities.ResultData
import com.clarxlabs.ellion.application.utilities.ResultError
import com.clarxlabs.ellion.auth.domain.validation.TextValidator

class LengthValidator(val min: Int = 6, val max: Int = 160) : TextValidator {
    override fun validate(value: String): Result<LengthValidatorResult, LengthValidatorResult> {
        if (value.length < min) return Result.Error(LengthValidatorResult.TOO_SHORT)

        if (value.length > max) return Result.Error(LengthValidatorResult.TOO_LONG)

        return Result.Data(LengthValidatorResult.VALID)
    }
}

enum class LengthValidatorResult : ResultData, ResultError { VALID, TOO_SHORT, TOO_LONG }
