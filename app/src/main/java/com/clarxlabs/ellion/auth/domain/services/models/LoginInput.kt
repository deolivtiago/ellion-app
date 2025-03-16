package com.clarxlabs.ellion.auth.domain.services.models

import android.util.Patterns
import com.clarxlabs.ellion.application.utilities.Either
import io.konform.validation.Invalid
import io.konform.validation.Valid
import io.konform.validation.Validation
import io.konform.validation.constraints.maxLength
import io.konform.validation.constraints.minLength
import io.konform.validation.constraints.notBlank
import kotlinx.serialization.Serializable

@Serializable
data class LoginInput(
    val email: String = "deoliv.tiago@gmail.com",
    val password: String = "4m1Mad?",
) {
    fun isValid(): Boolean =
        validate().let { it is Either.Success }

    fun emailError(): LoginInput =
        validate().let { if (it is Either.Failure) it.output else this.copy("", "") }

    fun passwordError(): LoginInput =
        validate().let { if (it is Either.Failure) it.output else this.copy("", "") }

    private fun validate(): Either<LoginInput, LoginInput> {
        return Validation<LoginInput> {
            LoginInput::email {
                notBlank()
                minLength(6)
                maxLength(160)
                constrain("Deve ser um email válido") {
                    Patterns.EMAIL_ADDRESS.matcher(it).matches()
                }
            }

            LoginInput::password {
                notBlank()
                minLength(6)
                maxLength(160)
                constrain("Deve conter números") { it.any { it.isDigit() } }
                constrain("Deve conter letras maiúsculas") { it.any { it.isUpperCase() } }
                constrain("Deve conter letras minúsculas") { it.any { it.isLowerCase() } }
                constrain("Deve conter símbolos. Ex: !?@#%^&*_+-$,.") {
                    Regex(".+").matches(it)
                }
            }
        }.validate(this).let {
            when (it) {
                is Valid -> Either.Success(it.value)
                is Invalid -> Either.Failure(
                    this.copy(
                        email = it.errors
                            .firstOrNull { it == LoginInput::email }
                            ?.message ?: "",
                        password = it.errors
                            .firstOrNull { it == LoginInput::password }
                            ?.message ?: "",
                    )
                )
            }
        }
    }
}
