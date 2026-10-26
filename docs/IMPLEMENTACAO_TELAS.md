# Checklist das interfaces

Referências: `docs/ui/01-*.png` a `25-*.png`. Implementação em Compose Multiplatform, Android e Web.
Dados demonstrativos em memória: fechar/recarregar o aplicativo reinicia a sessão. Não há autenticação, banco remoto ou persistência configurados.

| Etapa | Telas | Implementação | Compilação e integração |
|---|---|---|---|
| 1 | 01 Abertura, 02 Apresentação, 03 Login, 04 Cadastro, 05 Metas iniciais | 5/5 implementadas | Sintaxe e rotas revisadas; compilação e execução bloqueadas pelo ambiente |
| 2 | 06 Diário, 07 Refeição, 08 Busca, 09 Alimento, 10 Histórico | 5/5 implementadas | Sintaxe e rotas revisadas; compilação e execução bloqueadas pelo ambiente |
| 3 | 11 Progresso, 12 Comunidade, 13 Criar publicação, 14 Publicação, 15 Grupos | 5/5 implementadas | Sintaxe e rotas revisadas; compilação e execução bloqueadas pelo ambiente |
| 4 | 16 Grupo, 17 Desafios, 18 Desafio, 19 Conteúdo, 20 Receita | 5/5 implementadas | Sintaxe e rotas revisadas; compilação e execução bloqueadas pelo ambiente |
| 5 | 21 Perfil, 22 Editar perfil, 23 Metas, 24 Notificações, 25 Configurações | 5/5 implementadas | Sintaxe e rotas revisadas; compilação e execução bloqueadas pelo ambiente |

## Relação entre referências e código

Imagens na pasta `docs/ui/`. Caminhos Kotlin relativos a `composeApp/src/commonMain/kotlin/com/nutridia/feature/`.

| Imagem | Arquivo Kotlin | Situação |
|---|---|---|
| 01-tela-de-abertura.png | auth/SplashScreen.kt | [x] Implementada |
| 02-apresentacao-inicial.png | auth/OnboardingScreen.kt | [x] Implementada |
| 03-entrar.png | auth/LoginScreen.kt | [x] Existente adaptada, sem duplicação |
| 04-cadastro.png | auth/SignUpScreen.kt | [x] Implementada |
| 05-objetivo-e-metas-iniciais.png | goals/InitialGoalsScreen.kt | [x] Implementada |
| 06-diario-do-dia.png | diary/DiaryScreen.kt | [x] Implementada |
| 07-adicionar-refeicao.png | diary/AddMealScreen.kt | [x] Implementada |
| 08-busca-de-alimento.png | diary/FoodSearchScreen.kt | [x] Implementada |
| 09-detalhe-do-alimento.png | diary/FoodDetailScreen.kt | [x] Implementada |
| 10-calendario-e-historico.png | diary/HistoryScreen.kt | [x] Implementada |
| 11-progresso.png | goals/ProgressScreen.kt | [x] Implementada |
| 12-mural-da-comunidade.png | community/CommunityScreen.kt | [x] Implementada |
| 13-criar-publicacao.png | community/CreatePostScreen.kt | [x] Implementada |
| 14-detalhe-da-publicacao.png | community/PostDetailScreen.kt | [x] Implementada |
| 15-lista-de-grupos.png | community/GroupsScreen.kt | [x] Implementada |
| 16-detalhe-de-grupo.png | community/GroupDetailScreen.kt | [x] Implementada |
| 17-lista-de-desafios.png | community/ChallengesScreen.kt | [x] Implementada |
| 18-detalhe-de-desafio.png | community/ChallengeDetailScreen.kt | [x] Implementada |
| 19-lista-de-conteudo.png | content/ContentScreen.kt | [x] Implementada |
| 20-detalhe-de-receita.png | content/RecipeDetailScreen.kt | [x] Implementada; artigos usam a mesma rota de detalhe |
| 21-perfil.png | profile/ProfileScreen.kt | [x] Implementada |
| 22-editar-perfil.png | profile/EditProfileScreen.kt | [x] Implementada |
| 23-metas.png | goals/GoalsScreen.kt | [x] Implementada |
| 24-notificacoes.png | profile/NotificationsScreen.kt | [x] Implementada |
| 25-configuracoes.png | profile/SettingsScreen.kt | [x] Implementada |

## Organização e fluxo

