package com.dumanyusuf.chattapp.presenatation.person_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dumanyusuf.chattapp.R

@Composable
fun PersonPage(
    navController: NavController,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                // Geri tuşu
                navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "Geri"
                )
            }
            Text(
                text = stringResource(R.string.profil),
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // Profil içeriği
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = 20.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    painter = painterResource(R.drawable.person),
                    contentDescription = ""
                )

                IconButton(
                    onClick = {
                        // Kamera açma işlemi
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(42.dp)
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.camera),
                        contentDescription = ""
                    )
                }
            }

            Text(
                text ="userName",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text ="userMail",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = {
                    // Profil bilgilerini düzenleme
                }) {
                    Text(text = stringResource(R.string.edit_profil))
                }
            }
        }
    }
}

