# Algoritmo de Simulação — Copa do Mundo 2026

## Visão Geral

O sistema simula partidas usando um **algoritmo aleatório ponderado pela força histórica das confederações**. Em vez de um sorteio puro (onde Nova Zelândia teria a mesma chance que o Brasil), aplicamos um fator multiplicador baseado no desempenho das confederações em Copas do Mundo passadas.

## Fator de Confederação

Cada confederação tem um fator multiplicador aplicado à "expectativa de gols" da seleção:

| Confederação | Fator | Justificativa |
|---|---|---|
| **UEFA** (Europa) | 1.20 | 12 títulos mundiais, dominante histórica |
| **CONMEBOL** (Am. Sul) | 1.20 | 10 títulos mundiais, dominante histórica |
| **CONCACAF** (Am. Norte/Central) | 0.85 | México e EUA têm bom nível |
| **AFC** (Ásia) | 0.80 | Japão e Coreia do Sul vêm crescendo |
| **CAF** (África) | 0.80 | Marrocos chegou na semi em 2022 |
| **OFC** (Oceania) | 0.55 | Nível historicamente mais baixo |

## Cálculo de Gols por Time

Para cada seleção em uma partida, o número de gols é calculado assim:

```
gols_esperados = (aleatorio entre 0 e 3) * fator_confederacao
variacao       = (aleatorio entre -0.75 e +0.75)
gols_finais    = round(gols_esperados + variacao)
```

O resultado é limitado entre 0 e 6 gols.

## Bônus de Mando de Campo

O time da casa recebe um **bônus de +0.20** no fator multiplicador. Isso simula a vantagem psicológica de jogar em "casa".

## Fluxo Completo do Torneio

### Fase de Grupos (72 partidas)

- 12 grupos (A a L) de 4 seleções cada
- Cada seleção joga contra todas do mesmo grupo (6 jogos por grupo)
- Pontuação: V=3, E=1, D=0

### Critérios de Classificação (Ordem FIFA)

1. **Pontos**
2. **Saldo de gols**
3. **Gols pró**

> **Observação:** o critério de confronto direto (4º critério oficial da FIFA) foi simplificado por questões de prazo. Na prática, a probabilidade de empate nos 3 critérios anteriores é estatisticamente baixa.

### Coleta de Classificados (16 seleções)

A Copa de 2026 oficialmente classifica 32 times para as eliminatórias (12 primeiros + 12 segundos + 8 melhores terceiros). Para simplificar a implementação respeitando potências de 2, este simulador classifica **16 seleções**:

- **12 primeiros colocados** de cada grupo (1 de cada)
- **4 melhores segundos colocados** (entre os 12 segundos, escolhidos pelos mesmos critérios: pontos, saldo, gols pró)

### Fase Eliminatória (15 partidas)

- **Oitavas de final:** 16 seleções → 8 jogos
- **Quartas de final:** 8 seleções → 4 jogos
- **Semifinal:** 4 seleções → 2 jogos
- **Final:** 2 seleções → 1 jogo

**Chaveamento:** 1º vs 2º da lista de classificados, 3º vs 4º, e assim por diante.

## Disputa de Pênaltis

Em caso de empate no tempo regulamentar nas fases eliminatórias, há decisão por pênaltis:

1. **Fase inicial:** 5 cobranças para cada lado, com 75% de chance de gol por cobrança.
2. **Morte súbita:** se permanecer empatado após as 5 cobranças, há cobranças alternadas. A primeira em que um time converte e o outro erra decide o vencedor.

## Resultados Esperados

Com esse algoritmo, é esperado que:

- Seleções **UEFA/CONMEBOL** dominem a fase de grupos
- Seleções da **OFC** raramente avancem
- Confrontos **entre confederações fortes** (Brasil x Alemanha) sejam equilibrados
- Aproximadamente **25-30% das partidas eliminatórias** sejam decididas por pênaltis

## Persistência

Todas as 87 partidas (72 de grupos + 15 eliminatórias) são salvas no banco de dados na tabela `jogos`, incluindo:

- Placar do tempo regulamentar
- Vencedor (NULL em caso de empate na fase de grupos)
- Flag de decisão por pênaltis
- Placar dos pênaltis (quando aplicável)
- Fase do jogo (FK para tabela `fases`)
- Estádio (FK para tabela `estadios`)

## Reprodutibilidade

A classe `SimuladorPartida` oferece um construtor com `seed` (`new SimuladorPartida(seed)`) para fins de teste determinístico. Em produção, usa-se `new SimuladorPartida()` com seed aleatório.
