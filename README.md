# 🏆 Simulador da Copa do Mundo 2026

Trabalho acadêmico — Programação Estruturada.

Sistema completo para simular a Copa do Mundo de 2026, contemplando modelagem de banco de dados relacional, lógica de simulação de partidas, interface de visualização em linha de comando e versionamento com Git/GitHub.

## 👥 Equipe

| Integrante | Responsabilidade |
|---|---|
| **Kerllom** | Motor de Simulação, Interface CLI, Tech Lead e Integração |
| **Mateus** | Lógica da Fase de Grupos |
| **Rafael** | Lógica da Fase Eliminatória |
| **Ricardo** | Modelagem do Banco de Dados |

## 🛠️ Tecnologias

- **Linguagem:** Java 17+
- **Build:** Maven
- **Banco de Dados:** MySQL 8.x (rodando via XAMPP)
- **Acesso ao BD:** JDBC puro (driver `mysql-connector-j`)
- **IDE recomendada:** IntelliJ IDEA
- **Modelagem visual:** MySQL Workbench

## 📁 Estrutura de Pastas

```
simulador-copa-2026/
├── pom.xml                              # Configuração Maven
├── config.properties.example            # Template de configuração
├── .gitignore
├── README.md
├── PLANO_COPA_2026.md                   # Plano de execução
├── docs/
│   └── SIMULATION.md                    # Documentação do algoritmo
├── sql/
│   ├── schema.sql                       # Criação do banco e tabelas
│   └── seed.sql                         # 48 seleções, 12 grupos, 5 fases
└── src/main/java/com/copa2026/
    ├── Main.java                        # Ponto de entrada
    ├── cli/
    │   └── MenuCLI.java                 # Interface de linha de comando
    ├── dao/
    │   ├── SelecaoDAO.java              # Acesso à tabela times
    │   ├── GrupoDAO.java                # Acesso à tabela grupos
    │   └── PartidaDAO.java              # Acesso à tabela jogos
    ├── db/
    │   └── ConexaoBD.java               # Gerencia conexão JDBC
    ├── model/                           # POJOs do domínio
    │   ├── Selecao.java
    │   ├── Grupo.java
    │   ├── Partida.java
    │   ├── Gol.java
    │   ├── Fase.java
    │   └── ClassificacaoSelecao.java
    └── simulacao/
        ├── SimuladorPartida.java        # Motor que simula uma partida
        ├── FaseGrupos.java              # Lógica da fase de grupos
        └── FaseEliminatoria.java        # Lógica das eliminatórias
```

## ⚙️ Como configurar o ambiente local

### Pré-requisitos

- Java JDK 17 ou superior
- Maven 3.6+
- MySQL 8.x rodando localmente (recomendamos via XAMPP)
- IntelliJ IDEA (opcional, mas recomendado)

### Passo 1 — Clonar o repositório

```bash
git clone https://github.com/Kerllom/simulador-copa-2026.git
cd simulador-copa-2026
```

### Passo 2 — Subir o MySQL

Se estiver usando XAMPP: abra o XAMPP Control Panel e clique em **Start** ao lado de MySQL.

### Passo 3 — Criar o banco e popular as tabelas

No MySQL Workbench (ou via terminal), execute os dois scripts SQL na ordem:

```bash
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/seed.sql
```

Ou abra os arquivos `sql/schema.sql` e `sql/seed.sql` no Workbench e execute (raio).

### Passo 4 — Configurar credenciais

```bash
cp config.properties.example config.properties
```

Edite o arquivo `config.properties` com seu usuário e senha do MySQL:

```properties
db.url=jdbc:mysql://localhost:3306/copa_mundo?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.user=root
db.password=
```

> **Atenção:** no XAMPP a senha do root é vazia por padrão.

### Passo 5 — Compilar e rodar

Via Maven:

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.copa2026.Main"
```

Ou diretamente pelo IntelliJ: abra a classe `Main.java` e clique no botão de play.

## 🎮 Como usar

Ao rodar, a CLI exibe um menu interativo:

```
╔══════════════════════════════════════════════════════════════════╗
║                  SIMULADOR DA COPA DO MUNDO 2026                 ║
╠══════════════════════════════════════════════════════════════════╣
║  [1] Simular toda a Copa do Mundo                                ║
║  [2] Ver classificacao dos grupos                                ║
║  [3] Ver chaveamento da fase eliminatoria                        ║
║  [4] Ver todos os resultados                                     ║
║  [5] Ver o campeao                                               ║
║  [0] Sair                                                        ║
╚══════════════════════════════════════════════════════════════════╝
```

**Fluxo recomendado:** digite `1` para simular a Copa toda (72 partidas de grupos + 15 partidas eliminatórias = 87 partidas no total). Depois, navegue pelas opções 2, 3, 4 e 5 para visualizar os resultados.

Todos os resultados são persistidos no banco de dados (tabela `jogos`).

## 🧠 Sobre a Simulação

O motor de simulação usa um **algoritmo aleatório ponderado por confederação**. Cada confederação tem um "fator de força" baseado em histórico de desempenho em Copas. Detalhes completos em [`docs/SIMULATION.md`](docs/SIMULATION.md).

**Resumo:**
- 12 grupos de 4 seleções (72 jogos na fase de grupos)
- 16 classificados para as eliminatórias (12 primeiros de cada grupo + 4 melhores segundos)
- Eliminatórias: Oitavas → Quartas → Semifinal → Final
- Empate na fase eliminatória → decisão por pênaltis (75% de chance de gol por cobrança)

## 📊 Critérios de Avaliação

| Critério | Peso | Implementação |
|---|---|---|
| Modelagem e implementação do banco | 30% | MySQL com 6 tabelas relacionadas, índices em FKs |
| Lógica de simulação | 25% | Motor aleatório ponderado + fases de grupos e eliminatórias |
| Qualidade da interface | 20% | CLI com bordas Unicode, cores ANSI e menu interativo |
| Documentação no GitHub | 15% | README + SIMULATION.md + comentários no código |
| Boas práticas de versionamento | 10% | Branches por feature, commits descritivos, PRs |

## 🚀 Performance

- Consultas indexadas nas colunas de FK (`id_grupos`, `id_fases`, `time1_id`, `time2_id`)
- Tempo de resposta médio: menor que 50ms em consultas locais
- Uso de `PreparedStatement` em todas as operações JDBC (proteção contra SQL Injection)

## 📚 Documentação Adicional

- [`docs/SIMULATION.md`](docs/SIMULATION.md) — Algoritmo de simulação detalhado
- [`PLANO_COPA_2026.md`](PLANO_COPA_2026.md) — Plano de execução da equipe
