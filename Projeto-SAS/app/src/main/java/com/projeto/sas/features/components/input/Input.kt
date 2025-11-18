package com.projeto.sas.features.components.input

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType

// ============================================
// 2. CustomInputField.kt - Main Component
// ============================================

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projeto.sas.ui.theme.DarkGreen
import com.projeto.sas.ui.theme.FontStyle.BoldMD
import com.projeto.sas.ui.theme.FontStyle.RegularSM
import com.projeto.sas.ui.theme.FontStyle.RegularXS
import com.projeto.sas.ui.theme.Hint
import com.projeto.sas.ui.theme.LightGreen

enum class AccessoryPosition {
    LEADING,
    TRAILING
}

enum class ValidationState {
    ERROR,
    WARNING,
    NONE
}

data class ValidationResult(
    val state: ValidationState,
    val message: String? = null
)

data class InputAccessory(
    val icon: ImageVector,
    val position: AccessoryPosition,
    val onClick: (() -> Unit)? = null,
    val contentDescription: String? = null
)

private val ErrorColor = Color(0xFFD32F2F)
private val WarningColor = Color(0xFFFFA726)

@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    hint: String? = null,
    isSecure: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    accessory: InputAccessory? = null,
    validation: ((String) -> ValidationResult)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = 1,
    singleLine: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val validationResult = remember(value) {
        validation?.invoke(value) ?: ValidationResult(ValidationState.NONE)
    }

    val borderColor = when {
        validationResult.state == ValidationState.ERROR -> ErrorColor
        validationResult.state == ValidationState.WARNING -> WarningColor
        isFocused -> DarkGreen
        else -> LightGreen
    }

    Column(modifier = modifier) {
        label?.let {
            Text(
                text = it,
                style = BoldMD,
                color = when (validationResult.state) {
                    ValidationState.ERROR -> ErrorColor
                    ValidationState.WARNING -> WarningColor
                    else -> MaterialTheme.colorScheme.onSurface
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 2.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                ),
            placeholder = hint?.let {
                {
                    Text(
                        text = it,
                        style = RegularSM,
                        color = Hint
                    )
                }
            },
            leadingIcon = if (accessory?.position == AccessoryPosition.LEADING) {
                {
                    IconButton(
                        onClick = { accessory.onClick?.invoke() },
                        enabled = accessory.onClick != null
                    ) {
                        Icon(
                            imageVector = accessory.icon,
                            contentDescription = accessory.contentDescription,
                            tint = borderColor
                        )
                    }
                }
            } else null,
            trailingIcon = if (accessory?.position == AccessoryPosition.TRAILING) {
                {
                    IconButton(
                        onClick = { accessory.onClick?.invoke() },
                        enabled = accessory.onClick != null
                    ) {
                        Icon(
                            imageVector = accessory.icon,
                            contentDescription = accessory.contentDescription,
                            tint = borderColor
                        )
                    }
                }
            } else null,
            visualTransformation = if (isSecure) PasswordVisualTransformation() else VisualTransformation.None,
            enabled = enabled,
            readOnly = readOnly,
            interactionSource = interactionSource,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            singleLine = singleLine,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkGreen,
                unfocusedBorderColor = LightGreen,
            ),
            shape = RoundedCornerShape(8.dp)
        )


        // Validation message
        validationResult.message?.let { message ->
            Text(
                text = message,
                style = RegularXS,
                color = when (validationResult.state) {
                    ValidationState.ERROR -> ErrorColor
                    ValidationState.WARNING -> WarningColor
                    ValidationState.NONE -> MaterialTheme.colorScheme.onSurfaceVariant
                },
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}


@Composable
fun InputFieldExamplesScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            CustomInputField(
                value = email,
                onValueChange = { email = it },
                label = "Email Address",
                hint = "Enter your email",
                accessory = InputAccessory(
                    icon = Icons.Default.Email,
                    position = AccessoryPosition.LEADING,
                    contentDescription = "Email icon"
                ),
                validation = Validators.email ,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            CustomInputField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                hint = "Enter your password",
                isSecure = !passwordVisible,
                accessory = InputAccessory(
                    icon = if (passwordVisible) Icons.Default.Person else Icons.Default.Email,
                    position = AccessoryPosition.TRAILING,
                    onClick = { passwordVisible = !passwordVisible },
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                ),
                validation = Validators.password,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            CustomInputField(
                value = username,
                onValueChange = { username = it },
                label = "Username",
                hint = "Choose a username",
                accessory = InputAccessory(
                    icon = Icons.Default.Person,
                    position = AccessoryPosition.LEADING,
                    contentDescription = "User icon"
                ),
                validation = Validators.combine(
                    Validators.required,
                    Validators.minLength(3)
                )
            )

            CustomInputField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                hint = "Search...",
                accessory = if (searchQuery.isNotEmpty()) {
                    InputAccessory(
                        icon = Icons.Default.Clear,
                        position = AccessoryPosition.TRAILING,
                        onClick = { searchQuery = "" },
                        contentDescription = "Clear search"
                    )
                } else {
                    InputAccessory(
                        icon = Icons.Default.Search,
                        position = AccessoryPosition.LEADING,
                        contentDescription = "Search icon"
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            CustomInputField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = "Phone Number",
                hint = "+1 (555) 000-0000",
                accessory = InputAccessory(
                    icon = Icons.Default.Phone,
                    position = AccessoryPosition.LEADING,
                    contentDescription = "Phone icon"
                ),
                validation = { value ->
                    when {
                        value.isEmpty() -> ValidationResult(ValidationState.NONE)
                        value.length < 10 -> ValidationResult(
                            state = ValidationState.ERROR,
                            message = "Phone number must be at least 10 digits"
                        )
                        !value.all { it.isDigit() || it in setOf('+', '-', '(', ')', ' ') } ->
                            ValidationResult(
                                state = ValidationState.ERROR,
                                message = "Invalid phone number format"
                            )
                        else -> ValidationResult(ValidationState.NONE)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            CustomInputField(
                value = "",
                onValueChange = {},
                hint = "No label, just hint text",
                accessory = InputAccessory(
                    icon = Icons.Default.Info,
                    position = AccessoryPosition.TRAILING,
                    contentDescription = "Info"
                )
            )

            CustomInputField(
                value = "Disabled field",
                onValueChange = {},
                label = "Disabled Input",
                enabled = false,
                accessory = InputAccessory(
                    icon = Icons.Default.Lock,
                    position = AccessoryPosition.LEADING,
                    contentDescription = "Locked"
                )
            )

            CustomInputField(
                value = "",
                onValueChange = {},
                label = "Bio",
                hint = "Tell us about yourself",
                maxLines = 3,
                singleLine = false,
                validation = { value ->
                    when {
                        value.length > 200 -> ValidationResult(
                            state = ValidationState.ERROR,
                            message = "Bio must be 200 characters or less"
                        )
                        value.length > 150 -> ValidationResult(
                            state = ValidationState.WARNING,
                            message = "Approaching character limit (${value.length}/200)"
                        )
                        else -> ValidationResult(ValidationState.NONE)
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    InputFieldExamplesScreen()
}
