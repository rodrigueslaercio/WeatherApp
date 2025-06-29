package com.weatherapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.weatherapp.db.fb.FBDatabase
import com.weatherapp.db.fb.toFBUser
import com.weatherapp.model.User
import com.weatherapp.ui.DataField
import com.weatherapp.ui.PasswordField
import com.weatherapp.ui.theme.WeatherAppTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterPage(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun RegisterPage(modifier: Modifier = Modifier) {
    var nome by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var senha by rememberSaveable { mutableStateOf("") }
    var repetirSenha by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity

    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registre-se",
            fontSize = 24.sp
        )
        DataField(label = "Digite seu nome", value = nome, onValueChange = {nome = it})
        Spacer(modifier = Modifier.size(24.dp))
        DataField(label = "Digite seu e-mail", value = email, onValueChange = {email = it})
        Spacer(modifier = Modifier.size(24.dp))
        PasswordField(label = "Digite sua senha", value = senha, onValueChange = {senha = it})
        Spacer(modifier = Modifier.size(24.dp))
        PasswordField(label = "Repita sua senha", value = repetirSenha, onValueChange = {repetirSenha = it})
        Row(modifier = modifier) {
            Button(
                onClick = {
                    Firebase.auth.createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                FBDatabase().register(User(nome, email).toFBUser())
                                Toast.makeText(activity,
                                    "Registro OK!", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(activity, "Registro FALHOU!", Toast.LENGTH_LONG).show()
                            }
                        }
                },
                enabled = (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty() && repetirSenha.isNotEmpty())
                            && (senha == repetirSenha)
            ) {
                Text("Registrar")
            }
            Spacer(modifier = Modifier.size(24.dp))
            Button(
                onClick = {nome = ""; email = ""; senha = ""; repetirSenha = ""}
            ) {
                Text("Limpar")
            }
        }
    }
}