# 🏆 Plano de Execução — Simulador da Copa do Mundo 2026

> Documento de coordenação da equipe. Cada integrante deve ler a sua seção e seguir o cronograma à risca para entregarmos no prazo.

---

## 👥 Equipe e Responsabilidades

| Integrante | Responsabilidade Principal | Critério de Avaliação |
|---|---|---|
| **Ricardo** | Banco de Dados | 30% |
| **Mateus** | Lógica da Fase de Grupos | parte dos 25% |
| **Rafael** | Lógica da Fase Eliminatória | parte dos 25% |
| **Kerllom** | Motor de Simulação + Interface CLI + Tech Lead + Polimento | parte dos 25% + 20% + 15% + 10% |

---

## 🛠️ Stack Tecnológica

- **Linguagem:** Java
- **IDE:** IntelliJ IDEA
- **Build:** Maven
- **Banco:** MySQL (modelagem no MySQL Workbench)
- **Acesso ao BD:** JDBC puro (driver `mysql-connector-j`)
- **Versionamento:** Git + GitHub

⚠️ **Sem JPA/Hibernate.** Vamos de JDBC puro porque é mais rápido de implementar e mostra domínio de SQL (que é o que vale 30% da nota).

---

## 🎯 Decisões de Escopo (o que VAMOS e o que NÃO VAMOS fazer)

### ✅ Vamos fazer
- Simulação **aleatória pura** dos placares (mais rápido e atende o requisito)
- Interface **CLI simples e funcional** (menu de texto)
- Schema bem modelado com índices nas FKs
- README detalhado e documentação do algoritmo
- Commits descritivos em branches separadas com PRs

### ❌ NÃO vamos fazer (cortes de escopo por prazo)
- Simulação baseada em ranking FIFA (aleatório é suficiente)
- ASCII art elaborada na CLI (texto formatado já basta)
- Testes de carga com 10 usuários simultâneos (apenas documentamos no README)
- GitHub Actions, linter automatizado
- Interface web ou desktop

---

## 🌳 Estrutura de Branches no GitHub

```
main                      ← versão final (só recebe merge da dev)
└── dev                   ← branch de integração
    ├── feature/banco        ← Ricardo
    ├── feature/simulacao    ← Kerllom (motor de partida)
    ├── feature/grupos       ← Mateus
    ├── feature/eliminatorias ← Rafael
    └── feature/interface    ← Kerllom (CLI)
```

**Regra:** ninguém commita direto na `main` ou `dev`. Sempre na sua branch e abre PR para `dev`.

---

## 📅 Cronograma

### 🌙 HOJE À NOITE (3-4 horas)

**Kerllom:**
- [ ] Criar repositório no GitHub com as branches
- [ ] Setup do projeto Maven no IntelliJ
- [ ] Configurar `pom.xml` com dependência do `mysql-connector-j`
- [ ] Criar as **classes de modelo** (POJOs): `Selecao`, `Grupo`, `Partida`, `Fase`, `Gol`
- [ ] Criar classe `ConexaoBD` lendo de `config.properties`
- [ ] Criar `.gitignore` e `.env.example` (ou `config.properties.example`)
- [ ] Push inicial na branch `dev`
- [ ] Avisar no grupo: "ambiente pronto, clonem e começamos amanhã cedo"

**Ricardo:**
- [ ] Finalizar `schema.sql` com todas as tabelas e FKs
- [ ] Criar `seed.sql` com as **48 seleções classificadas para a Copa 2026** divididas em 12 grupos de 4
- [ ] Adicionar índices nas colunas mais consultadas (id_grupo, id_fase, id_partida, fk_selecao)
- [ ] Push na branch `feature/banco`
- [ ] **Mandar o schema final no grupo** para Kerllom alinhar as classes de modelo

**Mateus e Rafael:**
- Descansem. Acordem cedo amanhã. ☕

---

### ☀️ AMANHÃ DE MANHÃ (4-5 horas)

**Kerllom:**
- [ ] Implementar **motor de simulação de partida** (a função que recebe duas seleções e devolve um placar)
- [ ] Implementar **DAOs** (Data Access Objects) para gravar Partidas e Gols no BD
- [ ] Disponibilizar o motor pronto para Mateus e Rafael usarem
- [ ] Avisar no grupo quando estiver pronto

**Mateus:**
- [ ] Implementar **fase de grupos**:
  - Gerar os 6 jogos de cada grupo (cada seleção joga contra todas do mesmo grupo)
  - Chamar o motor de simulação do Kerllom para cada jogo
  - Calcular pontuação (V=3, E=1, D=0)
  - Aplicar critérios de desempate FIFA: pontos → saldo de gols → gols pró → confronto direto
  - Retornar os 2 primeiros colocados de cada grupo (32 classificados para as oitavas)

**Rafael:**
- [ ] Implementar **fase eliminatória**:
  - Montar chaveamento a partir dos 32 classificados
  - Simular oitavas → quartas → semifinais → final
  - Implementar **decisão por pênaltis** (aleatório) em caso de empate no tempo normal
  - Retornar o campeão e o histórico de todas as partidas

**Ricardo:**
- [ ] Ajudar Kerllom com os DAOs se precisar
- [ ] Começar a escrever a seção de "Modelagem do Banco" no README
- [ ] Estar disponível para ajustes no schema se aparecer necessidade

