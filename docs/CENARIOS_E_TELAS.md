# Cenários, Fluxos e Telas — NutriDia

Este documento detalha personas, cenários de uso, arquitetura de navegação, telas necessárias e padrões de layout planejados para o NutriDia. Serve como referência para o design e a implementação das interfaces (Jetpack Compose / Material 3).

---

## 1. Personas

### Persona 1 — Mariana, iniciante
- 28 anos, começou a reeducação alimentar há 2 semanas.
- Precisa de simplicidade no registro de refeições e de motivação constante.
- Valoriza dicas práticas e sentir que não está sozinha no processo.

### Persona 2 — Carlos, engajado
- 35 anos, já tem 4 meses de app, participa ativamente de desafios.
- Gosta de métricas, gráficos de progresso e de comparar evolução com metas.
- Interage bastante na comunidade: posta, comenta, incentiva outros usuários.

### Persona 3 — Dra. Beatriz, moderadora/nutricionista
- Cria conteúdo educativo (artigos/receitas) e modera grupos temáticos.
- Precisa de uma forma de publicar conteúdo confiável e acompanhar discussões dos grupos que modera.

---

## 2. Cenários de uso (user stories)

| # | Cenário | Ator | Descrição |
|---|---|---|---|
| C1 | Cadastro e onboarding | Mariana | Cria conta, define objetivo (emagrecimento, manutenção, ganho de massa) e metas iniciais (calorias, água). |
| C2 | Registro rápido de refeição | Mariana | Registra o café da manhã buscando o alimento na base ou repetindo uma refeição favorita, em menos de 30 segundos. |
| C3 | Registro por foto | Carlos | Fotografa o prato e adiciona uma estimativa/descrição manual quando não encontra o alimento na busca. |
| C4 | Acompanhar progresso semanal | Carlos | Abre a tela de progresso e visualiza gráfico de calorias e streak de dias consecutivos com meta batida. |
| C5 | Participar de desafio | Carlos | Entra em um desafio comunitário ("7 dias sem açúcar"), acompanha ranking e recebe badge ao concluir. |
| C6 | Buscar apoio no grupo | Mariana | Entra em um grupo de "iniciantes", posta uma dúvida e recebe respostas de outros membros. |
| C7 | Consultar conteúdo educativo | Mariana | Busca receitas saudáveis filtrando por restrição alimentar (ex: sem lactose). |
| C8 | Publicar conteúdo | Dra. Beatriz | Publica um artigo educativo no feed do grupo que modera. |
| C9 | Receber lembrete | Mariana | Recebe notificação para registrar o almoço e para beber água. |
| C10 | Editar meta | Carlos | Ajusta a meta diária de calorias após reavaliação. |
| C11 | Ver histórico de um dia específico | Mariana | Acessa o calendário do diário e revisa o que comeu há 5 dias. |

---

## 3. Fluxo de navegação (mapa geral)

Navegação principal por **bottom navigation bar** com 5 destinos, mais telas de pilha (stack) acessadas a partir delas.

```
[Splash] → [Onboarding] → [Login/Cadastro] → [Definição de metas iniciais]
                                                        │
                                                        ▼
                        ┌───────────────────────────────────────────────────┐
                        │                  BOTTOM NAVIGATION                 │
                        │  Diário │ Comunidade │ (+) │ Conteúdo │ Perfil      │
                        └───────────────────────────────────────────────────┘
```

- **Diário** → Detalhe de refeição, Adicionar refeição, Busca de alimento, Calendário/histórico, Progresso.
- **Comunidade** → Feed, Grupos, Detalhe de grupo, Desafios, Detalhe de desafio, Detalhe de post.
- **(+) Ação central (FAB)** → Atalho para "Adicionar refeição" (ação mais frequente do app).
- **Conteúdo** → Lista de artigos/receitas, Detalhe de artigo/receita.
- **Perfil** → Dados pessoais, Metas, Conquistas, Configurações, Notificações.

---

## 4. Telas necessárias

### 4.1 Autenticação e onboarding

| Tela | Objetivo | Componentes principais | Layout |
|---|---|---|---|
| Splash | Exibir marca enquanto verifica sessão | Logo, indicador de carregamento | Tela cheia centralizada |
| Onboarding (carrossel) | Apresentar a proposta do app (diário + comunidade) | 3 slides com ilustração + texto, indicador de página, botão "Pular" | Pager horizontal |
| Login | Autenticar usuário existente | Campos e-mail/senha, botão entrar, link "esqueci senha", link cadastro | Formulário vertical centralizado |
| Cadastro | Criar nova conta | Nome, e-mail, senha, aceite de termos | Formulário vertical |
| Definição de objetivo e metas iniciais | Coletar objetivo e metas base | Seleção de objetivo (cards), campos de meta (calorias, água, peso) | Wizard em etapas (stepper) |

### 4.2 Diário alimentar

| Tela | Objetivo | Componentes principais | Layout |
|---|---|---|---|
| Home / Diário do dia | Visão geral do dia: refeições registradas, resumo calórico, água, streak | Top bar com data/seletor de dia, resumo em cards/anel de progresso, lista de refeições (café, almoço, jantar, lanches) | Lista vertical com header de resumo |
| Adicionar refeição | Registrar alimento(s) em uma refeição | Busca com autocomplete, aba "favoritos"/"recentes", botão câmera, seletor de quantidade | Tela com busca + lista + FAB de confirmar |
| Busca de alimento | Encontrar alimento na base de dados | Campo de busca, lista de resultados com info calórica resumida | Lista com busca no topo |
| Detalhe do alimento | Ver/ajustar informações nutricionais antes de confirmar | Card nutricional (calorias, macros), seletor de porção/quantidade | Formulário simples |
| Calendário / histórico | Consultar dias anteriores | Calendário mensal, indicadores de dias com meta cumprida | Calendário + lista do dia selecionado |
| Progresso | Visualizar evolução de peso/calorias/hábitos | Gráficos de linha/barra, seletor de período (semana/mês), cards de streak e conquistas recentes | Lista vertical de gráficos e cards |

