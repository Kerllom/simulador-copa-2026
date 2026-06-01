package com.copa2026.cli;

import com.copa2026.dao.PartidaDAO;
import com.copa2026.model.ClassificacaoSelecao;
import com.copa2026.model.Fase;
import com.copa2026.model.Grupo;
import com.copa2026.model.Partida;
import com.copa2026.model.Selecao;
import com.copa2026.simulacao.FaseEliminatoria;
import com.copa2026.simulacao.FaseGrupos;
import com.copa2026.simulacao.SimuladorPartida;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Interface de linha de comando (CLI) para o Simulador da Copa do Mundo 2026.
 *
 * RESPONSAVEL: Kerllom
 *
 * Apresenta menus interativos com bordas Unicode para o usuario navegar entre
 * as funcionalidades: simular a Copa, ver classificacao dos grupos, ver
 * chaveamento da fase eliminatoria, e ver o campeao.
 */
public class MenuCLI {

    // Caracteres Unicode para bordas
    private static final String BORDA_TOPO = "╔══════════════════════════════════════════════════════════════════╗";
    private static final String BORDA_MEIO = "╠══════════════════════════════════════════════════════════════════╣";
    private static final String BORDA_BASE = "╚══════════════════════════════════════════════════════════════════╝";
    private static final String LATERAL = "║";

    // Cores ANSI (funciona na maioria dos terminais modernos)
    private static final String RESET = "\u001B[0m";
    private static final String AMARELO = "\u001B[33m";
    private static final String VERDE = "\u001B[32m";
    private static final String CIANO = "\u001B[36m";
    private static final String VERMELHO = "\u001B[31m";

    private final Scanner scanner;
    private final SimuladorPartida simulador;
    private final FaseGrupos faseGrupos;
    private final FaseEliminatoria faseEliminatoria;

    // Estado da simulacao em memoria
    private List<Grupo> grupos;
    private List<Partida> partidasGrupos;
    private List<Partida> partidasEliminatorias;
    private boolean simulacaoExecutada;

    public MenuCLI(List<Grupo> grupos) {
        this.scanner = new Scanner(System.in);
        this.simulador = new SimuladorPartida();
        this.faseGrupos = new FaseGrupos(simulador);
        this.faseEliminatoria = new FaseEliminatoria(simulador);
        this.grupos = grupos;
        this.partidasGrupos = new ArrayList<>();
        this.partidasEliminatorias = new ArrayList<>();
        this.simulacaoExecutada = false;
    }

    /**
     * Inicia o loop principal do menu.
     */
    public void iniciar() {
        boolean rodando = true;

        while (rodando) {
            limparTela();
            exibirMenuPrincipal();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    simularCopa();
                    pausar();
                    break;
                case 2:
                    if (verificarSimulacaoExecutada()) {
                        exibirClassificacaoGrupos();
                        pausar();
                    }
                    break;
                case 3:
                    if (verificarSimulacaoExecutada()) {
                        exibirChaveamentoEliminatorias();
                        pausar();
                    }
                    break;
                case 4:
                    if (verificarSimulacaoExecutada()) {
                        exibirTodosOsResultados();
                        pausar();
                    }
                    break;
                case 5:
                    if (verificarSimulacaoExecutada()) {
                        exibirCampeao();
                        pausar();
                    }
                    break;
                case 0:
                    rodando = false;
                    exibirDespedida();
                    break;
                default:
                    System.out.println(VERMELHO + ">> Opcao invalida! Tente novamente." + RESET);
                    pausar();
            }
        }

