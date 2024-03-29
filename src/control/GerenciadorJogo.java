/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import AbstractFactory.Jogador;
import Observer.Observador;
import State.EstadoJogo;
import java.util.List;
import javax.swing.Icon;
import decorator.flores.Flor;
import composite.Peca;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public interface GerenciadorJogo {
    
    void inicializarTabuleiro(int opcao) throws Exception;
    Flor[][] inicializarFlores(Flor[][] floresJogador,Jogador jogador) throws Exception;
    void addObservador(Observador obs);
    Icon getPeca(int col, int row) throws Exception;
    Icon getFlor(int col, int row) throws Exception;
    public Jogador getJogador1();     
    public void setJogador1(Jogador jogador1);    
    public Jogador getJogador2();  
    public void setJogador2(Jogador jogador2);
    public void selecionarCores();
    public void setCorDasFlores(int corDasFlores);
    public int getCorDasFlores();
    public String getEstadoJogo();
    public void setEstadoJogo(String estadoJogo);
    public Jogador getJogadorDaVez();
    public void setJogadorDaVez(Jogador jogadorDaVez);
    public void escolherFloresDeck(int row, int col);
    public Peca getFlorEscolhidaMao();
    public void setFlorEscolhidaMao(Flor florEscolhidaMao);
    public Flor[][] getFlorDaVez();
    public void setFlorDaVez(Flor[][] florDaVez);
    public String[] getOpcoesDeFlor();
    public String[] getOpcoesDeTabuleiro();
    public List<Flor> getMaoDaVez();
    public void setMaoDaVez(List<Flor> maoDaVez);
    public Icon getFlorMao(int coluna, int linha) throws Exception;
    public String getMensagemAtual();
    public void escolherFlorParaJogar(int index);
    public void setFlorEscolhidaParaMover(int numero);
    public void clicouNoTabuleiro(int rowAtPoint, int columnAtPoint);
    public String[] getOpcoesDeMover();
    public String getMensagemErro();
    public void juniorMovePecas();
    public void novaRodada();
    public void inicializarJogadores();
    public void inicializarPontuacao();
    public Icon getpontuacao(int col) throws Exception;;
    public void atualizarPontuacao() throws Exception;
    public EstadoJogo getEstadojogo();
}
