<!-- // ORIGEM: CODEX | Data de criacao: 2026-10-20 -->

# Instruções permanentes de autoria e alterações

Estas regras se aplicam a todo este repositório e às suas subpastas.

## Antes de trabalhar

- Analise a estrutura e consulte `git status --short` e o diff existente antes de editar.
- Antes de modificar arquivos existentes, informe ao usuário quais arquivos serão alterados e por quê.
- Preserve o código, os comentários, as funcionalidades e as alterações locais do usuário. Faça apenas as mudanças necessárias à tarefa solicitada.
- Não altere a lógica das telas apenas para adicionar identificação de autoria.

## Arquivos novos criados pelo Codex

- Inclua `// ORIGEM: CODEX | Data de criacao: AAAA-MM-DD`, usando a data real de criação. Preserve essa data nas edições seguintes.
- Em Kotlin e Kotlin Script (`.kt` e `.kts`), coloque o comentário antes do `package` ou dos imports, preservando cabeçalhos de licença e requisitos de sintaxe.
- Use comentários válidos para cada formato: em Markdown, HTML e XML, por exemplo, `<!-- // ORIGEM: CODEX | Data de criacao: AAAA-MM-DD -->`. Em XML, preserve a declaração XML como primeira instrução.
- Em formatos que usam `#` para comentários, use `# // ORIGEM: CODEX | Data de criacao: AAAA-MM-DD`.
- Não insira comentários em JSON, binários, imagens ou outros formatos que não os aceitam. Nesses casos, registre caminho, origem e data no relatório da tarefa; não prejudique o funcionamento para inserir a marcação.
- Não marque arquivos gerados automaticamente pelo compilador, pelo Gradle ou por ferramentas como se tivessem sido escritos pelo Codex.

## Alterações em arquivos existentes

- Preserve os comentários originais e delimite apenas os trechos efetivamente alterados pelo Codex, sempre que a sintaxe permitir:

```kotlin
// INICIO ALTERACAO CODEX | Data: AAAA-MM-DD
// Trecho criado ou modificado nesta alteração.
// FIM ALTERACAO CODEX
```

- Adapte os delimitadores ao formato do arquivo, mantendo os textos `// INICIO ALTERACAO CODEX` e `// FIM ALTERACAO CODEX` dentro de comentários válidos.
- Não envolva código manual inalterado em blocos atribuídos ao Codex. Não adicione um cabeçalho de origem Codex a um arquivo preexistente apenas porque ele foi editado.
- Preserve as marcações anteriores. Evite blocos redundantes ou aninhados; quando editar um bloco já marcado, mantenha a origem e registre a nova data em um comentário adequado, se necessário.
- Para exclusões ou mudanças em que comentários sejam inválidos ou prejudiquem a leitura ou o funcionamento, use o diff do Git e descreva a alteração no relatório da tarefa.

## Interpretação da autoria

- `ORIGEM: CODEX` identifica um arquivo novo criado pelo Codex.
- Os delimitadores identificam trechos alterados pelo Codex dentro de arquivos existentes.
- Ausência de marcação significa autoria não determinada por este sistema, e não prova de autoria humana.
- Nunca atribua ao Codex código criado manualmente pelo usuário. Não faça marcação retroativa por suposição.
- A identificação começa em 2026-10-20; não reclassifique o código já existente sem evidência e autorização do usuário.
- Estas instruções orientam o Codex; não constituem um mecanismo automático de detecção ou fiscalização de autoria.

## Git e revisão

- Utilize o repositório Git existente. Preserve seu histórico e não execute `git init` novamente.
- Não crie commits automaticamente. Não faça staging, descarte alterações, reescreva histórico ou publique mudanças sem solicitação do usuário.
- Execute os comandos a partir da pasta que contém este arquivo:
  - `git status --short`: arquivos modificados e arquivos novos ainda não rastreados.
  - `git diff`: alterações locais em arquivos rastreados.
  - `git diff --cached`: alterações já preparadas para commit.
  - `git log --oneline`: histórico existente.
  - `git log -p -- caminho/do/arquivo.kt`: histórico detalhado de um arquivo.
  - `git diff --no-index -- /dev/null AGENTS.md`: visualizar este arquivo novo antes de adicioná-lo ao Git, em ambientes que disponibilizem `/dev/null`. No Windows, use `git diff --no-index -- NUL AGENTS.md`.
- Arquivos novos não aparecem no `git diff` comum até serem rastreados. Revise também seu conteúdo e o resultado de `git status`.
- Ao concluir uma tarefa, informe os arquivos criados e modificados, a validação realizada e eventuais exceções às marcações.
- Valide de forma proporcional à mudança. Documentação isolada não exige compilar o aplicativo; alterações de código exigem as verificações relevantes disponíveis.
