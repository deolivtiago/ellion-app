package com.clarxlabs.ellion.auth.domain.validation

import com.clarxlabs.ellion.application.utilities.Result
import com.clarxlabs.ellion.application.utilities.ResultData
import com.clarxlabs.ellion.application.utilities.ResultError

interface TextValidator {
    fun validate(text: String): Result<ResultData, ResultError>

//    enum class Result {
//        INVALID_FORMAT,
//        TOO_SHORT,
//        TOO_LONG,
//        MUST_HAVE,
//        VALID,
//    }
}
