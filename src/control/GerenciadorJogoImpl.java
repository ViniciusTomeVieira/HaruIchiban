/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Builder.ConstruirPadrão;
import Builder.ConstruirPadrão2;
import Builder.CriadorDeTabuleiro;
import Observer.Observador;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Agua;
import model.Flor;
import model.FlorAmarela;
import model.FlorRosa;
import model.Jogador;
import model.NenufarClaro;
import model.NenufarClaroComFlorAmarela;
import model.NenufarClaroComFlorRosa;
import model.NenufarEscuro;
import model.NenufarEscuroComFlorAmarela;
import model.NenufarEscuroComFlorRosa;
import model.Peca;
import model.SapoAmarelo;
import model.SapoRosa;
import model.Tabuleiro;

/**
 *
 * @author vinny
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

    //Metodos de mover pecas
    private List<Peca> nenufaresParaRealocar = new ArrayList<>();
    private int indexParaRealocar = 1;

    //Flores
    private Flor[][] florDaVez = new Flor[2][4];
    private Flor florEscolhidaMao; // Flor que o jogador vai escolher para jogar no jogo
    private List<Flor> maoDaVez = new ArrayList<>();
    private int corDasFlores = 0;

    //Pontuação
    private boolean temQuadrado, temLinhaHorVer4, temLinhaDia4, temLinha5 = false;

    //Jogadores
    private Jogador jogador1 = new Jogador();
    private Jogador jogador2 = new Jogador();
    private Jogador jogadorDaVez;
    private int indiceMensagens = 0;
    private String[] mensagens = {"  pegue suas cartas", "  escolha uma carta para jogar", " selecione uma carta", " jogue no nenufar escuro", " escolha um nenufar para jogar", " escolha um nenufar para mover", " escolha um nenufar para o sapo", " escolha a direcao a mover", " escolha a nenufar que ficara escura"};

    //Observadores
    private List<Observador> observadores = new ArrayList<>();

    //Estado do jogo
    private String estadoJogo = "SelecionarCor"; // SelecionarCor / EscolherFlores /   JogarFlor    /  JuniorEscuro    /    SeniorEscolhe  /   JuniorMovePeças /   SeniorEscolheEscuro     
    //    Jogador1   / Jogador 1 e 2  / Jogador 1 e 2  /  JogadorJunior   /    Jogador Senior /   JogadorJunior   /   Jogador Senior

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
                builder = new ConstruirPadrão();
                break;
            case 1:
                builder = new ConstruirPadrão2();
                break;
            default:
                builder = new ConstruirPadrão();
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
                            floresJogador[i][j] = new FlorRosa(numero + 1);
                        } else {
                            floresJogador[i][j] = new FlorAmarela(numero + 1);
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
            if (maoDaVez.size() < 3 && florDaVez.length > 0) {
                enviarCartaParaMao(cartaEscolhida);
                florDaVez[col][row] = null;
                jogadorDaVez.setMao(maoDaVez);
                jogadorDaVez.setFlores(florDaVez);
                if (maoDaVez.size() == 3) {
                    trocarJogadorDaVez();
                }
            }
        }

        for (Observador obs : observadores) {
            obs.notificarFlorEscolhida();
        }
    }

    private void enviarCartaParaMao(Flor cartaEscolhida) {
        maoDaVez.add(cartaEscolhida);
    }

    private void trocarJogadorDaVez() {

        if (estadoJogo.equals("JuniorEscuro")) {
            if (jogador1.getJuniorOuSenior().equals("Junior")) {
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
        jogadorDaVez.setFlorEscolhida(maoDaVez.get(index));
        trocarJogadorDaVez();
    }

    public void compararFlores() {
        if (jogador1.getFlorEscolhida().getNumero() > jogador2.getFlorEscolhida().getNumero()) {
            jogador1.setJuniorOuSenior("Junior");
            jogador2.setJuniorOuSenior("Senior");
            System.out.println("Jogador 1 é o junior");
        } else if (jogador1.getFlorEscolhida().getNumero() < jogador2.getFlorEscolhida().getNumero()) {
            jogador1.setJuniorOuSenior("Senior");
            jogador2.setJuniorOuSenior("Junior");
            System.out.println("Jogador 2 é o junior");
        } else { //Empate
            Random random = new Random();
            int numero = random.nextInt(2);
            if (numero == 0) {
                jogador1.setJuniorOuSenior("Junior");
                jogador2.setJuniorOuSenior("Senior");
            } else {
                jogador1.setJuniorOuSenior("Senior");
                jogador2.setJuniorOuSenior("Junior");
            }
        }
        estadoJogo = "JuniorEscuro";
        trocarJogadorDaVez();
        indiceMensagens = 3;
        for (Observador obs : observadores) {
            obs.notificarJuniorSenior();
        }

    }

    public Jogador verificarJunior() {
        if (jogador1.getJuniorOuSenior().equals("Junior")) {
            return jogador1;
        } else {
            return jogador2;
        }
    }

    @Override
    public void clicouNoTabuleiro(int rowAtPoint, int columnAtPoint) {
        if (estadoJogo.equals("JuniorEscuro")) {
            if (tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == NenufarEscuro.class) {
                if (jogadorDaVez.getCorDaFlor().equals("Rosa")) {
                    tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarEscuroComFlorRosa();
                } else {
                    tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarEscuroComFlorAmarela();
                }
                jogadorDaVez.getMao().remove(jogadorDaVez.getFlorEscolhida());
                estadoJogo = "SeniorEscolhe";
                indiceMensagens = 4;
                trocarJogadorDaVez();
                for (Observador obs : observadores) {
                    obs.notificarTabuleiroAlterado();
                }
            }
            verificarFloracao(tabuleiroGerenciador);
        }

        if (estadoJogo.equals("SeniorEscolhe")) {
            if (tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == NenufarClaro.class) {
                if (jogadorDaVez.getCorDaFlor().equals("Rosa")) {
                    if (sapo != null) {
                        tabuleiroGerenciador[columnAtPoint][rowAtPoint] = sapo;
                        sapo = null;
                    } else {
                        tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarClaroComFlorRosa();
                    }
                } else {
                    if (sapo != null) {
                        tabuleiroGerenciador[columnAtPoint][rowAtPoint] = sapo;
                        sapo = null;
                    } else {
                        tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarClaroComFlorAmarela();
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
                    tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarClaroComFlorRosa();
                } else {
                    sapo = tabuleiroGerenciador[columnAtPoint][rowAtPoint];
                    tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarClaroComFlorAmarela();
                }
                indiceMensagens = 6;
                for (Observador obs : observadores) {
                    obs.notificarTabuleiroAlterado();
                }
            }
            verificarFloracao(tabuleiroGerenciador);
        }

        if (estadoJogo.equals("JuniorMovePecas")) {
            if (tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == NenufarClaro.class) {
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
            if (tabuleiroGerenciador[columnAtPoint][rowAtPoint].getClass() == NenufarClaro.class) {
                tabuleiroGerenciador[columnAtPoint][rowAtPoint] = new NenufarEscuro();
                indiceMensagens = 0;
                estadoJogo = "EscolherFlores";
                recomecarComJogador1();
                verificarPontuação();
                for (Observador obs : observadores) {
                    obs.notificarTabuleiroAlterado();
                }
            }
        }
        verificarVencedor();

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
        verificarFloracao(tabuleiroGerenciador);
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
            if (tabuleiroGerenciador[posicaoNenufarX - indexParaRealocar][posicaoNenufarY].getClass() != null && tabuleiroGerenciador[posicaoNenufarX - 1][posicaoNenufarY].getClass() != Agua.class) {
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
                    Peca p = tabuleiroGerenciador[posicaoNenufarX - indexParaRealocar + 1][posicaoNenufarY];
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
                    Peca p = tabuleiroGerenciador[posicaoNenufarX + indexParaRealocar - 1][posicaoNenufarY];
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
            if (tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY - indexParaRealocar].getClass() == Agua.class || (posicaoNenufarY - indexParaRealocar) > 0) {
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
            if (tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + indexParaRealocar].getClass() == Agua.class || (posicaoNenufarY + indexParaRealocar) < 4) {
                //Logica do array
                if (nenufaresParaRealocar.size() == 0) { //Caso ache agua de primeira
                    Peca p = tabuleiroGerenciador[posicaoNenufarX][posicaoNenufarY + indexParaRealocar - 1];
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

    private void verificarFloracao(Peca[][] tabuleiro) {
        for (int linha = 0; linha < 5; linha++) {
            for (int coluna = 0; coluna < 5; coluna++) {
                if (tabuleiro[linha][coluna].getClass() == NenufarClaroComFlorAmarela.class
                        || tabuleiro[linha][coluna].getClass() == NenufarClaroComFlorRosa.class
                        || tabuleiro[linha][coluna].getClass() == NenufarEscuroComFlorAmarela.class
                        || tabuleiro[linha][coluna].getClass() == NenufarEscuroComFlorRosa.class) {
                    verificarQuadrado(tabuleiro, linha, coluna);
                    verificarLinhaHori(tabuleiro, linha, coluna);
                    verificarLinhaVert(tabuleiro, linha, coluna);
                    verificarLinhaDiag(tabuleiro, linha, coluna);

                }
            }

        }
    }

    private void verificarQuadrado(Peca[][] tabuleiro, int linha, int coluna) {
        if (tabuleiro[linha][coluna].getClass() == tabuleiro[linha + 1][coluna].getClass()
                || tabuleiro[linha][coluna].getClass() == tabuleiro[linha][coluna + 1].getClass()
                || tabuleiro[linha][coluna].getClass() == tabuleiro[linha + 1][coluna + 1].getClass()) {
            temQuadrado = true;
        }
    }

    private void verificarLinhaHori(Peca[][] tabuleiro, int linha, int coluna) {
        int cont = 0;
        for (int i = coluna; i < 5; i++) {
            if (tabuleiro[linha][i].getClass() == tabuleiro[linha][coluna].getClass()) {
                cont++;
            }
        }
        if (cont == 3) {
            temLinhaHorVer4 = true;
        } else if (cont == 4) {
            temLinha5 = true;
        }
    }

    private void verificarLinhaVert(Peca[][] tabuleiro, int linha, int coluna) {
        int cont = 0;
        for (int i = linha; i < 5; i++) {
            if (tabuleiro[i][coluna].getClass() == tabuleiro[linha][coluna].getClass()) {
                cont++;
            }
        }
        if (cont == 3) {
            temLinhaHorVer4 = true;
        } else if (cont == 4) {
            temLinha5 = true;
        }
    }

    private void verificarLinhaDiag(Peca[][] tabuleiro, int linha, int coluna) {
        // verifica diagonal central direita
        int cont = 0;
        for (int i = 0; i < 5; i++) {
            if (tabuleiro[i][i].getClass() == tabuleiro[linha][coluna].getClass()) {
                cont++;
            }
        }
        if (cont == 3) {
            temLinhaDia4 = true;
        } else if (cont == 4) {
            temLinha5 = true;
        }
        //verifica a diagonal central esquerda
        cont = 0;
        for (int i = 0; i < 5; i++) {
            if (tabuleiro[i][4 - i].getClass() == tabuleiro[linha][coluna].getClass()) {
                cont++;
            }
        }
        if (cont == 3) {
            temLinhaDia4 = true;
        } else if (cont == 4) {
            temLinha5 = true;
        }
        // diagonal direita de baixo
        cont = 0;
        for (int i = 1; i < 5; i++) {
            if (tabuleiro[i][4 - i].getClass() == tabuleiro[linha][coluna].getClass()) {
                cont++;
            }
        }
        if (cont == 3) {
            temLinhaDia4 = true;
        } else if (cont == 4) {
            temLinha5 = true;
        }
        //verifica a diagonal esquerda de baixo
        cont = 0;
        for (int i = 1; i < 5; i++) {
            if (tabuleiro[i - 1][i].getClass() == tabuleiro[linha][coluna].getClass()) {
                cont++;
            }
        }
        if (cont == 3) {
            temLinhaDia4 = true;
        } else if (cont == 4) {
            temLinha5 = true;
        }
        //verifica a diagonal esquerda de  cima
        cont = 0;
        for (int i = 0; i < 4; i++) {
            if (tabuleiro[i][3 - i].getClass() == tabuleiro[linha][coluna].getClass()) {
                cont++;
            }
        }
        if (cont == 3) {
            temLinhaDia4 = true;
        } else if (cont == 4) {
            temLinha5 = true;
        }
        //verifica a diagonal direita de cima
        cont = 0;
        for (int i = 0; i < 4; i++) {
            if (tabuleiro[i][i + 1].getClass() == tabuleiro[linha][coluna].getClass()) {
                cont++;
            }
        }
        if (cont == 3) {
            temLinhaDia4 = true;
        } else if (cont == 4) {
            temLinha5 = true;
        }
    }

    private void verificarPontuação() {
        if (temLinha5 = true) {
            jogadorDaVez.setPontuacao(jogadorDaVez.getPontuacao() + 5);
        } else if (temLinhaDia4 = true) {
            jogadorDaVez.setPontuacao(jogadorDaVez.getPontuacao() + 3);
        } else if (temLinhaHorVer4 = true) {
            jogadorDaVez.setPontuacao(jogadorDaVez.getPontuacao() + 2);
        } else if (temQuadrado = true) {
            jogadorDaVez.setPontuacao(jogadorDaVez.getPontuacao() + 1);
        }

        for (Observador obs : observadores) {
            obs.notificarRodadaEncerado();

        }
    }

    private void verificarVencedor() {
        String vencedor="";
        if (jogador1.getPontuacao() >= 5){ 
            vencedor ="Jogador 1 venceu!!!!";
        }else if( jogador2.getPontuacao() >= 5) {
            vencedor ="Jogador 2 venceu!!!!";
        }
        for (Observador obs : observadores) {
            obs.notificarJogoEncerado(vencedor);

        }
    }

    @Override
    public void novaRodada() {
        
    }

}