- `core/components`: campos, botões, cartões, indicadores, diálogos e contrato multiplataforma para Voltar.
- `data/DemoData.kt`: modelos e conteúdo demonstrativo, sem alegar dados reais de uma base completa.
- `data/AppState.kt`: estado compartilhado da sessão e ações locais de navegação, refeições, comunidade e preferências.
- `navigation`: 25 destinos, integração das telas e barra inferior. Android usa BackHandler; Web usa os botões visíveis de retorno, sem integrar o histórico do navegador.
- Layout centralizado com largura máxima de 640 dp em janelas amplas. Conteúdo com rolagem, respeito aos insets e área do teclado.
- Início: abertura → apresentação → login/cadastro → metas iniciais/diário.
- Diário: refeições → busca → alimento → seleção → salvar. Calendário abre histórico. Botões dão acesso a progresso e metas.
- Comunidade: publicar, comentar, curtir, grupos e desafios. A ação de publicar no grupo mantém o grupo selecionado.
- Conteúdo: filtros, favoritos, detalhes e adição de receita ao diário. Conteúdos salvos são acessíveis no perfil.
- Perfil: edição, progresso, metas e configurações. Notificações são acessíveis pelo sino da comunidade.

## Verificação realizada e bloqueios

- [x] 25 arquivos de tela e 25 destinos identificados; login existente reaproveitado.
- [x] Análise sintática auxiliar dos 42 arquivos Kotlin: nenhum erro de sintaxe, usando o parser Kotlin 2.1.10 disponível localmente. Não é uma compilação do projeto Kotlin 2.1.21.
- [x] 12 checagens auxiliares de regras locais passaram: porções e nutrientes, água não negativa, salvamento de refeição, entradas inválidas, retorno da busca, grupos, curtidas, comentários, desafios e isolamento da sessão.
- Essas checagens auxiliares compilaram os modelos e o AppState com Kotlin 2.1.10 e uma implementação simples de estado, exclusivamente em `.gradle/validation`, fora dos fontes do aplicativo. Não validam o runtime Compose, recomposição ou interfaces.
- [x] `git diff --check`: sem erros. Nenhum commit, staging, exclusão de arquivo existente ou alteração do histórico.
- [ ] Compilação Web `:composeApp:compileKotlinWasmJs`: bloqueada. Dependências Kotlin 2.1.21 e Compose 1.8.2 não estão em cache. Tentativa online falhou com `Permission denied` nas conexões aos repositórios Google/Maven Central.
- [ ] Compilação Android `:composeApp:compileDebugKotlinAndroid`: bloqueada por ausência da plataforma `android-35` configurada pelo projeto. Estão instaladas as plataformas 36 e 36.1; a configuração original foi preservada.
- [ ] Execução oficial dos testes em `commonTest`: pendente da resolução das dependências. O arquivo de testes foi incluído; `kotlin("test")` foi a única dependência acrescentada e somente para testes.
- [ ] Comparação visual em execução, navegação por toque e verificação de acessibilidade no aparelho: pendentes, pois não foi possível gerar um build executável. Fidelidade visual ainda não aprovada em runtime.

## Como concluir a validação no ambiente de desenvolvimento

1. Abra a pasta que contém `settings.gradle.kts` no Android Studio.
2. Use JDK 17 e instale **Android SDK Platform 35** pelo SDK Manager. Preserve compileSdk e targetSdk em 35.
3. Configure o SDK localmente pelo Android Studio e permita ao Gradle acessar `google()`, Maven Central e Gradle Plugin Portal.
4. Execute a sincronização e, a partir da raiz Kotlin, rode:

```powershell
.\gradlew.bat :composeApp:assembleDebug :composeApp:testDebugUnitTest
.\gradlew.bat :composeApp:compileKotlinWasmJs :composeApp:wasmJsBrowserDevelopmentWebpack
```

5. Para executar a versão Web: `.\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun`.
6. Verifique as 25 telas em aparelho/emulador e na Web, incluindo largura reduzida, teclado e tema escuro. Compare cada tela com o PNG correspondente antes de considerar a revisão visual concluída.

Nenhuma troca de versão de SDK, Kotlin, Compose ou dependência de produção foi feita para contornar o ambiente.

## Limites conhecidos

- Cadastro e login permitem explorar a interface local; não criam conta nem autenticam no Firebase.
- Câmera, galeria, recuperação de senha, denúncia remota, exclusão remota de conta e notificações do sistema exigem integrações futuras. A interface informa quando uma operação não está disponível.
- Textos adicionais de onboarding e cálculo por Mifflin-St Jeor não são inventados: há somente a apresentação ilustrada e metas editáveis, com explicação dos dados faltantes.
- A moldura do celular e a barra de status das imagens não são reproduzidas dentro da interface.
- Os emojis ilustrativos usam a fonte disponível na plataforma e podem variar entre Android e Web.
- Git preservado, sem staging e sem commits automáticos.
