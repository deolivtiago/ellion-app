package com.clarxlabs.ellion.auth.domain.validation.validators

import com.clarxlabs.ellion.application.utilities.Result
import com.clarxlabs.ellion.application.utilities.ResultData
import com.clarxlabs.ellion.application.utilities.ResultError
import com.clarxlabs.ellion.auth.domain.validation.TextValidator

class RegexValidator(val regex: Regex) : TextValidator {
    override fun validate(value: String): Result<RegexValidatorResult, RegexValidatorResult> {
        if (!value.matches(regex)) return Result.Error(RegexValidatorResult.MUST_HAVE)

        return Result.Data(RegexValidatorResult.VALID)
    }
}

enum class RegexValidatorResult : ResultData, ResultError { VALID, MUST_HAVE }