---

### 🌇 AMANHÃ À TARDE (4-5 horas)

**Kerllom:**
- [ ] Integrar tudo numa classe `Main`:
  1. Conecta no BD
  2. Carrega as 48 seleções
  3. Chama fase de grupos (Mateus)
  4. Pega os 32 classificados
  5. Chama fase eliminatória (Rafael)
  6. Grava tudo no BD
- [ ] Implementar **Interface CLI** com menu:
  ```
  === SIMULADOR DA COPA DO MUNDO 2026 ===
  1 - Simular Copa do Mundo
  2 - Ver classificação dos grupos
  3 - Ver chaveamento da fase eliminatória
  4 - Ver resultados de todos os jogos
  5 - Ver campeão
  0 - Sair
  ```

**Todos:**
- [ ] Testes manuais rodando a aplicação do zero
- [ ] Reportar bugs no grupo

---

### 🌃 AMANHÃ À NOITE - Polimento (Kerllom)

- [ ] README completo com:
  - Propósito do projeto
  - Tecnologias utilizadas
  - Estrutura de pastas
  - Como configurar o ambiente local (MySQL, variáveis de ambiente)
  - Como rodar o sistema
- [ ] `SIMULATION.md` documentando o algoritmo de simulação
- [ ] `.env.example` (ou `config.properties.example`)
- [ ] Merge de todas as `feature/*` na `dev`
- [ ] Merge final `dev` → `main` via PR
- [ ] Última simulação de teste rodando do zero (clonar repo limpo, configurar BD, rodar)
- [ ] **ENTREGAR** o link do repositório na plataforma

---

## 📐 Contratos Importantes (NÃO MUDAR SEM AVISAR)

### Classes de Modelo (Kerllom cria hoje à noite)

```java
public class Selecao {
    private int id;
    private String nome;
    private String pais;
    private int idGrupo;
    // getters/setters/construtores
}

public class Partida {
    private int id;
    private Selecao casa;
    private Selecao visitante;
    private int golsCasa;
    private int golsVisitante;
    private Fase fase;
    private boolean decididoNosPenaltis;
    // getters/setters/construtores
}

public class Grupo {
    private int id;
    private String nome; // "A", "B", ..., "L"
    private List<Selecao> selecoes;
    // getters/setters/construtores
}

public enum Fase {
    GRUPOS, OITAVAS, QUARTAS, SEMIFINAL, FINAL
}
```

### Assinatura do Motor de Simulação (Kerllom implementa)

```java
public class SimuladorPartida {
    public Partida simular(Selecao casa, Selecao visitante, Fase fase);
}
```

⚠️ **Mateus e Rafael:** vocês vão CHAMAR essa função. Não precisam implementá-la.

---

## ✍️ Padrão de Commits (Conventional Commits)

Use sempre prefixos:
- `feat:` nova funcionalidade
- `fix:` correção de bug
- `docs:` documentação
- `refactor:` refatoração sem mudança de comportamento
- `chore:` configuração, dependências

**Bons exemplos:**
- ✅ `feat: adiciona motor de simulação de partida`
- ✅ `feat: implementa critérios de desempate FIFA na fase de grupos`
- ✅ `fix: corrige cálculo de saldo de gols`
- ✅ `docs: adiciona instruções de configuração do MySQL no README`

**Maus exemplos:**
- ❌ `update`
- ❌ `mudanças`
- ❌ `commit`

---

## 🚨 Regras de Ouro

1. **Cada um na sua branch.** Ninguém commita direto na `main` ou `dev`.
2. **Commits pequenos e frequentes.** Não junte 8 horas de trabalho num commit só.
3. **Mensagens descritivas.** Imagine o avaliador lendo seu histórico.
4. **Avise no grupo** antes de alterar qualquer coisa compartilhada (classes de modelo, schema do BD).
5. **Pull request para merge.** O PR é o que comprova o trabalho em equipe (vale parte dos 10% de versionamento).
6. **Sem credenciais hardcoded.** Senha do MySQL vai em `config.properties` (que está no `.gitignore`).
7. **Tratamento de erros.** Pelo menos `try/catch` nas operações de BD.
8. **Comentários nas funções principais.** Não em tudo, mas nas mais complexas (simulação, desempates).

---

## ✅ Checklist Final de Entrega

Antes de mandar o link, confirmar que o repositório tem:

- [ ] README.md detalhado
- [ ] schema.sql
- [ ] seed.sql (com as 48 seleções)
- [ ] config.properties.example (ou .env.example)
- [ ] .gitignore (ignorando IDE, build/, config.properties real)
- [ ] Histórico de commits com mensagens descritivas
- [ ] Branches de feature visíveis
- [ ] PRs registrados (mesmo que mergeados)
- [ ] Código compila e roda do zero
- [ ] Todos os 4 integrantes têm commits no repositório

---

## 📞 Comunicação

Qualquer dúvida, bloqueio ou mudança de planos → **avisem no grupo IMEDIATAMENTE**. Com esse prazo, não dá pra ficar travado em silêncio. Se alguém não tiver o que fazer porque depende de outra parte, fala no grupo que rearranjamos.

**VAMOS! 🚀**
