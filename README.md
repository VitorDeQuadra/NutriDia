# NutriDia

**Diário de Nutrição e Reeducação Alimentar Comunitária**

NutriDia é um aplicativo mobile Android para registro diário de alimentação (diário alimentar) aliado a um espaço comunitário de apoio à reeducação alimentar. A ideia central é unir o acompanhamento individual de hábitos (refeições, metas, progresso) com elementos sociais que aumentam a adesão do usuário ao processo — desafios em grupo, compartilhamento de conquistas, dicas de conteúdo educativo e apoio mútuo entre participantes.

> Projeto acadêmico desenvolvido para a disciplina de **Programação de Dispositivos Móveis** — UNIDEP.

## Sobre o projeto

Reeducação alimentar costuma falhar não por falta de informação, mas por falta de constância e de suporte. O NutriDia parte dessa premissa e propõe dois pilares complementares:

1. **Diário pessoal** — registro rápido de refeições, acompanhamento de metas (calorias, água, hábitos) e visualização de progresso ao longo do tempo.
2. **Comunidade** — grupos temáticos, desafios coletivos, feed de posts entre usuários e conteúdo educativo (artigos e receitas), criando senso de pertencimento e responsabilidade compartilhada.

Para o detalhamento de personas, cenários de uso, fluxos de navegação e telas necessárias, veja [docs/CENARIOS_E_TELAS.md](docs/CENARIOS_E_TELAS.md).

## Funcionalidades principais

- **Diário alimentar** — registro de refeições por busca de alimento, favoritos ou foto; cálculo de calorias/macros do dia.
- **Metas pessoais** — definição de metas de calorias, água e hábitos, com acompanhamento visual (gráficos e streaks).
- **Comunidade** — feed de posts, grupos de apoio por objetivo (emagrecimento, veganismo, diabetes, etc.) e reações/comentários.
- **Desafios coletivos** — desafios com duração definida (ex: "7 dias sem açúcar"), ranking e badges de conclusão.
- **Conteúdo educativo** — artigos e receitas saudáveis curados, filtráveis por restrição alimentar.
- **Gamificação** — conquistas, sequências (streaks) e progresso visual para reforçar consistência.
- **Notificações e lembretes** — lembretes de refeição, hidratação e atualizações da comunidade.

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Kotlin |
| UI | Jetpack Compose (Material 3) |
| Arquitetura | MVVM + Repository |
| Assincronismo | Kotlin Coroutines + Flow |
| Navegação | Navigation Compose |
| Backend / Dados | Firebase (Authentication, Firestore, Storage, Cloud Messaging) |
| Injeção de dependência | Hilt *(sugerido)* |

## Estrutura do projeto (planejada)

O projeto ainda está em fase de estruturação. A organização de pacotes prevista segue o padrão feature-first com camadas MVVM:

```
app/
├── src/main/java/com/nutridia/
│   ├── core/               # utilitários, extensões, tema (Compose), componentes de UI compartilhados
│   ├── data/
│   │   ├── remote/         # integração com Firebase (Auth, Firestore, Storage)
│   │   ├── repository/     # implementações dos repositórios
│   │   └── model/          # modelos de dados
│   ├── domain/              # casos de uso (use cases) e regras de negócio
│   ├── feature/
│   │   ├── auth/            # login e cadastro
│   │   ├── diary/           # diário alimentar (registro de refeições)
│   │   ├── goals/           # metas e progresso
│   │   ├── community/       # feed, grupos, desafios
│   │   ├── content/         # artigos e receitas
│   │   └── profile/         # perfil e configurações
│   └── navigation/          # grafo de navegação do app
└── src/test/                # testes unitários
```

## Como executar

> Pré-requisitos: Android Studio (Koala ou superior), JDK 17, projeto Firebase configurado.

1. Clone o repositório:
   ```
   git clone https://github.com/VitorDeQuadra/NutriDia.git
   ```
2. Abra o projeto no Android Studio.
3. Crie um projeto no [Firebase Console](https://console.firebase.google.com/) e adicione um app Android com o `applicationId` do projeto.
4. Baixe o arquivo `google-services.json` e coloque-o em `app/`.
5. Habilite no Firebase: **Authentication** (e-mail/senha), **Firestore Database** e **Storage**.
6. Sincronize o Gradle e execute em um emulador ou dispositivo físico.

## Status do projeto

🚧 Em fase inicial de planejamento e documentação — implementação em andamento como trabalho acadêmico.

## Documentação adicional

- [Cenários, fluxos e telas](docs/CENARIOS_E_TELAS.md)

## Licença

Distribuído sob a licença MIT. Veja [LICENSE](LICENSE) para mais detalhes.

## Autores

Vitor De Quadra
Rayana Calumby de Oliveira
