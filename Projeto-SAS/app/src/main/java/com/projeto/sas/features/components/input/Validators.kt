import com.projeto.sas.features.components.input.ValidationResult
import com.projeto.sas.features.components.input.ValidationState

object Validators {
    val required: (String) -> ValidationResult = { value ->
        if (value.isEmpty()) ValidationResult(ValidationState.ERROR, "Required")
        else ValidationResult(ValidationState.NONE)
    }

    val email: (String) -> ValidationResult = { value ->
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches())
            ValidationResult(ValidationState.ERROR, "Invalid email")
        else
            ValidationResult(ValidationState.NONE)
    }

    val password: (String) -> ValidationResult = { value ->
        if (value.length < 6)
            ValidationResult(ValidationState.ERROR, "Password too short")
        else
            ValidationResult(ValidationState.NONE)
    }

    fun minLength(length: Int): (String) -> ValidationResult = { value ->
        if (value.length < length)
            ValidationResult(ValidationState.ERROR, "Minimum length $length")
        else ValidationResult(ValidationState.NONE)
    }

    fun combine(vararg validators: (String) -> ValidationResult): (String) -> ValidationResult = { value ->
        validators.forEach { validator ->
            val result = validator(value)
            if (result.state != ValidationState.NONE)   ValidationResult(ValidationState.NONE)
        }
        ValidationResult(ValidationState.NONE)
    }
}
