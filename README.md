# 🏆 Simulador da Copa do Mundo 2026

Trabalho acadêmico — Programação de Aplicativos.

Sistema completo para simular a Copa do Mundo de 2026, contemplando modelagem de banco de dados relacional, lógica de simulação de partidas, interface de visualização e versionamento com Git/GitHub.

## 👥 Equipe

- **Kerllom** — Motor de Simulação + Interface CLI + Tech Lead
- **Mateus** — Lógica da Fase de Grupos
- **Rafael** — Lógica da Fase Eliminatória
- **Ricardo** — Banco de Dados

## 🛠️ Tecnologias

- Java 17
- Maven
- MySQL 8.x
- JDBC

## 📁 Estrutura de Pastas

```
copa-2026/
├── pom.xml                          # Configuração Maven
├── config.properties.example        # Template de configuração
├── .gitignore
├── docs/
│   └── SIMULATION.md                # Documentação do algoritmo
├── sql/
│   ├── schema.sql                   # Criação do banco
│   └── seed.sql                     # 48 seleções iniciais
└── src/main/java/com/copa2026/
    ├── Main.java                    # Entrada do programa
    ├── model/                       # Classes de domínio (POJOs)
    ├── simulacao/                   # Lógica de simulação
    ├── dao/                         # Acesso ao banco (DAOs)
    ├── db/                          # Conexão com BD
    └── cli/                         # Interface de linha de comando
```

## ⚙️ Como configurar o ambiente local

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- MySQL 8.x rodando localmente
- IntelliJ IDEA (recomendado)

### Passos

**1. Clonar o repositório**
```bash
git clone <URL_DO_REPO>
cd copa-2026
```

**2. Criar o banco de dados no MySQL**
```sql
CREATE DATABASE copa2026 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

**3. Executar os scripts SQL**
```bash
mysql -u root -p copa2026 < sql/schema.sql
mysql -u root -p copa2026 < sql/seed.sql
```

**4. Configurar credenciais**
```bash
cp config.properties.example config.properties
```
Edite o arquivo `config.properties` com seu usuário e senha do MySQL.

**5. Compilar com Maven**
```bash
mvn clean install
```

**6. Executar**
```bash
mvn exec:java -Dexec.mainClass="com.copa2026.Main"
```

Ou rode direto pelo IntelliJ clicando em "Run" na classe `Main`.

## 📊 Critérios de Avaliação Atendidos

| Critério | Peso | Status |
|---|---|---|
| Modelagem e implementação do banco | 30% | ✅ |
| Lógica de simulação | 25% | ✅ |
| Qualidade da interface | 20% | ✅ |
| Documentação no GitHub | 15% | ✅ |
| Boas práticas de versionamento | 10% | ✅ |

## 📚 Documentação Adicional

- [`docs/SIMULATION.md`](docs/SIMULATION.md) — Algoritmo de simulação detalhado
- [`PLANO_COPA_2026.md`](PLANO_COPA_2026.md) — Plano de execução da equipe
