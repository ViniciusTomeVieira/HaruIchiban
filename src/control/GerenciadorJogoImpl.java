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
import model.NenufarEscuro;
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

    //Flores
    private Flor[][] florDaVez = new Flor[2][4];
    private Flor florEscolhidaMao; // Flor que o jogador vai escolher para jogar no jogo
    private List<Flor> maoDaVez = new ArrayList<>();
    private int corDasFlores = 0;

    //Jogadores
    private Jogador jogador1 = new Jogador();
    private Jogador jogador2 = new Jogador();
    private Jogador jogadorDaVez;
    private int indiceMensagens = 0;
    private String[] mensagens = {"  pegue suas cartas","  escolha uma carta para jogar"," selecione uma carta"," jogue no nenufar escuro"," escolha um nenufar para jogar"," escolha um nenufar para mover", " escolha a direcao a mover"," escolha a nenufar que ficara escura"};

    //Observadores
    private List<Observador> observadores = new ArrayList<>();

    //Estado do jogo
    private String estadoJogo = "SelecionarCor"; // SelecionarCor / EscolherFlores /   JogarFlor    /  JuniorEscuro    /    SeniorEscolhe  /   JuniorMovePeças /   SeniorEscolheEscuro     
    //    Jogador1   / Jogador 1 e 2  / Jogador 1 e 2  /  JogadorJunior   /    Jogador Senior /   JogadorJunior   /   Jogador Senior

    private String[] opcoesDeFlor = {"Rosa", "Amarela"};
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
            case "SelecionarCor": selecionarCores(); break;
            case "EscolherFlores": 
            case "JogarFlor": //jogarFlor(); break;
            case "JuniorEscuro":
            case "SeniorEscolhe":
            case "JuniorMovePeças":
            case "SeniorEscolheEscuro":
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
        
        tabuleiroGerenciador=builder.getTabuleiroCriado();
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
    public Icon getFlorMao(int coluna, int linha) throws Exception{
        if(maoDaVez.get(linha) != null){
            if(coluna > 0){
                return verificarNumeroFlor(maoDaVez.get(linha));
            }else{
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
            Flor cartaEscolhida = florDaVez[col][row]; // Talvez tenha que inverter
            if (maoDaVez.size() < 3 && florDaVez.length > 0) {
                enviarCartaParaMao(cartaEscolhida);
                florDaVez[col][row] = null;
                jogadorDaVez.setMao(maoDaVez);
                jogadorDaVez.setFlores(florDaVez);
            } else {
                trocarJogadorDaVez();
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
        for (Observador obs : observadores) {
            obs.notificarJogadorDaVezAlterado();
        }
    }
    
    private void avancarEstadoJogo(String estadoJogo) {
        switch(estadoJogo){
            case "EscolherFlores": this.estadoJogo = "JogarFlor"; indiceMensagens = 1;
            case "JogarFlor": this.estadoJogo = "JuniorEscuro";           
        }
    }
    
    @Override
    public String getMensagemAtual(){
        return mensagens[indiceMensagens];
    }
    

    public void escolherFlorParaJogar(int index) {
        jogadorDaVez.setFlorEscolhida(maoDaVez.get(index));
        trocarJogadorDaVez();
    }

    public void compararFlores() {
        if (jogador1.getFlorEscolhida().getNumero() < jogador2.getFlorEscolhida().getNumero()) {
            jogador1.setJuniorOuSenior("Junior");
            jogador2.setJuniorOuSenior("Senior");
        } else if (jogador1.getFlorEscolhida().getNumero() > jogador2.getFlorEscolhida().getNumero()) {
            jogador1.setJuniorOuSenior("Senior");
            jogador2.setJuniorOuSenior("Junior");
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
    public String[] getOpcoesDeTabuleiro() {
        return opcoesDeTabuleiro;
    }

    public Peca[][] getTabuleiroGerenciador() {
        return tabuleiroGerenciador;
    }

    public void setTabuleiroGerenciador(Peca[][] tabuleiroGerenciador) {
        this.tabuleiroGerenciador = tabuleiroGerenciador;
    }

   

    

}
