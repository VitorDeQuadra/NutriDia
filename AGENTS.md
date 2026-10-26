# Instruções permanentes de alterações

Estas regras se aplicam a todo este repositório e às suas subpastas.

## Antes de trabalhar

- Analise a estrutura e consulte `git status --short` e o diff existente antes de editar.
- Antes de modificar arquivos existentes, informe ao usuário quais arquivos serão alterados e por quê.
- Preserve o código, os comentários, as funcionalidades e as alterações locais do usuário. Faça apenas as mudanças necessárias à tarefa solicitada.

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
- Ao concluir uma tarefa, informe os arquivos criados e modificados e a validação realizada.
- Valide de forma proporcional à mudança. Documentação isolada não exige compilar o aplicativo; alterações de código exigem as verificações relevantes disponíveis.
