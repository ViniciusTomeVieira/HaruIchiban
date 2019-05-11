/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Observer.Observador;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Icon;
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

/**
 *
 * @author vinny
 */
public class GerenciadorJogoImpl implements GerenciadorJogo {
    
    //Tabuleiro
    private Peca[][] tabuleiro;          
    
    //Flores
    private Flor[][] florDaVez = new Flor[2][4];
    private Flor florEscolhidaMao; // Flor que o jogador vai escolher para jogar no jogo
    private List<Flor> maoDaVez = new ArrayList<>();
    private int corDasFlores = 0;
    
    //Jogadores
    private Jogador jogador1 = new Jogador();
    private Jogador jogador2 = new Jogador();
    private Jogador jogadorDaVez;
    
    //Observadores
    private List<Observador> observadores = new ArrayList<>();
    
    //Estado do jogo
    private String estadoJogo = "SelecionarCor"; // SelecionarCor / EscolherFlores /   JogarFlor    /  JuniorEscuro    /    SeniorEscolhe  /   JuniorMovePeças /   SeniorEscolheEscuro     
                                                //    Jogador1   / Jogador 1 e 2  / Jogador 1 e 2  /  JogadorJunior   /    Jogador Senior /   JogadorJunior   /   Jogador Senior

    private String[] opcoes = {"Rosa","Amarela"};
     
    private GerenciadorJogoImpl() {}
    
    private static GerenciadorJogoImpl instance;
    
    public synchronized static GerenciadorJogoImpl getInstance(){
        if(instance==null)
            instance = new GerenciadorJogoImpl();
        
        return instance;
    }
    
    @Override
    public void fluxoJogo() {
        switch(estadoJogo){
            case "SelecionarCor": selecionarCores();
            case "EscolherFlores": //escolherFloresIniciais();
            case "JogarFlor": //jogarFlor();
            case "JuniorEscuro": 
            case "SeniorEscolhe":
            case "JuniorMovePeças":
            case "SeniorEscolheEscuro":
        }
    }

    
    
    
    
    @Override
    public void inicializarTabuleiro() throws Exception { // Vai ser alterado
        tabuleiro = new Peca[5][5];
		tabuleiro[0][0] = new NenufarClaro();
		tabuleiro[0][1] = new Agua();
		tabuleiro[0][2] = new NenufarClaro();
		tabuleiro[0][3] = new Agua();
		tabuleiro[0][4] = new NenufarClaro();
		
		tabuleiro[1][0] = new Agua();
                tabuleiro[1][1] = new SapoRosa();
		tabuleiro[1][2] = new NenufarClaro();
		tabuleiro[1][3] = new NenufarEscuro(); // Aleatorio?
		tabuleiro[1][4] = new Agua();
		

		tabuleiro[2][0] = new NenufarClaro();
		tabuleiro[2][1] = new NenufarClaro();
		tabuleiro[2][2] = new Agua();
		tabuleiro[2][3] = new SapoAmarelo();
		tabuleiro[2][4] = new NenufarClaro();
                
                tabuleiro[3][0] = new Agua();
		tabuleiro[3][1] = new NenufarClaro();
		tabuleiro[3][2] = new NenufarClaro();
		tabuleiro[3][3] = new NenufarClaro();
		tabuleiro[3][4] = new Agua();
                
                tabuleiro[4][0] = new NenufarClaro();
		tabuleiro[4][1] = new Agua();
		tabuleiro[4][2] = new NenufarClaro();
		tabuleiro[4][3] = new Agua();
		tabuleiro[4][4] = new NenufarClaro();
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
                        if(jogador.getCorDaFlor().equals("Rosa")){
                            floresJogador[i][j] = new FlorRosa(numero + 1);
                            }else{
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
        if(tabuleiro[coluna][linha] != null){
            return tabuleiro[coluna][linha].getImagem();
        }
        return null;
    }

    //Mostra as flores do deck
    @Override
    public Icon getFlor(int coluna, int linha) throws Exception { //Pronto
        if(florDaVez[coluna][linha] != null){
            return florDaVez[coluna][linha].getImagem();
        }
        return null;
    }


    @Override
    public void selecionarCores() {
        switch(corDasFlores){
            case 0: jogador1.setCorDaFlor("Rosa"); jogador2.setCorDaFlor("Amarela"); break;
            case 1: jogador1.setCorDaFlor("Amarela"); jogador2.setCorDaFlor("Rosa"); break;
            default: jogador1.setCorDaFlor("Rosa"); jogador2.setCorDaFlor("Amarela");
        }       
        System.out.println("Cor da flor jogador 1: " + jogador1.getCorDaFlor());
        System.out.println("Cor da flor jogador 2: " + jogador2.getCorDaFlor());
        estadoJogo = "EscolherFlores";
        
        // Chamar o observer e notificar a view para mostrar a borda e atualizar o texto de dica
    }
    
    
    
    
    //Remove a flor que o jogador escolheu de seu deck e adiciona em sua mão
    
    @Override
    public void escolherFloresDeck(int row, int col) {
         Flor cartaEscolhida = florDaVez[col][row]; // Talvez tenha que inverter
         if(maoDaVez.size() < 3 && florDaVez.length > 0){
             enviarCartaParaMao(cartaEscolhida);
             florDaVez[col][row] = null;
             jogadorDaVez.setMao(maoDaVez);
             jogadorDaVez.setFlores(florDaVez);
         }else{
             trocarJogadorDaVez();
         }
         
         for(Observador obs: observadores){
             obs.notificarFlorEscolhida();
         }
    }
    
    private void enviarCartaParaMao(Flor cartaEscolhida) {
        maoDaVez.add(cartaEscolhida);
    }
    
     private void trocarJogadorDaVez() {
         if(jogadorDaVez.getNome().equals("Jogador 1")){
             jogadorDaVez = jogador2;
         }else{
             jogadorDaVez = jogador1;
         }
         for(Observador obs: observadores){
             obs.notificarJogadorDaVezAlterado();
         }
    }
     
     
     
     public void escolherFlorParaJogar(int index){
         jogadorDaVez.setFlorEscolhida(maoDaVez.get(index));
         trocarJogadorDaVez();
     }
     
     public void compararFlores(){
         if(jogador1.getFlorEscolhida().getNumero() < jogador2.getFlorEscolhida().getNumero()){
             jogador1.setJuniorOuSenior("Junior");
             jogador2.setJuniorOuSenior("Senior");
         }else if(jogador1.getFlorEscolhida().getNumero() > jogador2.getFlorEscolhida().getNumero()){
             jogador1.setJuniorOuSenior("Senior");
             jogador2.setJuniorOuSenior("Junior");
         }else{ //Empate
             Random random = new Random();
             int numero = random.nextInt(2);
             if(numero == 0){
                 jogador1.setJuniorOuSenior("Junior");
                 jogador2.setJuniorOuSenior("Senior");
             }else{
                 jogador1.setJuniorOuSenior("Senior");
                 jogador2.setJuniorOuSenior("Junior");
             }
         }
         for(Observador obs: observadores){
             obs.notificarJuniorSenior();
         }
         
     }
     
     public Jogador verificarJunior(){
         if(jogador1.getJuniorOuSenior().equals("Junior")){
             return jogador1;
         }else{
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
    public String[] getOpcoes() {
        return opcoes;
    }

    @Override
    public void setOpcoes(String[] opcoes) {
        this.opcoes = opcoes;
    }

   

    
    

    
    
}