### 4.3 Comunidade

| Tela | Objetivo | Componentes principais | Layout |
|---|---|---|---|
| Feed da comunidade | Exibir posts de grupos e usuários seguidos | Lista de posts (cards com foto/texto, curtidas, comentários), botão criar post | Feed com scroll infinito |
| Criar post | Publicar atualização, dúvida ou conquista | Campo de texto, anexo de foto, seletor de grupo (opcional) | Formulário modal |
| Detalhe do post | Ver post completo e comentários | Conteúdo do post, lista de comentários, campo de novo comentário | Detalhe com lista de comentários |
| Lista de grupos | Descobrir e acessar grupos temáticos | Grid/lista de grupos com imagem, nome, nº de membros, botão participar | Grid de cards |
| Detalhe de grupo | Ver feed e informações do grupo | Header do grupo (descrição, membros), feed interno, botão sair/participar | Feed com header expansível |
| Lista de desafios | Ver desafios ativos e disponíveis | Cards de desafio (título, duração, participantes), filtro ativo/concluído | Lista de cards |
| Detalhe de desafio | Participar e acompanhar desafio | Descrição, progresso pessoal, ranking de participantes, botão entrar/sair | Detalhe com abas (Sobre / Ranking) |

### 4.4 Conteúdo educativo

| Tela | Objetivo | Componentes principais | Layout |
|---|---|---|---|
| Lista de conteúdo | Explorar artigos e receitas | Chips de filtro (categoria/restrição), grid/lista de cards com imagem e título | Grid de cards com filtros no topo |
| Detalhe de artigo/receita | Ler conteúdo completo | Imagem de capa, texto, ingredientes/modo de preparo (se receita), botão salvar/favoritar | Scroll vertical de conteúdo rico |

### 4.5 Perfil e configurações

| Tela | Objetivo | Componentes principais | Layout |
|---|---|---|---|
| Perfil | Ver dados, metas e conquistas do usuário | Avatar, nome, resumo de estatísticas, grid de badges/conquistas | Header + grid de conquistas |
| Editar perfil | Atualizar dados pessoais | Formulário (nome, foto, restrições alimentares) | Formulário vertical |
| Metas | Ajustar metas de calorias, água, peso | Campos numéricos, sliders | Formulário vertical |
| Notificações | Central de notificações recebidas | Lista de notificações agrupadas por data | Lista vertical |
| Configurações | Preferências gerais e conta | Lista de opções (tema, lembretes, privacidade, sair da conta) | Lista de settings (ListItem + Switch) |

---

## 5. Padrões de layout e UI

- **Design system:** Material Design 3 (Material You), com tema claro/escuro.
- **Navegação principal:** `NavigationBar` (bottom nav) com 5 itens; item central pode ser um `FloatingActionButton` sobreposto para a ação "Adicionar refeição".
- **Padrão de listas:** `LazyColumn`/`LazyVerticalGrid` com paginação para feed e histórico.
- **Cards:** usados para refeições, posts, desafios, grupos e conteúdo educativo — mantendo visual consistente (imagem/ícone + título + metadados + ação).
- **Gráficos de progresso:** anéis de progresso (círculo de meta calórica diária) e gráficos de linha/barra para evolução semanal/mensal.
- **Estados de tela:** todo componente de lista deve prever estado vazio (empty state), carregamento (skeleton/shimmer ou `CircularProgressIndicator`) e erro (mensagem + ação de retry).
- **Formulários:** organizados em `OutlinedTextField`/steppers, com validação inline e botão de ação fixo no rodapé.
- **Acessibilidade:** contraste adequado, `contentDescription` em imagens/ícones, tamanhos de toque mínimos de 48dp.

---

## 6. Componentes reutilizáveis (sugeridos)

- `MealCard` — refeição do diário (nome, horário, calorias, miniatura).
- `PostCard` — post da comunidade (autor, texto/imagem, curtidas, comentários).
- `ChallengeCard` — desafio (título, duração, nº de participantes, status).
- `ContentCard` — artigo/receita (imagem, título, tags).
- `ProgressRing` — indicador circular de meta diária.
- `AchievementBadge` — conquista/badge de gamificação.
- `EmptyState` / `LoadingState` / `ErrorState` — estados padrão de tela.
- `AppTopBar` / `AppBottomNavigation` — navegação padrão do app.

---

## 7. Modelo de dados — visão de alto nível (Firestore)

| Coleção | Campos principais |
|---|---|
| `users` | id, nome, email, foto, objetivo, metas (calorias, água, peso), restrições alimentares |
| `meals` | id, userId, data, tipo (café/almoço/jantar/lanche), itens (alimento, quantidade, calorias) |
| `foods` | id, nome, calorias, macros (proteína, carboidrato, gordura), unidade de porção |
| `goals` | userId, calorias, água, peso alvo, atualizado em |
| `groups` | id, nome, descrição, imagem, membros[], moderadorId |
| `posts` | id, autorId, grupoId (opcional), texto, imagem, curtidas[], criadoEm |
| `comments` | id, postId, autorId, texto, criadoEm |
| `challenges` | id, título, descrição, duração, participantes[], ranking |
| `content` | id, tipo (artigo/receita), título, imagem, corpo, tags[], autorId |

> Estrutura sujeita a ajustes conforme a modelagem detalhada do banco for evoluindo durante a implementação.
