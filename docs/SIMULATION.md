# Algoritmo de Simulação — Copa do Mundo 2026

## Visão Geral

O sistema simula partidas usando um **algoritmo aleatório ponderado pela força histórica das confederações**. Em vez de um sorteio puro (onde Nova Caledônia teria a mesma chance que o Brasil), aplicamos um fator multiplicador baseado no desempenho das confederações em Copas do Mundo passadas.

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