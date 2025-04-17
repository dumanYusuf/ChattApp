package com.dumanyusuf.chattapp.presenatation.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.chattapp.R
import com.dumanyusuf.chattapp.Screan
import com.dumanyusuf.chattapp.domain.model.Users
import com.google.firebase.Timestamp
import com.google.gson.Gson
import java.net.URLEncoder

@Composable
fun SignupPage(
    viewModel: SignupViewModel = hiltViewModel(),
    navController: NavController,
    nextToLogin: () -> Unit,
) {
    var userName by remember { mutableStateOf("") }
    var userMail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val stateRegister = viewModel.signupState.collectAsState().value


    val emailError = viewModel.emailError.collectAsState()
    val usernameError = viewModel.usernameError.collectAsState()
    val passwordError = viewModel.passwordError.collectAsState()
    val confirmPasswordError = viewModel.confirmPasswordError.collectAsState()

    LaunchedEffect(stateRegister) {
        if (stateRegister.isSuccess) {
            navController.navigate(Screan.HomePage.route) {
                popUpTo(Screan.SignUpPage.route){inclusive=true}
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.welcome_text),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    CustomTextField(
                        value = userName,
                        onValueChange = { userName = it },
                        placeholder = stringResource(R.string.enter_name),
                        error = usernameError.value
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        value = userMail,
                        onValueChange = { userMail = it },
                        placeholder = stringResource(R.string.enter_email),
                        error = emailError.value
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = stringResource(R.string.enter_password),
                        isPassword = true,
                        error = passwordError.value
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = stringResource(R.string.confirm_password),
                        isPassword = true,
                        error = confirmPasswordError.value
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    if (stateRegister.error.isNotBlank()) {
                        Text(
                            text = stateRegister.error,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            if (viewModel.validateInputs(userMail, userName, password, confirmPassword)) {
                                // E-posta formatını kontrol et
                                if (!userMail.endsWith("@gmail.com")) {
                                    viewModel._emailError.value = "Sadece @gmail.com uzantılı e-postalar kabul edilir"
                                    return@Button
                                }

                                // Şifre uzunluğunu kontrol et
                                if (password.length < 6) {
                                    viewModel._passwordError.value = "Şifre en az 6 karakter olmalıdır"
                                    return@Button
                                }

                                viewModel.registerUser(userName, userMail, password)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (stateRegister.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(4.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(text = stringResource(R.string.signup), fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.onBackground,
                            text = stringResource(R.string.already_have_account)
                        )
                        TextButton(onClick = nextToLogin) {
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                text = stringResource(R.string.login)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    error: String = ""
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = error.isNotEmpty(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor = Color.Gray,
                cursorColor = MaterialTheme.colorScheme.onBackground,
                errorBorderColor = Color.Red
            )
        )

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }
    }
}