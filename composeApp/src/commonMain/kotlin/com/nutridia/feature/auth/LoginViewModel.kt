package com.nutridia.feature.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Regras de validação e ações da tela de Login.
 *
 * Mantido como classe simples de estado para funcionar igual em Android e Web.
 * Quando o Firebase Authentication entrar no projeto, esta classe passa a ser um
 * `androidx.lifecycle.ViewModel` e `autenticar()` chama o repositório de auth.
 */
class LoginViewModel {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun aoMudarEmail(valor: String) {
        uiState = uiState.copy(email = valor, emailErro = null, mensagem = null)
    }

    fun aoMudarSenha(valor: String) {
        uiState = uiState.copy(senha = valor, senhaErro = null, mensagem = null)
    }

    fun alternarVisibilidadeSenha() {
        uiState = uiState.copy(senhaVisivel = !uiState.senhaVisivel)
    }

    fun consumirMensagem() {
        uiState = uiState.copy(mensagem = null)
    }

    fun entrar() {
        val emailErro = validarEmail(uiState.email)
        val senhaErro = validarSenha(uiState.senha)

        if (emailErro != null || senhaErro != null) {
            uiState = uiState.copy(emailErro = emailErro, senhaErro = senhaErro)
            return
        }

        // Protótipo: sem backend. A autenticação real será via Firebase Auth.
        uiState = uiState.copy(
            emailErro = null,
            senhaErro = null,
            mensagem = "Login validado! (protótipo — sem backend)",
        )
    }

    fun esqueciSenha() {
        uiState = uiState.copy(mensagem = "Recuperação de senha ainda não implementada.")
    }

    fun criarConta() {
        uiState = uiState.copy(mensagem = "Tela de cadastro ainda não implementada.")
    }

    private fun validarEmail(email: String): String? = when {
        email.isBlank() -> "Informe seu e-mail"
        !EMAIL_REGEX.matches(email.trim()) -> "E-mail inválido"
        else -> null
    }

    private fun validarSenha(senha: String): String? = when {
        senha.isBlank() -> "Informe sua senha"
        senha.length < MIN_SENHA -> "Mínimo de $MIN_SENHA caracteres"
        else -> null
    }

    private companion object {
        const val MIN_SENHA = 6
        val EMAIL_REGEX = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
    }
}
