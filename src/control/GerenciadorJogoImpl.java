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
import State.CompararFlores;
import State.EscolherFlores;
import State.EstadoJogo;
import State.JuniorEscuro;
import State.JuniorMovePecas;
import State.SelecionarCor;
import State.SeniorEscolhe;
import State.SeniorEscolheEscuro;
import Strategy.CalcularPontuacao;
import Visitor.MontarImgPontuacao;
import Visitor.VerificaPadrao;
import composite.Objeto;
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
import composite.Peca;
import composite.Tabuleiro;
import decorator.nenufares.Nenufar;
import decorator.nenufares.NenufarBase;
import decorator.nenufares.NenufarClaroComFlorAmarela;
import decorator.nenufares.NenufarClaroComFlorRosa;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public class GerenciadorJogoImpl implements GerenciadorJogo {

    //Tabuleiro
    private Tabuleiro tabuleiro;
    public Peca sapo;
    private int posicaoMover; // 1 = esquerda, 2 = direita, 3 = cima, 4 = baixo
    private int posicaoNenufarX;
    private int posicaoNenufarY;
    private boolean escolheuFlorMover;
    private String mensagemErro;
    private boolean terminouMoverNenufar;
    private boolean sapoInserido = false;
    public Peca sapoAmarelo;
    public Peca sapoRosa;

    //Metodos de mover pecas
    private List<Peca> nenufaresParaRealocar = new ArrayList<>();
    private int indexParaRealocar = 1;

    //Flores
    public Flor[][] florDaVez = new Flor[2][4];
    private Flor florEscolhidaMao; // Flor que o jogador vai escolher para jogar no jogo
    private List<Flor> maoDaVez = new ArrayList<>();
    private int corDasFlores = 0;

    //Pontuação
//    private boolean temQuadrado, temLinhaHorVer4, temLinhaDia4, temLinha5 = false;
//    private List<Peca> formacaoDeFlores = new ArrayList<>();
//    private int indexOfPontuacao;
    private Icon[] pontuacao = new ImageIcon[9];
    //Abstract Factory
    public FabricaJogador fabricaJogador;
    public boolean empate;

    //Jogadores
    public Jogador jogador1;
    public Jogador jogador2;
    private Jogador jogadorDaVez;
    private int indiceMensagens = 0;
    private String[] mensagens = {"  pegue suas cartas", "  escolha uma carta para jogar", " selecione uma carta", " jogue no nenufar escuro", " escolha um nenufar para jogar", " escolha um nenufar para mover", " escolha um nenufar para o sapo", " escolha a direcao a mover", " escolha a nenufar que ficara escura", " jogue sua flor no sapo correspondente", " jogue o sapo em um nenufar claro"};

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
        estadojogo = new SelecionarCor(this);
        fabricaJogador = new FabricaNormal();
        jogador1 = fabricaJogador.criarJogador(jogador1);
        jogador2 = fabricaJogador.criarJogador(jogador2);
    }

    public void setEstadojogo(EstadoJogo estadojogo) {
        this.estadojogo = estadojogo;
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
        tabuleiro = new Tabuleiro();
        DiretorBuilder dr1 = new DiretorBuilder(builder);
        dr1.construir(tabuleiro.getTabuleiro());

        tabuleiro.setTabuleiro(builder.getTabuleiroCriado());
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
        if (tabuleiro.getPecaTabuleiro(coluna, linha) != null) {
            return tabuleiro.getPecaTabuleiro(coluna, linha).getImagem();
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
        estadojogo.selecionarCores();
        estadojogo.proxEstado();
    }

    //Remove a flor que o jogador escolheu de seu deck e adiciona em sua mão
    @Override
    public void escolherFloresDeck(int row, int col) {
        estadojogo.escolherFloresDeck(row, col);
    }

    public void enviarCartaParaMao(Flor cartaEscolhida) {
        maoDaVez.add(cartaEscolhida);
    }

    public void trocarJogadorDaVez() {

        if (empate) {
            if (sapoRosa != null && sapoAmarelo != null) {
                indiceMensagens = 10;
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
                }
            }

        } else {

            if (estadojogo.getClass() == JuniorEscuro.class) {
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
                    if (estadojogo.getClass() == JuniorMovePecas.class || estadojogo.getClass() == SeniorEscolhe.class || estadojogo.getClass() == SeniorEscolheEscuro.class) {
                    } else {
                        estadojogo.proxEstado();
                        if (estadojogo.getClass() == CompararFlores.class) {
                            estadojogo.compararFlores();
                        }
                    }
                }
            }
        }
        for (Observador obs : observadores) {
            obs.notificarJogadorDaVezAlterado();
        }
    }

    public void setIndiceMensagens(int indiceMensagens) {
        this.indiceMensagens = indiceMensagens;
    }

    public List<Observador> getObservadores() {
        return observadores;
    }

    public void setObservadores(List<Observador> observadores) {
        this.observadores = observadores;
    }

    @Override
    public String getMensagemAtual() {
        return mensagens[indiceMensagens];
    }

    @Override
    public void escolherFlorParaJogar(int index) {
        estadojogo.escolherFlorParaJogar(index);
    }

    public FabricaJogador getFabricaJogador() {
        return fabricaJogador;
    }

    @Override
    public EstadoJogo getEstadojogo() {
        return estadojogo;
    }

    public void setPosicaoNenufarX(int posicaoNenufarX) {
        this.posicaoNenufarX = posicaoNenufarX;
    }

    public void setPosicaoNenufarY(int posicaoNenufarY) {
        this.posicaoNenufarY = posicaoNenufarY;
    }

    public void setEscolheuFlorMover(boolean escolheuFlorMover) {
        this.escolheuFlorMover = escolheuFlorMover;
    }

    public void setSapoInserido(boolean sapoInserido) {
        this.sapoInserido = sapoInserido;
    }

    public boolean isSapoInserido() {
        return sapoInserido;
    }

    @Override
    public void clicouNoTabuleiro(int rowAtPoint, int columnAtPoint) {
        if (!empate) {
            estadojogo.juniorEscuro(columnAtPoint, rowAtPoint);
            estadojogo.seniorEscolhe(columnAtPoint, rowAtPoint);
            estadojogo.juniorMovePecas(columnAtPoint, rowAtPoint);
            estadojogo.seniorEscolheEscuro(columnAtPoint, rowAtPoint);
        } else {
            empate(columnAtPoint, rowAtPoint);
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
        selecionarCores();
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

    @Override
    public String[] getOpcoesDeMover() {
        return opcoesDeMover;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    //Mover nenufares
    private void moverNenufarEsquerda() throws ArrayIndexOutOfBoundsException {

        try {
            if (tabuleiro.getPecaTabuleiro(posicaoNenufarX - indexParaRealocar, posicaoNenufarY).getClass() != null && tabuleiro.getPecaTabuleiro(posicaoNenufarX - indexParaRealocar, posicaoNenufarY).getClass() != Agua.class) {
                nenufaresParaRealocar.add(tabuleiro.getPecaTabuleiro(posicaoNenufarX - indexParaRealocar, posicaoNenufarY));
                System.out.println(tabuleiro.getPecaTabuleiro(posicaoNenufarX - indexParaRealocar, posicaoNenufarY));
                indexParaRealocar++;
                System.out.println(indexParaRealocar);
                moverNenufarEsquerda();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            indexParaRealocar--;
        }
        if (!terminouMoverNenufar) {
            if (tabuleiro.getPecaTabuleiro(posicaoNenufarX - indexParaRealocar, posicaoNenufarY).getClass() == Agua.class || (posicaoNenufarX - indexParaRealocar) < 0) {
                //Logica do array
                if (nenufaresParaRealocar.isEmpty()) { //Caso ache agua de primeira
                    Peca p = tabuleiro.getPecaTabuleiro(posicaoNenufarX - indexParaRealocar, posicaoNenufarY);
                    tabuleiro.setPecaTabuleiro(posicaoNenufarX - indexParaRealocar, posicaoNenufarY, tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY));
                    tabuleiro.setPecaTabuleiro(posicaoNenufarX, posicaoNenufarY, p);
                    terminouMoverNenufar();
                } else { //Faz a troca de todas as posicoes
                    for (int i = indexParaRealocar; i > 0; i--) {
                        Peca p = tabuleiro.getPecaTabuleiro(posicaoNenufarX - i, posicaoNenufarY); // Comeca com Agua
                        tabuleiro.setPecaTabuleiro(posicaoNenufarX - i, posicaoNenufarY, tabuleiro.getPecaTabuleiro(posicaoNenufarX - i + 1, posicaoNenufarY));
                        tabuleiro.setPecaTabuleiro(posicaoNenufarX - i + 1, posicaoNenufarY, p);
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
            if (tabuleiro.getPecaTabuleiro(posicaoNenufarX + indexParaRealocar, posicaoNenufarY).getClass() != null && tabuleiro.getPecaTabuleiro(posicaoNenufarX + indexParaRealocar, posicaoNenufarY).getClass() != Agua.class) {
                nenufaresParaRealocar.add(tabuleiro.getPecaTabuleiro(posicaoNenufarX + indexParaRealocar, posicaoNenufarY));
                System.out.println(tabuleiro.getPecaTabuleiro(posicaoNenufarX + indexParaRealocar, posicaoNenufarY));
                indexParaRealocar++;
                System.out.println(indexParaRealocar);
                moverNenufarDireita();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            indexParaRealocar--;
        }
        if (!terminouMoverNenufar) {
            if (tabuleiro.getPecaTabuleiro(posicaoNenufarX + indexParaRealocar, posicaoNenufarY).getClass() == Agua.class || (posicaoNenufarX + indexParaRealocar) > 4) {
                //Logica do array
                if (nenufaresParaRealocar.isEmpty()) { //Caso ache agua de primeira
                    Peca p = tabuleiro.getPecaTabuleiro(posicaoNenufarX + indexParaRealocar, posicaoNenufarY);
                    tabuleiro.setPecaTabuleiro(posicaoNenufarX + indexParaRealocar, posicaoNenufarY, tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY));
                    tabuleiro.setPecaTabuleiro(posicaoNenufarX, posicaoNenufarY, p);
                    terminouMoverNenufar();
                } else { //Faz a troca de todas as posicoes
                    for (int i = indexParaRealocar; i > 0; i--) {
                        Peca p = tabuleiro.getPecaTabuleiro(posicaoNenufarX + i, posicaoNenufarY);// Comeca com Agua
                        tabuleiro.setPecaTabuleiro(posicaoNenufarX + i, posicaoNenufarY, tabuleiro.getPecaTabuleiro(posicaoNenufarX + i - 1, posicaoNenufarY));
                        tabuleiro.setPecaTabuleiro(posicaoNenufarX + i - 1, posicaoNenufarY, p);
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
            if (tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY - indexParaRealocar).getClass() != null && tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY - indexParaRealocar).getClass() != Agua.class) {
                nenufaresParaRealocar.add(tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY - indexParaRealocar));
                System.out.println(tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY - indexParaRealocar));
                indexParaRealocar++;
                System.out.println(indexParaRealocar);
                moverNenufarCima();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            indexParaRealocar--;
        }
        if (!terminouMoverNenufar) {
            if (tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY - indexParaRealocar).getClass() == Agua.class || (posicaoNenufarY - indexParaRealocar) < 0) {
                //Logica do array
                if (nenufaresParaRealocar.isEmpty()) { //Caso ache agua de primeira
                    Peca p = tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY - indexParaRealocar);
                    tabuleiro.setPecaTabuleiro(posicaoNenufarX, posicaoNenufarY - indexParaRealocar, tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY));
                    tabuleiro.setPecaTabuleiro(posicaoNenufarX, posicaoNenufarY, p);
                    terminouMoverNenufar();
                } else { //Faz a troca de todas as posicoes
                    for (int i = indexParaRealocar; i > 0; i--) {
                        Peca p = tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY - i); // Comeca com Agua
                        tabuleiro.setPecaTabuleiro(posicaoNenufarX, posicaoNenufarY - i, tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY - i + 1));
                        tabuleiro.setPecaTabuleiro(posicaoNenufarX, posicaoNenufarY - i + 1, p);
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
            if (tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY + indexParaRealocar).getClass() != null && tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY + indexParaRealocar).getClass() != Agua.class) {
                nenufaresParaRealocar.add(tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY + indexParaRealocar));
                System.out.println(tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY + indexParaRealocar));
                indexParaRealocar++;
                System.out.println(indexParaRealocar);
                moverNenufarBaixo();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            indexParaRealocar--;
        }
        if (!terminouMoverNenufar) {
            if (tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY + indexParaRealocar).getClass() == Agua.class || (posicaoNenufarY + indexParaRealocar) > 4) {
                //Logica do array
                if (nenufaresParaRealocar.isEmpty()) { //Caso ache agua de primeira
                    Peca p = tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY + indexParaRealocar);
                    tabuleiro.setPecaTabuleiro(posicaoNenufarX, posicaoNenufarY + indexParaRealocar, tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY));
                    tabuleiro.setPecaTabuleiro(posicaoNenufarX, posicaoNenufarY, p);
                    terminouMoverNenufar();
                } else { //Faz a troca de todas as posicoes
                    for (int i = indexParaRealocar; i > 0; i--) {
                        Peca p = tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY + i); // Comeca com Agua
                        tabuleiro.setPecaTabuleiro(posicaoNenufarX, posicaoNenufarY + i, tabuleiro.getPecaTabuleiro(posicaoNenufarX, posicaoNenufarY + i - 1));
                        tabuleiro.setPecaTabuleiro(posicaoNenufarX, posicaoNenufarY + i - 1, p);
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
        estadojogo.proxEstado();
        trocarJogadorDaVez();
        for (Observador obs : observadores) {
            obs.notificarTabuleiroAlterado();
        }

    }

    public void recomecarComJogador1() {
        jogadorDaVez = jogador1;
        maoDaVez = jogador1.getMao();
        florDaVez = jogador1.getFlores();
        indexParaRealocar = 1;
        terminouMoverNenufar = false;
        for (Observador obs : observadores) {
            obs.notificarJogadorDaVezAlterado();
        }
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
//    public void verificarPontuação() throws Exception {
////        System.out.println("1: " + temQuadrado + ", 2: " + temLinhaHorVer4 + ", 3:" + temLinhaDia4 + "5: " + temLinha5);
////        if (temLinha5 == true) {
////            jogadorDaVez.setPontuacao(jogadorDaVez.getPontuacao() + 5);
////        } else if (temLinhaDia4 == true) {
////            jogadorDaVez.setPontuacao(jogadorDaVez.getPontuacao() + 3);
////        } else if (temLinhaHorVer4 == true) {
////            jogadorDaVez.setPontuacao(jogadorDaVez.getPontuacao() + 2);
////        } else if (temQuadrado == true) {
////            jogadorDaVez.setPontuacao(jogadorDaVez.getPontuacao() + 1);
////        }
////        if (temLinhaHorVer4 == true || temQuadrado == true || temLinhaDia4 == true || temLinha5 == true) {
////            System.out.println("entrou observer");
////            for (Observador obs : observadores) {
////                obs.notificarRodadaEncerado();
////
////            }
////        }
//        VerificaPadrao v1 = new VerificaPadrao();
//        tabuleiro.accept(v1);
//        v1.getPontuacao();
//        System.out.println("entrou observer");
//        for (Observador obs : observadores) {
//            obs.notificarRodadaEncerado();
//
//        }
//
//    }
    public void verificarVencedor() {
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
        ImageIcon imagemInserir;
        if (vencedor.equals("Jogador 1 venceu!!!!")) {
            if (jogador1.getCorDaFlor().equals("Rosa")) {
                imagemInserir = new ImageIcon("imagens/NenufarClaroComFlorRosa");
            } else {
                imagemInserir = new ImageIcon("imagens/NenufarClaroComFlorAmarela");
            }
        } else {
            if (jogador2.getCorDaFlor().equals("Rosa")) {
                imagemInserir = new ImageIcon("imagens/NenufarClaroComFlorRosa");
            } else {
                imagemInserir = new ImageIcon("imagens/NenufarClaroComFlorAmarela");
            }
        }

        for (Objeto obj : tabuleiro.getPecas()) {
            obj.setImagem(imagemInserir);
        }
        
        tabuleiro.organizar();
        
        for (Observador obs : observadores) {
            obs.notificarTabuleiroAlterado();
        }

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
                tabuleiro.setPecaTabuleiro(j, i, null);
            }
        }
        recomecarComJogador1();
        try {
            inicializarTabuleiro(0); // Arrumar
        } catch (Exception ex) {
            Logger.getLogger(GerenciadorJogoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int verificarTamanhoDeck() {
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

    @Override
    public void inicializarPontuacao() {
        for (int i = 0; i < 9; i++) {
            pontuacao[i] = new ImageIcon("Imagens/" + (i + 1) + "base.jpg");
        }
    }

    @Override
    public Icon getpontuacao(int col) throws Exception {

        if (pontuacao[col] != null) {
            return pontuacao[col];
        }
        return null;
    }

    @Override
    public void atualizarPontuacao() throws Exception {
        VerificaPadrao v1 = new VerificaPadrao();
        tabuleiro.accept(v1);
        if (jogador1.getCorDaFlor().equals("Rosa")) {
            jogador1.setPontuacao(jogador1.getPontuacao() + v1.getPontuacaoRosa());
        } else {
            jogador1.setPontuacao(jogador1.getPontuacao() + v1.getPontuacaoAmarelo());
        }
        if (jogador2.getCorDaFlor().equals("Amarela")) {
            jogador2.setPontuacao(jogador2.getPontuacao() + v1.getPontuacaoAmarelo());
        } else {
            jogador2.setPontuacao(jogador2.getPontuacao() + v1.getPontuacaoRosa());
        }
        System.out.println(jogador1.getPontuacao());
        System.out.println(jogador2.getPontuacao());
        MontarImgPontuacao montar = new MontarImgPontuacao();
        jogador1.accept(montar);
        pontuacao[jogador1.getPontuacao()] = montar.getPontuacao() != null ? montar.getPontuacao() : pontuacao[jogador1.getPontuacao()];

        jogador2.accept(montar);
        pontuacao[pontuacao.length - jogador2.getPontuacao() - 1] = montar.getPontuacao() != null ? montar.getPontuacao() : pontuacao[pontuacao.length - jogador2.getPontuacao() - 1];
    }

    private void empate(int columnAtPoint, int rowAtPoint) {
        Peca peca = getTabuleiro().getPecaTabuleiro(columnAtPoint, rowAtPoint);
        Nenufar nenufarBase = new NenufarBase();
        if (jogadorDaVez.getCorDaFlor().equals("Rosa")) {
            if (peca.getNome().equals("SapoRosa")) {
                NenufarClaroComFlorRosa nccfr = new NenufarClaroComFlorRosa(nenufarBase);
                nccfr.selecionarImageNenufar();
                getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint, nenufarBase);
                jogadorDaVez.setFlorEscolhida(null);
                sapoRosa = peca;
                trocarJogadorDaVez();
            } else {
                if (peca.getNome().equals("NenufarClaro") && jogadorDaVez.getFlorEscolhida() == null) {
                    if (sapoRosa != null) {
                        getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint, sapoRosa);
                        sapoRosa = null;
                    } else {
                        getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint, sapoAmarelo);
                        sapoAmarelo = null;
                        estadojogo = new EscolherFlores(this);
                        indiceMensagens = 0;
                        empate = false;
                        recomecarComJogador1();
                    }
                }
            }
        } else {
            if (peca.getNome().equals("SapoAmarelo")) {
                NenufarClaroComFlorAmarela nccfa = new NenufarClaroComFlorAmarela(nenufarBase);
                nccfa.selecionarImageNenufar();
                getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint, nenufarBase);
                jogadorDaVez.setFlorEscolhida(null);
                sapoAmarelo = peca;
                trocarJogadorDaVez();

            } else {
                if (peca.getNome().equals("NenufarClaro") && jogadorDaVez.getClass() == JogadorJunior.class) {
                    if (sapoRosa != null) {
                        getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint, sapoRosa);
                        sapoRosa = null;
                    } else {
                        if (sapoAmarelo != null) {
                            getTabuleiro().setPecaTabuleiro(columnAtPoint, rowAtPoint, sapoAmarelo);
                            sapoAmarelo = null;
                            estadojogo = new EscolherFlores(this);
                            indiceMensagens = 0;
                            empate = false;
                            recomecarComJogador1();
                        }
                    }
                }
            }
        }

    }

}
