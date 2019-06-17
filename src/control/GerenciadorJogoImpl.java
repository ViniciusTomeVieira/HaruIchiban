/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import AbstractFactory.FabricaJogador;
import AbstractFactory.FabricaJunior;
import AbstractFactory.FabricaNormal;
import AbstractFactory.FabricaSenior;
import AbstractFactory.Jogador;
import AbstractFactory.JogadorJunior;
import Builder.ConstruirPadrao;
import Builder.ConstruirPadrao2;
import Builder.CriadorDeTabuleiro;
import Observer.Observador;
import State.EstadoJogo;
import Strategy.CalcularPontuacao;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

//Professor disse que pode ficar
import javax.swing.Icon;
import javax.swing.ImageIcon;

import model.Agua;
import decorator.flores.Flor;
import decorator.flores.FlorAmarela;
import decorator.flores.FlorBase;
import decorator.flores.FlorRosa;
import decorator.nenufares.Nenufar;
import decorator.nenufares.NenufarBase;
import decorator.nenufares.NenufarClaro;
import decorator.nenufares.NenufarClaroComFlorAmarela;
import decorator.nenufares.NenufarClaroComFlorRosa;
import decorator.nenufares.NenufarEscuro;
import decorator.nenufares.NenufarEscuroComFlorAmarela;
import decorator.nenufares.NenufarEscuroComFlorRosa;
import composite.Peca;
import decorator.sapos.SapoAmarelo;
import decorator.sapos.SapoRosa;
import composite.Tabuleiro;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public class GerenciadorJogoImpl implements GerenciadorJogo {

    //Tabuleiro
    private Peca[][] tabuleiroGerenciador = new Peca[5][5];
    private Tabuleiro tabuleiro;
    private Peca sapo;
    private int posicaoMover; // 1 = esquerda, 2 = direita, 3 = cima, 4 = baixo
    private int posicaoNenufarX;
    private int posicaoNenufarY;
    private boolean escolheuFlorMover;
    private String mensagemErro;
    private boolean terminouMoverNenufar;
    private int contadorFloresMao;
    private boolean sapoInserido = false;

    //Metodos de mover pecas
    private List<Peca> nenufaresParaRealocar = new ArrayList<>();
    private int indexParaRealocar = 1;

    //Flores
    private Flor[][] florDaVez = new Flor[2][4];
    private Flor florEscolhidaMao; // Flor que o jogador vai escolher para jogar no jogo
    private List<Flor> maoDaVez = new ArrayList<>();
    private int corDasFlores = 0;

    //Pontuação
//    private boolean temQuadrado, temLinhaHorVer4, temLinhaDia4, temLinha5 = false;
//    private List<Peca> formacaoDeFlores = new ArrayList<>();
//    private int indexOfPontuacao;
    //Abstract Factory
    private FabricaJogador fabricaJogador;

    //Jogadores
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador jogadorDaVez;
    private int indiceMensagens = 0;
    private String[] mensagens = {"  pegue suas cartas", "  escolha uma carta para jogar", " selecione uma carta", " jogue no nenufar escuro", " escolha um nenufar para jogar", " escolha um nenufar para mover", " escolha um nenufar para o sapo", " escolha a direcao a mover", " escolha a nenufar que ficara escura"};

    //Observadores
    private List<Observador> observadores = new ArrayList<>();

    //Estado do jogo
    private EstadoJogo estadojogo;
    private String estadoJogo = "SelecionarCor"; // SelecionarCor / EscolherFlores /   JogarFlor    /  JuniorEscuro    /    SeniorEscolhe  /   JuniorMovePeças /   SeniorEscolheEscuro     
    //    Jogador1   / JogadorModel 1 e 2  / JogadorModel 1 e 2  /  JogadorJunior   /    JogadorModel Senior /   JogadorJunior   /   JogadorModel Senior

    private String[] opcoesDeFlor = {"Rosa", "Amarela"};
    private String[] opcoesDeMover = {"Esquerda", "Direita", "Cima", "Baixo"};
    private final String[] opcoesDeTabuleiro = {"Padrão 1", "Padrão 2"};

    private GerenciadorJogoImpl() {
    }

    private static GerenciadorJogoImpl instance;

    public synchronized static GerenciadorJogoImpl getInstance() {
        if (instance == null) {
            instance = new GerenciadorJogoImpl();
        }
        return instance;
    }

    @Override
    public void inicializarJogadores() {
        fabricaJogador = new FabricaNormal();
        jogador1 = fabricaJogador.criarJogador(jogador1);
        jogador2 = fabricaJogador.criarJogador(jogador2);
    }

    public void setEstadojogo(EstadoJogo estadojogo) {
        this.estadojogo = estadojogo;
    }
    
    public void avancarEstado() {
		this.estadojogo.proxEstado();
	}

    @Override
    public void fluxoJogo() {
        switch (estadoJogo) {
            case "SelecionarCor":
                selecionarCores();
                break;
            case "CompararFlores":
                compararFlores();
                break;
        }
    }

    @Override
    public void inicializarTabuleiro(int opcao) throws Exception { // Vai ser alterado
        CriadorDeTabuleiro builder;

        switch (opcao) {
            case 0:
                builder = new ConstruirPadrao();
                break;
            case 1:
                builder = new ConstruirPadrao2();
                break;
            default:
                builder = new ConstruirPadrao();
        }

        DiretorBuilder dr1 = new DiretorBuilder(builder);
        dr1.construir(tabuleiroGerenciador);


        tabuleiroGerenciador = builder.getTabuleiroCriado();
    }

    @Override
    public Flor[][] inicializarFlores(Flor[][] floresJogador, Jogador jogador) throws Exception { // Pronto
        floresJogador = new Flor[2][4];
        Random valor = new Random();
        List<String> numerosSorteados = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                while (floresJogador[i][j] == null) {
                    int numero = valor.nextInt(8);
                    if (numerosSorteados.contains(numero + "")) {
                    } else {
                        if (jogador.getCorDaFlor().equals("Rosa")) {
                            Flor florBase = new FlorBase();
                            FlorRosa florRosa = new FlorRosa(florBase);
                            florRosa.selecionarImagemFlor();
                            florRosa.setNumero(numero + 1);
                            floresJogador[i][j] = florBase;
                            System.out.println("Flor inserida no deck do :" + jogador.getNome() + " " + (floresJogador[i][j].getNumero()));
                        } else {
                            Flor florBase = new FlorBase();
                            FlorAmarela florAmarela = new FlorAmarela(florBase);
                            florAmarela.setNumero(numero + 1);
                            florAmarela.selecionarImagemFlor();
                            floresJogador[i][j] = florBase;
                            //floresJogador[i][j] = new FlorAmarela(numero + 1);
                            System.out.println("Flor inserida no deck do :" + jogador.getNome() + " " + (floresJogador[i][j].getNumero()));
                        }
                        numerosSorteados.add(numero + "");
                    }
                }

            }
        }
        return floresJogador;

    }

    @Override
    public void addObservador(Observador obs) {
        observadores.add(obs);
    }

    @Override
    public Icon getPeca(int coluna, int linha) throws Exception { //Pronto
        if (tabuleiroGerenciador[coluna][linha] != null) {
            return tabuleiroGerenciador[coluna][linha].getImagem();
        }
        return null;
    }

    //Mostra as flores do deck
    @Override
    public Icon getFlor(int coluna, int linha) throws Exception { //Pronto
        if (florDaVez[coluna][linha] != null) {
            return florDaVez[coluna][linha].getImagem();
        }
        return null;
    }

    @Override
    public Icon getFlorMao(int coluna, int linha) throws Exception {
        if (maoDaVez.get(linha) != null) {
            if (coluna > 0) {
                System.out.println(maoDaVez.get(linha).getNumero());
                return verificarNumeroFlor(maoDaVez.get(linha));
            } else {
                return maoDaVez.get(linha).getImagem();
            }

        }
        return null;
    }

    private Icon verificarNumeroFlor(Flor flor) {
        return new ImageIcon("imagens/" + flor.getNumero() + ".png");
    }

    @Override
    public void selecionarCores() {
        switch (corDasFlores) {
            case 0:
                jogador1.setCorDaFlor("Rosa");
                jogador2.setCorDaFlor("Amarela");
                break;
            case 1:
                jogador1.setCorDaFlor("Amarela");
                jogador2.setCorDaFlor("Rosa");
                break;
            default:
                jogador1.setCorDaFlor("Rosa");
                jogador2.setCorDaFlor("Amarela");
        }
        estadoJogo = "EscolherFlores";
    }

    //Remove a flor que o jogador escolheu de seu deck e adiciona em sua mão
    @Override
    public void escolherFloresDeck(int row, int col) {

        if (estadoJogo.equals("EscolherFlores")) {
            Flor cartaEscolhida = florDaVez[col][row];
            if (maoDaVez.size() < 3 && verificarTamanhoDeck() > 0) {
                if (florDaVez[col][row] != null) {
                    enviarCartaParaMao(cartaEscolhida);
                    florDaVez[col][row] = null;
                    jogadorDaVez.setMao(maoDaVez);
                    jogadorDaVez.setFlores(florDaVez);
                    if (maoDaVez.size() == 3) {
                        trocarJogadorDaVez();
                    }
                    for (Observador obs : observadores) {
                        obs.notificarFlorEscolhida();
                    }
                }
            } else {
                trocarJogadorDaVez();
                for (Observador obs : observadores) {
                    obs.notificarFlorEscolhida();
                }
            }
        }

    }

    private void enviarCartaParaMao(Flor cartaEscolhida) {
        maoDaVez.add(cartaEscolhida);
    }

    private void trocarJogadorDaVez() {

        if (estadoJogo.equals("JuniorEscuro")) {
            if (jogador1.getClass() == JogadorJunior.class) {
                jogadorDaVez = jogador1;
                maoDaVez = jogador1.getMao();
                florDaVez = jogador1.getFlores();
            } else {
                jogadorDaVez = jogador2;
                maoDaVez = jogador2.getMao();
                florDaVez = jogador2.getFlores();
            }
        } else {
            if (jogadorDaVez.getNome().equals("Jogador 1")) {
                jogadorDaVez = jogador2;
                maoDaVez = jogador2.getMao();
                florDaVez = jogador2.getFlores();
            } else {
                jogadorDaVez = jogador1;
                maoDaVez = jogador1.getMao();
                florDaVez = jogador1.getFlores();
                avancarEstadoJogo(estadoJogo);
            }
        }
        for (Observador obs : observadores) {
            obs.notificarJogadorDaVezAlterado();
        }
    }

    private void avancarEstadoJogo(String estadoJogo) {
        switch (estadoJogo) {
            case "EscolherFlores":
                this.estadoJogo = "JogarFlor";
                indiceMensagens = 1;
                break;
            case "JogarFlor":
                this.estadoJogo = "CompararFlores";
                fluxoJogo();
                break;
        }
    }

    @Override
    public String getMensagemAtual() {
        return mensagens[indiceMensagens];
    }

    @Override
    public void escolherFlorParaJogar(int index) {
        if (estadoJogo.equals("JogarFlor")) {
            jogadorDaVez.setFlorEscolhida(maoDaVez.get(index));
            trocarJogadorDaVez();
        }
    }

    public void compararFlores() {
        if (jogador1.getFlorEscolhida().getNumero() < jogador2.getFlorEscolhida().getNumero()) {
            fabricaJogador = new FabricaJunior();
            jogador1 = fabricaJogador.criarJogador(jogador1);
            fabricaJogador = new FabricaSenior();
            jogador2 = fabricaJogador.criarJogador(jogador2);
            jogador1.setJuniorSenior("Junior");
            jogador2.setJuniorSenior("Senior");
            jogador1.getMao().remove(jogador1.getFlorEscolhida());
            jogador2.getMao().remove(jogador2.getFlorEscolhida());
            estadoJogo = "JuniorEscuro";
            trocarJogadorDaVez();
            indiceMensagens = 3;
            for (Observador obs : observadores) {
                obs.notificarJuniorSenior();
            }
        } else if (jogador1.getFlorEscolhida().getNumero() > jogador2.getFlorEscolhida().getNumero()) {
            fabricaJogador = new FabricaSenior();
            jogador1 = fabricaJogador.criarJogador(jogador1);
            fabricaJogador = new FabricaJunior();
            jogador2 = fabricaJogador.criarJogador(jogador2);
            jogador1.setJuniorSenior("Senior");
            jogador2.setJuniorSenior("Junior");
            jogador1.getMao().remove(jogador1.getFlorEscolhida());
            jogador2.getMao().remove(jogador2.getFlorEscolhida());
            estadoJogo = "JuniorEscuro";
            trocarJogadorDaVez();
            indiceMensagens = 3;
            for (Observador obs : observadores) {
                obs.notificarJuniorSenior();
            }
        } else { //Empate
            for (Observador obs : observadores) {
                obs.notificarEmpateComparacao();
            }
            estadoJogo = "JogarFlor";
        }

    }

    @Override
    public void clicouNoTabuleiro(int rowAtPoint, int columnAtPoint) {
        Nenufar nenufarBase = new NenufarBase();
        if (estadoJogo.equals("JuniorEscuro")) {
            if (tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == NenufarEscuro.class) {
                if (jogadorDaVez.getCorDaFlor().equals("Rosa")) {            
                    tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarEscuroComFlorRosa(nenufarBase);
                } else {
                    tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarEscuroComFlorAmarela(nenufarBase);
                }
                jogadorDaVez.getMao().remove(jogadorDaVez.getFlorEscolhida());
                estadoJogo = "SeniorEscolhe";
                indiceMensagens = 4;
                trocarJogadorDaVez();
                for (Observador obs : observadores) {
                    obs.notificarTabuleiroAlterado();
                }
            }
        }

        if (estadoJogo.equals("SeniorEscolhe")) {
            if (tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == NenufarClaro.class) {
                if (jogadorDaVez.getCorDaFlor().equals("Rosa")) {
                    if (sapo != null) {
                        tabuleiroGerenciador[columnAtPoint][rowAtPoint] = sapo;
                        sapo = null;
                    } else {
                        tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarClaroComFlorRosa(nenufarBase);
                    }
                } else {
                    if (sapo != null) {
                        tabuleiroGerenciador[columnAtPoint][rowAtPoint] = sapo;
                        sapo = null;
                    } else {
                        tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarClaroComFlorAmarela(nenufarBase);
                    }
                }

                //Troca
                indiceMensagens = 5;
                estadoJogo = "JuniorMovePecas";
                jogadorDaVez.getMao().remove(jogadorDaVez.getFlorEscolhida());
                trocarJogadorDaVez();
                for (Observador obs : observadores) {
                    obs.notificarTabuleiroAlterado();
                }

            } else if (tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == SapoRosa.class || tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == SapoAmarelo.class) {
                if (jogadorDaVez.getCorDaFlor().equals("Rosa")) {
                    sapo = tabuleiroGerenciador[columnAtPoint][rowAtPoint];
                    tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarClaroComFlorRosa(nenufarBase);
                } else {
                    sapo = tabuleiroGerenciador[columnAtPoint][rowAtPoint];
                    tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarClaroComFlorAmarela(nenufarBase);
                }
                indiceMensagens = 6;
                for (Observador obs : observadores) {
                    obs.notificarTabuleiroAlterado();
                }
            }
        }

        if (estadoJogo.equals("JuniorMovePecas")) {
            if (tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() != Agua.class) {
                posicaoNenufarX = columnAtPoint;
                posicaoNenufarY = rowAtPoint;
                escolheuFlorMover = true;
                indiceMensagens = 7;
                for (Observador obs : observadores) {
                    obs.notificarFlorEscolhidaParaMover();
                }
            }
        }

        if (estadoJogo.equals("SeniorEscolheEscuro")) {
            if (sapo != null) {
                if (tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == NenufarClaro.class) {
                    tabuleiroGerenciador[columnAtPoint][rowAtPoint] = sapo;
                    sapoInserido = true;
                }
            }
            if ((tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == NenufarClaro.class || tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == SapoAmarelo.class || tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == SapoRosa.class)) {
                if ((tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == SapoAmarelo.class || tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == SapoRosa.class) && sapo == null) {
                    sapo = tabuleiroGerenciador[columnAtPoint][rowAtPoint];
                    tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarEscuro(nenufarBase);
                    indiceMensagens = 6;
                    for (Observador obs : observadores) {
                        obs.notificarTabuleiroAlterado();
                    }
                }
                if (tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == NenufarClaro.class) {
                    tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarEscuro(nenufarBase);
                    sapoInserido = true;
                }
                if (sapoInserido) {
                    sapo = null;
                    sapoInserido = false;
                    indiceMensagens = 0;
                    estadoJogo = "EscolherFlores";
                    recomecarComJogador1();
                    if (verificarTamanhoDeck() == 0) {
                        trocarJogadorDaVez();
                    }
                    if (verificarTamanhoDeck() == 0) {
                        trocarJogadorDaVez();
                    }
                    for (Observador obs : observadores) {
                        obs.notificarTabuleiroAlterado();
                    }
//                    verificarFloracao();
                    verificarPontuação();
                    verificarVencedor();

                }

            }
        }

    }

    @Override
    public void juniorMovePecas() {
        if (escolheuFlorMover) {
            switch (posicaoMover) {
                case 0:
                    moverNenufarEsquerda();
                    break;
                case 1:
                    moverNenufarDireita();
                    break;
                case 2:
                    moverNenufarCima();
                    break;
                case 3:
                    moverNenufarBaixo();
                    break;
            }
        } else {
        }
    }

    //Getters e Setters
    @Override
    public Jogador getJogador1() {
        return jogador1;
    }

    @Override
    public void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    @Override
    public Jogador getJogador2() {
        return jogador2;
    }

    @Override
    public void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }

    @Override
    public Flor[][] getFlorDaVez() {
        return florDaVez;
    }

    @Override
    public void setFlorDaVez(Flor[][] florDaVez) {
        this.florDaVez = florDaVez;
    }

    @Override
    public int getCorDasFlores() {
        return corDasFlores;
    }

    @Override
    public void setCorDasFlores(int corDasFlores) {
        this.corDasFlores = corDasFlores;
    }

    @Override
    public Peca getFlorEscolhidaMao() {
        return florEscolhidaMao;
    }

    @Override
    public void setFlorEscolhidaMao(Flor florEscolhidaMao) {
        this.florEscolhidaMao = florEscolhidaMao;
    }

    @Override
    public String getEstadoJogo() {
        return estadoJogo;
    }

    @Override
    public void setEstadoJogo(String estadoJogo) {
        this.estadoJogo = estadoJogo;
    }

    /**
     *
     * @return
     */
    @Override
    public Jogador getJogadorDaVez() {
        return jogadorDaVez;
    }

    @Override
    public void setJogadorDaVez(Jogador jogadorDaVez) {
        this.jogadorDaVez = jogadorDaVez;
    }

    @Override
    public List<Flor> getMaoDaVez() {
        return maoDaVez;
    }

    @Override
    public void setMaoDaVez(List<Flor> maoDaVez) {
        this.maoDaVez = maoDaVez;
    }

    @Override
    public String[] getOpcoesDeFlor() {
        return opcoesDeFlor;
    }

    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }

    @Override
    public String[] getOpcoesDeTabuleiro() {
        return opcoesDeTabuleiro;
    }

    public Peca[][] getTabuleiroGerenciador() {
        return tabuleiroGerenciador;
    }

    public void setTabuleiroGerenciador(Peca[][] tabuleiroGerenciador) {
        this.tabuleiroGerenciador = tabuleiroGerenciador;
    }

    @Override
    public String[] getOpcoesDeMover() {
        return opcoesDeMover;
    }

    //Mover nenufares
    private void moverNenufarEsquerda() throws ArrayIndexOutOfBoundsException {

        try {
            if (tabuleiroGerenciador[posicaoNenufarX - indexParaRealocar][posicaoNenufarY].getClass() != null && tabuleiroGerenciador[posicaoNenufarX - indexParaRealocar][posicaoNenufarY].getClass() != Agua.class) {
                nenufaresParaRealocar.add(tabuleiroGerenciador[posicaoNenufarX - indexParaRealocar][posicaoNenufarY]);
                System.out.println(tabuleiroGerenciador[posicaoNenufarX - indexParaRealocar][posicaoNenufarY]);
                indexParaRealocar++;
                System.out.println(indexParaRealocar);
                moverNenufarEsquerda();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            indexParaRealocar--;
        }
        if (!terminouMoverNenufar) {
            if (tabuleiroGerenciador[posicaoNenufarX - indexParaRealocar][posicaoNenufarY].getClass() == Agua.class || (posicaoNenufarX - indexParaRealocar) < 0) {
                //Logica do array
                if (nenufaresParaRealocar.size() == 0) { //Caso ache agua de primeira
                    Peca p = tabuleiroGerenciador[posicaoNenufarX - indexParaRealocar][posicaoNenufarY];
                    tabuleiroGerenciador[posicaoNenufarX - indexParaRealocar][posicaoNenufarY] = tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY];
                    tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY] = p;
                    terminouMoverNenufar();
                } else { //Faz a troca de todas as posicoes
                    for (int i = indexParaRealocar; i > 0; i--) {
                        Peca p = tabuleiroGerenciador[posicaoNenufarX - i][posicaoNenufarY]; // Comeca com Agua
                        tabuleiroGerenciador[posicaoNenufarX - i][posicaoNenufarY] = tabuleiroGerenciador[posicaoNenufarX - i + 1][posicaoNenufarY];
                        tabuleiroGerenciador[posicaoNenufarX - i + 1][posicaoNenufarY] = p;
                    }
                    terminouMoverNenufar();
                }
            } else if ((posicaoNenufarX - indexParaRealocar) <= 0) {
                mensagemErro = "Você não pode mover um nenufar nessa direção!!!";
                indexParaRealocar = 1;
                terminouMoverNenufar = false;
                notificarErro();
            }
        }

    }

    private void moverNenufarDireita() {
        try {
            if (tabuleiroGerenciador[posicaoNenufarX + indexParaRealocar][posicaoNenufarY].getClass() != null && tabuleiroGerenciador[posicaoNenufarX + indexParaRealocar][posicaoNenufarY].getClass() != Agua.class) {
                nenufaresParaRealocar.add(tabuleiroGerenciador[posicaoNenufarX + indexParaRealocar][posicaoNenufarY]);
                System.out.println(tabuleiroGerenciador[posicaoNenufarX + indexParaRealocar][posicaoNenufarY]);
                indexParaRealocar++;
                System.out.println(indexParaRealocar);
                moverNenufarDireita();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            indexParaRealocar--;
        }
        if (!terminouMoverNenufar) {
            if (tabuleiroGerenciador[posicaoNenufarX + indexParaRealocar][posicaoNenufarY].getClass() == Agua.class || (posicaoNenufarX + indexParaRealocar) > 4) {
                //Logica do array
                if (nenufaresParaRealocar.size() == 0) { //Caso ache agua de primeira
                    Peca p = tabuleiroGerenciador[posicaoNenufarX + indexParaRealocar][posicaoNenufarY];
                    tabuleiroGerenciador[posicaoNenufarX + indexParaRealocar][posicaoNenufarY] = tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY];
                    tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY] = p;
                    terminouMoverNenufar();
                } else { //Faz a troca de todas as posicoes
                    for (int i = indexParaRealocar; i > 0; i--) {
                        Peca p = tabuleiroGerenciador[posicaoNenufarX + i][posicaoNenufarY]; // Comeca com Agua
                        tabuleiroGerenciador[posicaoNenufarX + i][posicaoNenufarY] = tabuleiroGerenciador[posicaoNenufarX + i - 1][posicaoNenufarY];
                        tabuleiroGerenciador[posicaoNenufarX + i - 1][posicaoNenufarY] = p;
                    }
                    terminouMoverNenufar();
                }
            } else if ((posicaoNenufarX + indexParaRealocar) >= 4) {
                mensagemErro = "Você não pode mover um nenufar nessa direção!!!";
                indexParaRealocar = 1;
                terminouMoverNenufar = false;
                notificarErro();
            }
        }
    }

    private void moverNenufarCima() {
        System.out.println("Entrou no cima");
        try {
            if (tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY - indexParaRealocar].getClass() != null && tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY - indexParaRealocar].getClass() != Agua.class) {
                nenufaresParaRealocar.add(tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY - indexParaRealocar]);
                System.out.println(tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY - indexParaRealocar]);
                indexParaRealocar++;
                System.out.println(indexParaRealocar);
                moverNenufarCima();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            indexParaRealocar--;
        }
        if (!terminouMoverNenufar) {
            if (tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY - indexParaRealocar].getClass() == Agua.class || (posicaoNenufarY - indexParaRealocar) < 0) {
                //Logica do array
                if (nenufaresParaRealocar.size() == 0) { //Caso ache agua de primeira
                    Peca p = tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY - indexParaRealocar];
                    tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY - indexParaRealocar] = tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY];
                    tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY] = p;
                    terminouMoverNenufar();
                } else { //Faz a troca de todas as posicoes
                    for (int i = indexParaRealocar; i > 0; i--) {
                        Peca p = tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY - i]; // Comeca com Agua
                        tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY - i] = tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY - i + 1];
                        tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY - i + 1] = p;
                    }
                    terminouMoverNenufar();
                }
            } else if ((posicaoNenufarY - indexParaRealocar) <= 0) {
                mensagemErro = "Você não pode mover um nenufar nessa direção!!!";
                indexParaRealocar = 1;
                terminouMoverNenufar = false;
                notificarErro();
            }
        }
    }

    private void moverNenufarBaixo() {
        try {
            if (tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + indexParaRealocar].getClass() != null && tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + indexParaRealocar].getClass() != Agua.class) {
                nenufaresParaRealocar.add(tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + indexParaRealocar]);
                System.out.println(tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + indexParaRealocar]);
                indexParaRealocar++;
                System.out.println(indexParaRealocar);
                moverNenufarBaixo();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            indexParaRealocar--;
        }
        if (!terminouMoverNenufar) {
            if (tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + indexParaRealocar].getClass() == Agua.class || (posicaoNenufarY + indexParaRealocar) > 4) {
                //Logica do array
                if (nenufaresParaRealocar.size() == 0) { //Caso ache agua de primeira
                    Peca p = tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + indexParaRealocar];
                    tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + indexParaRealocar] = tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY];
                    tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY] = p;
                    terminouMoverNenufar();
                } else { //Faz a troca de todas as posicoes
                    for (int i = indexParaRealocar; i > 0; i--) {
                        Peca p = tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + i]; // Comeca com Agua
                        tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + i] = tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + i - 1];
                        tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + i - 1] = p;
                    }
                    terminouMoverNenufar();
                }
            } else if ((posicaoNenufarY + indexParaRealocar) >= 4) {
                mensagemErro = "Você não pode mover um nenufar nessa direção!!!";
                indexParaRealocar = 1;
                terminouMoverNenufar = false;
                notificarErro();
            }
        }
    }

    @Override
    public void setFlorEscolhidaParaMover(int numero) {
        posicaoMover = numero;
        juniorMovePecas();
    }

    private void notificarErro() {
        for (Observador obs : observadores) {
            obs.notificarErro();
        }
    }

    private void terminouMoverNenufar() {
        terminouMoverNenufar = true;
        indiceMensagens = 8;
        indexParaRealocar = 1;
        estadoJogo = "SeniorEscolheEscuro";
        trocarJogadorDaVez();
        for (Observador obs : observadores) {
            obs.notificarTabuleiroAlterado();
        }

    }

    private void recomecarComJogador1() {
        jogadorDaVez = jogador1;
        maoDaVez = jogador1.getMao();
        florDaVez = jogador1.getFlores();
        indexParaRealocar = 1;
        terminouMoverNenufar = false;
    }

