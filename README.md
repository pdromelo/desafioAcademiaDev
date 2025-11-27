## Equipe

- Kauã dos Santos Oliveira    GU3046672
- Pedro Câmara de Melo    GU3042324

## Estrutura do Projeto

O projeto foi organizado em camadas respeitando a Regra da Dependência (dependências apontam para dentro)

## Funcionalidades Implementadas

### Para Administradores
- Gerenciar status de cursos (ativar/inativar)
- Alterar planos de assinatura dos alunos
- Atender tickets de suporte (fila FIFO)
- Gerar relatórios e análises
- Exportar dados para CSV (com seleção dinâmica de colunas)

### Para Alunos
- Matricular-se em cursos (respeitando limites do plano)
- Consultar matrículas e progresso
- Atualizar progresso em cursos
- Cancelar matrículas
- Abrir tickets de suporte

### Relatórios Disponíveis
- Cursos por nível de dificuldade (ordenados alfabeticamente)
- Instrutores únicos de cursos ativos
- Alunos agrupados por plano de assinatura
- Média geral de progresso
- Aluno com maior número de matrículas

## Como Executar

Executar java ```main.Main```

## Usuários Pré-cadastrados

### Administradores:
- `admin@academiadev.com`
- `suporte@academiadev.com`

### Alunos:
- `joao@email.com` (BasicPlan - max 3 cursos)
- `maria@email.com` (PremiumPlan - ilimitado)
- `pedro@email.com` (BasicPlan - max 3 cursos)
- `ana@email.com` (PremiumPlan - ilimitado)
- `lucas@email.com` (BasicPlan - max 3 cursos)

## Tecnologias e Conceitos Utilizados

- **Java 8+**: Streams API, Lambdas, Optional
- **Clean Architecture**: Separação clara de responsabilidades
- **SOLID**: Princípios aplicados em toda a arquitetura
- **Collections Framework**: Map, Set, List, Queue (ArrayDeque)
- **Reflection**: Para exportação genérica de CSV
- **POO**: Herança, Polimorfismo, Encapsulamento, Abstração

## Padrões de Design

- **Dependency Injection**: Manual, via construtor
- **Repository Pattern**: Abstração da camada de persistência
- **Use Case Pattern**: Cada caso de uso em uma classe dedicada
- **Template Method**: SubscriptionPlan abstrato com implementações concretas

## Regras de Negócio

### Sistema de Matrículas
- BasicPlan: máximo 3 matrículas ativas
- PremiumPlan: matrículas ilimitadas
- Só é possível matricular-se em cursos ACTIVE
- Progresso inicia em 0% e vai até 100%

### Fila de Suporte
- FIFO (First-In, First-Out)
- Qualquer usuário pode abrir tickets
- Apenas administradores podem processar tickets

### Exportação CSV
- Seleção dinâmica de colunas via Reflection
- Retorna String formatada (não gera arquivo físico)

