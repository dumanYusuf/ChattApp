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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.chattapp.R
import com.dumanyusuf.chattapp.Screan
import com.dumanyusuf.chattapp.presenatation.sign_in.SigninViewModel

@Composable
fun SiginPage(
    navController: NavController,
    vievmodel:SigninViewModel= hiltViewModel()
) {

    var userMail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val stateLogin=vievmodel.stateLogin.collectAsState().value

    LaunchedEffect(stateLogin) {
        if (stateLogin.isSuccess){
            navController.navigate(Screan.HomePage.route){
               popUpTo(Screan.SignInPage.route){inclusive=true}
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
            Text(
                text = stringResource(R.string.login),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.padding(8.dp))


            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation =CardDefaults.cardElevation(6.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    CustomTextField(
                        value = userMail,
                        onValueChange = { userMail = it },
                        placeholder = stringResource(R.string.enter_email)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = stringResource(R.string.enter_password),
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    if (stateLogin.error.isNotBlank()){
                        Text(
                            text = stateLogin.error,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            vievmodel.loginUser(userMail,password)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                       if (stateLogin.isLoading){
                           CircularProgressIndicator(
                               modifier = Modifier.padding(4.dp),
                               color = MaterialTheme.colorScheme.onPrimary
                           )
                       }
                        else{
                           Text(
                               //color = MaterialTheme.colorScheme.onBackground,
                               text = stringResource(R.string.login), fontSize = 16.sp)
                       }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.onBackground,
                            text = stringResource(R.string.dont_have_account)
                        )
                        TextButton(
                            onClick = {
                                // kayıt sayfasına yönlendirme
                                navController.navigate(Screan.SignUpPage.route)
                            }) {
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                text = stringResource(R.string.signup)
                            )
                        }
                    }
                }
            }
        }
    }
}

