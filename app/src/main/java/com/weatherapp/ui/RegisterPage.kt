package com.weatherapp.ui

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RegisterPage(modifier: Modifier = Modifier) {
    var nome by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var senha by rememberSaveable { mutableStateOf("") }
    var repetirSenha by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registre-se",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.padding(24.dp))
        OutlinedTextField(
            value = nome,
            label = { Text(text = "Digite seu nome") },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { nome = it }
        )
        Spacer(modifier = Modifier.padding(24.dp))
        OutlinedTextField(
            value = email,
            label = { Text(text = "Digite seu e-mail") },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { email = it }
        )
        Spacer(modifier = Modifier.padding(24.dp))
        OutlinedTextField(
            value = senha,
            label = { Text(text = "Digite sua senha") },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { senha = it },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.padding(24.dp))
        OutlinedTextField(
            value = repetirSenha,
            label = { Text(text = "Repita a sua senha") },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { repetirSenha = it },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.padding(24.dp))
        Row(modifier = modifier) {
            Button(
                onClick = {
                    Toast.makeText(activity, "Registrado com sucesso.", Toast.LENGTH_LONG).show()
                    activity?.finish()
                },
                enabled = (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty() && repetirSenha.isNotEmpty())
                        && (senha == repetirSenha)
            ) {
                Text("Registrar")
            }

            Button(
                onClick = {
                    nome = ""; email = ""; senha = ""; repetirSenha = ""
                }
            ) {
                Text("Limpar")
            }
        }
    }
}