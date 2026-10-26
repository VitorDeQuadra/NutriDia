package com.nutridia.feature.auth

/**
 * Estado da tela de Login (padrão MVVM — a UI apenas renderiza este estado).
 */
data class LoginUiState(
    val email: String = "",
    val senha: String = "",
    val emailErro: String? = null,
    val senhaErro: String? = null,
    val senhaVisivel: Boolean = false,
    val carregando: Boolean = false,
    val mensagem: String? = null,
    // INICIO ALTERACAO CODEX | Data: 2026-10-20
    val validado: Boolean = false,
    // FIM ALTERACAO CODEX
) {
    val podeEnviar: Boolean
        get() = !carregando && email.isNotBlank() && senha.isNotBlank()
}