        scanner.close();
    }

    // ============================================================
    // MENUS
    // ============================================================

    private void exibirMenuPrincipal() {
        System.out.println(CIANO + BORDA_TOPO + RESET);
        System.out.println(CIANO + LATERAL + RESET + centralizar("SIMULADOR DA COPA DO MUNDO 2026", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + BORDA_MEIO + RESET);
        System.out.println(CIANO + LATERAL + RESET + alinharEsquerda("  [1] Simular toda a Copa do Mundo", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + LATERAL + RESET + alinharEsquerda("  [2] Ver classificacao dos grupos", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + LATERAL + RESET + alinharEsquerda("  [3] Ver chaveamento da fase eliminatoria", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + LATERAL + RESET + alinharEsquerda("  [4] Ver todos os resultados", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + LATERAL + RESET + alinharEsquerda("  [5] Ver o campeao", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + LATERAL + RESET + alinharEsquerda("  [0] Sair", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + BORDA_BASE + RESET);

        String status = simulacaoExecutada ? VERDE + "[Simulacao executada]" + RESET : AMARELO + "[Aguardando simulacao]" + RESET;
        System.out.println("Status: " + status);
        System.out.print("\n>> Escolha uma opcao: ");
    }

    // ============================================================
    // ACOES
    // ============================================================

    private void simularCopa() {
        System.out.println("\n" + CIANO + BORDA_TOPO + RESET);
        System.out.println(CIANO + LATERAL + RESET + centralizar("SIMULANDO A COPA DO MUNDO 2026", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + BORDA_BASE + RESET);

        System.out.println("\n" + AMARELO + ">> Simulando fase de grupos..." + RESET);
        partidasGrupos = faseGrupos.simularFaseDeGrupos(grupos);
        System.out.println(VERDE + "[OK] " + partidasGrupos.size() + " partidas simuladas na fase de grupos." + RESET);

        System.out.println("\n" + AMARELO + ">> Coletando classificados..." + RESET);
        List<Selecao> classificados = new ArrayList<>();
        for (Grupo grupo : grupos) {
            List<Partida> partidasDoGrupo = filtrarPartidasDoGrupo(grupo, partidasGrupos);
            List<Selecao> top2 = faseGrupos.getClassificados(grupo, partidasDoGrupo);
            classificados.addAll(top2);
        }
        System.out.println(VERDE + "[OK] " + classificados.size() + " selecoes classificadas para as oitavas." + RESET);

        System.out.println("\n" + AMARELO + ">> Simulando fase eliminatoria..." + RESET);
        partidasEliminatorias = faseEliminatoria.simularEliminatorias(classificados);

        if (partidasEliminatorias == null || partidasEliminatorias.isEmpty()) {
            System.out.println(VERMELHO + "[AVISO] Fase eliminatoria nao retornou partidas." + RESET);
            partidasEliminatorias = new ArrayList<>();
        } else {
            System.out.println(VERDE + "[OK] " + partidasEliminatorias.size() + " partidas simuladas na fase eliminatoria." + RESET);
        }

        // Persiste todas as partidas no banco
        System.out.println("\n" + AMARELO + ">> Salvando resultados no banco..." + RESET);
        try {
            PartidaDAO partidaDAO = new PartidaDAO();
            partidaDAO.limparTodas(); // Limpa simulacao anterior
            partidaDAO.salvarLote(partidasGrupos);
            partidaDAO.salvarLote(partidasEliminatorias);
            int total = partidasGrupos.size() + partidasEliminatorias.size();
            System.out.println(VERDE + "[OK] " + total + " partidas salvas no banco de dados." + RESET);
        } catch (SQLException e) {
            System.err.println(VERMELHO + "[ERRO] Falha ao salvar no banco: " + e.getMessage() + RESET);
            System.err.println(AMARELO + "       Os dados ficam apenas em memoria nesta sessao." + RESET);
        }

        simulacaoExecutada = true;
        System.out.println("\n" + VERDE + ">> Simulacao concluida com sucesso!" + RESET);
    }

    private void exibirClassificacaoGrupos() {
        System.out.println("\n" + CIANO + BORDA_TOPO + RESET);
        System.out.println(CIANO + LATERAL + RESET + centralizar("CLASSIFICACAO DA FASE DE GRUPOS", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + BORDA_BASE + RESET);

        for (Grupo grupo : grupos) {
            List<Partida> partidasDoGrupo = filtrarPartidasDoGrupo(grupo, partidasGrupos);
            List<ClassificacaoSelecao> tabela = montarTabelaCompleta(grupo, partidasDoGrupo);

            System.out.println("\n" + AMARELO + ">> GRUPO " + grupo.getNome() + RESET);
            System.out.println("+-------------------------+----+----+----+----+----+----+-----+-----+");
            System.out.println("| Selecao                 | J  | V  | E  | D  | GP | GC | SG  | PTS |");
            System.out.println("+-------------------------+----+----+----+----+----+----+-----+-----+");

            int pos = 1;
            for (ClassificacaoSelecao c : tabela) {
                String marcador = (pos <= 2) ? VERDE + "*" + RESET : " ";
                String nome = String.format("%-22s", truncar(c.getSelecao().getNome(), 22));
                String linha = String.format("| %s %s| %2d | %2d | %2d | %2d | %2d | %2d | %+3d | %3d |",
                        marcador, nome,
                        c.getJogosDisputados(), c.getVitorias(), c.getEmpates(), c.getDerrotas(),
                        c.getGolsPro(), c.getGolsContra(), c.getSaldoDeGols(), c.getPontos());
                System.out.println(linha);
                pos++;
            }
            System.out.println("+-------------------------+----+----+----+----+----+----+-----+-----+");
        }

        System.out.println("\n" + VERDE + "*" + RESET + " = Classificado para as oitavas de final");
    }

    private void exibirChaveamentoEliminatorias() {
        System.out.println("\n" + CIANO + BORDA_TOPO + RESET);
        System.out.println(CIANO + LATERAL + RESET + centralizar("CHAVEAMENTO DA FASE ELIMINATORIA", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + BORDA_BASE + RESET);

        if (partidasEliminatorias.isEmpty()) {
            System.out.println("\n" + VERMELHO + "Fase eliminatoria nao foi simulada." + RESET);
            return;
        }

        exibirPartidasDeUmaFase("OITAVAS DE FINAL", Fase.OITAVAS);
        exibirPartidasDeUmaFase("QUARTAS DE FINAL", Fase.QUARTAS);
        exibirPartidasDeUmaFase("SEMIFINAIS", Fase.SEMIFINAL);
        exibirPartidasDeUmaFase("FINAL", Fase.FINAL);
    }

    private void exibirTodosOsResultados() {
        System.out.println("\n" + CIANO + BORDA_TOPO + RESET);
        System.out.println(CIANO + LATERAL + RESET + centralizar("TODOS OS RESULTADOS", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + BORDA_BASE + RESET);

        System.out.println("\n" + AMARELO + ">> FASE DE GRUPOS (" + partidasGrupos.size() + " partidas)" + RESET);
        for (Grupo grupo : grupos) {
            System.out.println("\n  " + CIANO + "Grupo " + grupo.getNome() + RESET);
            List<Partida> partidasDoGrupo = filtrarPartidasDoGrupo(grupo, partidasGrupos);
            for (Partida p : partidasDoGrupo) {
                System.out.println("    " + formatarPartida(p));
            }
        }

        if (!partidasEliminatorias.isEmpty()) {
            System.out.println("\n" + AMARELO + ">> FASE ELIMINATORIA (" + partidasEliminatorias.size() + " partidas)" + RESET);
            exibirPartidasDeUmaFase("Oitavas", Fase.OITAVAS);
            exibirPartidasDeUmaFase("Quartas", Fase.QUARTAS);
            exibirPartidasDeUmaFase("Semifinal", Fase.SEMIFINAL);
            exibirPartidasDeUmaFase("Final", Fase.FINAL);
        }
    }

    private void exibirCampeao() {
        System.out.println("\n" + CIANO + BORDA_TOPO + RESET);
        System.out.println(CIANO + LATERAL + RESET + centralizar("CAMPEAO DA COPA DO MUNDO 2026", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + BORDA_BASE + RESET);

        if (partidasEliminatorias.isEmpty()) {
            System.out.println("\n" + VERMELHO + "Campeao ainda nao definido (fase eliminatoria nao simulada)." + RESET);
            return;
        }

        Selecao campeao = faseEliminatoria.getCampeao(partidasEliminatorias);
        if (campeao == null) {
            System.out.println("\n" + VERMELHO + "Nao foi possivel determinar o campeao." + RESET);
            return;
        }

        System.out.println();
        System.out.println(AMARELO + "                            * * * * *" + RESET);
        System.out.println(AMARELO + "                          *           *" + RESET);
        System.out.println(AMARELO + "                        *               *" + RESET);
        System.out.println(AMARELO + centralizar(">>> " + campeao.getNome().toUpperCase() + " <<<", 70) + RESET);
        System.out.println(AMARELO + "                        *               *" + RESET);
        System.out.println(AMARELO + "                          *           *" + RESET);
        System.out.println(AMARELO + "                            * * * * *" + RESET);
        System.out.println();
        System.out.println(centralizar("Confederacao: " + campeao.getConfederacao(), 66));
    }

    // ============================================================
    // METODOS AUXILIARES
    // ============================================================

    private void exibirPartidasDeUmaFase(String titulo, Fase fase) {
        List<Partida> partidasDaFase = new ArrayList<>();
        for (Partida p : partidasEliminatorias) {
            if (p.getFase() == fase) {
                partidasDaFase.add(p);
            }
        }

        if (partidasDaFase.isEmpty()) return;

        System.out.println("\n" + AMARELO + ">> " + titulo + RESET);
        for (Partida p : partidasDaFase) {
            System.out.println("  " + formatarPartida(p));
        }
    }

    private String formatarPartida(Partida p) {
        return String.format("%-22s %d x %d %s%s",
                truncar(p.getCasa().getNome(), 22),
                p.getGolsCasa(),
                p.getGolsVisitante(),
                truncar(p.getVisitante().getNome(), 22),
                p.isDecididoNosPenaltis()
                        ? " (penaltis " + p.getPenaltisCasa() + "-" + p.getPenaltisVisitante() + ")"
                        : "");
    }

    private List<Partida> filtrarPartidasDoGrupo(Grupo grupo, List<Partida> partidas) {
        List<Partida> resultado = new ArrayList<>();
        List<Integer> idsDoGrupo = new ArrayList<>();
        for (Selecao s : grupo.getSelecoes()) {
            idsDoGrupo.add(s.getId());
        }

        for (Partida p : partidas) {
            if (idsDoGrupo.contains(p.getCasa().getId())
                    && idsDoGrupo.contains(p.getVisitante().getId())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /**
     * Reaproveita a logica de processamento de partidas do Mateus
     * para gerar a tabela completa com V/E/D/J/GP/GC.
     */
    private List<ClassificacaoSelecao> montarTabelaCompleta(Grupo grupo, List<Partida> partidas) {
        List<ClassificacaoSelecao> tabela = new ArrayList<>();
        for (Selecao s : grupo.getSelecoes()) {
            tabela.add(new ClassificacaoSelecao(s));
        }

        for (Partida partida : partidas) {
            ClassificacaoSelecao fichaCasa = null;
            ClassificacaoSelecao fichaVisitante = null;

            for (ClassificacaoSelecao ficha : tabela) {
                if (ficha.getSelecao().getId() == partida.getCasa().getId()) fichaCasa = ficha;
                if (ficha.getSelecao().getId() == partida.getVisitante().getId()) fichaVisitante = ficha;
            }

            fichaCasa.setGolsPro(fichaCasa.getGolsPro() + partida.getGolsCasa());
            fichaCasa.setGolsContra(fichaCasa.getGolsContra() + partida.getGolsVisitante());
            fichaVisitante.setGolsPro(fichaVisitante.getGolsPro() + partida.getGolsVisitante());
            fichaVisitante.setGolsContra(fichaVisitante.getGolsContra() + partida.getGolsCasa());

            fichaCasa.setJogosDisputados(fichaCasa.getJogosDisputados() + 1);
            fichaVisitante.setJogosDisputados(fichaVisitante.getJogosDisputados() + 1);

            if (partida.getGolsCasa() > partida.getGolsVisitante()) {
                fichaCasa.setPontos(fichaCasa.getPontos() + 3);
                fichaCasa.setVitorias(fichaCasa.getVitorias() + 1);
                fichaVisitante.setDerrotas(fichaVisitante.getDerrotas() + 1);
            } else if (partida.getGolsCasa() < partida.getGolsVisitante()) {
                fichaVisitante.setPontos(fichaVisitante.getPontos() + 3);
                fichaVisitante.setVitorias(fichaVisitante.getVitorias() + 1);
                fichaCasa.setDerrotas(fichaCasa.getDerrotas() + 1);
            } else {
                fichaCasa.setPontos(fichaCasa.getPontos() + 1);
                fichaVisitante.setPontos(fichaVisitante.getPontos() + 1);
                fichaCasa.setEmpates(fichaCasa.getEmpates() + 1);
                fichaVisitante.setEmpates(fichaVisitante.getEmpates() + 1);
            }
        }

        tabela.sort(Comparator
                .comparingInt(ClassificacaoSelecao::getPontos).reversed()
                .thenComparingInt(ClassificacaoSelecao::getSaldoDeGols).reversed()
                .thenComparingInt(ClassificacaoSelecao::getGolsPro).reversed());

        return tabela;
    }

    private boolean verificarSimulacaoExecutada() {
        if (!simulacaoExecutada) {
            System.out.println("\n" + VERMELHO + ">> Voce precisa simular a Copa primeiro! (Opcao 1)" + RESET);
            return false;
        }
        return true;
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void pausar() {
        System.out.println("\n" + CIANO + ">> Pressione ENTER para voltar ao menu..." + RESET);
        scanner.nextLine();
    }

    private void limparTela() {
        // Limpa a tela usando ANSI escape codes
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void exibirDespedida() {
        System.out.println("\n" + CIANO + BORDA_TOPO + RESET);
        System.out.println(CIANO + LATERAL + RESET + centralizar("Obrigado por usar o Simulador da Copa 2026!", 66) + CIANO + LATERAL + RESET);
        System.out.println(CIANO + BORDA_BASE + RESET);
    }S

    private String centralizar(String texto, int largura) {
        int tamanho = textoSemAnsi(texto).length();
        if (tamanho >= largura) return texto;
        int espacosEsquerda = (largura - tamanho) / 2;
        int espacosDireita = largura - tamanho - espacosEsquerda;
        return " ".repeat(espacosEsquerda) + texto + " ".repeat(espacosDireita);
    }

    private String alinharEsquerda(String texto, int largura) {
        int tamanho = textoSemAnsi(texto).length();
        if (tamanho >= largura) return texto;
        return texto + " ".repeat(largura - tamanho);
    }

    private String textoSemAnsi(String texto) {
        return texto.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    private String truncar(String texto, int max) {
        if (texto.length() <= max) return texto;
        return texto.substring(0, max - 1) + "...";
    }
}