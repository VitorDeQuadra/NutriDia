package com.nutridia.feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nutridia.core.components.*
import com.nutridia.data.AppState
import com.nutridia.navigation.Destination

@Composable
fun SignUpScreen(state: AppState) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }
    var consent by remember { mutableStateOf(false) }
    val validEmail = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$").matches(email.trim())
    Screen("Criar conta", state::back, bottom = {
        Box(Modifier.padding(16.dp)) {
            PrimaryButton("Criar conta", {
                state.name = name.trim(); state.email = email.trim()
                state.navigate(Destination.InitialGoals)
            }, enabled = name.isNotBlank() && validEmail && password.length >= 6 && consent)
        }
    }) {
        Text("Leva menos de um minuto. Depois você define suas metas.")
        EntryField(name, { name = it }, "Nome")
        EntryField(email, { email = it }, "E-mail", error = if (email.isNotEmpty() && !validEmail) "E-mail inválido" else null)
        OutlinedTextField(password, { password = it }, Modifier.fillMaxWidth(), label = { Text("Senha") },
            singleLine = true, shape = CardShape,
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = { IconAction(if (visible) "◉" else "◎", "Mostrar ou ocultar senha") { visible = !visible } },
            supportingText = { Text("Mínimo de 6 caracteres") })
        Row(verticalAlignment = Alignment.Top) {
            Checkbox(consent, { consent = it })
            Text("Li e aceito os termos de uso e autorizo o tratamento dos meus dados de saúde.", Modifier.weight(1f))
        }
        Notice("Modo demonstração: nenhum cadastro é enviado. Os dados ficam apenas nesta sessão. Termos e política definitivos ainda não estão disponíveis.")
    }
}
