package com.clarxlabs.ellion.auth.domain.validation

import android.util.Log

class ValidationComposite(private val validators: List<ValidationStrategy>) : ValidationStrategy {
    override fun validate(value: String): ValidationStrategy.Result {

        val r = validators
            .map { it.validate(value) }
            .sortedBy { it.ordinal }
            .last()

        Log.d("result", r.toString())

        return r
    }
}