//    private void verificarFloracao() {
//        for (int linha = 0; linha < 5; linha++) {
//            for (int coluna = 0; coluna < 5; coluna++) {
//                if (tabuleiroGerenciador[coluna][linha].getClass() == NenufarClaroComFlorAmarela.class
//                        || tabuleiroGerenciador[coluna][linha].getClass() == NenufarClaroComFlorRosa.class
//                        || tabuleiroGerenciador[coluna][linha].getClass() == NenufarEscuroComFlorAmarela.class
//                        || tabuleiroGerenciador[coluna][linha].getClass() == NenufarEscuroComFlorRosa.class) {
//                    verificarQuadrado(coluna, linha);
//                    verificarLinhaHori(coluna, linha);
//                    verificarLinhaVert(coluna, linha);
//                    verificarLinhaDiag(coluna, linha);
//
//                }
//            }
//
//        }
//    }

//    private void verificarQuadrado(int coluna, int linha) {
//
//        try {
//            if (tabuleiroGerenciador[coluna][linha].getClass() == tabuleiroGerenciador[coluna + 1][linha].getClass()
//                    && tabuleiroGerenciador[coluna][linha].getClass() == tabuleiroGerenciador[coluna][linha + 1].getClass()
//                    && tabuleiroGerenciador[coluna][linha].getClass() == tabuleiroGerenciador[coluna + 1][linha + 1].getClass()) {
//                temQuadrado = true;
//            }
//        } catch (Exception ex) {
//            System.out.println("não tem quadrado");
//        }
//
//    }
//    private void verificarLinhaHori(int coluna, int linha) {
//        try {
//            while (tabuleiroGerenciador[coluna][linha].getClass() == tabuleiroGerenciador[coluna + 1][linha].getClass()) {
//                formacaoDeFlores.add(tabuleiroGerenciador[coluna][linha]);
//                coluna++;
//            }
//        } catch (ArrayIndexOutOfBoundsException ex) {
//        }
//        if (formacaoDeFlores.size() == 4) {
//            temLinhaHorVer4 = true;
//        } else if (formacaoDeFlores.size() == 5) {
//            temLinha5 = true;
//        }
//        formacaoDeFlores.clear();
//    }
//    private void verificarLinhaVert(int coluna, int linha) {
//        try {
//            while (tabuleiroGerenciador[coluna][linha].getClass() == tabuleiroGerenciador[coluna][linha + 1].getClass()) {
//                formacaoDeFlores.add(tabuleiroGerenciador[coluna][linha]);
//                linha++;
//            }
//        } catch (ArrayIndexOutOfBoundsException ex) {
//
//        }
//
//        System.out.println("quantidade na formação:" + formacaoDeFlores.size());
//        if (formacaoDeFlores.size() == 4) {
//            temLinhaHorVer4 = true;
//        } else if (formacaoDeFlores.size() == 5) {
//            temLinha5 = true;
//        }
//        formacaoDeFlores.clear();
//    }
//    private void verificarLinhaDiag(int coluna, int linha) {
//
//        try {
//            while (tabuleiroGerenciador[coluna][linha].getClass() == tabuleiroGerenciador[coluna + 1][linha + 1].getClass()) {
//                formacaoDeFlores.add(tabuleiroGerenciador[coluna][linha]);
//                coluna++;
//                linha++;
//            }
//        } catch (ArrayIndexOutOfBoundsException ex) {
//        }
//
//        if (formacaoDeFlores.size() == 4) {
//            temLinhaHorVer4 = true;
//        } else if (formacaoDeFlores.size() == 5) {
//            temLinha5 = true;
//        }
//        formacaoDeFlores.clear();
//
//    }
    private void verificarPontuação() {
//        System.out.println("1: " + temQuadrado + ", 2: " + temLinhaHorVer4 + ", 3:" + temLinhaDia4 + "5: " + temLinha5);
//        if (temLinha5 == true) {
//            jogadorDaVez.setPontuacao(jogadorDaVez.getPontuacao() + 5);
//        } else if (temLinhaDia4 == true) {
//            jogadorDaVez.setPontuacao(jogadorDaVez.getPontuacao() + 3);
//        } else if (temLinhaHorVer4 == true) {
//            jogadorDaVez.setPontuacao(jogadorDaVez.getPontuacao() + 2);
//        } else if (temQuadrado == true) {
//            jogadorDaVez.setPontuacao(jogadorDaVez.getPontuacao() + 1);
//        }
//        if (temLinhaHorVer4 == true || temQuadrado == true || temLinhaDia4 == true || temLinha5 == true) {
//            System.out.println("entrou observer");
//            for (Observador obs : observadores) {
//                obs.notificarRodadaEncerado();
//
//            }
//        }
        CalcularPontuacao calcular = new CalcularPontuacao();
        jogadorDaVez.setPontuacao(calcular.verificar(tabuleiroGerenciador));

        if (calcular.verificar(tabuleiroGerenciador) != 0) {
            System.out.println("entrou observer");
            for (Observador obs : observadores) {
                obs.notificarRodadaEncerado();

            }
        }
    }

    private void verificarVencedor() {
        String vencedor = "";
        if (jogador1.getPontuacao() >= 5) {
            vencedor = "Jogador 1 venceu!!!!";
            jogoEncerrado(vencedor);
        } else if (jogador2.getPontuacao() >= 5) {
            vencedor = "Jogador 2 venceu!!!!";
            jogoEncerrado(vencedor);
        }

    }

    private void jogoEncerrado(String vencedor) {
        for (Observador obs : observadores) {
            obs.notificarJogoEncerado(vencedor);

        }
    }

    @Override
    public void novaRodada() {
        try {
            jogador1.setFlores(inicializarFlores(jogador1.getFlores(), jogador1));
            jogador2.setFlores(inicializarFlores(jogador2.getFlores(), jogador2));
        } catch (Exception ex) {
            Logger.getLogger(GerenciadorJogoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        jogador1.getMao().clear();
        jogador2.getMao().clear();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tabuleiroGerenciador[i][j] = null;
            }
        }
        recomecarComJogador1();
        try {
            inicializarTabuleiro(0); // Arrumar
        } catch (Exception ex) {
            Logger.getLogger(GerenciadorJogoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private int verificarTamanhoDeck() {
        int qtd = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (florDaVez[i][j] != null) {
                    qtd++;
                }
            }
        }
        return qtd;
    }

}
